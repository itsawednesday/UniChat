package com.mapchat.server.controller.data.params;


/**
 * 
 * Get contact parameters
 *
 */
public class GetContactParam {
	private String emailId;

	public GetContactParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetContactParam(String username) {
		super();
		this.emailId = username;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String username) {
		this.emailId = username;
	}
}
