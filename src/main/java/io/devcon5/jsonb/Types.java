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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Utility class with factory methods to create type handles that simplify JSON deserialization with jsonb.
 * <br>
 * For example, to create a {@link Type} instance for <code>List&lt;String&gt;</code> invoke
 * <pre><code>
 *     ParameterizedType listOfStrings = Types.listOf(String.class);
 * </code></pre>
 *
 *
 * Created on 20.03.2017.
 */
public final class Types {

    private Types() {

    }

    /**
     * Creates a parameterized type definition for a generic list
     *
     * @param genericType
     *         the type of the entries of the list
     *
     * @return a new instance of {@link ParameterizedType}
     */
    public static ParameterizedType listOf(Type genericType) {

        return new ParameterizedTypeImpl(List.class, genericType);
    }

    /**
     * Creates a parameterized type definition for a generic set
     *
     * @param genericType
     *         the type of teh entries in the set
     *
     * @return a new instance of {@link ParameterizedType}
     */
    public static ParameterizedType setOf(Type genericType) {

        return new ParameterizedTypeImpl(Set.class, genericType);
    }

    /**
     * Creates a parameterrized type definition for a generic map
     *
     * @param keyType
     *         the type of the keys of the map
     * @param valueType
     *         the type of the values of the map
     *
     * @return a new instance of {@link ParameterizedType}
     */
    public static ParameterizedType mapOf(Type keyType, Type valueType) {

        return new ParameterizedTypeImpl(Map.class, keyType, valueType);
    }

    /**
     * Creates a parameterized type definition for a generic optional
     *
     * @param genericType
     *         the type of teh entries in the set
     *
     * @return a new instance of {@link ParameterizedType}
     */
    public static ParameterizedType optionalOf(Type genericType) {

        return new ParameterizedTypeImpl(Optional.class, genericType);
    }

    /**
     * Creates a parameterized type definition for a rawtype
     *
     * @param rawType
     *         the base type
     * @param typeParameters
     *         the generic type parameters
     *
     * @return a new instance of {@link ParameterizedType}
     */
    public static ParameterizedType of(Class<?> rawType, Type... typeParameters) {

        return new ParameterizedTypeImpl(rawType, typeParameters);
    }

    /**
     * Creates a parameterized type definition for a owned rawtype. For example Owner.Raw&lt;String&gt;
     *
     * @param ownerType
     *  the type that owns the rawtype
     * @param rawType
     *         the base type
     * @param typeParameters
     *         the generic type parameters
     *
     * @return a new instance of {@link ParameterizedType}
     */
    public static ParameterizedType ofOwnerType(Type ownerType, Class<?> rawType, Type... typeParameters) {

        return new ParameterizedTypeImpl(rawType, typeParameters, ownerType);
    }

    /**
     *
     */
    static class ParameterizedTypeImpl implements ParameterizedType {

        private final Type rawType;
        private final Type[] typeArgs;
        private final Type ownerType;

        ParameterizedTypeImpl(final Type rawType, final Type[] typeArgs, final Type ownerType) {

            this.rawType = rawType;
            this.ownerType = ownerType;
            this.typeArgs = typeArgs;
        }

        ParameterizedTypeImpl(final Type rawType, final Type... typeArgs) {

            this(rawType, typeArgs, null);
        }

        @Override
        public Type[] getActualTypeArguments() {

            return typeArgs;
        }

        @Override
        public Type getRawType() {

            return this.rawType;
        }

        @Override
        public Type getOwnerType() {

            return this.ownerType;
        }

        @Override
        public boolean equals(final Object o) {

            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;

            if (rawType != null ? !rawType.equals(that.rawType) : that.rawType != null) {
                return false;
            }
            if (!Arrays.equals(typeArgs, that.typeArgs)) {
                return false;
            }
            return ownerType != null ? ownerType.equals(that.ownerType) : that.ownerType == null;
        }

        @Override
        public int hashCode() {

            int result = rawType != null ? rawType.hashCode() : 0;
            result = 31 * result + (typeArgs != null ? typeArgs.hashCode() : 0);
            result = 31 * result + (ownerType != null ? ownerType.hashCode() : 0);
            return result;
        }
    }

}
