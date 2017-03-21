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

package io.devcon5.jsonb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

/**
 * Created on 21.03.2017.
 */
public class TypesTest {

    @Test
    public void listOf() throws Exception {
        ParameterizedType type = Types.listOf(String.class);

        assertNotNull(type);
        assertEquals(List.class, type.getRawType());
        assertNotNull(type.getActualTypeArguments());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
        assertNull(type.getOwnerType());

    }

    @Test
    public void setOf() throws Exception {
        ParameterizedType type = Types.setOf(String.class);

        assertNotNull(type);
        assertEquals(Set.class, type.getRawType());
        assertNotNull(type.getActualTypeArguments());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
        assertNull(type.getOwnerType());
    }

    @Test
    public void mapOf() throws Exception {
        ParameterizedType type = Types.mapOf(String.class, Long.class);

        assertNotNull(type);
        assertEquals(Map.class, type.getRawType());
        assertNotNull(type.getActualTypeArguments());
        assertEquals(2, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
        assertEquals(Long.class, type.getActualTypeArguments()[1]);
        assertNull(type.getOwnerType());
    }

    @Test
    public void optionalOf() throws Exception {
        ParameterizedType type = Types.optionalOf(String.class);

        assertNotNull(type);
        assertEquals(Optional.class, type.getRawType());
        assertNotNull(type.getActualTypeArguments());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
        assertNull(type.getOwnerType());

    }

    @Test
    public void of() throws Exception {
        ParameterizedType type = Types.of(AtomicReference.class, String.class);

        assertNotNull(type);
        assertEquals(AtomicReference.class, type.getRawType());
        assertNotNull(type.getActualTypeArguments());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
        assertNull(type.getOwnerType());
    }

    @Test
    public void ofOwnerType() throws Exception {
        //its a bit hypothetical to have List.AtomicReference<String> :)
        ParameterizedType type = Types.ofOwnerType(List.class, AtomicReference.class, String.class);

        assertNotNull(type);
        assertEquals(AtomicReference.class, type.getRawType());
        assertNotNull(type.getActualTypeArguments());
        assertEquals(1, type.getActualTypeArguments().length);
        assertEquals(String.class, type.getActualTypeArguments()[0]);
        assertEquals(List.class, type.getOwnerType());
    }

}
