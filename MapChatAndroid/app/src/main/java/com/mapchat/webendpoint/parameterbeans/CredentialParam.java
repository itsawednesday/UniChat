package com.mapchat.webendpoint.parameterbeans;

import java.util.Objects;

public class CredentialParam {
    private String emailId;
    private String password;

    public CredentialParam() {
    }

    public CredentialParam(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CredentialParam that = (CredentialParam) o;
        return Objects.equals(emailId, that.emailId) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, password);
    }
}
