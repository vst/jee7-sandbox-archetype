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

import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.picketlink.Identity;

import javax.ejb.EJBException;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Provides an exception mapper for the REST API.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Provider
public class HTTPExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    private Instance<Identity> identityInstance;

    @Override
    public Response toResponse(Throwable exception) {
        // Check if the exception is of EJB Exception:
        if (EJBException.class.isInstance(exception)) {
            // Yes, get the cause as the exception:
            exception = exception.getCause();
        }

        // Get the error message:
        String message = exception.getMessage();

        // Check if there is a message:
        if (message == null) {
            // Nope, unexpected error:
            message = "Unexpected error";
        }

        // Check exception types and act accordingly:
        if (AccessDeniedException.class.isInstance(exception)) {
            // Access is denied. Authorisation or authentication problem?
            if (this.identityInstance.get().isLoggedIn()) {
                // This is an authorisation problem:
                return HTTPResponseBuilder.accessDenied().build();
            }
            else {
                // This is an authentication problem:
                return HTTPResponseBuilder.authenticationRequired().build();
            }
        }
        else if (WebApplicationException.class.isInstance(exception)) {
            // Check if not found:
            if (((WebApplicationException) exception).getResponse().getStatus() == 404) {
                return HTTPResponseBuilder.notFound().build();
            }
        }

        // This is a bad request:
        return HTTPResponseBuilder.badRequest().data("message", message).build();
    }
}
