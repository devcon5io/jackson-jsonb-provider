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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class JacksonJsonbTest {

    private static final String REF_JSON = "{\"name\":\"Test\",\"value\":12}";

    private JacksonJsonb subject;

    private Example example;

    @Before
    public void setUp() throws Exception {

        this.example = new Example();
        this.example.setName("Test");
        this.example.setValue(12);
        this.subject = new JacksonJsonb(new ObjectMapper());
    }

    private void assertResultIsOk(final Example ex) {

        assertNotNull(ex);
        assertEquals("Test", ex.getName());
        assertEquals(12, ex.getValue());
    }

    @Test
    public void fromJsonInputStreamAndClass() throws Exception {

        Example ex = subject.fromJson(new ByteArrayInputStream(REF_JSON.getBytes()), Example.class);
        assertResultIsOk(ex);
    }

    @Test
    public void fromJsonInputStreamAndType() throws Exception {

        Example ex = subject.fromJson(new ByteArrayInputStream(REF_JSON.getBytes()), (Type)Example.class);
        assertResultIsOk(ex);
    }

    @Test
    public void fromJsonReaderAndClass() throws Exception {
        Example ex = subject.fromJson(new StringReader(REF_JSON), Example.class);
        assertResultIsOk(ex);
    }

    @Test
    public void fromJsonReaderAndType() throws Exception {
        Example ex = subject.fromJson(new StringReader(REF_JSON), (Type)Example.class);
        assertResultIsOk(ex);
    }

    @Test
    public void fromJsonStringAndClass() throws Exception {
        Example ex = subject.fromJson(REF_JSON, Example.class);
        assertResultIsOk(ex);
    }

    @Test
    public void fromJsonStringAndType() throws Exception {
        Example ex = subject.fromJson(REF_JSON, (Type)Example.class);
        assertResultIsOk(ex);
    }

    @Test
    public void toJsonFromObject() throws Exception {
        String result = subject.toJson(this.example);
        assertEquals(REF_JSON, result);
    }

    @Test
    public void toJsonFromObjectAndType() throws Exception {
        String result = subject.toJson(this.example, Example.class);
        assertEquals(REF_JSON, result);
    }

    @Test
    public void toJsonFromObjectToWriter() throws Exception {
        StringWriter writer = new StringWriter();
        subject.toJson(this.example, writer);
        String result = writer.toString();
        assertEquals(REF_JSON, result);
    }

    @Test
    public void toJsonFromObjectAndTypeToWriter() throws Exception {
        StringWriter writer = new StringWriter();
        subject.toJson(this.example, Example.class, writer);
        String result = writer.toString();
        assertEquals(REF_JSON, result);

    }

    @Test
    public void toJsonFromObjectToOutputStream() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        subject.toJson(this.example, os);
        String result = new String(os.toByteArray());
        assertEquals(REF_JSON, result);
    }

    @Test
    public void toJsonFromObjectAndTypeToOutputStream() throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        subject.toJson(this.example, Example.class, os);
        String result = new String(os.toByteArray());
        assertEquals(REF_JSON, result);
    }

    @Test
    public void close() throws Exception {

    }

    public static class Example {

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
    }

}
