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

import java.util.HashSet;
import java.util.Set;
import java.security.Principal;

/**
 * Defines a generic user class.
 * 
 * @author Vehbi Sinan Tunalioglu
 */
public class User implements Principal {	
	/**
	 * Defines the username of the user.
	 */
	private String username;
	
	/**
	 * Defines the password of the user.
	 */
	private String password;
	
	/**
	 * Defines a set of roles of the user.
	 */
	private Set<String> roles = new HashSet<String>();

	/**
	 * Constructor using fields.
	 * 
	 * @param username the username of the user.
	 * @param password the password of the user.
	 */
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor using fields.
	 * 
	 * @param username the username of the user.
	 * @param password the password of the user.
	 * @param roles the set of roles defined for the user.
	 */
	public User(String username, String password, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getUsername();
	}

	@Override
	public String getName() {
		return this.getUsername();
	}

	/**
	 * Returns true if the user is in any of the roles provided.
	 * 
	 * @param roles
	 * @return true if the user is in any of the roles provided.
	 */
	public boolean hasAnyRole (Set<String> rolesToCheck) {
		if (rolesToCheck.isEmpty()) {
			return true;
		}
		else if (this.roles.isEmpty()) {
			return false;
		}
		return rolesToCheck.containsAll(this.roles);
	}

}