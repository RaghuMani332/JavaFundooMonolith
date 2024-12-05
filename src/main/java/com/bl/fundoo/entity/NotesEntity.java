package com.bl.fundoo.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noteId;
    private String title;
    private String description;
    private String bgColor;
    private String imagePath;
    private Date remainder;
    private boolean isArchive;
    private boolean isPinned;
    private boolean isTrash ;
    private Date createdAt;
    private Date modifiedAt;
    
    @ElementCollection
	@CollectionTable(name = "notes_collabrators", joinColumns = @JoinColumn(referencedColumnName = "noteId"))
    private List<UserEntity> collabEmailId;
    
    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity user;
    
    @ManyToOne(targetEntity = LableEntity.class)
    private LableEntity lable;
    

	@Override
	public String toString() {
		return "NotesEntity [noteId=" + noteId + ", title=" + title + ", description=" + description + ", bgColor="
				+ bgColor + ", imagePath=" + imagePath + ", remainder=" + remainder + ", isArchive=" + isArchive
				+ ", isPinned=" + isPinned + ", isTrash=" + isTrash + ", createdAt=" + createdAt + ", modifiedAt="
				+ modifiedAt + ", collabEmailId=" + collabEmailId + ", user=" + user + "]";
	}

    
}
