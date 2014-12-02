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


import com.vsthost.jee7.sandbox.models.Person;
import org.picketlink.idm.model.AbstractIdentityType;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.annotation.AttributeProperty;
import org.picketlink.idm.model.annotation.IdentityStereotype;
import org.picketlink.idm.model.annotation.StereotypeProperty;
import org.picketlink.idm.model.annotation.Unique;
import org.picketlink.idm.query.AttributeParameter;
import org.picketlink.idm.query.QueryParameter;

import static org.picketlink.idm.model.annotation.IdentityStereotype.Stereotype.USER;
import static org.picketlink.idm.model.annotation.StereotypeProperty.Property.IDENTITY_USER_NAME;

/**
 * Defines a user account model.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@IdentityStereotype(USER)
public class UserAccount extends AbstractIdentityType implements Account {
    /**
     * Defines an attribute parameter as a query attribute which can be used to query users by their activation code.
     */
    public static final AttributeParameter ACTIVATION_CODE = QUERY_ATTRIBUTE.byName("activationCode");

    /**
     * Defines a query parameter as a query attribute which can be used to query users by their username.
     */
    public static final QueryParameter USERNAME = QUERY_ATTRIBUTE.byName("username");

    /**
     * Defines the username of the account which is hardwired to the identity model.
     */
    @StereotypeProperty(IDENTITY_USER_NAME)
    @AttributeProperty
    @Unique
    private String username;

    /**
     * Defines the activation code of the account.
     */
    @AttributeProperty
    private String activationCode;

    /**
     * Defines the related person entity of the account.
     */
    @AttributeProperty
    private Person person;

    /**
     * Default constructor.
     */
    public UserAccount() {
        this(null);
    }

    /**
     * Constructor consuming the user name of the account.
     * @param username The user name of the account.
     */
    public UserAccount(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void invalidateActivationCode() {
        this.activationCode = null;
    }
}
