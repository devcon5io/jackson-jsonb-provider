/*
 * Copyright 2017, DevCon5 GmbH, Switzerland
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.devcon5.jsonb.jackson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JacksonJsonbBuilderTest {

    @Mock
    private JsonProvider prov;

    private JsonbConfig config;

    @Before
    public void setUp() throws Exception {
        this.config = new JsonbConfig();
    }

    @Test
    public void withEmptyConfig_nullsDisabledAsDefault() throws Exception {
        JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
        assertSame(builder, builder.withConfig(config));

        String result = builder.build().toJson(new Example());
        assertEquals("{\"value\":0}", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withNullConfig() throws Exception {
        JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
        assertSame(builder, builder.withConfig(null));
    }

    @Test
    public void withNullsEnabledByJsonbConfig() throws Exception {
        JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
        this.config.withNullValues(true);
        assertSame(builder, builder.withConfig(config));

        String result = builder.build().toJson(new Example());
        assertEquals("{\"list\":[],\"name\":null,\"value\":0}", result);
    }

    @Test
    public void withNullsEnabledByPropertyConfig() throws Exception {
        JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
        this.config.setProperty(JacksonJsonbBuilder.INCLUDE_NULL_VALUES, true);
        assertSame(builder, builder.withConfig(config));

        String result = builder.build().toJson(new Example());
        assertEquals("{\"list\":[],\"name\":null,\"value\":0}", result);
    }

    @Test
    public void withEmptyEnabledByPropertyConfig() throws Exception {
        JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
        this.config.setProperty(JacksonJsonbBuilder.INCLUDE_EMPTY_VALUES, true);
        assertSame(builder, builder.withConfig(config));

        String result = builder.build().toJson(new Example());
        System.out.println(result);
        assertEquals("{\"list\":[],\"value\":0}", result);
    }

    @Test
    public void withProvider() throws Exception {
        JacksonJsonbBuilder builder = new JacksonJsonbBuilder();
        assertSame(builder, builder.withProvider(prov));
    }

    @Test
    public void build() throws Exception {

        Jsonb jsonb = new JacksonJsonbBuilder().build();
        assertNotNull(jsonb);
    }

    public static class Example {

        private List<String> list = new ArrayList<>();
        private String name;
        private int value;

        public String getName() {

            return name;
        }

        public void setName(final String name) {

            this.name = name;
        }

        public int getValue() {

            return value;
        }

        public void setValue(final int value) {

            this.value = value;
        }

        public void setList(final List<String> list) {

            this.list = list;
        }

        public List<String> getList() {

            return list;
        }
    }

}
