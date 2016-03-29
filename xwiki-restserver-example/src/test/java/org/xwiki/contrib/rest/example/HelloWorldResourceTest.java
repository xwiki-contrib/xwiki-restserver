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

import org.junit.Rule;
import org.junit.Test;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the example.
 */
public class HelloWorldResourceTest
{
    @Rule
    public MockitoComponentMockingRule<HelloWorldResource> mocker =
            new MockitoComponentMockingRule<>(HelloWorldResource.class);

    @Test
    public void getHelloWorld() throws Exception
    {
        // Test
        HelloWorld result = mocker.getComponentUnderTest().getHelloWorld();

        // Verify
        assertEquals("Hello World!", result.getMessage());
        assertEquals(1, result.getVersion());
        assertEquals(3, result.getOtherMessages().size());
        assertTrue(result.getOtherMessages().contains("Message 1"));
        assertTrue(result.getOtherMessages().contains("Message 2"));
        assertTrue(result.getOtherMessages().contains("Message 3"));
    }
}
