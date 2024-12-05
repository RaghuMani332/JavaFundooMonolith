package com.bl.fundoo.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bl.fundoo.entity.NotesEntity;
import com.bl.fundoo.entity.UserEntity;
import com.bl.fundoo.exception.NotesNotFoundException;
import com.bl.fundoo.exception.UserMissmatchException;
import com.bl.fundoo.exception.UserNotFoundException;
import com.bl.fundoo.mailsender.EmailService;
import com.bl.fundoo.repositary.NotesRepositary;
import com.bl.fundoo.repositary.UserRepo;
import com.bl.fundoo.requestdto.NotesRequest;
import com.bl.fundoo.responcedto.NotesResponce;
import com.bl.fundoo.service.NotesService;
import com.bl.fundoo.util.ResponceStructure;


@Service
public class NotesServiceImpl implements NotesService {


	@Autowired
	private UserRepo userRepo;


	@Autowired
	private NotesRepositary notesRepo; 
	
	
	@Autowired
	private EmailService emailservice;


	@Override
	public ResponseEntity<ResponceStructure<NotesResponce>> addNotes(NotesRequest request,String email) {
		if(!email.equals(request.getUserEmailId())) throw new UserMissmatchException("the user mentioned in notes and  is different");
		NotesEntity note =mapToEntity(request);
		NotesEntity entity = notesRepo.save(note);
		UserEntity user = userRepo.findUserEntityByEmailId(request.getUserEmailId()).orElseThrow(() -> new UserNotFoundException(" given user is not found in db"));
		List<UserEntity> li = userRepo.findByEmailIdIn(request.getCollabEmailId());
		if(li.size() != request.getCollabEmailId().size()) throw new UserNotFoundException(" given colabrator is not found in db");
		request.getCollabEmailId().forEach(e -> sendEmail(e, email));
		NotesResponce responce = mapToResponce(entity);
		return  mapResponseEntity(responce, "Added successfully", HttpStatus.CREATED);
	}

	private void sendEmail(String toMail,String from)
	{
		emailservice.sendEmail(toMail,"Mail for Fundoo Notes Collabration","Hii "+toMail + " , " + from +" have collabrates a note with you please open your fundoo accont to check the notes , Thank you");
	}



	@Override
	public ResponseEntity<ResponceStructure<List<NotesResponce>>> getNotes(String email) {
		System.out.println(email);
		UserEntity user = userRepo.findUserEntityByEmailId(email).orElseThrow(() -> new UserNotFoundException(" given user is not found in db"));
		Optional<List<NotesEntity>> notes = notesRepo.findNotesEntityByUserId(user.getId());
		List<NotesEntity> colabedNotes = notesRepo.findNotesEntityByCollabEmailId(userRepo.findUserEntityByEmailId(email).get()).get();
		colabedNotes.addAll(notes.get());
		List<NotesResponce> notesResponces = colabedNotes.stream()
				.map(this::mapToResponce)
				.collect(Collectors.toList());

		return mapResponseEntity(notesResponces, "Notes belonging to user and their collaborations", HttpStatus.FOUND);

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


	@Override
	public ResponseEntity<ResponceStructure<NotesResponce>> getNotesById(int id) {
		NotesEntity entity=notesRepo.findById(1).get();
//		System.out.println(entity);
		return mapResponseEntity(mapToResponce(entity), "Notes fetched successfully", HttpStatus.FOUND);
	}




	@Override
	public boolean deleteNotesById(int id) {
		notesRepo.deleteById(id);
		return true;
	}




	@Override
	public ResponseEntity<ResponceStructure<NotesResponce>> updateTrash(int id) {
		NotesEntity notesEntity = notesRepo.findById(id).orElseThrow(()->new NotesNotFoundException("NotesNotfound for the given id"));
		notesEntity.setTrash(!notesEntity.isTrash());
		NotesEntity entity = notesRepo.save(notesEntity);
		return mapResponseEntity(mapToResponce(entity), "Notes updated as trash", HttpStatus.OK);		
	}




	@Override
	public ResponseEntity<ResponceStructure<NotesResponce>> updateArchive(int id) {
		NotesEntity notesEntity = notesRepo.findById(id).orElseThrow(()->new NotesNotFoundException("NotesNotfound for the given id"));
		notesEntity.setArchive(!notesEntity.isArchive());
		NotesEntity entity = notesRepo.save(notesEntity);
		return mapResponseEntity(mapToResponce(entity), "Notes updated as archive", HttpStatus.OK);	
	}


	@Override
	public ResponseEntity<ResponceStructure<NotesResponce>> updateNotes(int id, NotesRequest request,String email) {
		if (!request.getUserEmailId().equals(email) && !request.getCollabEmailId().contains(email)) {
	        throw new UserMissmatchException("The user mentioned in notes and token is different");
	    }
		NotesEntity entity = notesRepo.findById(id).get();
		NotesEntity requestEntity = mapToEntity(request);

		if(requestEntity.getCollabEmailId().containsAll(entity.getCollabEmailId()) && entity.getCollabEmailId().containsAll(requestEntity.getCollabEmailId()) )
		{
			requestEntity.setNoteId(id);
			entity = notesRepo.save(requestEntity);
		}
		else if(entity.getUser().getEmailId().equals(email))
		{
			requestEntity.setNoteId(id);
			entity = notesRepo.save(requestEntity);
		}
		else
		{
			throw new UserMissmatchException("collabrator has only access to edit notes , they can't able to change the collabrators");
		}

		return mapResponseEntity(mapToResponce(entity), "Notes updated as archive", HttpStatus.OK);	
	}













	private <T> ResponseEntity<ResponceStructure<T>> mapResponseEntity(T data, String message, HttpStatus status) {
		ResponceStructure<T> response = new ResponceStructure<>();
		response.setMessage(message);
		response.setStatus(status.value());
		response.setData(data);
		return new ResponseEntity<>(response, status);
	}


	private NotesEntity mapToEntity(NotesRequest request) {

		UserEntity user = userRepo.findUserEntityByEmailId(request.getUserEmailId()).orElseThrow(() -> new UserNotFoundException("the given user is not found in db"));
		request.getCollabEmailId().forEach(u -> userRepo.findUserEntityByEmailId(u).orElseThrow(() -> new UserNotFoundException("the given collab id " +u +" is not found in db please register the user")));

		return NotesEntity.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.bgColor(request.getBgColor())
				.imagePath(request.getImagePath())
				.remainder(request.getRemainder())
				.isArchive(request.isArchive())
				.isPinned(request.isPinned())
				.isTrash(false)
				.createdAt(new Date())
				.modifiedAt(new Date())
				.collabEmailId(userRepo.findByEmailIdIn(request.getCollabEmailId()))
				.user(user)
				.build();

	}










}
