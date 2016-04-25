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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.xwiki.contrib.rest.users.XMLRestUserConfiguration;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @version $Id: $
 */
public class XMLRestUserTest
{
    @Rule
    public MockitoComponentMockingRule<XMLRestUser> mocker =
            new MockitoComponentMockingRule<>(XMLRestUser.class);

    private XMLRestUserConfiguration configuration;

    @Before
    public void setUp() throws Exception
    {
        configuration = mocker.getInstance(XMLRestUserConfiguration.class);
    }

    @Test
    public void isPasswordValid() throws Exception
    {
        when(configuration.getPasswordSalt()).thenReturn("MySalt");

        XMLRestUser user = mocker.getComponentUnderTest();
        user.setHashedPassword("ED0208D0D41456D180A6BB2361259B5856F25BD001B1e32af5858BD6795F68D98DB030057038FAFF544B" +
                "ECE36C35F9C602CF6408B4F9110E864DADDB5EDA9646");

        assertTrue(user.isPasswordValid("MyPassword"));
        assertFalse(user.isPasswordValid("WrongPassword"));
    }
}
