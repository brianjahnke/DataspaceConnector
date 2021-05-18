/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * The base class for all descriptions.
 * @param <T> The type of the class described by the description.
 */
@Data
public class AbstractDescription<T> {
    /**
     * The overflow for all elements that cannot be mapped.
     */
    @JsonIgnore
    private Map<String, String> additional;

    /**
     * Add a value to the overflow field.
     * If the key already exists it will be overwritten.
     * Null for either key or values will be ignored.
     * @param key The key.
     * @param value The value.
     */
    @JsonAnySetter
    public void addOverflow(final String key, final String value) {
        if (additional == null) {
            additional = new HashMap<>();
        }

        if (key == null || value == null) {
            return;
        }

        additional.put(key, value);
    }
}
