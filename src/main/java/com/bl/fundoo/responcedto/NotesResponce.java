package com.bl.fundoo.responcedto;

import java.util.Date;
import java.util.List;

import com.bl.fundoo.entity.UserEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotesResponce {
	
	private int noteId;
	private String title;
	private String description;
	private String bgColor;
	private String imagePath;
	private Date remainder;
	private boolean isArchive;
	private boolean isPinned;
	private boolean isTrash = false;
	private Date createdAt;
	private Date modifiedAt;
	private List<String> collabId;
	private String user;
}
