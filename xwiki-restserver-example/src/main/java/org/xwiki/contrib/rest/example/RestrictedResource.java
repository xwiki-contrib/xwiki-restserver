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
package org.xwiki.contrib.rest.example;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jackson.Formatted;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.rest.RestResource;
import org.xwiki.contrib.rest.users.Restricted;

/**
 * Example of resource restricted to a given group.
 *
 * @version $Id: $
 * @since 1.0
 */
@Path(RestrictedResource.PATH)
@Component
@Singleton
@Named(RestrictedResource.PATH)
public class RestrictedResource implements RestResource
{
    /**
     * Path of the REST resource.
     */
    public static final String PATH = "/restricted";

    /**
     * @return a JSON-serialized object if the user of the request is a member of the "admin" group. User will get a 401
     * error otherwise.
     */
    @GET
    @Produces("application/json")
    @Formatted
    @Restricted(groups = {"admin"})
    public POJO getPOJO()
    {
        POJO object = new POJO("Restricted Resource", 42);
        return object;
    }
}
