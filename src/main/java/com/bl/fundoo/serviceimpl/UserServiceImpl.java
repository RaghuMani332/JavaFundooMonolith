package com.bl.fundoo.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bl.fundoo.entity.UserEntity;
import com.bl.fundoo.exception.DuplicateEntryException;
import com.bl.fundoo.exception.UserNotFoundException;
import com.bl.fundoo.repositary.UserRepo;
import com.bl.fundoo.requestdto.UserRequest;
import com.bl.fundoo.responcedto.UserResponce;
import com.bl.fundoo.security.JWTService;
import com.bl.fundoo.service.UserService;
import com.bl.fundoo.util.ResponceStructure;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo dao;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private ResponceStructure<UserResponce> structure;
	
	
	@Autowired
	private PasswordEncoder encoder;
	
	private UserEntity mapToEntity(UserRequest request) {
		return UserEntity.builder()
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.emailId(request.getEmailId())
			.password(request.getPassword())
			.build();
	}
	
	private UserResponce mapToRespoce(UserEntity user) {
		return UserResponce.builder().id(user.getId())
									.firstName(user.getFirstName())
									.lastName(user.getLastName())
									.emailId(user.getEmailId())
									.build();
	}
	
	  private <T> ResponseEntity<ResponceStructure<T>> mapResponseEntity(T data, String message, HttpStatus status) {
	        ResponceStructure<T> response = new ResponceStructure<>();
	        response.setMessage(message);
	        response.setStatus(status.value());
	        response.setData(data);
	        return new ResponseEntity<>(response, status);
	    }
	
	
	@Override
	public ResponseEntity<ResponceStructure<UserResponce>> addUser(UserRequest request) {
		
		
		UserEntity user = mapToEntity(request);
		user.setPassword(encoder.encode(request.getPassword()));
		System.out.println(user.getPassword());
		
		try
		{
			user=dao.save(user);
		}
		catch(DataIntegrityViolationException ex)
		{
			throw new DuplicateEntryException("The entered email is already registered please try new one");
		}
		UserResponce responce = mapToRespoce(user);
		return mapResponseEntity(responce, "User created successfully", HttpStatus.CREATED);

		
	}

	@Override
	public String login(UserRequest user) {
		Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getFirstName(), user.getPassword()));
		if(auth.isAuthenticated())
		{
			return jwtService.generateToken(user);
		}
	
		else
			return "fail";
	}

	@Override
	public ResponseEntity<ResponceStructure<UserResponce>> update(int id, UserRequest request) {
		UserEntity user= dao.findById(id).orElseThrow(()-> new UserNotFoundException("user not found for the given id"));
		UserEntity userEntity = mapToEntity(request);
		userEntity.setId(id);
		userEntity.setPassword(encoder.encode(request.getPassword()));
		
		try
		{
			user=dao.save(userEntity);
		}
		catch(DataIntegrityViolationException ex)
		{
			throw new DuplicateEntryException("The entered email is already registered please try new one");
		}
		
		return mapResponseEntity(mapToRespoce(userEntity), "user updated successfully", HttpStatus.OK);	
	}

	@Override
	public boolean delete(int id) {
		dao.deleteById(id);
		return true;
	}

	


	

}
