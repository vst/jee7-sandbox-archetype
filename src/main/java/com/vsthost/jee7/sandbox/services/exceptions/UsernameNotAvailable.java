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

package com.vsthost.jee7.sandbox.services.exceptions;

/**
 * A high level exception for end users indicating that the username
 * queried is taken or not available.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class UsernameNotAvailable extends HighLevelException {
    private static final long serialVersionUID = 1L;

    public UsernameNotAvailable(String username) {
        super(HighLevelException.USERNAME_NOT_AVAILABLE, "The user name \"" + username + "\" is not available.");
    }
}
