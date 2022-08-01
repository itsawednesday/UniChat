package com.mapchat.server.controller.security.data;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mapchat.server.model.tables.User;
/**
 * 
 * User data authentication class
 *
 */
public class UserAuthData implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public UserAuthData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserAuthData(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserAuthData(User u) {
		this.username = u.getEmailId();
		this.password = u.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
