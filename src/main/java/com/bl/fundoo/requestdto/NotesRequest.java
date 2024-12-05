package com.bl.fundoo.requestdto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class NotesRequest {

	 	private String title;
	    private String description;
	    private String bgColor;
	    private String imagePath;
	    private Date remainder;
	    private boolean isArchive ;
	    private boolean isPinned;
//	    private boolean isTrash = false; // When creating notes, this should be false
	    private List<String> collabEmailId;
	    private String userEmailId;
}




