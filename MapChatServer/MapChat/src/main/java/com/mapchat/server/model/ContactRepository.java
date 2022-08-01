package com.mapchat.server.model;

/**
 * Contacts repository
 * 
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mapchat.server.model.tables.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	@Query("Select c From Contact c Where c.userEmailId = ?1 and c.status = 1")
	List<Contact> findByContacts(String emailId);

	@Query("Select c From Contact c Where c.userEmailId = ?1 and c.status = 0")
	List<Contact> findByContactRequestSent(String emailId);

	@Query("Select c From Contact c Where c.contactEmailId = ?1 and c.status = 0")
	List<Contact> findByContactRequestReceived(String emailId);

	@Query("Select c From Contact c Where c.userEmailId = ?1 and c.contactEmailId = ?2")
	List<Contact> findByContact(String userEmailId, String contactEmailId);

	// Send Request
	@Modifying
	@Query(value = "Insert Into Contact (user_email_id, contact_email_id, status) Values (:userEmailId, :contactEmailId, 0)", nativeQuery = true)
	@Transactional
	int insertContact(@Param("userEmailId") String userEmailId, @Param("contactEmailId") String contactEmailId);

	// Accept Request
	@Modifying
	@Query(value = "Update Contact u Set u.status = 1 Where u.user_email_id = ?1 and u.contact_email_id = ?2", nativeQuery = true)
	@Transactional
	int setContactAccepted(String userEmailId, String contactEmailId);

	// Request Reject/Removed
	@Modifying
	@Query(value = "Delete From Contact u Where u.user_email_id = ?1 and u.contact_email_id = ?2", nativeQuery = true)
	@Transactional
	int setContactRejected(String userEmailId, String contactEmailId);

}
