package com.mapchat.server.controller.data.rparams;

/**
 * 
 * User profile class
 *
 */
public class Profile {
	private long id;
	private String profileName;
	private String emailId;

	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Profile(long id, String profileName, String emailId) {
		super();
		this.id = id;
		this.profileName = profileName;
		this.emailId = emailId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
