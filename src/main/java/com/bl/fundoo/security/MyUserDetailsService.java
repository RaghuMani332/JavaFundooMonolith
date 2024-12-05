package com.bl.fundoo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bl.fundoo.exception.UserNotFoundException;
import com.bl.fundoo.repositary.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load user detail service");
		return repo.findUserEntityByFirstName(username).map(user -> new MyUserDetails(user)).orElseThrow(() -> new UsernameNotFoundException("User name found in the given Database register"));
	}

}
