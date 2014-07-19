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

import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.vsthost.jee7.sandbox.models.User;

/**
 * Provides a security interceptor based on HTTP requests.
 *
 * <p>
 *
 * This approach enables the use of a {@code @Secured} annotation on
 * an RS endpoint. Along with this annotation, the usual {@code
 * javax.annotation.security} annotations can be used for roles. This
 * interceptor will check for roles, too.
 *
 * <p>
 *
 * There are numerous alternatives to inject (sic) the principle into
 * the invoking method. The reason that this CDI method is preferred
 * over others is that (1) it injects principle into the HTTP request,
 * (2) it does not require additional libraries, and (3) it is
 * powerful enough to consider other annotations.
 *
 * <p>
 *
 * For a relatively detailed comparison of alternative methods, check
 * out <a href="https://github.com/lefloh/jax-rs-context">Florian
 * Hirsch's nice example</a>.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@Secured
@Interceptor
public class SecurityInterceptor {
	@Inject
	EntityManager em;

	@Inject
    private Logger logger;

    @Inject
    private Authenticator authenticator;

    /**
     * Authenticates and authorizes an HTTP servlet request.
     *
     * <p>
     *
     * This function consumes an invocation context. The context is
     * supposed to have a HttpServletRequest parameter set. The idea
     * is to check the request object, authenticate the user, update
     * the request object with the principle information, and
     * subsequently check if the principle is authorized for the
     * requested resource.
     *
     * @param context The invocation context.
     * @return Proceedings to the next interceptor.
     * @throws Exception
     */
    @AroundInvoke
    public Object secure(InvocationContext context) throws Exception {
        // Attempt to authenticate the user, update the request
        // instance and get the instance:
        logger.fine("Attempting to authenticate the HTTP Request.");
        HttpServletRequest request = this.authenticate(context);

        // Attempt to authorize the principle:
        logger.fine("Attempting to authorize the HTTP Request.");
        this.authorizeRequest(context, request);

        // Done, proceed with the next interceptor, if any:
        return context.proceed();
    }

	/**
     * Authenticates the HTTP request.
     *
     * <p>
     *
     * If authentication is not successful, HTTP 401 (UNAUTHORIZED)
     * will be raised. Otherwise, the request will be updated with the
     * principle information.
     *
     * @param context The context in which the request will be
     * authenticated.
     * @return An updated HTTP Servlet request.
     */
    private HttpServletRequest authenticate(InvocationContext context) {
        // Get all the parameters of the context:
        Object[] parameters = context.getParameters();

        // Get the index of the request parameter:
        int requestIndex = -1;
        for (int i=0; i < parameters.length; i++) {
        	if(parameters[i] instanceof HttpServletRequest) {
        		requestIndex = i;
        		break;
        	}
        }
        
        // Check if we have a request:
        if (requestIndex < -1) {
        	// No, we don't have a request. This is not right. We should
        	// have had a request instance. Raise server error:
            logger.severe("No HTTP Request found to be authenticated/authorized.");
            throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
        }
	
        // OK, we have a request, get it:
        HttpServletRequest request = (HttpServletRequest) parameters[requestIndex];
        
        // Attempt to get the principle:
        Optional<User> principal = authenticator.authenticate(request);

        // Check if the principle exists:
        if (!principal.isPresent()) {
            // Nope, we will raise an error: HTTP 401:
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        // OK, we have a principle. Update the request and return:
        HttpServletRequestWrapper updatedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public Principal getUserPrincipal() {
            	return new UserPrincipal(
            			principal.get().getUsername(),
            			principal.get().getRoles(),
            			principal.get());            	
            }
        };
        
        // Now, update the context with the new request instance:
        parameters[requestIndex] = updatedRequest;
        
        // Done, return with the updated request:
        return updatedRequest;
    }

    /**
     * Authorizes the request or raises FORBIDDEN/UNAUTHORIZED errors.
     * 
     * @param context the context in which the request is authorized.
     * @param request the request to be authorized.
     * @throws Web application exceptions for FORBIDDEN/UNAUTHORIZED
     * errors. 
     */
    private void authorizeRequest(InvocationContext context, HttpServletRequest request) {
    	// We will check if there is any security decorators. Get 
    	// the method first:
    	Method method = context.getMethod();
    	
    	// Check if we permit access to all:
        if (method.isAnnotationPresent(PermitAll.class)) {
            // Yes, we permit all access. Filtered with success. Return:
            return;
        }

        // Check if we deny access to all:
        if (method.isAnnotationPresent(DenyAll.class)) {
            // Yes, we deny access to all. Filtered with failure. Raise
        	// HTTP 401 (ACCESS DENIED):
            throw new WebApplicationException(Status.FORBIDDEN);
        }
        
        // Verify roles:
        if (method.isAnnotationPresent(RolesAllowed.class)) {
        	// Get the RolesAllowed annotation:
        	RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
        	
        	// Get the set of roles allowed:
        	Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
        	        	
        	// Get the user principal:
        	UserPrincipal userPrincipal = (UserPrincipal) request.getUserPrincipal();
        	
        	// Does user have any of these roles?
        	if (!userPrincipal.getRoles().stream().anyMatch(p -> rolesSet.contains(p))) {
        		// No, user does not have any of these roles. Filtered
        		// with failure. Raise HTTP 401 (ACCESS UNAUTHORIZED):
                throw new WebApplicationException(Status.UNAUTHORIZED);
            }
        }
    }
    
    /**
     * A public, inner class for modeling a simple HTTP Servlet
     * principle.
     */
    public class UserPrincipal implements Principal {
    	/**
    	 * Defines the name of the principal.
    	 */
    	private String name;
    	
    	/**
    	 * Defines the roles of the principal.
    	 */
    	private Set<String> roles; 
    	
    	/**
    	 * Defines an object for the principal.
    	 */
    	private Object object;

    	/**
    	 * Overriding default constructor.
    	 * 
    	 * @param name
    	 * @param roles
    	 * @param user
    	 */
		public UserPrincipal(String name, Set<String> roles, Object object) {
			this.setName(name);
			this.setRoles(roles);
			this.setObject(object);
		}

		/**
		 * @return the name
		 */
    	@Override
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
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

		/**
		 * @return the object
		 */
		public Object getObject() {
			return object;
		}

		/**
		 * @param object the object to set
		 */
		public void setObject(Object object) {
			this.object = object;
		}
		
		/**
		 * Returns true if the user has any such role.
		 * 
		 * @param role
		 * @return boolean flag.
		 */
		public boolean hasRole (String role) {
			return this.roles.contains(role);
		}
    }
}
