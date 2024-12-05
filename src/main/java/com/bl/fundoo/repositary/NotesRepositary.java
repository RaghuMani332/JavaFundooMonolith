package com.bl.fundoo.repositary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.fundoo.entity.LableEntity;
import com.bl.fundoo.entity.NotesEntity;
import com.bl.fundoo.entity.UserEntity;

public interface NotesRepositary extends JpaRepository<NotesEntity, Integer>{

	Optional<List<NotesEntity>> findNotesEntityByUserId(int id);

	Optional<List<NotesEntity>> findNotesEntityByCollabEmailId(UserEntity user);

	List<NotesEntity> findNotesByLable(LableEntity lableEntity);
}
