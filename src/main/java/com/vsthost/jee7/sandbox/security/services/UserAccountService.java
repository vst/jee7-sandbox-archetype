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

package com.vsthost.jee7.sandbox.security.services;

import com.vsthost.jee7.sandbox.models.Person;
import com.vsthost.jee7.sandbox.security.models.AccountOpeningInfo;
import com.vsthost.jee7.sandbox.security.models.AuthorizationRoles;
import com.vsthost.jee7.sandbox.security.models.UserAccount;
import com.vsthost.jee7.sandbox.security.token.JWSToken;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.query.IdentityQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides a management service for user accounts.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Stateless
public class UserAccountService {
    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    @Inject
    private Token.Provider<JWSToken> tokenProvider;

    /**
     * Finds the user account by its username if there exists one.
     *
     * @param username The username of the user account.
     * @return An optional value of a user account. The function may return {@code None} is no
     * user account is found by the name provided.
     */
    public Optional<UserAccount> findByUsername(String username) {
        // Check the username against null or empty case:
        if (username == null || username.isEmpty() || username.trim().isEmpty()) {
            // Throw a runtime exception.
            throw new IllegalArgumentException("Invalid user name.");
        }

        // Create the query instance:
        IdentityQuery<UserAccount> query = this.identityManager.createIdentityQuery(UserAccount.class);

        // Set the query parameter:
        query.setParameter(UserAccount.USERNAME, username);

        // Get results:
        List<UserAccount> result = query.getResultList();

        // If there is a result, return the first one:
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        }

        // There is no such a user account. Return empty optional:
        return Optional.empty();
    }


    /**
     * Finds the user account by its activation code if there exists one.
     *
     * @param code The activation code of the user account.
     * @return An optional value of a user account. The function may return {@code None} is no
     * user account is found by the activation code provided.
     */
    public Optional<UserAccount> findByActivationCode(String code) {
        // Check the code against null or empty case:
        if (code == null || code.isEmpty() || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid activation code.");
        }

        // Create the query instance:
        IdentityQuery<UserAccount> query = this.identityManager.createIdentityQuery(UserAccount.class);

        // Set the query parameters:
        query.setParameter(UserAccount.ACTIVATION_CODE, code);

        // Get results:
        List<UserAccount> result = query.getResultList();

        // If there is a result, return the first one:
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        }

        // There is no such a user account. Return empty optional:
        return Optional.empty();
    }

    /**
     * Creates a new user account for the given account opening information.
     * <p>
     * Note that this function throws runtime exceptions for invalid/insufficient
     * account opening information.
     *
     * @param info The account opening information.
     * @return The user account created.
     */
    public UserAccount create(AccountOpeningInfo info) {
        // Check the validity of the account opening information:
        if (!info.isValid()) {
            // Information is not valid. Throw a runtime error:
            throw new IllegalArgumentException("Account information is not sufficient.");
        }

        // Instantiate a person instance for the account:
        Person person = new Person();

        // Set the fields of the person instance:
        person.setEmailAddress(info.getEmailAddress());
        person.setName(info.getName());

        // Instantiate a new user account instance:
        // TODO: Check the availability of the username first.
        UserAccount account = new UserAccount(info.getUsername());

        // Set the person of the user account:
        account.setPerson(person);

        // Set a new activation code:
        account.setActivationCode(UUID.randomUUID().toString());

        // Add the account to the identity manager:
        this.identityManager.add(account);

        // Set the password of the account
        this.updatePassword(account, info.getPassword());

        // For now, disable the account as manual activation by code is required:
        this.disableAccount(account);

        // Done, return the new account:
        return account;
    }

    /**
     * Updates the password of a given account.
     *
     * @param account The account of which the password to be updated.
     * @param password The password to be set.
     */
    public void updatePassword(Account account, String password) {
        this.identityManager.updateCredential(account, new Password(password));
    }

    /**
     * Checks if the user account has the given role.
     *
     * @param account The account which the role is checked on.
     * @param roleName The name of the role to be checked.
     * @return {@code true} if the user account has the role, {@code false} otherwise.
     */
    public boolean hasRole(UserAccount account, String roleName) {
        // Get the (stored) role by the role name:
        Role role = BasicModel.getRole(this.identityManager, roleName);

        // Check the role on the account and return its result:
        return BasicModel.hasRole(this.relationshipManager, account, role);
    }

    /**
     * Grants the given role to the user account.
     *
     * @param account The account which the role to be granted to.
     * @param roleName The name of the role to be granted.
     */
    public void grantRole(UserAccount account, String roleName) {
        // Get the (stored) role by the role name:
        Role role = BasicModel.getRole(this.identityManager, roleName);

        // Grant the role:
        BasicModel.grantRole(this.relationshipManager, account, role);
    }

    /**
     * Revokes the given role from the user account.
     *
     * @param account The account which the role to be revoked from.
     * @param roleName The name of the role to be revoked.
     */
    public void revokeRole(UserAccount account, String roleName) {
        // Get the (stored) role by the role name:
        Role role = BasicModel.getRole(this.identityManager, roleName);

        // Revoke the role:
        BasicModel.revokeRole(this.relationshipManager, account, role);
    }

    /**
     * Activates the user account with the given activation code.
     *
     * @param activationCode The activation code associated with the user account.
     * @return The new activation token which replaces the given code once the activation is done.
     */
    public Token activateAccount(String activationCode) {
        // Attempt to retrieve the user by the activation code.x
        Optional<UserAccount> accountOpt = this.findByActivationCode(activationCode);

        // Check if the there is such an account:
        if (!accountOpt.isPresent()) {
            throw new IllegalArgumentException("Invalid activation code.");
        }

        // Get the account:
        UserAccount account = accountOpt.get();

        // Set the account to enabled:
        account.setEnabled(true);

        // Invalidate the account activation code:
        account.invalidateActivationCode();

        // Update the identity manager for the account:
        this.identityManager.update(account);

        // Issue a new token and return:
        return this.issueToken(account);
    }

    /**
     * Enables the user account.
     *
     * @param account The user account to be enabled.
     */
    public void enableAccount(UserAccount account) {
        // Check if the account has administrator role:
        if (this.hasRole(account, AuthorizationRoles.ADMIN)) {
            throw new IllegalArgumentException("Administrator accounts can not be enabled.");
        }

        // Set enabled:
        account.setEnabled(true);

        // Invalidate the activation code:
        account.invalidateActivationCode();

        // Update the identity manager, if account is persisted:
        if (account.getId() != null) {
            this.identityManager.update(account);
        }
    }

    /**
     * Disables the user account.
     *
     * @param account The user account to be enabled.
     */
    public void disableAccount(UserAccount account) {
        // Check if the account has administrator role:
        if (this.hasRole(account, AuthorizationRoles.ADMIN)) {
            throw new IllegalArgumentException("Administrator accounts can not be disabled.");
        }

        // Set disabled:
        account.setEnabled(false);

        // Update the identity manager and issue token, if account is persisted:
        if (account.getId() != null) {
            // Issue the token (also invalidate the current one):
            this.issueToken(account);

            // Update the identity manager with the account info:
            this.identityManager.update(account);
        }
    }

    /**
     * Issues a new token.
     *
     * @param account The account for which a new token will be issued.
     *
     * @return The newly issued token.
     */
    private Token issueToken(Account account) {
        // Issue the new token for the account:
        return this.tokenProvider.issue(account);
    }
}
