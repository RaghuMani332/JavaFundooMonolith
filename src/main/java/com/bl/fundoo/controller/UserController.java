package com.bl.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bl.fundoo.requestdto.UserRequest;
import com.bl.fundoo.responcedto.UserResponce;
import com.bl.fundoo.service.UserService;
import com.bl.fundoo.util.ResponceStructure;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	
	@Autowired
	private UserService service ;
	
	
	@PostMapping("/adduser")
	public ResponseEntity<ResponceStructure<UserResponce>> addUser(@RequestBody UserRequest request)
	{
		return service.addUser(request);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody UserRequest request) {
		
		return service.login(request);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponceStructure<UserResponce>> updateUser(@PathVariable int id, @RequestBody UserRequest request , @AuthenticationPrincipal Jwt jwt) {
		return service.update(id,request);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deleteUser(@PathVariable int id) {
		return service.delete(id);
	}
	
	
	
	
}
