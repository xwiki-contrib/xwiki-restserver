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

import java.util.ArrayList;
import java.util.List;

/**
 * Plain Old Java Object (POJO) holding the "Hello World" messages. Does not have any interest except to show an example
 * of how an object can be serialized with the XWiki Rest Server Application.
 *
 * @version $Id: $
 * @since 1.0
 */
public class POJO
{
    private String message;

    private int version;

    private List<String> otherMessages;

    /**
     * Build a POJO with a message and a version.
     * @param message a message
     * @param version a number
     */
    public POJO(String message, int version)
    {
        this.message = message;
        this.version = version;
        this.otherMessages = new ArrayList<>();
    }

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @return the version
     */
    public int getVersion()
    {
        return version;
    }

    /**
     * @return other messages
     */
    public List<String> getOtherMessages()
    {
        return otherMessages;
    }

    /**
     * @param message message to add in the "other messages" list
     */
    public void addMessage(String message)
    {
        otherMessages.add(message);
    }
}
