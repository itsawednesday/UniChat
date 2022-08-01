package com.mapchat.server.model.tables;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * Class for generation of the contacts table (entity)
 */
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String userEmailId;
	private String contactEmailId;
	/*
	 * 0 = Request Sent, 1 = Accepted, 2 = Blocked
	 */
	private int status;

	public Contact() {
		super();
	}

	public Contact(Long id, String userEmailId, String contactEmailId, int status) {
		super();
		this.id = id;
		this.userEmailId = userEmailId;
		this.contactEmailId = contactEmailId;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
