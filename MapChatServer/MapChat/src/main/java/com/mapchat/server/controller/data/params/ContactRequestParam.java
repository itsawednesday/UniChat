package com.mapchat.server.controller.data.params;

/**
 * Class for defining contact request parameters
 *
 */
public class ContactRequestParam {
	private String userEmailId;
	private String contactEmailId;

	public ContactRequestParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContactRequestParam(String userEmailId, String contactEmailId) {
		super();
		this.userEmailId = userEmailId;
		this.contactEmailId = contactEmailId;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getContactEmailId() {
		return contactEmailId;
	}

	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}

}
