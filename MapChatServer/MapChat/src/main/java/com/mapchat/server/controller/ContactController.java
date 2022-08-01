package com.mapchat.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mapchat.server.controller.data.params.ContactRequestParam;
import com.mapchat.server.controller.data.params.GetContactParam;
import com.mapchat.server.controller.data.params.SearchContactParam;
import com.mapchat.server.controller.data.rparams.Profile;
import com.mapchat.server.model.ContactRepository;
import com.mapchat.server.model.UserRepository;
import com.mapchat.server.model.tables.Contact;
import com.mapchat.server.model.tables.User;

/**
 * 
 * Contact controller class used for managing contact REST requests
 *
 */

@RestController
public class ContactController {

	@Autowired
	ContactRepository contactRepository;
	@Autowired
	UserRepository userRepository;

	/**
	 * Returns all contacts of the user
	 * @param param
	 * @return
	 */
	
	@PostMapping("/GetContacts")
	@ResponseBody
	public List<Contact> getContacts(@RequestBody GetContactParam param) {
		System.out.println("Checking " + param.getEmailId());

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getEmailId())) {
			return null;
		}

		return contactRepository.findByContacts(param.getEmailId());
	}
	
	/**
	 * 
	 * Display request received messaged by requested user.
	 * @param param
	 * @return
	 */

	@PostMapping("/GetContactRequestReceived")
	@ResponseBody
	public List<Contact> getContactRequestReceived(@RequestBody GetContactParam param) {
		System.out.println("Checking " + param.getEmailId());

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getEmailId())) {
			return null;
		}

		return contactRepository.findByContactRequestReceived(param.getEmailId());
	}

	
	/**
	 * Returns request sent message
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/GetContactRequestSent")
	@ResponseBody
	public List<Contact> getContactRequestSent(@RequestBody GetContactParam param) {
		System.out.println("Checking " + param.getEmailId());

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getEmailId())) {
			return null;
		}

		return contactRepository.findByContactRequestSent(param.getEmailId());
	}
	
	/**
	 * Method for sending the friend request to the specified user 
	 * Find user by email id
	 * @param param
	 * @return
	 */

	@PostMapping("/SendContactRequest")
	@ResponseBody
	public Boolean sendContactRequest(@RequestBody ContactRequestParam param) {

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getUserEmailId()) || !checkEmail(param.getContactEmailId())) {
			return false;
		}

		try {
			List<Contact> contacts = contactRepository.findByContact(param.getUserEmailId(), param.getContactEmailId());

			if (contacts != null && contacts.size() > 0) {
				return false;
			} else {
				// Add if not already added
				int count = contactRepository.insertContact(param.getUserEmailId(), param.getContactEmailId());
				if (count > 0) {
					return true;
				}

			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}

		return false;
	}

	
	/**
	 * 
	 * Accepts the friend request
	 * @param param
	 * @return
	 */
	@PostMapping("/AcceptContactRequest")
	@ResponseBody
	public Boolean acceptRequest(@RequestBody ContactRequestParam param) {

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getUserEmailId()) || !checkEmail(param.getContactEmailId())) {
			return null;
		}

		try {
			List<Contact> contacts = contactRepository.findByContact(param.getUserEmailId(), param.getContactEmailId());

			if (contacts != null && contacts.size() > 0) {
				return null;
			} else {
				// Add if not already added
				contacts = contactRepository.findByContact(param.getContactEmailId(), param.getUserEmailId());

				// If Not yet added as a friend to each other
				if (contacts != null) {

					// Add this user as contact in the other user
					contactRepository.insertContact(param.getUserEmailId(), param.getContactEmailId());
					// update it to friend in both users
					int count = contactRepository.setContactAccepted(param.getUserEmailId(), param.getContactEmailId());
					count = contactRepository.setContactAccepted(param.getContactEmailId(), param.getUserEmailId());
					if (count > 0) {
						return true;
					}
				}

			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}

		return false;
	}

	
	/**
	 * 
	 * Adds reject request functionality
	 * @param param
	 * @return
	 */
	@PostMapping("/RejectContactRequest")
	@ResponseBody
	public Boolean rejectRequest(@RequestBody ContactRequestParam param) {

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getUserEmailId()) || !checkEmail(param.getContactEmailId())) {
			return null;
		}

		try {
			List<Contact> contacts = contactRepository.findByContact(param.getUserEmailId(), param.getContactEmailId());

			if (contacts != null && contacts.size() > 0) {
				return null;
			} else {
				int count = contactRepository.setContactRejected(param.getContactEmailId(), param.getUserEmailId());
				if (count > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}

		return false;
	}
	
	/*
	 * Removes the existing contact
	 * 
	 */

	@PostMapping("/RemoveContactRequest")
	@ResponseBody
	public Boolean removeRequest(@RequestBody ContactRequestParam param) {

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getUserEmailId()) || !checkEmail(param.getContactEmailId())) {
			return null;
		}

		try {
			List<Contact> contacts = contactRepository.findByContact(param.getUserEmailId(), param.getContactEmailId());

			if (contacts != null && contacts.size() > 0) {
				return null;
			} else {

				int count = contactRepository.setContactRejected(param.getUserEmailId(), param.getContactEmailId());
				if (count > 0) {
					return true;
				}

			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}

		return false;
	}
	
	/**
	 * Searches for new contacts by email id or name
	 * @param param
	 * @return
	 */

	@PostMapping("/SearchContacts")
	@ResponseBody
	public List<Profile> searchContacts(@RequestBody SearchContactParam param) {

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getEmailId())) {
			return null;
		}

		try {

			if (checkEmail(param.getSearchText())) {
				User u = userRepository.findByEmail(param.getEmailId());
				if (u != null) {
					List<Profile> contacts = new ArrayList<Profile>();
					contacts.add(new Profile(u.getId(), u.getName(), u.getEmailId()));
					return contacts;
				} else {
					return null;
				}
			} else {
				List<User> urs = userRepository.findByNameContainingIgnoreCase(param.getSearchText());
				if (urs != null) {
					List<Profile> contacts = new ArrayList<Profile>();
					for (User u : urs) {
						contacts.add(new Profile(u.getId(), u.getName(), u.getEmailId()));
					}
					return contacts;
				}
			}

		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}

		return null;
	}

	private boolean checkEmail(String emailId) {
		return (emailId.lastIndexOf("@myport.ac.uk") <= 9 && emailId.lastIndexOf("@myport.ac.uk") >= 7);
	}

}
