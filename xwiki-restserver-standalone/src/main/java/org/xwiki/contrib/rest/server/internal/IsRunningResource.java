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
package org.xwiki.contrib.rest.server.internal;

import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.rest.RestResource;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Basic resource used to know is the application is running and ready.
 *
 * @version $Id: $
 */
@Component
@Singleton
@Named(IsRunningResource.PATH)
@Path(IsRunningResource.PATH)
public class IsRunningResource implements RestResource
{
    /**
     * Path of the resource.
     */
    public static final String PATH = "isrunning";

    /**
     * @return "OK", a plain text message to prove the server is running
     */
    @GET
    @Produces("text/plain")
    public String get()
    {
        return "OK";
    }
}
