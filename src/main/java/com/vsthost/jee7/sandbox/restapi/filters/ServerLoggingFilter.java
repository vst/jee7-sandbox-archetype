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

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Logs client requests.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ServerLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    @Inject
    Logger logger;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Construct the request log instance:
        RequestLog log = new RequestLog(
                new GregorianCalendar(),
                requestContext.getMethod(),
                requestContext.getUriInfo().getAbsolutePath().getPath(),
                requestContext.getHeaders());

        // TODO: Persist the log instance.
        logger.info(log.toString());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // Construct the response log instance:
        // TODO: The response status reads always 0. Fix it.
        ResponseLog log = new ResponseLog(
                new GregorianCalendar(),
                responseContext.getStatus(),
                responseContext.getHeaders());

        // TODO: Persist the log instance.
        logger.info(log.toString());
    }
}
