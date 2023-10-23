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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link SHA512}.
 *
 * @since 5.1
 * @version $Id: $
 */
public class SHA512Test
{
    @Test
    public void test() throws Exception
    {
        assertEquals("1b1e03763179b6750556c29d2d3d3d8e2ffc7ef9bea59e156aaee7c257f1b1c7b75e53c0d9153a40fc8e43cb31d0742956234650397432c0a72f73b697ce64fb",
                SHA512.hash("aSimpleTest", "aSimpleSalt"));
    }
}
