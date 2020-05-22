/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amazonaws.services.kinesisanalytics.runtime;

import com.amazonaws.services.kinesisanalytics.runtime.models.PropertyGroup;
import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class encapsulates the Runtime values Kinesis Analytics provides to Applications
 */
public class KinesisAnalyticsRuntime {

    /**
     * Returns the application properties passed to the application when the application was started or updated
     *
     * @return a map of PropertyGroupId to java.util.Properties
     * @throws IOException if an exception is encountered
     */
    public static Map<String, Properties> getApplicationProperties() throws IOException {
        Properties configProperties = getConfigProperties();
        return getApplicationProperties(configProperties.getProperty(ConfigConstants.APPLICATION_PROPERTIES_FILE));
    }

    /**
     * Returns the application properties passed to the application when the application was started or updated
     *
     * @param filename name of the file that contains the property groups
     * @return a map of PropertyGroupId to java.util.Properties
     * @throws IOException if an exception is encountered
     */
    public static Map<String, Properties> getApplicationProperties(String filename) throws IOException {
        Map<String, Properties> appProperties = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(new FileInputStream(filename));
            for (JsonNode elem : root) {
                PropertyGroup propertyGroup = mapper.treeToValue(elem, PropertyGroup.class);
                Properties properties = new Properties();

                properties.putAll(propertyGroup.properties);
                appProperties.put(propertyGroup.groupID, properties);
            }
        } catch (FileNotFoundException ignored) {
            // swallow file not found and return empty runtime properties
        }
        return appProperties;
    }

    @VisibleForTesting
    static Properties getConfigProperties() throws IOException {
        InputStream configPropertiesStream = KinesisAnalyticsRuntime.class.getClassLoader().getResourceAsStream("config.properties");
        if (configPropertiesStream == null) {
            throw new FileNotFoundException("config.properties");
        }

        Properties configProperties = new Properties();
        configProperties.load(configPropertiesStream);
        return configProperties;
    }
}
