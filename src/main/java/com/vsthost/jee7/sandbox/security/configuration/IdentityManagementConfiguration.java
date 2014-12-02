/*
 * Copyright (c) 2014 Vehbi Sinan Tunalioglu
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

package com.vsthost.jee7.sandbox.security.configuration;

import com.vsthost.jee7.sandbox.security.models.UserAccount;
import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

import javax.enterprise.event.Observes;

/**
 * Configures the identity management for the Picketlink security.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class IdentityManagementConfiguration {

    /**
     * Listens to the Picketlink security configuration event and
     * configures the identity management.
     *
     * @param event The Picketlink security configuration event.
     */
    public void configure(@Observes SecurityConfigurationEvent event) {
        // Get the security configuration builder from the event:
        SecurityConfigurationBuilder builder = event.getBuilder();

        // Configure the identity management:
        builder
                .idmConfig()
                .named("default.config")
                .stores()
                .jpa()
                .supportType(UserAccount.class)
                .supportAllFeatures();
    }
}
