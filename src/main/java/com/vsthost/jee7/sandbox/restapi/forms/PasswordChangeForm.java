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

package com.vsthost.jee7.sandbox.restapi.forms;

/**
 * Provides a password change form.
 *
 * @author Vehbi Sinan Tunalioglu
 */
public class PasswordChangeForm {
    /**
     * Defines the old password of the user account.
     */
    private String oldPassword;

    /**
     * Defines the new password of the user account.
     */
    private String newPassword;

    /**
     * Defines the confirmation for the new password of the user account.
     */
    private String newPasswordConfirmation;

    /**
     * Default empty constructor.
     */
    public PasswordChangeForm () {}

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    /**
     * Checks if the supplied form information is valid.
     *
     * @return Boolean indicating if the supplied form information is valid.
     */
    public boolean isValid () {
        return newPassword.equals(newPasswordConfirmation);
    }
}
