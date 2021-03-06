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

import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.contrib.rest.XWikiJaxRsApplication;
import org.xwiki.contrib.rest.server.XWikiRestServer;

/**
 * Run the server on the command-line.
 *
 * @version $Id: $
 * @since 1.0
 */
public final class Main
{
    private static final int PORT_NUMBER = 9000;

    /**
     * It's forbidden to create an instance of this class.
     */
    private Main()
    {
    }

    /**
     * Entry-point of the application.
     * @param args arguments of the command line
     */
    @SuppressWarnings("checkstyle:uncommentedmain")
    public static void main(String[] args)
    {
        try {
            XWikiRestServer server = new XWikiRestServer(PORT_NUMBER, new XWikiJaxRsApplication(), "localhost");
            server.run();
        } catch (ComponentLookupException e) {
            e.printStackTrace();
        }
    }
}
