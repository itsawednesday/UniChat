package com.mapchat.server.controller.data.params;

/**
 * Class for search contact parameters
 *
 */
public class SearchContactParam {
	private String emailId;
	private String searchText;

	public SearchContactParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchContactParam(String emailId, String searchText) {
		super();
		this.emailId = emailId;
		this.searchText = searchText;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
