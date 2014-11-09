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

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.credential.storage.TokenCredentialStorage;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.json.jose.JWSBuilder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.UUID;

/**
 * Provides a JWS token provider.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Stateless
public class JWSTokenProvider implements Token.Provider<JWSToken> {

    @Inject
    private PartitionManager partitionManager;

    @Override
    public JWSToken issue(Account account) {
        // Instantiate a new JWS builder:
        JWSBuilder builder = new JWSBuilder();

        // Get the private key:
        byte[] privateKey = getPrivateKey();

        // Setup the JWS with the private key provided within the partition:
        // TODO: See the expiration time.
        builder
                .id(UUID.randomUUID().toString())
                .rsa256(privateKey)
                .issuer(account.getPartition().getName())
                .issuedAt(getCurrentTime())
                .subject(account.getId())
                .expiration(getCurrentTime() + (5 * 60))
                .notBefore(getCurrentTime());

        // Build the JWS token:
        JWSToken token = new JWSToken(builder.build().encode());

        // Update the accounts credentials:
        this.getIdentityManager().updateCredential(account, token);

        // Done, return the token:
        return token;
    }

    @Override
    public JWSToken renew(Account account, JWSToken renewToken) {
        // Renew by issuing a new token:
        return this.issue(account);
    }

    @Override
    public void invalidate(Account account) {
        // Invalidate the token by removing credentials on the account:
        this.getIdentityManager().removeCredential(account, TokenCredentialStorage.class);
    }

    @Override
    public Class<JWSToken> getTokenType() {
        // Returns the JWS token class:
        return JWSToken.class;
    }

    /**
     * Returns the private key.
     *
     * @return The private key.
     */
    private byte[] getPrivateKey() {
        return getPartition().<byte[]>getAttribute("PrivateKey").getValue();
    }

    /**
     * Returns the current time in seconds.
     *
     * @return The current time in seconds.
     */
    private int getCurrentTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Returns the security partition.
     *
     * @return The security partition.
     */
    private Realm getPartition() {
        return this.partitionManager.getPartition(Realm.class, Realm.DEFAULT_REALM);
    }

    /**
     * Returns the identity manager.
     *
     * @return The identity manager.
     */
    private IdentityManager getIdentityManager() {
        return this.partitionManager.createIdentityManager(this.getPartition());
    }
}

