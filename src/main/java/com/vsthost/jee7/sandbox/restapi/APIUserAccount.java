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

package com.vsthost.jee7.sandbox.restapi;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.vsthost.jee7.sandbox.security.models.AccountOpeningInfo;
import com.vsthost.jee7.sandbox.security.models.AuthorizationRoles;
import com.vsthost.jee7.sandbox.security.models.UserAccount;
import com.vsthost.jee7.sandbox.security.services.UserAccountService;
import org.picketlink.authorization.annotations.LoggedIn;

/**
 * Defines a secured REST API endpoint for user management.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Path("/useraccounts")
@LoggedIn
@RolesAllowed(AuthorizationRoles.ADMIN)
public class APIUserAccount {
    @Inject
    private UserAccountService userAccountService;

	@Inject
	private Logger logger;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserAccount> getUserAccounts (@Context HttpServletRequest request) {
		return this.userAccountService.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserAccount createUserAccount (@Context HttpServletRequest request, AccountOpeningInfo aoinfo) {
		// Attempt to create one and returns:
		return this.userAccountService.create(aoinfo);
	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserAccount getUserAccount (@Context HttpServletRequest request, @PathParam("id") String id) {
		// Get an optional user account:
		Optional<UserAccount> optua = this.userAccountService.getById(id);

		// Check and return:
		if (optua.isPresent()) {
			return optua.get();
		}

		// Raise a 404:
		throw new WebApplicationException(HttpURLConnection.HTTP_NOT_FOUND);
	}
}
