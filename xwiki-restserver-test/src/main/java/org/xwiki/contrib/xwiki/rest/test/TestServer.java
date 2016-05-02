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
package org.xwiki.contrib.xwiki.rest.test;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.xwiki.contrib.rest.XWikiJaxRsApplication;
import org.xwiki.contrib.rest.server.XWikiRestServer;

/**
 * Utility class to create an XWikiRestServer and perform requests on it.
 *
 * @version $Id: $
 */
public class TestServer
{
    public XWikiJaxRsApplication application;

    private XWikiRestServer server;

    /**
     * Construct a new TestServer.
     * @param application the application to deploy
     */
    public TestServer(XWikiJaxRsApplication application)
    {
        this.application = application;
    }

    /**
     * Start the server in a thread
     * @throws Exception if error occurs
     */
    public void start() throws Exception
    {
        server = new XWikiRestServer(9000, application, "localhost");
        server.start();

        // Wait until the server is running
        while (!isServerRunning())
        {
            Thread.sleep(50);
        }
    }

    /**
     * Stop the server
     */
    public void stop() throws Exception
    {
        server.stop(true);
    }

    /**
     * @return if the server is running and ready
     * @throws IOException if error occurs
     */
    public boolean isServerRunning() throws IOException
    {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("http://localhost:9000/isrunning");
            client.execute(request);
            return true;
        } catch (HttpHostConnectException e) {
            return false;
        }
    }

    /**
     * Perform a GET request on the specified resource
     * @param resource the resource to reach
     * @return the content
     * @throws IOException if error happens
     */
    public String doGet(String resource) throws IOException
    {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:9000" + resource);
        HttpResponse response = client.execute(request);
        StringWriter result = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), result);
        return result.toString();
    }
}
