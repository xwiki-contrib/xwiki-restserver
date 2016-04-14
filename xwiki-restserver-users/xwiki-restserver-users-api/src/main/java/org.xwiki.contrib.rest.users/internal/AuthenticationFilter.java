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
@Named("Authentication")
public class AuthenticationFilter implements RestFilter
{
    private static final String AUTHORIZATION_PROPERTY = "Authorization";

    private static final String AUTHENTICATION_SCHEME = "Basic";

    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource\n", 401,
            new Headers<>());

    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR\n", 500,
            new Headers<>());

    @Inject
    private RestUserManager restUserManager;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException
    {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) containerRequestContext.getProperty(
                "org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();

        if (method.isAnnotationPresent(Restricted.class)) {
            //Get request headers
            MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();

            //Fetch authorization header
            List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
            if (authorization == null || authorization.isEmpty()) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //Get encoded username and password
            String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword;
            try {
                usernameAndPassword = new String(Base64.decode(encodedUserPassword));
            } catch (IOException e) {
                containerRequestContext.abortWith(SERVER_ERROR);
                return;
            }

            //Split username and password tokens
            StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            RestUser user = restUserManager.getUser(username);
            if (user == null || !user.isPasswordValid(password)) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            // Is the user in the correct group?
            Restricted annotation = method.getAnnotation(Restricted.class);
            String[] groups = annotation.groups();
            boolean found = false;
            for (int i = 0; i < groups.length; ++i) {
                if (user.isInGroup(groups[i])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                containerRequestContext.abortWith(ACCESS_DENIED);
            }
        }
    }
}
