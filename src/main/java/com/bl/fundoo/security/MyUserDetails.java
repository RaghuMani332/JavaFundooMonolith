package com.bl.fundoo.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bl.fundoo.entity.UserEntity;

public class MyUserDetails implements UserDetails{

	private UserEntity user;
	
	public MyUserDetails(UserEntity user)
	{
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getFirstName();
	}
	
	public int getUserId()
	{
		return user.getId();
	}

}
