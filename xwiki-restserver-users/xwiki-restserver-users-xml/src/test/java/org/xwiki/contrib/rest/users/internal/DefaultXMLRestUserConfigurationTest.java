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

import java.util.Map;

import jakarta.inject.Provider;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.component.util.DefaultParameterizedType;
import org.xwiki.contrib.rest.configuration.XMLConfiguration;
import org.xwiki.contrib.rest.users.RestUser;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @version $Id: $
 */
public class DefaultXMLRestUserConfigurationTest
{
    @Rule
    public MockitoComponentMockingRule<DefaultXMLRestUserConfiguration> mocker =
            new MockitoComponentMockingRule<>(DefaultXMLRestUserConfiguration.class);

    private XMLConfiguration xmlConfiguration;
    private Provider<RestUser> userProvider;

    @Before
    public void setUp() throws Exception
    {
        xmlConfiguration = mocker.getInstance(XMLConfiguration.class);
        Document xml = new SAXBuilder().build(getClass().getResourceAsStream("/config.xml"));
        when(xmlConfiguration.getXML()).thenReturn(xml);

        userProvider = mocker.registerMockComponent(new DefaultParameterizedType(null, Provider.class,
                RestUser.class), "xml");
        when(userProvider.get()).thenReturn(new XMLRestUser(), new XMLRestUser());
    }

    @Test
    public void getUsers() throws Exception
    {
        Map<String, XMLRestUser> users = mocker.getComponentUnderTest().getUsers();
        assertNotNull(users);
        assertEquals(2, users.size());

        XMLRestUser user1 = users.get("toto");
        assertNotNull(user1);
        assertEquals("toto",user1.getName());
        assertEquals("hashedPassword1", user1.getHashedPassword());
        assertTrue(user1.isInGroup("groupA"));
        assertTrue(user1.isInGroup("groupB"));
        assertTrue(user1.isInGroup("groupC"));
        assertFalse(user1.isInGroup("MI6"));

        XMLRestUser user2 = users.get("jamesbond");
        assertNotNull(user2);
        assertEquals("jamesbond",user2.getName());
        assertEquals("hashedPassword2", user2.getHashedPassword());
        assertFalse(user2.isInGroup("groupA"));
        assertFalse(user2.isInGroup("groupB"));
        assertFalse(user2.isInGroup("groupC"));
        assertTrue(user2.isInGroup("MI6"));
    }

    @Test
    public void getPasswordSalt() throws Exception
    {
        assertEquals("MySalt", mocker.getComponentUnderTest().getPasswordSalt());
    }


}
