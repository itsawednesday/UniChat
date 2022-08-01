package com.mapchat.server.controller.data.params;

/**
 * 
 * Gets login parameters
 *
 */
public class LoginParam {
	private String emailId;
	private String password;

	public LoginParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginParam(String username, String password) {
		super();
		this.emailId = username;
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String username) {
		this.emailId = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
