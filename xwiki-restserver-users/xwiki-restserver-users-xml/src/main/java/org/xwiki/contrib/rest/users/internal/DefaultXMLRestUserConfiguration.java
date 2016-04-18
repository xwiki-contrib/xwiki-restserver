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

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.jdom2.Element;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.contrib.rest.XWikiRestServerException;
import org.xwiki.contrib.rest.configuration.XMLConfiguration;
import org.xwiki.contrib.rest.users.RestUser;
import org.xwiki.contrib.rest.users.XMLRestUserConfiguration;
import org.xwiki.text.StringUtils;

/**
 * @version $Id: $
 */
public class DefaultXMLRestUserConfiguration implements XMLRestUserConfiguration
{
    @Inject
    private XMLConfiguration xmlConfiguration;

    @Inject
    private ComponentManager componentManager;

    private Map<String, XMLRestUser> users = new HashMap<>();

    @Override
    public Map<String, XMLRestUser> getUsers()
    {
        return users;
    }

    @Override
    public void reload() throws XWikiRestServerException
    {
        loadUsers();
    }

    @Override
    public String getPasswordSalt()
    {
        if (xmlConfiguration.getXML() != null) {
            Element usersElement = xmlConfiguration.getXML().getRootElement().getChild("users");
            if (usersElement != null) {
                String salt = usersElement.getChildText("password-salt");
                if (StringUtils.isNotBlank(salt)) {
                    return salt;
                }
            }
        }
        return null;
    }

    private void loadUsers() throws XWikiRestServerException
    {
        try {
            if (xmlConfiguration.getXML() != null) {
                users.clear();
                Element usersElement = xmlConfiguration.getXML().getRootElement().getChild("users");
                if (usersElement != null) {
                    for (Element userElement : usersElement.getChildren("user")) {
                        XMLRestUser user = componentManager.getInstance(RestUser.class);
                        user.setName(userElement.getChildTextTrim("name"));
                        user.setHashedPassword(userElement.getChildTextTrim("password"));
                        for (Element groupElement : userElement.getChildren("group")) {
                            String group = groupElement.getTextTrim();
                            if (StringUtils.isNotBlank(group)) {
                                user.addGroup(group);
                            }
                        }
                        users.put(user.getName(), user);
                    }
                }
            }
        } catch (ComponentLookupException e) {
            throw new XWikiRestServerException(e);
        }
    }
}
