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
