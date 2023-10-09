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
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson2 based implmentation of Jsonb API
 */
@ApplicationScoped
public class JacksonJsonbBuilder implements JsonbBuilder {

    /**
     * Property name that can be used in configuration to toggle whether null-values should be serialized or not.
     * Same behavior as using <code>jsoncConfig.withNullValues(true|false)</code> or using
     * {@link JsonbConfig#NULL_VALUES}.
     */
    public static final String INCLUDE_NULL_VALUES = "jsonb.jackson.nullValues";
    /**
     * Property name that can be used in configuration
     */
    public static final String INCLUDE_EMPTY_VALUES = "jsonb.jackson.emptyValues";

    private boolean includeNulls = false;
    private boolean includeEmpty = false;

    @Override
    public JsonbBuilder withConfig(final JsonbConfig jsonbConfig) {

        if(jsonbConfig == null) {
            throw new IllegalArgumentException("Configuration must not be null");
        }
        this.includeNulls = (boolean) jsonbConfig.getProperty(JsonbConfig.NULL_VALUES).orElse(this.includeNulls);
        this.includeNulls = (boolean) jsonbConfig.getProperty(INCLUDE_NULL_VALUES).orElse(this.includeNulls);
        this.includeEmpty = (boolean) jsonbConfig.getProperty(INCLUDE_EMPTY_VALUES).orElse(this.includeEmpty);
        return this;
    }

    @Override
    public JsonbBuilder withProvider(final JsonProvider jsonProvider) {
        //noop
        return this;
    }
    
    @Inject 
    private Provider<ObjectMapper> mapper;
    
    @Override
    public Jsonb build() {
        final ObjectMapper mapper=Optional.ofNullable(this.mapper).map(Provider::get).orElseGet(ObjectMapper::new);
        if(!includeNulls){
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            if(!includeEmpty){
                mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            }
        }
        return new JacksonJsonb(mapper);
    }
}
