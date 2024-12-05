package com.bl.fundoo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bl.fundoo.requestdto.NotesRequest;
import com.bl.fundoo.responcedto.NotesResponce;
import com.bl.fundoo.util.ResponceStructure;

public interface NotesService {

	ResponseEntity<ResponceStructure<NotesResponce>> addNotes(NotesRequest request,String email);

	ResponseEntity<ResponceStructure<List<NotesResponce>>> getNotes(String email);

	ResponseEntity<ResponceStructure<NotesResponce>> getNotesById(int id);

	boolean deleteNotesById(int id);

	ResponseEntity<ResponceStructure<NotesResponce>> updateTrash(int id);

	ResponseEntity<ResponceStructure<NotesResponce>> updateArchive(int id);

	ResponseEntity<ResponceStructure<NotesResponce>> updateNotes(int id, NotesRequest request, String email);

}
