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

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jackson.Formatted;
import org.xwiki.component.annotation.Component;
import org.xwiki.contrib.rest.RestResource;

/**
 * Example of resource.
 *
 * @version $Id: $
 * @since 1.0
 */
@Path("/hello")
@Component
@Singleton
public class HelloWorldResource implements RestResource
{
    @GET
    @Produces("application/json")
    @Formatted
    public HelloWorld getHelloWorld()
    {
        HelloWorld object = new HelloWorld("Hello World!", 1);
        object.addMessage("Message 1");
        object.addMessage("Message 2");
        object.addMessage("Message 3");
        return object;
    }
}
