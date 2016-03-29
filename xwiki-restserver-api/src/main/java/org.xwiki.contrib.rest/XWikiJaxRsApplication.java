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
package org.xwiki.contrib.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.xwiki.component.embed.EmbeddableComponentManager;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;

/**
 * A JAX-RS application which automatically registers the RestResource components that are present in the class path.
 *
 * @version $Id: $
 * @since 1.0
 */
@ApplicationPath("/")
public class XWikiJaxRsApplication extends Application
{
    /**
     * The XWiki Component Manager.
     */
    private EmbeddableComponentManager componentManager = new EmbeddableComponentManager();

    /**
     * The set containing all the discovered resources and providers that will constitute the JAX-RS application.
     */
    private Set<Object> restComponents = new HashSet<>();

    /**
     * Creates an XWikiJaxRsApplication.
     * @throws ComponentLookupException if error happens while RestResources are initialized
     */
    public XWikiJaxRsApplication() throws ComponentLookupException
    {
        componentManager.initialize(this.getClass().getClassLoader());
        for (RestResource component : componentManager.<RestResource>getInstanceList(RestResource.class)) {
            restComponents.add(component);
        }
        for (RestFilter filter : componentManager.<RestFilter>getInstanceList(RestFilter.class)) {
            restComponents.add(filter);
        }
    }

    @Override
    public Set<Object> getSingletons() {
        return restComponents;
    }

    /**
     * @return the component manager
     */
    public ComponentManager getComponentManager()
    {
        return componentManager;
    }
}
