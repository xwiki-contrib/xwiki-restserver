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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xwiki.contrib.rest.XWikiJaxRsApplication;
import org.xwiki.contrib.xwiki.rest.test.TestServer;
import org.xwiki.test.annotation.AllComponents;

import static org.junit.Assert.assertEquals;

/**
 * Functional test that runs a standalone REST server and executes some requests.
 *
 * @version $Id: $
 */
@AllComponents
public class FunctionalTests
{
    private static TestServer testServer;

    @BeforeClass
    public static void setUp() throws Exception
    {
        testServer = new TestServer(new XWikiJaxRsApplication());
        testServer.start();
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        testServer.stop();
    }

    @Test
    public void testHelloWorldResource() throws Exception
    {
        // Test
        String result = testServer.doGet("/hello");

        // Verify
        String expectedResult = "{\n"
                + "  \"message\" : \"Hello World!\",\n"
                + "  \"version\" : 1,\n"
                + "  \"otherMessages\" : [ \"Message 1\", \"Message 2\", \"Message 3\" ]\n"
                + "}";

        assertEquals(expectedResult, result);
    }
}
