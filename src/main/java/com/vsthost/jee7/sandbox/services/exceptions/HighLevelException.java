/*
 * Copyright (c) 2013-2014 Vehbi Sinan Tunalioglu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vsthost.jee7.sandbox.services.exceptions;

/**
 * A high level exception for end users.
 *
 * <p>
 *
 * TODO: Make sure that these exceptions are sent to the endusers.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class HighLevelException extends Exception {
    private static final long serialVersionUID = 1L;
    int code;
    String message;

    public static final int USERNAME_NOT_AVAILABLE = 1;
    public static final int NO_SUCH_USER = 2;
    public static final int NO_USER_FOR_CREDENTIALS = 3;
    public static final int NO_SUCH_ROLE = 4;

    public HighLevelException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String toJson () {
        return String.format("{\"code\": %d, \"message\": \"%s\"}",
                             this.code,
                             this.message.replaceAll("\"", "\\\""));
    }
}
