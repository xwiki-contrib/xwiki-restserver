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

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.contrib.rest.XWikiRestServerException;
import org.xwiki.contrib.rest.users.RestUser;
import org.xwiki.contrib.rest.users.XMLRestUserConfiguration;

/**
 * A user loaded from a XML file.
 *
 * @version $Id: $
 */
@Component
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
@Named("xml")
public class XMLRestUser implements RestUser
{
    @Inject
    private Logger logger;

    @Inject
    private Provider<XMLRestUserConfiguration> configurationProvider;

    private String name;

    private String hashedPassword;

    private Set<String> groups = new HashSet<>();

    /**
     * @param name the name of the user
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param hashedPassword the password of the user, hashed with SHA512 and a salt (could be improved)
     */
    public void setHashedPassword(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }

    /**
     * @return the hashed password
     */
    public String getHashedPassword()
    {
        return hashedPassword;
    }

    /**
     * @return the name of the user
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param group add a group this user belongs to
     */
    public void addGroup(String group)
    {
        groups.add(group);
    }

    @Override
    public boolean isPasswordValid(String s)
    {
        try {
            return StringUtils.equalsIgnoreCase(
                    SHA512.hash(s, configurationProvider.get().getPasswordSalt()), hashedPassword);
        } catch (XWikiRestServerException e) {
            logger.error("Failed to compute the validity of the user password.", e);
            return false;
        }
    }

    @Override
    public boolean isInGroup(String s)
    {
        return groups.contains(s);
    }
}
