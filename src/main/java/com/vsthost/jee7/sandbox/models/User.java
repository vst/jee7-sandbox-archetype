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

package com.vsthost.jee7.sandbox.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Defines a generic, simple user entity.
 * 
 * @author Vehbi Sinan Tunalioglu
 */
@Entity
@XmlRootElement
@XmlType(propOrder={"id", "username", "roles"})
@NamedQueries({
    @NamedQuery(name="User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name="User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name="User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name="User.findByCredentials", query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"),
    @NamedQuery(name="User.update", query="UPDATE User u SET u.password = :password, u.roles = :roles WHERE u.username = :username"),
    @NamedQuery(name="User.updatePassword", query="UPDATE User u SET u.password = :password WHERE u.username = :username"),
    @NamedQuery(name="User.updateRoles", query="UPDATE User u SET u.roles = :roles WHERE u.username = :username"),

})
public class User {
	/**
	 * Defines the internal id of the user.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Defines the username of the user.
	 */
    @NotNull
	@Column(unique=true)
	private String username;
	
	/**
	 * Defines the password of the user.
	 */
	private String password;
	
	/**
	 * Defines a set of roles for the user.
	 */
	private String roles;
	
	/**
	 * Default constructor.
	 */
	public User () { }

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
	 * @param roles a set of roles defined for the user.
	 */
	public User(String username, String password, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = String.join(":", roles);
	}
	
	/**
	 * @return the id
	 */
    @XmlTransient
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
    @XmlTransient
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
		return new HashSet<String>(Arrays.asList(this.roles.split(":")));
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<String> roles) {
		this.roles = String.join(":", roles);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("User [id=%d, username=%s]", this.getId(), this.getUsername());
	}
}