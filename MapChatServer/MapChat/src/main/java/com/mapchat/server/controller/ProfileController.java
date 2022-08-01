package com.mapchat.server.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mapchat.server.controller.data.params.LoginParam;
import com.mapchat.server.controller.data.params.RegisterParam;
import com.mapchat.server.controller.data.params.VerifyEmailParam;
import com.mapchat.server.controller.data.rparams.Profile;
import com.mapchat.server.model.UserRepository;
import com.mapchat.server.model.tables.User;


/**
 * Profile controller class for managing the user profile 
 * 
 */
@RestController
public class ProfileController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	
	/**
	 * Log in authentication
	 * Checks for invalid email input
	 * @param param
	 * @return
	 */
	@PostMapping("/Login")
	@ResponseBody
	public Profile authenticate(@RequestBody LoginParam param) {
		System.out.println("Checking " + param.getEmailId());

		// check for university email id @myport.ac.uk
		if (!checkEmail(param.getEmailId())) {
			return null;
		}

		User u = userRepository.findByEmailIdnPassword(param.getEmailId(), param.getPassword());

		if (u == null) {
			return null;
		}

		Profile p = new Profile(u.getId(), u.getName(), u.getEmailId());

		return p;
	}
	
	/**
	 * Register method
	 * Adds the user data to the database (After validation)
	 * @param param
	 * @return
	 */

	@PostMapping("/Register")
	@ResponseBody
	public Boolean register(@RequestBody RegisterParam param) {
		System.out.println("Checking " + param.getEmailId() + " " + param.getName() + " " + param.getPassword());
		Boolean isSuccess = false;

		if (!checkEmail(param.getEmailId())) {
			return false;
		}

		try {

			User u = userRepository.findByEmail(param.getEmailId());
			if (u != null) {
				System.out.print("Already Registered");
				return false;
			}

			Random rand = new Random(); // instance of random class
			int random = rand.nextInt(10000);

			System.out.println("Random Number is " + random);

			if (!sendEmail(param.getEmailId(), param.getName(), random + "")) {
				return false;
			}

			userRepository.insertProfile(param.getName(), param.getEmailId(), param.getPassword(), random);
			isSuccess = true;
		} catch (Exception e) {
			System.out.println("Error Saving " + e.getMessage());
		}

		return isSuccess;
	}
	
	/**
	 * 
	 * Method for verifying the OTP number
	 * @param param
	 * @return
	 */

	@PostMapping("/VerifyEmail")
	@ResponseBody
	public Profile verifyEmail(@RequestBody VerifyEmailParam param) {
		System.out.println("Checking " + param.getEmailId() + " OTP " + param.getOtp());
		Profile p = null;

		if (!checkEmail(param.getEmailId())) {
			return p;
		}

		try {

			long otp = Long.parseLong(param.getOtp());

			User u = userRepository.verifyByEmailOTP(param.getEmailId(), otp);
			if (u != null) {
				int count = userRepository.setProfileVerified(param.getEmailId());
				if (count > 0) {
					p = new Profile();
					p.setProfileName(u.getName());
				}
			}

		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
		return p;
	}

	@PostMapping("/SayHello")
	@ResponseBody
	public String sayhelloooo(@RequestBody String cred) {

		return "request received";
	}

	private boolean checkEmail(String emailId) {
		return (emailId.lastIndexOf("@myport.ac.uk") <= 9 && emailId.lastIndexOf("@myport.ac.uk") >= 7);
	}

	private boolean sendEmail(String emailId, String name, String otp) {
		boolean isSuccess = true;
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(emailId);

			msg.setSubject("Map Chat Verification Code");
			msg.setText("Hi " + name + ", \nYour Verification Code is " + otp);

			javaMailSender.send(msg);
		} catch (Exception e) {
			System.out.println("Error Sending Mail : " + e.getMessage());
			isSuccess = false;
		}

		return isSuccess;

	}

}
