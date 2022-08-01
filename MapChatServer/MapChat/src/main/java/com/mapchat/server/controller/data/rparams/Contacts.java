package com.mapchat.server.controller.data.rparams;

import java.util.List;

import com.mapchat.server.model.tables.*;

/**
 * Saves list of contactsS
 * 
 */
public class Contacts {
	private List<Contact> contacts;

	public Contacts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contacts(List<Contact> contacts) {
		super();
		this.contacts = contacts;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

}
