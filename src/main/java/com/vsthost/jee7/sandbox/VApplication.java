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

package com.vsthost.jee7.sandbox;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Provides a generic class which defines the application.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class VApplication {
    /**
     * Defines the version of the entire web application.
     */
    public static final String APP_VERSION = "0.0.1-SNAPSHOT";

    /**
     * Defines the version of the API.
     */
    public static final String API_VERSION = "0.0dev";

    /**
     * Provides the entity manager.
     */
    @Produces
    @PersistenceContext
    private EntityManager em;

    /**
     * Produces a logger instance to be injected at the injection point.
     *
     * @param injectionPoint Where the logger will be injected to.
     * @return A logger instance.
     */
    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
