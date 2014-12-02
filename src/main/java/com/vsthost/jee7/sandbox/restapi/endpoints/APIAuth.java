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

package com.vsthost.jee7.sandbox.restapi.endpoints;

import com.vsthost.jee7.sandbox.security.token.JWSToken;
import org.picketlink.Identity;
import org.picketlink.authorization.annotations.LoggedIn;
import org.picketlink.idm.credential.Token;
import org.picketlink.idm.model.Account;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Provides user account related services.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Path("/auth")
public class APIAuth {
    @Inject
    private Token.Provider<JWSToken> tokenProvider;

    @Inject
    private Identity identity;

    @POST
    @LoggedIn
    @Path("/logout/")
    public void logout() {
        // Get the user account:
        Account account = this.identity.getAccount();

        // Invalidate the token:
        this.tokenProvider.invalidate(account);

        // Logout the user account:
        this.identity.logout();
    }

}
