package com.mapchat.server.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mapchat.server.model.tables.*;

/**
 * User repository
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailId(String emailId);

	@Query("Select u From User u Where u.emailId = ?1 and u.password = ?2")
	User findByEmailIdnPassword(String emailId, String password);

	@Query("Select u From User u Where u.emailId = ?1")
	User findByEmail(String emailId);

	@Query("Select u From User u Where u.emailId = ?1 and u.randomNumber = ?2 and u.status = 0")
	User verifyByEmailOTP(String emailId, long otp);

	@Modifying
	@Query(value = "Insert Into User (name, email_id, password, random_number, status) Values (:name, :emailId, :password, :randomNumber, 0)", nativeQuery = true)
	@Transactional
	void insertProfile(@Param("name") String name, @Param("emailId") String emailId, @Param("password") String password,
			@Param("randomNumber") long randomNumber);

	@Modifying
	@Query(value = "Update User u Set u.status = 1 Where u.email_id = ?1", nativeQuery = true)
	@Transactional
	int setProfileVerified(String emailId);

	List<User> findByNameContainingIgnoreCase(String name);

}
