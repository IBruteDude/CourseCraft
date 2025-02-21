package com.coursecraft.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coursecraft.entity.User;


public interface UserRepository extends JpaRepository<User, UUID> {

	@Query("SELECT u FROM User u WHERE u.userName = :userName")
	Optional<User> findByUserName(String userName);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	Optional<User> findByEmail(String email);

}
