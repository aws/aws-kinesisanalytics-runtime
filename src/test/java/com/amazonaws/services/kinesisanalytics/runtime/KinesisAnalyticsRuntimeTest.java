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

import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class KinesisAnalyticsRuntimeTest {
    @Test
    public void testGetApplicationProperties() throws IOException {
        Map<String, Properties> propertyGroups = KinesisAnalyticsRuntime.getApplicationProperties(getClass()
                        .getClassLoader()
                        .getResource("testApplicationProperties.json")
                        .getPath());

        Properties stream1Props = propertyGroups.get("Stream1");
        assertThat(stream1Props.getProperty("Region")).isEqualTo("us-east-1");

        Properties stream2Props = propertyGroups.get("Stream2");
        assertThat(stream2Props.getProperty("Region")).isEqualTo("us-east-2");
        assertThat(stream2Props.getProperty("Offset")).isEqualTo("latest");
    }

    @Test
    public void testGetApplicationPropertiesNoFile() throws IOException {
        Map<String, Properties> propertyGroups = KinesisAnalyticsRuntime.getApplicationProperties("nosuchfile.json");
        assertThat(propertyGroups).isEmpty();
    }

    @Test
    public void testGetConfigProperties() throws IOException {
        Properties configProperties = KinesisAnalyticsRuntime.getConfigProperties();
        assertThat(configProperties).isNotNull();
    }
}
