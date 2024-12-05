package com.bl.fundoo.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bl.fundoo.entity.LableEntity;
import com.bl.fundoo.entity.NotesEntity;
import com.bl.fundoo.entity.UserEntity;
import com.bl.fundoo.exception.LableNotFoundByIdException;
import com.bl.fundoo.exception.NotesNotFoundException;
import com.bl.fundoo.repositary.LableRepositary;
import com.bl.fundoo.repositary.NotesRepositary;
import com.bl.fundoo.repositary.UserRepo;
import com.bl.fundoo.requestdto.LableRequest;
import com.bl.fundoo.responcedto.LableResponce;
import com.bl.fundoo.responcedto.NotesResponce;
import com.bl.fundoo.service.LableService;
import com.bl.fundoo.util.ResponceStructure;

@Service
public class LableServiceImpl implements LableService{


	@Autowired
	private NotesRepositary notesRepo;

	@Autowired
	private LableRepositary lableRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public ResponseEntity<ResponceStructure<LableResponce>> addLable(LableRequest request,String email) {
		UserEntity userEntity = userRepo.findUserEntityByEmailId(email).get();
		LableEntity lable = mapToEntity(request,userEntity);
		LableEntity entity = lableRepo.save(lable);
		LableResponce responce = mapToResponce(entity);
		return mapToResponceEntity(responce,"LableCreated",HttpStatus.CREATED);

	}



	@Override
	public ResponseEntity<ResponceStructure<LableResponce>> addNotes(int lableId, int noteId) {
		NotesEntity entity=notesRepo.findById(noteId).get();
		LableEntity lable = lableRepo.findById(lableId).orElseThrow(() -> new LableNotFoundByIdException("no  notes found for the given note id"));
		entity.setLable(lable);
		notesRepo.save(entity);
		LableResponce responce = mapToResponce(lable);
		return mapToResponceEntity(responce,"Lable Added to notes",HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<ResponceStructure<List<LableResponce>>> getLable(String email) {
		UserEntity userEntity = userRepo.findUserEntityByEmailId(email).get();
		List<LableEntity> li = lableRepo.findByUser(userEntity);
		List<LableResponce> responce = li.stream().map(l -> mapToResponce(l)).toList();
		return mapToResponceEntity(responce,"Lables retrived for the user",HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponceStructure<LableResponce>> updateLable(int id,LableRequest request) {
		LableEntity lable = lableRepo.findById(id).get();
		lable.setLableName(request.getLableName());
		lableRepo.save(lable);
		LableResponce responce = mapToResponce(lable);
		return mapToResponceEntity(responce,"Lable Added to notes",HttpStatus.OK);	
	}

	@Override
	public boolean deleteLable(int id) {
		List<NotesEntity> notes = notesRepo.findNotesByLable(lableRepo.findById(id).orElseThrow(() -> new NotesNotFoundException("no lable found for given id")));
		notes.stream().forEach(n -> 
		{
			n.setLable(null);
			notesRepo.save(n);
			});
		lableRepo.deleteById(id);
		return true;
	}
	

	@Override
	public ResponseEntity<ResponceStructure<LableResponce>> removeNotes(int lableId, int noteId) {
		NotesEntity entity=notesRepo.findById(noteId).orElseThrow(() -> new NotesNotFoundException("no notes found for given id"));
		entity.setLable(null);
		notesRepo.save(entity);
		LableEntity lable = lableRepo.findById(lableId).orElseThrow(() -> new LableNotFoundByIdException("no  lable found for the given note id"));
		LableResponce responce = mapToResponce(lable);
		return mapToResponceEntity(responce,"Lable removed from notes",HttpStatus.OK);
	}

	private LableEntity mapToEntity(LableRequest request,UserEntity entity) {
		return LableEntity.builder()
				.lableName(request.getLableName())
				.user(entity)
				.build();
	}

	private <T> ResponseEntity<ResponceStructure<T>> mapToResponceEntity(T data,String message , HttpStatus status)
	{
		ResponceStructure<T> structure = new ResponceStructure<>();
		structure.setData(data);
		structure.setMessage(message);
		structure.setStatus(status.value());
		return new ResponseEntity<>(structure,status);
	}
	private LableResponce mapToResponce(LableEntity entity)
	{
		if(entity.getNotes()==null)
		{
			return LableResponce.builder()
					.id(entity.getId())
					.lableName(entity.getLableName())
//					.notes(entity.getNotes().stream().map(n -> mapToResponce(n)).toList())
					.build();
		}
		return LableResponce.builder()
				.id(entity.getId())
				.lableName(entity.getLableName())
				.notes(entity.getNotes().stream().map(n -> mapToResponce(n)).toList())
				.build();
	}


	private NotesResponce mapToResponce(NotesEntity entity) {
		return NotesResponce.builder()
				.noteId(entity.getNoteId())
				.title(entity.getTitle())
				.description(entity.getDescription())
				.bgColor(entity.getBgColor())
				.imagePath(entity.getImagePath())
				.remainder(entity.getRemainder())
				.isArchive(entity.isArchive())
				.isPinned(entity.isPinned())
				.isTrash(entity.isTrash())
				.createdAt(entity.getCreatedAt())
				.modifiedAt(entity.getModifiedAt())
				.collabId(entity.getCollabEmailId().stream().map(u -> u.getEmailId()).toList())
				.user(entity.getUser().getEmailId())
				.build();
	}








}
