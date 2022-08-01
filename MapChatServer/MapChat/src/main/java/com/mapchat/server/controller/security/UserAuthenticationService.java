package com.mapchat.server.controller.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mapchat.server.controller.security.data.UserAuthData;
import com.mapchat.server.model.UserRepository;
import com.mapchat.server.model.tables.User;

/**
 * 
 * User authentication class=
 *
 */
@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> u = userRepository.findByEmailId(username);
		u.orElseThrow(() -> new UsernameNotFoundException("User Not Found with " + username));
		return u.map(UserAuthData::new).get();
	}

}
