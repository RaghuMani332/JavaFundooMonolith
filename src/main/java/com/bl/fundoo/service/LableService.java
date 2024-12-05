package com.bl.fundoo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bl.fundoo.requestdto.LableRequest;
import com.bl.fundoo.responcedto.LableResponce;
import com.bl.fundoo.util.ResponceStructure;

public interface LableService {

	ResponseEntity<ResponceStructure<LableResponce>> addLable(LableRequest request, String email);

	ResponseEntity<ResponceStructure<List<LableResponce>>> getLable(String email);

	ResponseEntity<ResponceStructure<LableResponce>> updateLable(int id,LableRequest request);

	boolean deleteLable(int id);


	ResponseEntity<ResponceStructure<LableResponce>> addNotes(int lableIdid, int noteId);

	ResponseEntity<ResponceStructure<LableResponce>> removeNotes(int lableIdid, int noteId);

}
