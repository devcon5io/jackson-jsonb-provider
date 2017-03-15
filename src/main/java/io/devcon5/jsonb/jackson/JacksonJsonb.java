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

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Jackson2 based implementation for JsonB API
 */
public class JacksonJsonb implements Jsonb {

    private final ObjectMapper mapper;

    public JacksonJsonb(final ObjectMapper mapper) {
        this.mapper = mapper;

    }

    @Override
    public <T> T fromJson(final String s, final Class<T> aClass) throws JsonbException {

        try {
            return mapper.readValue(s, aClass);
        } catch (IOException e) {
            throw new JsonbException("Could not parse content", e);
        }
    }

    @Override
    public <T> T fromJson(final String s, final Type type) throws JsonbException {

        try {
            return mapper.readValue(s, TypeFactory.defaultInstance().constructType(type));
        } catch (IOException e) {
            throw new JsonbException("Could not parse content", e);
        }
    }

    @Override
    public <T> T fromJson(final Reader reader, final Class<T> aClass) throws JsonbException {

        try {
            return mapper.readValue(reader, aClass);
        } catch (IOException e) {
            throw new JsonbException("Could not parse content", e);
        }
    }

    @Override
    public <T> T fromJson(final Reader reader, final Type type) throws JsonbException {

        try {
            return mapper.readValue(reader, TypeFactory.defaultInstance().constructType(type));
        } catch (IOException e) {
            throw new JsonbException("Could not parse content", e);
        }
    }

    @Override
    public <T> T fromJson(final InputStream inputStream, final Class<T> aClass) throws JsonbException {

        try {
            return mapper.readValue(inputStream, aClass);
        } catch (IOException e) {
            throw new JsonbException("Could not parse content", e);
        }
    }

    @Override
    public <T> T fromJson(final InputStream inputStream, final Type type) throws JsonbException {

        try {
            return mapper.readValue(inputStream, TypeFactory.defaultInstance().constructType(type));
        } catch (IOException e) {
            throw new JsonbException("Could not parse content", e);
        }
    }

    @Override
    public String toJson(final Object o) throws JsonbException {

        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new JsonbException("Could not serialize object of type" + o.getClass(), e);
        }
    }

    @Override
    public String toJson(final Object o, final Type type) throws JsonbException {

        return toJson(o);
    }

    @Override
    public void toJson(final Object o, final Writer writer) throws JsonbException {
        try {
            mapper.writeValue(writer, o);
        } catch (JsonProcessingException e) {
            throw new JsonbException("Could not serialize object of type" + o.getClass(), e);
        } catch (IOException e) {
            throw new JsonbException("Could not write serialized object of type" + o.getClass(), e);
        }
    }

    @Override
    public void toJson(final Object o, final Type type, final Writer writer) throws JsonbException {
        toJson(o, writer);
    }

    @Override
    public void toJson(final Object o, final OutputStream outputStream) throws JsonbException {
        try {
            mapper.writeValue(outputStream, o);
        } catch (JsonProcessingException e) {
            throw new JsonbException("Could not serialize object of type" + o.getClass(), e);
        } catch (IOException e) {
            throw new JsonbException("Could not write serialized object of type" + o.getClass(), e);
        }
    }

    @Override
    public void toJson(final Object o, final Type type, final OutputStream outputStream) throws JsonbException {
        toJson(o, outputStream);
    }

    @Override
    public void close() throws Exception {
        //noop
    }
}
