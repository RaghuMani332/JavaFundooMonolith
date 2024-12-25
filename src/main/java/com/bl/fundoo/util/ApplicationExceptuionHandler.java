package com.bl.fundoo.util;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bl.fundoo.exception.DuplicateEntryException;
import com.bl.fundoo.exception.LableNotFoundByIdException;
import com.bl.fundoo.exception.UserMissmatchException;
import com.bl.fundoo.exception.UserNotFoundException;

@RestControllerAdvice
public class ApplicationExceptuionHandler extends ResponseEntityExceptionHandler {
	
	
	private ResponseEntity<Object> errorStructure(HttpStatus status,String message,Object rootCause)
	{
		return new ResponseEntity<Object>(Map.of("status",status.value(),
				"message",message,
				"rootcause",rootCause),status);
	}
	
	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<Object> duplicateEntry(DuplicateEntryException duplicateEntryException)
	{
		return errorStructure(HttpStatus.CONFLICT, duplicateEntryException.getMessage(), "THE USER EMAIL ID IS ALREADY REGISTERED SO TRY WITH NEW EMAIL ID");
	}
	
	@ExceptionHandler(exception=UserNotFoundException.class)
	public ResponseEntity<Object> userNotFound(UserNotFoundException exception)
	{
		return errorStructure(HttpStatus.NOT_FOUND, exception.getMessage(),"User Not Found in the Database please register using the email id");
	}
	


	@ExceptionHandler(exception=LableNotFoundByIdException.class)
	public ResponseEntity<Object> lableNotFoundByIdException(LableNotFoundByIdException exception)
	{
		return errorStructure(HttpStatus.NOT_FOUND, exception.getMessage(),"Lable Not Found in the Database please provide correct id");
	}
	

	@ExceptionHandler(exception=UserMissmatchException.class)
	public ResponseEntity<Object> userMissmatchException(UserMissmatchException exception)
	{
		return errorStructure(HttpStatus.UNAUTHORIZED, exception.getMessage(),"The user trying to access the note is not able to access the notes");
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> universalHandler(RuntimeException exception)
	{
		return errorStructure(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), "Unexpected exception occured");

	}
}
