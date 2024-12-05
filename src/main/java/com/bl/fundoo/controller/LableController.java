package com.bl.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bl.fundoo.requestdto.LableRequest;
import com.bl.fundoo.responcedto.LableResponce;
import com.bl.fundoo.responcedto.NotesResponce;
import com.bl.fundoo.security.JWTService;
import com.bl.fundoo.service.LableService;
import com.bl.fundoo.util.ResponceStructure;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/lable")
public class LableController {

	@Autowired
	private LableService service;
	

	@Autowired
	private JWTService jwtService;
	
	
	@PostMapping("addlable")
	public ResponseEntity<ResponceStructure<LableResponce>> addLable(@RequestBody LableRequest request,HttpServletRequest http)
	{
		Claims claims = jwtService.extractToken(getToken(http));
		String email = (String) claims.get("emailId");
		return service.addLable(request,email);
	}
	
	@PutMapping("addNotesToLable")
	public ResponseEntity<ResponceStructure<LableResponce>> addNotes(@RequestParam int lableId, @RequestParam int noteId)
	{
		return service.addNotes(lableId,noteId);
	}
	
	@PutMapping("removeNotesFromLable")
	public ResponseEntity<ResponceStructure<LableResponce>> removeNotes(@RequestParam int lableId, @RequestParam int noteId)
	{
		return service.removeNotes(lableId,noteId);
	}
	
	
	@GetMapping("getLable")
	public ResponseEntity<ResponceStructure<List<LableResponce>>> getLable(HttpServletRequest http) {
		Claims claims = jwtService.extractToken(getToken(http));
		String email = (String) claims.get("emailId");
		return service.getLable(email);
	}
	
	@PutMapping("updateLable/{id}")
	public ResponseEntity<ResponceStructure<LableResponce>> updateLable(@PathVariable int id,@RequestBody LableRequest request)
	{
		return service.updateLable(id,request);
	}
	
	
	@DeleteMapping("deleteLable/{id}")
	public boolean deleteLable(@PathVariable int id)
	{
		return service.deleteLable(id);
	}
	
	
	
	
	
	
	
	private String getToken(HttpServletRequest http)
	{
		String header = http.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove "Bearer " prefix
        }
		return null;
	}
	
	
}
