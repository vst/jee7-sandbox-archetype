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

package com.vsthost.jee7.sandbox.restapi.serializers;

import com.vsthost.jee7.sandbox.security.models.UserAccount;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Serializes UserAccount class instances.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class UserAccountWriter implements MessageBodyWriter<UserAccount> {
    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return UserAccount.class.isAssignableFrom(aClass);
    }

    @Override
    public long getSize(UserAccount userAccount, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(UserAccount userAccount, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        // Get the JSON generator:
        JsonGenerator gen = Json.createGenerator(outputStream);

        // Start writing the object:
        gen.writeStartObject();

        // Add slots:
        gen.write("id", userAccount.getId());
        gen.write("username", userAccount.getUsername());
        gen.write("enabled", userAccount.isEnabled());
        gen.write("createdDate", userAccount.getCreatedDate().toString());
        if (userAccount.getExpirationDate() != null) {
            gen.write("expirationDate", userAccount.getExpirationDate().toString());
        }
        else {
            gen.write("expirationDate", JsonValue.NULL);
        }

        // Add person:
        gen.writeStartObject("person");
        gen.write("emailAddress", userAccount.getPerson().getEmailAddress());
        gen.write("name", userAccount.getPerson().getName());
        gen.write("id", userAccount.getPerson().getId());
        gen.writeEnd();

        // Finish writing:
        gen.writeEnd();

        // Flush the generator:
        gen.flush();
    }
}
