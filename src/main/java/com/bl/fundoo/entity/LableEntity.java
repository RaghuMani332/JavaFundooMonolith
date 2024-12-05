package com.bl.fundoo.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LableEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String lableName;
	
	@OneToMany(mappedBy = "lable" , targetEntity = NotesEntity.class)
	private List<NotesEntity> notes;
	
	@ManyToOne(targetEntity = UserEntity.class)
	private UserEntity user;

	@Override
	public String toString() {
		return "LableEntity [id=" + id + ", lableName=" + lableName + ", notes=" + notes + ", user=" + user + "]";
	}
	
	
}
