package com.bl.fundoo.repositary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bl.fundoo.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findUserEntityByFirstName(String username);

	Optional<UserEntity> findUserEntityByEmailId(String userEmailId);

    List<UserEntity> findByEmailIdIn(List<String> emailIds);

}
