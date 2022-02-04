package com.assignment.openweather.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class AuthenticatedUser implements UserDetails {

	final String username;
	
	private String userNameAs;

	private final String token;

	private String ipAddress;

	public AuthenticatedUser(String username, String token) {
		this.username = username;
		this.token = token;
	}
	
	public String getUserNameAs() {
		return userNameAs;
	}
	
	public AuthenticatedUser setUserNameAs(final String userNameAs) {
		this.userNameAs = userNameAs;
		return this;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "AuthenticatedUser [username=" + username + ", token=" + token + "]";
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public AuthenticatedUser setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}
}
