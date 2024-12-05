package com.bl.fundoo.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bl.fundoo.entity.LableEntity;
import com.bl.fundoo.entity.UserEntity;


@Repository
public interface LableRepositary extends JpaRepository<LableEntity, Integer> {

	List<LableEntity> findByUser(UserEntity userEntity);

}
