package com.bl.fundoo.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class UserRequest {

	
	 	@NotBlank(message = "First name cannot be empty")
	    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
	    private String firstName;

	    @NotBlank(message = "Last name cannot be empty")
	    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
	    private String lastName;

	    @NotBlank(message = "Email ID cannot be empty")
	    @Email(message = "Invalid email format")
	    private String emailId;

	    @NotBlank(message = "Password cannot be empty")
	    @Size(min = 8, message = "Password must be at least 8 characters long")
	    private String password;
	
}
