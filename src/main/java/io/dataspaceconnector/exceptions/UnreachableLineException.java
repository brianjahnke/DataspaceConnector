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
package io.dataspaceconnector.exceptions;

import io.dataspaceconnector.utils.ErrorMessages;

/**
 * Thrown to indicate that this line in the code should not have been possible to reach.
 */
public class UnreachableLineException extends RuntimeException {
    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a UnexpectedMessageType with the specified detail message.
     *
     * @param msg The detail message.
     */
    public UnreachableLineException(final String msg) {
        super(msg);
    }

    /**
     * Construct a UnexpectedMessageType with the specified detail message.
     *
     * @param msg The detail message.
     */
    public UnreachableLineException(final ErrorMessages msg) {
        super(msg.toString());
    }
}
