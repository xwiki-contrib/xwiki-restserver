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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.contrib.rest.users.User;
import org.xwiki.contrib.rest.users.UsersConfiguration;

/**
 * @version $Id: $
 */
public class DefaultUsersConfiguration implements UsersConfiguration, Initializable
{
    private Map<String, User> users = new HashMap<>();

    @Override
    public void initialize() throws InitializationException
    {
        /*
        try {
            InputStream is = new FileInputStream("../users.cfg");
            LineIterator iterator = IOUtils.lineIterator(is, "UTF-8");
            while (iterator.hasNext()) {
                StringTokenizer tokenizer = new StringTokenizer(iterator.nextLine(), ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                users.add(new User(username, password));
            }
        } catch (IOException e) {
            throw new InitializationException("Error", e);
        }*/
        User user1 = new User("username", "password");
        user1.addGroup("admin");
        users.put(user1.getUsername(), user1);

        User user2 = new User("username2", "password2");
        user2.addGroup("simple");
        users.put(user2.getUsername(), user2);
    }

    @Override
    public Collection<User> getUsers()
    {
        return users.values();
    }

    @Override
    public User getUser(String username)
    {
        return users.get(username);
    }
}
