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
package org.xwiki.contrib.rest.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.inject.Inject;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;

/**
 * @version $Id: $
 */
public abstract class AbstractXMLConfiguration implements XMLConfiguration, Initializable
{
    @Inject
    protected Logger logger;

    protected Document xmlDocument;

    private ArrayList<Path> configurationLocations = new ArrayList<>();

    @Override
    public void initialize() throws InitializationException
    {
        loadConfiguration();
    }

    /**
     * Add a location where the configuration file could be located.
     * @param location the configuration location
     */
    protected void addLocation(Path location)
    {
        configurationLocations.add(location);
    }

    protected void loadConfiguration()
    {
        try {
            Path location = getConfigurationLocation();
            if (location != null) {
                xmlDocument = new SAXBuilder().build(location.toFile());
            }
        } catch (JDOMException | IOException e) {
            logger.error("Failed to parse the configuration file.", e);
        }
    }

    private Path getConfigurationLocation()
    {
        String configuredLocation = System.getProperty("config");
        if (configuredLocation != null) {
            Path location = Paths.get(configuredLocation);
            configurationLocations.add(0, location);
            if (Files.notExists(location)) {
                logger.warn("No configuration [{}] has been found.", location);
            }
        }

        for (Path location : configurationLocations) {
            logger.debug("Try [{}] as configuration.", location);
            if (Files.exists(location)) {
                logger.info("Using [{}] as configuration.", location);
                return location;
            }
        }

        logger.warn("No configuration file found. Use default values instead.");
        return null;
    }

    @Override
    public Document getXML()
    {
        return xmlDocument;
    }

    @Override
    public void reload()
    {
        loadConfiguration();
    }
}
