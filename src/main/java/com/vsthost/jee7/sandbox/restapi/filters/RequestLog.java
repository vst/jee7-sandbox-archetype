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

package com.vsthost.jee7.sandbox.restapi.filters;

import com.vsthost.jee7.sandbox.restapi.utils.Formatter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.core.MultivaluedMap;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Provides a class for request logs.
 *
 * @author Vehbi Sinan tunalioglu
 */
public class RequestLog {
    /**
     * Defines the datetime of the request.
     */
    private Calendar datetime;

    /**
     * Defines the method of the request.
     */
    private String method;

    /**
     * Defines the path of the request.
     */
    private String path;

    /**
     * Defines the set of headers of the request.
     */
    private MultivaluedMap<String, String> headers;

    /**
     * Default, empty constructor.
     */
    public RequestLog() {}

    /**
     * Constructor consuming all class fields.
     *
     * @param method
     * @param path
     * @param datetime
     * @param headers
     */
    public RequestLog(Calendar datetime, String method, String path, MultivaluedMap<String, String> headers) {
        this.datetime = datetime;
        this.method = method;
        this.path = path;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public MultivaluedMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(MultivaluedMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonGenerator generator = Json.createGenerator(stream);
        generator
                .writeStartObject()
                .write("method", this.getMethod())
                .write("path", this.getPath())
                .write("datetime", Formatter.DatetimeFormatter(this.getDatetime()))
                .writeStartObject("headers");
        for (Map.Entry<String, List<String>> entry: this.getHeaders().entrySet()) {
            generator.writeStartArray(entry.getKey());
            entry.getValue().forEach(generator::write);
            generator.writeEnd();
        }
        generator.writeEnd();
        generator.writeEnd();
        generator.close();
        return stream.toString();
    }
}
