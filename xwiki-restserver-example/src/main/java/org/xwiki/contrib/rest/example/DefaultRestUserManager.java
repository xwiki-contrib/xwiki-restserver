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

import java.util.Arrays;
import java.util.Collection;

import org.xwiki.contrib.rest.users.RestUser;
import org.xwiki.contrib.rest.users.RestUserManager;

/**
 * Demo implementation of the {@link RestUserManager} component.
 *
 * @version $Id: $
 */
public class DefaultRestUserManager implements RestUserManager
{
    private RestUser defaultRestUser = new RestUser()
    {
        private Collection<String> groups = Arrays.asList("admin", "other");

        @Override
        public boolean isPasswordValid(String password)
        {
            // Here you can add some smart security like password hashing (with salt), etc...
            return "abcd".equals(password);
        }

        @Override
        public boolean isInGroup(String groupId)
        {
            return groups.contains(groupId);
        }
    };

    @Override
    public RestUser getUser(String username)
    {
        return "myUser".equals(username) ? defaultRestUser : null;
    }
}
