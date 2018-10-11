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
package org.xwiki.contrib.rest.users;

import java.util.Map;

import org.xwiki.component.annotation.Role;
import org.xwiki.contrib.rest.XWikiRestServerException;
import org.xwiki.contrib.rest.users.internal.XMLRestUser;

/**
 * Provide a configuration that load all users and the password salt in an xml file.
 *
 * @version $Id: $
 */
@Role
public interface XMLRestUserConfiguration
{
    /**
     * @return a map of users, where the user's name is the key
     */
    Map<String, XMLRestUser> getUsers();

    /**
     * Reload the configuration, in case he XML file has been modified during the execution of the server.
     * @throws XWikiRestServerException if an error happens
     */
    void reload() throws XWikiRestServerException;

    /**
     * @return the salt use to hash the password. Should be improved so that each user has a unique salt.
     */
    String getPasswordSalt();
}
