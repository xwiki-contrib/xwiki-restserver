/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.contrib.rest.users.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.contrib.rest.RestFilter;
import org.xwiki.contrib.rest.users.Restricted;
import org.xwiki.contrib.rest.users.RestUser;
import org.xwiki.contrib.rest.users.RestUserManager;

/**
 * Add the username and the password get from the Basic Auth mechanism and put it in the headers.
 *
 * @version $Id: $
 */
@Component
@Singleton
@Named("BasicAuthentication")
public class BasicAuthenticationFilter implements RestFilter, Initializable
{
    private static final String AUTHORIZATION_PROPERTY = "Authorization";

    private static final String AUTHENTICATION_SCHEME = "Basic";

    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource\n", 401,
            new Headers<>());

    @Inject
    private RestUserManager restUserManager;

    @Override
    public void initialize() throws InitializationException
    {
        ACCESS_DENIED.getHeaders().putSingle("WWW-Authenticate", "Basic realm=\"XWikiRestServer\"");
        ACCESS_DENIED.getHeaders().putSingle("Content-Type", "text/plain");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(
                "org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        // If the method has the Restricted annotation, we might abort the request
        if (method.isAnnotationPresent(Restricted.class)) {
            RestUser user = getUser(requestContext);
            if (user == null || !isUserInRestrictedGroup(user, method)) {
                requestContext.abortWith(ACCESS_DENIED);
            }
        }
    }

    /**
     * Get the user from the HTTP header.
     *
     * @param requestContext request context.
     * @return the detected user, or null if the authentication has failed
     */
    private RestUser getUser(ContainerRequestContext requestContext)
    {
        try {
            // Get request headers
            MultivaluedMap<String, String> headers = requestContext.getHeaders();

            // Fetch authorization header
            List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            if (authorization == null || authorization.isEmpty()) {
                return null;
            }

            // Get encoded username and password
            String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            // Decode username and password
            String usernameAndPassword = new String(Base64.decode(encodedUserPassword));

            // Split username and password tokens
            StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            // Get the user from the user manager
            RestUser user = restUserManager.getUser(username);

            // Return the user if the given password is valid
            return user != null && user.isPasswordValid(password) ? user : null;

        } catch (IOException | NoSuchElementException e) {
            return null;
        }
    }

    private boolean isUserInRestrictedGroup(RestUser user, Method method)
    {
        Restricted annotation = method.getAnnotation(Restricted.class);
        String[] groups = annotation.groups();
        for (int i = 0; i < groups.length; ++i) {
            if (user.isInGroup(groups[i])) {
                return true;
            }
        }
        return false;
    }

}
