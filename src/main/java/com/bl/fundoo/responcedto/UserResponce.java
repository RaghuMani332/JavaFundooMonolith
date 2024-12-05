package com.bl.fundoo.responcedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class UserResponce {
	private int id;
	private String firstName;
	private String lastName;
	private String emailId;
}
