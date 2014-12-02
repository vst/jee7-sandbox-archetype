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

package com.vsthost.jee7.sandbox.restapi.endpoints;

import com.vsthost.jee7.sandbox.restapi.forms.PasswordChangeForm;
import com.vsthost.jee7.sandbox.restapi.utils.HTTPResponseBuilder;
import com.vsthost.jee7.sandbox.security.models.UserAccount;
import com.vsthost.jee7.sandbox.security.services.UserAccountService;
import org.picketlink.authorization.annotations.LoggedIn;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Defines a secured REST API endpoint for self account management.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Path("/me")
@LoggedIn
public class APIMe {
    @Inject
    private UserAccountService userAccountService;

	@Inject
	private Logger logger;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserAccount get(@Context HttpServletRequest request) {
		// Get the optional user account:
		Optional<UserAccount> optua = this.userAccountService.findByUsername(request.getUserPrincipal().getName());

		// Check and return:
		if (optua.isPresent()) {
			return optua.get();
		}

		// Raise a 500 (Why would that happen anyway?):
		throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
	}

	@Path("/password")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(@Context HttpServletRequest request, PasswordChangeForm form) {
		// Check form inputs:
		if (!form.isValid()) {
			throw new IllegalArgumentException("Passwords don't match");
		}

		// Get the optional user account:
		Optional<UserAccount> optua = this.userAccountService.findByUsername(request.getUserPrincipal().getName());

		// Check and return:
		if (!optua.isPresent()) {
			// Raise a 500 (Why would that happen anyway?):
			throw new WebApplicationException(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}

		// Check the old password:
		if (!this.userAccountService.checkCredentials(optua.get().getUsername(), form.getOldPassword())) {
			throw new IllegalArgumentException("Password is invalid.");
		}

		// Change the password:
		this.userAccountService.updatePassword(optua.get(), form.getNewPassword());

		// Done, return good status:
		return HTTPResponseBuilder.ok().build();
	}
}
