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
package org.xwiki.contrib.rest.server;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.xwiki.contrib.rest.XWikiJaxRsApplication;
import org.xwiki.contrib.rest.server.internal.IsRunningApplication;

/**
 * @version $Id: $
 * @since 1.0
 */
public class XWikiRestServer implements Runnable
{
    private int portNumber;

    private XWikiJaxRsApplication application;

    public XWikiRestServer(int portNumber, XWikiJaxRsApplication application)
    {
        this.portNumber = portNumber;
        this.application = application;
    }

    @Override
    public void run()
    {
        // Not very clean but RestEasy has only considered Undertow for test purpose.
        // TODO: look if using Netty is better
        // http://docs.jboss.org/resteasy/docs/3.0.13.Final/userguide/html/RESTEasy_Embedded_Container.html
        System.setProperty("org.jboss.resteasy.port", Integer.toString(this.portNumber));
        UndertowJaxrsServer server = new UndertowJaxrsServer();
        server.deploy(application);
        server.deploy(new IsRunningApplication(), "isrunning");
        server.start();
    }

    public XWikiJaxRsApplication getApplication()
    {
        return application;
    }

    public void setApplication(XWikiJaxRsApplication application)
    {
        this.application = application;
    }

    public int getPortNumber()
    {
        return portNumber;
    }

    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }
}
