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

package com.vsthost.jee7.sandbox.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vsthost.jee7.sandbox.services.exceptions.NoSuchUser;
import com.vsthost.jee7.sandbox.services.exceptions.NoUserForCredentials;
import com.vsthost.jee7.sandbox.services.exceptions.UsernameNotAvailable;

/**
 * Provides a User management service.
 * 
 * @author Vehbi Sinan Tunalioglu
 */
@Stateless
public class UserService {
	@Inject
	private EntityManager em;
	
//	/**
//	 * Returns a user if there is a user found matching
//	 * the username and the password. Raises exception otherwise.
//	 *
//	 * @param username the username of the user.
//	 * @param password the password of the user.
//	 * @return A user instance.
//	 * @throws NoUserForCredentials
//	 */
//	public User getByCredentials (String username, String password) throws NoUserForCredentials {
//		// Try to get a user:
//        Query query = em
//        		.createNamedQuery("User.findByCredentials")
//        		.setParameter("username", username)
//        		.setParameter("password", password)
//        		.setMaxResults(1);
//
//        // Get a result:
//		List<?> result = query.getResultList();
//
//		// Check the result:
//        if (result.isEmpty()) {
//        	// No user with the credentials. Raise exception:
//        	throw new NoUserForCredentials();
//        }
//
//        // Done, return the user:
//        return (User) result.get(0);
//	}
//
//	/**
//	 * Returns a user if there is a user found matching
//	 * the username. Raises exception otherwise.
//	 *
//	 * @param username the username of the user.
//	 * @return A user.
//	 * @throws NoSuchUser
//	 */
//	public User getByUsername (String username) throws NoSuchUser {
//		// Try to get a user:
//        Query query = em
//        		.createNamedQuery("User.findByUsername")
//        		.setParameter("username", username)
//        		.setMaxResults(1);
//
//        // Get a result:
//		List<?> result = query.getResultList();
//
//		// Check the result:
//        if (result.isEmpty()) {
//        	// No such user. Throw an exception:
//        	throw new NoSuchUser(username);
//        }
//
//        // Done, return the user:
//        return (User) result.get(0);
//	}
//
//	/**
//	 * Returns true if the username is available.
//	 *
//	 * @param username the username to be checked.
//	 * @return True if the username is not taken yet, False if the
//	 * username is already taken.
//	 */
//	public boolean isUsernameAvailable (String username) {
//		return em
//				.createNamedQuery("User.findByUsername")
//        		.setParameter("username", username)
//        		.getResultList().isEmpty();
//	}
//
//	/**
//	 * Adds a new user to the system with given username, password and
//	 * roles.
//	 *
//	 * @param username the username of the user.
//	 * @param password the password of the user.
//	 * @param roles list of roles of the user.
//	 * @return The newly created user.
//	 * @throws UsernameNotAvailable
//	 */
//	public User addUser (String username, String password, Set<String> roles) throws UsernameNotAvailable {
//		// Check if the username is available:
//		if (!this.isUsernameAvailable(username)) {
//			// Nope, username is not available. Raise exception:
//			throw new UsernameNotAvailable(username);
//		}
//
//		// Create the instance:
//		User user = new User(username, password, roles);
//
//		// Persist the instance:
//		em.persist(user);
//		em.flush();
//
//		// Done, return with the user.
//		return user;
//	}
//
//	/**
//	 * Returns all users.
//	 *
//	 * @return All users.
//	 */
//	public List<User> getAllUsers () {
//        return em.createNamedQuery("User.findAll", User.class).getResultList();
//	}
//
//	/**
//	 * Updates the password of the user with the username.
//	 *
//	 * @param username the username of the user.
//	 * @param password the new password of the user.
//	 * @return true if there is an update.
//	 */
//	public boolean changePassword(String username, String password) {
//		return em
//				.createNamedQuery("User.updatePassword")
//				.setParameter("username", username)
//				.setParameter("password", password)
//				.executeUpdate() > 0;
//	}
//
//	/**
//	 * Updates password and roles of the user defined by the username.
//	 *
//	 * @param username the username of the user
//	 * @param password an optional password to be updated. If not present,
//	 * password is not updated.
//	 * @param roles the new roles of the user.
//	 * @return true if there is an update.
//	 */
//	public boolean update(String username, Optional<String> password, List<String> roles) {
//		// Prepare the roles parameter:
//		String rolesParam = String.join(":", roles);
//
//		// Attempt to change the password:
//		Query query = null;
//		if (password.isPresent()) {
//			query = em
//					.createNamedQuery("User.update")
//					.setParameter("username", username)
//					.setParameter("password", password.get())
//					.setParameter("roles", rolesParam);
//		}
//		else {
//			query = em
//					.createNamedQuery("User.updateRoles")
//					.setParameter("username", username)
//					.setParameter("roles", rolesParam);
//		}
//
//		// Done with query. Execute:
//		return query.executeUpdate() > 0;
//	}
}
