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

import javax.net.ssl.SSLContext;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.xwiki.contrib.rest.RestResource;
import org.xwiki.contrib.rest.XWikiJaxRsApplication;
import org.xwiki.contrib.rest.XWikiRestServerException;

import io.undertow.Undertow;

/**
 * The XWiki Rest Server that run in its own Thread and which is responsible for handling HTTP requests and loading the
 * corresponding {@link RestResource} component.
 *
 * @version $Id: $
 * @since 1.0
 */
public class XWikiRestServer implements Runnable
{
    private static final String SERVER_IS_NOT_RUNNING = "XWikiRestServer is not running.";

    private int portNumber;

    private SSLContext sslContext;

    private XWikiJaxRsApplication application;

    private Thread thread;

    private UndertowJaxrsServer server;

    private String host;

    /**
     * Construct a new XWikiRestServer without starting it.
     * @param portNumber the port number of the server
     * @param application an XWikiJaxRsApplication object
     * @param host the host to listen (could be "*.*.*.*" or "127.0.0.1", etc...)
     */
    public XWikiRestServer(int portNumber, XWikiJaxRsApplication application, String host)
    {
        this.portNumber = portNumber;
        this.application = application;
        this.host = host;
    }

    /**
     * Construct a new XWikiRestServer, with support for SSL, without starting it.
     * @param portNumber the port number of the server
     * @param sslContext an SSLContext object
     * @param application an XWikiJaxRsApplication object
     * @param host the host to listen (could be "*.*.*.*" or "127.0.0.1", etc...)
     */
    public XWikiRestServer(int portNumber, SSLContext sslContext, XWikiJaxRsApplication application, String host)
    {
        this.portNumber = portNumber;
        this.application = application;
        this.sslContext = sslContext;
        this.host = host;
    }

    /**
     * Start the server in its proper thread.
     *
     * @throws XWikiRestServerException if an error occurs
     */
    public void start() throws XWikiRestServerException
    {
        if (thread != null) {
            throw new XWikiRestServerException("XWikiRestServer is already running.");
        }
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Join the thread until it terminates.
     *
     * @throws XWikiRestServerException if an error occurs
     * @throws InterruptedException if any thread has interrupted the current thread.
     */
    public void join() throws XWikiRestServerException, InterruptedException
    {
        if (thread == null) {
            throw new XWikiRestServerException(SERVER_IS_NOT_RUNNING);
        }
        thread.join();
    }

    /**
     * Stop the server and maybe wait for the thread to terminate.
     *
     * @param wait whether or not the end of the thread should be waited.
     *
     * @throws XWikiRestServerException if an error occurs
     * @throws InterruptedException if any thread has interrupted the current thread.
     */
    public void stop(boolean wait) throws XWikiRestServerException, InterruptedException
    {
        if (thread == null) {
            throw new XWikiRestServerException(SERVER_IS_NOT_RUNNING);
        }
        server.stop();
        if (wait) {
            join();
        }
    }

    @Override
    public void run()
    {
        // We use Undertow as an embedded server: simple, light and efficient.
        server = new UndertowJaxrsServer();

        // We manually create the Undertow Builder to set the port that we want
        Undertow.Builder undertowBuilder = Undertow.builder();

        if (isUsingSSL()) {
            undertowBuilder.addHttpsListener(this.portNumber, host, this.sslContext);
        } else {
            undertowBuilder.addHttpListener(this.portNumber, host);
        }

        // We deploy the applications
        server.deploy(application);

        // We start the server
        server.start(undertowBuilder);
    }

    /**
     * @return the XWikiJaxRsApplication used by the server
     */
    public XWikiJaxRsApplication getApplication()
    {
        return application;
    }

    /**
     * @param application the XWikiJaxRsApplication to be used by the server (not taken into account if the server
     * is running)
     */
    public void setApplication(XWikiJaxRsApplication application)
    {
        this.application = application;
    }

    /**
     * @return the port number used by the server
     */
    public int getPortNumber()
    {
        return portNumber;
    }

    /**
     * @param portNumber the port number to be used by the server (not taken into account if the server is running)
     */
    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }

    /**
     * @return whether or not SSL is being used and managed by the server.
     */
    public boolean isUsingSSL()
    {
        return sslContext != null;
    }

    /**
     * @param host the host to listen (could be "*.*.*.*" or "127.0.0.1", etc...) (not taken into account if the server
     * is running)
     */
    public void setHost(String host)
    {
        this.host = host;
    }

    /**
     * @return the listened host
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @param sslContext the sslContext to be used by the server (not taken into account if the server is running)
     */
    public void setSslContext(SSLContext sslContext)
    {
        this.sslContext = sslContext;
    }
}
