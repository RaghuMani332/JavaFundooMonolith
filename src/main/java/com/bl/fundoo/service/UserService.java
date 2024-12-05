package com.bl.fundoo.service;

import org.springframework.http.ResponseEntity;

import com.bl.fundoo.entity.UserEntity;
import com.bl.fundoo.requestdto.UserRequest;
import com.bl.fundoo.responcedto.UserResponce;
import com.bl.fundoo.util.ResponceStructure;

public interface UserService {

	ResponseEntity<ResponceStructure<UserResponce>> addUser(UserRequest request);

	String login(UserRequest request);

	ResponseEntity<ResponceStructure<UserResponce>> update(int id, UserRequest request);

	boolean delete(int id);

}
