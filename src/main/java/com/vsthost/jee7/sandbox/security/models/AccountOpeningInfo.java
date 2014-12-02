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

package com.vsthost.jee7.sandbox.security.models;

/**
 * Defines a bean which represents the information required
 * to open a user account in the system.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class AccountOpeningInfo {
    /**
     * Defines the user name of the account to be opened.
     */
    private String username;

    /**
     * Defines the password of the account to be opened.
     */
    private String password;

    /**
     * Defines the confirmed password of the account to be opened
     */
    private String passwordConfirmation;

    /**
     * Defines the name of the person of the account to be opened.
     */
    private String name;

    /**
     * Defines the email address of the person of the account to be opened.
     */
    private String emailAddress;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Checks against the constraint that fields are not null and not empty.
     *
     * TODO: Use BeanValidation.
     * @return {@code true} if fields are not null and not empty, {@code false} otherwise.
     */
    public boolean isValid () {
        return this.getUsername() != null &&
                this.getPassword() != null &&
                this.getPasswordConfirmation() != null &&
                this.getName() != null &&
                this.getEmailAddress() != null &&
                !this.getUsername().trim().isEmpty() &&
                !this.getPassword().trim().isEmpty() &&
                !this.getPasswordConfirmation().trim().isEmpty() &&
                !this.getName().trim().isEmpty() &&
                !this.getEmailAddress().trim().isEmpty();

    }
}
