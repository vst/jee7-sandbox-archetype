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

package com.vsthost.jee7.sandbox.security;

import java.util.Base64;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.vsthost.jee7.sandbox.models.User;
import com.vsthost.jee7.sandbox.services.UserService;
import com.vsthost.jee7.sandbox.services.exceptions.NoUserForCredentials;

/**
 * Defines a simple authenticator.
 * 
 * <p>
 * 
 * Note that the password is kept as a plain password in the database.
 * For production purposes, hashing is needed.
 * 
 * @author Vehbi Sinan Tunalioglu
 */
@ApplicationScoped
public class Authenticator {
	@Inject
	private UserService userService;
	
	/**
	 * Attempts to authenticate a user.
	 *  
	 * @param request the client request object.
	 * @return An optional user if the authentication is successful.
	 * @throws Web application exceptions if there is a "BAD_REQUEST".
	 */
	public Optional<User> authenticate(HttpServletRequest request) {
		// Attempt to get the authentication header:
		String authHeader = request.getHeader("Authorization");
		
		// Check the authentication header:
		if (authHeader == null || !authHeader.startsWith("Basic ")) {
			return null;
		}
		
		// Get the encoded username and password:
        final String encodedTokens = authHeader.replaceFirst("Basic ", "");

        // Decode the username and password:
        String decodedTokens = null;
        try {
        	decodedTokens = new String(Base64.getDecoder().decode(encodedTokens.getBytes()));
        }
        catch (IllegalArgumentException e) {
        	// Broken request, raise Bad Request:
        	throw new WebApplicationException(Status.BAD_REQUEST);
        }

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(decodedTokens, ":");
		
        // Get the username:
        String username = null; 
        if (tokenizer.hasMoreTokens()) {
        	username = tokenizer.nextToken();
        }
        else {
        	// Broken request, raise Bad Request:
        	throw new WebApplicationException(Status.BAD_REQUEST);
        }
        
        // Get the password:
        String password = null; 
        if (tokenizer.hasMoreTokens()) {
        	password = tokenizer.nextToken();
        }
        else {
        	// Broken request, raise Bad Request:
        	throw new WebApplicationException(Status.BAD_REQUEST);
        }
        
        // Get an optional user and return:
        try {
			return Optional.of(this.userService.getByCredentials(username, password));
		}
        catch (NoUserForCredentials e) {
        	return Optional.empty();
        }
	}
}