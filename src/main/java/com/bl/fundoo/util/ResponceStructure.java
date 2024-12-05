package com.bl.fundoo.util;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ResponceStructure<T> {

	private int status;
	private String message;
	private T data;
}
