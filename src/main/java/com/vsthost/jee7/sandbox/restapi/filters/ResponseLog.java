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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Provides a class for request logs.
 *
 * @author Vehbi Sinan tunalioglu
 */
public class ResponseLog {
    /**
     * Defines the datetime of the request.
     */
    private Calendar datetime;

    /**
     * Defines the status of the response.
     */
    int status;

    /**
     * Defines the set of headers of the request.
     */
    private MultivaluedMap<String, Object> headers;

    /**
     * Default, empty constructor.
     */
    public ResponseLog() {}

    /**
     * Constructor consuming all class fields.
     *
     * @param datetime
     * @param headers
     */
    public ResponseLog(Calendar datetime, int status, MultivaluedMap<String, Object> headers) {
        this.datetime = datetime;
        this.headers = headers;
    }


    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MultivaluedMap<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(MultivaluedMap<String, Object> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonGenerator generator = Json.createGenerator(stream);
        generator
                .writeStartObject()
                .write("datetime", Formatter.DatetimeFormatter(this.getDatetime()))
                .write("status", this.getStatus())
                .writeStartObject("headers");
        for (Map.Entry<String, List<Object>> entry: this.getHeaders().entrySet()) {
            generator.writeStartArray(entry.getKey());
            for (Object o : entry.getValue()) {
                generator.write(o.toString());
            }
            generator.writeEnd();
        }
        generator.writeEnd();
        generator.writeEnd();
        generator.close();
        return stream.toString();
    }
}
