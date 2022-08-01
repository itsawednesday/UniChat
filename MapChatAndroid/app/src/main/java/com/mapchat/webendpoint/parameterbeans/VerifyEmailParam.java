package com.mapchat.webendpoint.parameterbeans;

public class VerifyEmailParam {
    private String emailId;
    private String otp;

    public VerifyEmailParam() {
    }

    public VerifyEmailParam(String emailId, String otp) {
        this.emailId = emailId;
        this.otp = otp;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
