package com.mapchat.server.controller.data.params;

/**
 * 
 * Class for register parameters
 *
 */

public class RegisterParam {
	private String name;
	private String emailId;
	private String password;

	public RegisterParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterParam(String name, String emailId, String password) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
