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
package org.xwiki.contrib.rest.internal;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.contrib.rest.RestExceptionMapper;

/**
 * @version $Id: $
 */
@Component
@Singleton
@Named("ResourceNotFoundExceptionHandler")
@Provider
public class ResourceNotFoundExceptionMapper implements RestExceptionMapper, ExceptionMapper<NotFoundException>,
        Initializable
{
    private String errorMessage;

    @Override
    public void initialize() throws InitializationException
    {
        try {
            errorMessage = IOUtils.toString(getClass().getResourceAsStream("/404.html"));
        } catch (IOException e) {
            throw new InitializationException("Failed to initilize the ResourceNotFoundExceptionMapper.", e);
        }
    }

    @Override
    public Response toResponse(NotFoundException exception)
    {
        return Response.status(Response.Status.NOT_FOUND)
                .header("Content-Type", "text/html").entity(errorMessage).build();
    }


}
