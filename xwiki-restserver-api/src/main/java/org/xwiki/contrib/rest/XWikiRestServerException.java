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
package org.xwiki.contrib.rest;

/**
 * Exception thrown by the XWiki Rest Server.
 *
 * @version $Id: $
 */
public class XWikiRestServerException extends Exception
{
    /**
     * Construct an empty exception with nothing but an error message.
     * @param message A message explaining the problem.
     */
    public XWikiRestServerException(String message)
    {
        super(message);
    }

    /**
     * Wrap an existing Throwable to a XWikiRestServerException and add a new error message.
     * @param message A message explaining the problem.
     * @param cause the Throwable object to wrap
     */
    public XWikiRestServerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Wrap an existing Throwable to a XWikiRestServerException.
     * @param cause the Throwable object to wrap
     */
    public XWikiRestServerException(Throwable cause)
    {
        super(cause);
    }
}
