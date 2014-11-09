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

package com.vsthost.jee7.sandbox.restapi.secured;

//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//import javax.annotation.security.PermitAll;
//import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DefaultValue;
//import javax.ws.rs.FormParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response.Status;
//
//import com.vsthost.jee7.sandbox.models.User;
//import com.vsthost.jee7.sandbox.security.models.AuthRoles;
//import com.vsthost.jee7.sandbox.security.Secured;
//import com.vsthost.jee7.sandbox.security.SecurityInterceptor.UserPrincipal;
import com.vsthost.jee7.sandbox.services.UserService;
//import com.vsthost.jee7.sandbox.services.exceptions.NoSuchUser;
//import com.vsthost.jee7.sandbox.services.exceptions.NoUserForCredentials;
//import com.vsthost.jee7.sandbox.services.exceptions.UsernameNotAvailable;

/**
 * Defines a secured REST API endpoint for user management.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Path("/")
public class APIUser {	
	@Inject
	private UserService userService;
	
//	@Path("/users")
//	@GET
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	@Secured
//	@RolesAllowed({AuthRoles.ADMIN})
//	public List<User> getUsers (@Context HttpServletRequest request) {
//		return this.userService.getAllUsers();
//	}
//
//	@Path("/users/{username}")
//	@GET
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	@Secured
//	@RolesAllowed({AuthRoles.ADMIN})
//	public User getUser (
//			@Context HttpServletRequest request,
//			@PathParam("username") String username) {
//		try {
//			return this.userService.getByUsername(username);
//		}
//		catch (NoSuchUser e) {
//			// This can happen:
//			throw new WebApplicationException(e.toJson(), Status.NOT_FOUND);
//		}
//	}
//
//	@Path("/users")
//	@PUT
//	@Secured
//	@RolesAllowed({AuthRoles.ADMIN})
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	public User createUser (
//			@Context HttpServletRequest request,
//			@FormParam("username") String username,
//			@FormParam("password") String password,
//			@FormParam("role") String role) {
//		try {
//			return this.userService.addUser(username, password, new HashSet<String>(Arrays.asList(role)));
//		}
//		catch (UsernameNotAvailable e) {
//			throw new WebApplicationException(e.toJson(), Status.BAD_REQUEST);
//		}
//	}
//
//
//	@Path("/users/{username}")
//	@POST
//	@Secured
//	@RolesAllowed({AuthRoles.ADMIN})
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public void update (
//			@Context HttpServletRequest request,
//			@PathParam("username") String username,
//			@DefaultValue("") @FormParam("password") String password,
//			@FormParam("roles") List<String> roles) {
//		this.userService.update(
//				username,
//				(password == "") ? Optional.empty() : Optional.of(password),
//				roles);
//	}
//
//	@Path("/me")
//	@GET
//	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	@Secured
//	@PermitAll
//	public User getMyUser (@Context HttpServletRequest request) {
//		try {
//			return this.userService.getByUsername(request.getUserPrincipal().getName());
//		}
//		catch (NoSuchUser e) {
//			// This is not normal. Why would that happen? We raise internal server error.
//			throw new WebApplicationException(e.toJson(), Status.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@Path("/me/password")
//	@POST
//	@Secured
//	@PermitAll
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	public void updateMyPassword (
//			@Context HttpServletRequest request,
//			@FormParam("oldPassword") String oldPassword,
//			@FormParam("newPassword") String newPassword) {
//		// Get the user principal:
//		UserPrincipal user = (UserPrincipal) request.getUserPrincipal();
//
//		// Try to authenticate the user with the old password:
//		try {
//			this.userService.getByCredentials(user.getName(), oldPassword);
//		}
//		catch (NoUserForCredentials e) {
//			throw new WebApplicationException(Status.UNAUTHORIZED);
//		}
//
//		// Change the password:
//		this.userService.changePassword(user.getName(), newPassword);
//	}
}