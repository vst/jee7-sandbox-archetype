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
package com.vsthost.jee7.sandbox.security.token;

import org.picketlink.idm.credential.AbstractToken;
import org.picketlink.json.jose.JWS;
import org.picketlink.json.jose.JWSBuilder;

/**
 * Provides a custom JSON Web Signature token implementation.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class JWSToken extends AbstractToken {

    /**
     * Defines the JSON Web Signature instance.
     */
    private final JWS jws;

    /**
     * Constructor consuming the encoded token.
     *
     * @param encodedToken The encoded token.
     */
    public JWSToken(String encodedToken) {
        super(encodedToken);

        // Build the JSON Web Signature:
        this.jws = new JWSBuilder().build(encodedToken);
    }

    @Override
    public String getSubject() {
        // Return the subject of the JSON Web Signature.
        return this.jws.getSubject();
    }
}

