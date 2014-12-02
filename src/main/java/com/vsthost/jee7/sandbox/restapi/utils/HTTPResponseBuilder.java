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

package com.vsthost.jee7.sandbox.restapi.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides an HTTP Response builder and related convenience methods.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class HTTPResponseBuilder {
    /**
     * Defines the response builder.
     */
    private final ResponseBuilder response;

    /**
     * Defines the message data.
     */
    private final Map<String, Object> dataContainer = new HashMap<String, Object>();

    /**
     * Constructs an HTTP Response with the status code provided.
     */
    public HTTPResponseBuilder(Status status) {
        // Initialise the response:
        this.response = Response.status(status);
    }

    public HTTPResponseBuilder data(String key, Object value) {
        // Add the key/value pair to data container:
        this.dataContainer.put(key, value);

        // Done, return self:
        return this;
    }

    public HTTPResponseBuilder error(String error) {
        // Add the error to the data container:
        this.data("error", error);

        // Done, return self:
        return this;
    }

    public HTTPResponseBuilder error(String error, Object message) {
        // Add the error to the data container:
        this.data("error", error);

        // Add error message:
        this.data("message", message);

        // Done, return self:
        return this;
    }

    public Response build() {
        return this.response.entity(this.dataContainer).build();
    }


    public static HTTPResponseBuilder ok () {
        return new HTTPResponseBuilder(Status.OK);
    }

    public static HTTPResponseBuilder accessDenied () {
        return new HTTPResponseBuilder(Status.FORBIDDEN).error("Access denied");
    }

    public static HTTPResponseBuilder authenticationRequired () {
        return new HTTPResponseBuilder(Status.UNAUTHORIZED).error("Authentication required");
    }

    public static HTTPResponseBuilder notFound () {
        return new HTTPResponseBuilder(Status.NOT_FOUND).error("Not found");
    }

    public static HTTPResponseBuilder badRequest () {
        return new HTTPResponseBuilder(Status.BAD_REQUEST).error("Bad request");
    }
}
