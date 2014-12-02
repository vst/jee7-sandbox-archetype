package com.vsthost.jee7.sandbox.restapi.serializers;

/**
 * Created by vst on 2/12/14.
 */
public class PasswordChangeForm {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;

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

    public boolean isValid () {
        return newPassword.equals(newPasswordConfirmation);
    }
}
