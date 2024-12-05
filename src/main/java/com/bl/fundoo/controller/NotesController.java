package com.bl.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bl.fundoo.requestdto.NotesRequest;
import com.bl.fundoo.responcedto.NotesResponce;
import com.bl.fundoo.security.JWTService;
import com.bl.fundoo.service.NotesService;
import com.bl.fundoo.util.ResponceStructure;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService service;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("addNote")
	public ResponseEntity<ResponceStructure<NotesResponce>> addNote(@RequestBody NotesRequest request, HttpServletRequest http) 
	{
		
		Claims claims = jwtService.extractToken(getToken(http));
		String email = (String) claims.get("emailId");
		return service.addNotes(request,email);
		
	}
	
	@GetMapping("getNotes")
	public ResponseEntity<ResponceStructure<List<NotesResponce>>> getNotes(HttpServletRequest http)
	{
		Claims claims = jwtService.extractToken(getToken(http));
		String email = (String) claims.get("emailId");
		return service.getNotes(email);
	}
	@GetMapping("getById/{id}")
	public ResponseEntity<ResponceStructure<NotesResponce>> getNotesById(@PathVariable int id)
	{
		return service.getNotesById(id);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<ResponceStructure<NotesResponce>> updateNotes(@PathVariable int id,@RequestBody NotesRequest request,HttpServletRequest http)
	{
		Claims claims = jwtService.extractToken(getToken(http));
		String email = (String) claims.get("emailId");
		return service.updateNotes(id,request,email);
	}
	
	@DeleteMapping("delete/{id}")
	public boolean deleteNotesById(@PathVariable int id)
	{
		return service.deleteNotesById(id);
	}
	//openfeing, eureca server, actuator, gateway,circuit breaker
	@DeleteMapping("updateTrash/{id}")
    public ResponseEntity<ResponceStructure<NotesResponce>> updateTrash(@PathVariable int id)
    {
		return service.updateTrash(id);
    }

	
	@PutMapping("updateArchive/{id}")
    public ResponseEntity<ResponceStructure<NotesResponce>> updateArchive(@PathVariable int id)
    {
		return service.updateArchive(id);
    }
	
//	@PutMapping("updateCollabrator/{id}")
//	public ResponseEntity<ResponceStructure<NotesResponce>> updateCollabrator(@PathVariable int id,@RequestBody NotesRequest request )
//	{
//		return service.updateCollabrator();
//	}


	
	
	
	
	
	
	
	
	
	
	private String getToken(HttpServletRequest http)
	{
		String header = http.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remove "Bearer " prefix
        }
		return null;
	}
	
}
