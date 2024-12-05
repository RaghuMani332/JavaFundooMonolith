package com.bl.fundoo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DuplicateEntryException extends RuntimeException{
	private String message;

}
