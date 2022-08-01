package com.mapchat.server.controller.data.params;

/**
 * 
 * Class for verifying email parameters
 *
 */
public class VerifyEmailParam {
	private String emailId;
	private String otp;

	public VerifyEmailParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VerifyEmailParam(String email, String otp) {
		super();
		this.emailId = email;
		this.otp = otp;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String username) {
		this.emailId = username;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}
