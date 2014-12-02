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

package com.vsthost.jee7.sandbox.security;

import com.vsthost.jee7.sandbox.models.Person;
import com.vsthost.jee7.sandbox.security.models.AuthorizationRoles;
import com.vsthost.jee7.sandbox.security.models.UserAccount;
import com.vsthost.jee7.sandbox.security.services.UserAccountService;
import org.picketlink.event.PartitionManagerCreateEvent;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.config.SecurityConfigurationException;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Realm;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.query.IdentityQuery;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * TODO: Complete documentation.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Stateless
public class SecurityInitializer {
    /**
     * Defines the path of the keystore file.
     */
    public static final String KEYSTORE_FILE_PATH = "/keystore.jks";

    /**
     * Defines the keystore.
     */
    private KeyStore keyStore;

    /**
     * Defines the UserAccountService instance.
     */
    @Inject
    private UserAccountService userAccountService;

    /**
     * Configures the default partition.
     *
     * @param event The partition manager creation event.
     */
    public void configureDefaultPartition(@Observes PartitionManagerCreateEvent event) {
        // Get the partition manager:
        PartitionManager partitionManager = event.getPartitionManager();

        // Create default partition:
        this.createDefaultPartition(partitionManager);

        // Create default roles:
        this.createDefaultRoles(partitionManager);

        // Create the admin account:
        this.createAdminAccount(partitionManager);
    }

    /**
     * Creates the default partition.
     *
     * @param partitionManager The partition manager.
     */
    private void createDefaultPartition(PartitionManager partitionManager) {
        // Get the partition:
        Realm partition = partitionManager.getPartition(Realm.class, Realm.DEFAULT_REALM);

        // Check the partition:
        if (partition == null) {
            try {
                // Attempt to create a new partition:
                partition = new Realm(Realm.DEFAULT_REALM);

                // Set the public key attribute:
                partition.setAttribute(new Attribute<byte[]>("PublicKey", this.getPublicKey()));

                // Set the private key attribute:
                partition.setAttribute(new Attribute<byte[]>("PrivateKey", this.getPrivateKey()));

                // Add the partition to the partition manager:
                partitionManager.add(partition);
            } catch (Exception e) {
                // Cannot create a default partiton:
                throw new SecurityConfigurationException("Could not create default partition.", e);
            }
        }
    }


    /**
     * Creates default roles:
     *
     * @param partitionManager The partition manager.
     */
    private void createDefaultRoles(PartitionManager partitionManager) {
        // Create and get the identity manager:
        IdentityManager identityManager = partitionManager.createIdentityManager();

        // Create roles one-by-one:
        this.createRole(AuthorizationRoles.ADMIN, identityManager);
        this.createRole(AuthorizationRoles.USER, identityManager);
    }

    /**
     * Returns the private key.
     *
     * @return The private key.
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     */
    private byte[] getPrivateKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        // TODO: Check the following statement.
        return getKeyStore().getKey("secure-key", "example".toCharArray()).getEncoded();
    }

    /**
     * Returns the public key.
     *
     * @return The public key.
     * @throws KeyStoreException
     */
    private byte[] getPublicKey() throws KeyStoreException {
        // TODO: Check the following statement.
        return getKeyStore().getCertificate("secure-key").getPublicKey().getEncoded();
    }

    /**
     * Returns the keystore.
     *
     * @return The keystor.
     */
    private KeyStore getKeyStore() {
        // Check if the keystore is instantiated:
        if (this.keyStore == null) {
            // Nope, attempt to get a keystore instance:
            try {
                // Get and set the keystore:
                this.keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

                // Load the keystore:
                // TODO: Check the following statement.
                this.getKeyStore().load(getClass().getResourceAsStream(KEYSTORE_FILE_PATH), "example".toCharArray());
            }
            catch (Exception e) {
                // Cannot load the keystore:
                throw new SecurityException("Could not load key store.", e);
            }
        }

        // Done, return the keystore:
        return this.keyStore;
    }

    /**
     * Creates the default admin account.
     *
     * @param partitionManager The partition manager which the admin account
     *                         is going to be created through.
     */
    public void createAdminAccount(PartitionManager partitionManager) {
        // Get the identity manager:
        IdentityManager identityManager = partitionManager.createIdentityManager();

        // Define the username:
        String username = "admin";

        // Let's check if admin is already created. Create the query instance:
        IdentityQuery<UserAccount> query = identityManager.createIdentityQuery(UserAccount.class);

        // Set the query parameter:
        query.setParameter(UserAccount.USERNAME, username);

        // Check if there is any admin user:
        if(query.getResultList().size() > 0) {
            return;
        }

        // Create a new person:
        Person person = new Person();

        // Set person attributes:
        person.setName("Administrator");
        person.setEmailAddress("admin@example.com");

        // Create the user account instance:
        UserAccount account = new UserAccount(username);

        // Set the person of the account:
        account.setPerson(person);

        // Add the account to the identity manager:
        identityManager.add(account);

        // Set the password of the account
        identityManager.updateCredential(account, new Password(username));

        // Grant the administrator role:
        BasicModel.grantRole(
                partitionManager.createRelationshipManager(),
                account,
                BasicModel.getRole(identityManager, AuthorizationRoles.ADMIN));
    }


    /**
     * Create a new role with the given identity manager.
     *
     * @param roleName The name of the role.
     * @param identityManager The identity manager.
     * @return The role which is created.
     */
    public static Role createRole(String roleName, IdentityManager identityManager) {
        // Get the role, if any:
        Role role = BasicModel.getRole(identityManager, roleName);

        // Check the existance of the role:
        if (role == null) {
            // Create a new role instance:
            role = new Role(roleName);

            // Add the role to the identity manger:
            identityManager.add(role);
        }

        // Done, return with the role:
        return role;
    }
}
