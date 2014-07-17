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

import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

/**
 * Defines a simple, demonstration only authenticator.
 * 
 * @author Vehbi Sinan Tunalioglu
 */
@ApplicationScoped
public class Authenticator {

	/**
	 * Defines a static database for demonstration purposes.
	 */
	private static Map<String, User> Database;
	
	// Initialize a demo database:
	static {
		Authenticator.Database = new HashMap<String, User>();
		Authenticator.Database.put("admin", new User("admin", "admin", new HashSet<String>(Arrays.asList(AuthRoles.ADMIN))));
		Authenticator.Database.put("user", new User("user", "user", new HashSet<String>(Arrays.asList(AuthRoles.USER))));
		Authenticator.Database.put("guest", new User("guest", "guest"));
	}

	/**
	 * Attempts to authenticate a user.
	 *  
	 * @param request The client request object.
	 * @return The principle.
	 */
	public Principal authenticate(HttpServletRequest request) {
		// Attempt to get the authentication hueader:
		String authHeader = request.getHeader("Authorization");
		
		// Check the authentication header:
		if (authHeader == null || !authHeader.startsWith("Basic ")) {
			return null;
		}
		
		// Get the encoded username and password:
        final String encodedTokens = authHeader.replaceFirst("Basic ", "");

        // Decode the username and password:
        // TODO: Following statement may raise exception if decoding is unsuccessful. Fail gracefully if so.
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
        
        // Get the user with the username:
        User user = Authenticator.Database.get(username);
        
        // Check the user:
        if (user == null) {
        	// No such user. Return null:
        	return null;
        }
        
        // Check the password:
        if (!user.getPassword().equals(password)) {
        	// Password does not match:
        	return null;
        }
        
        // Well done, return the user:
        return user;		
	}
	
}