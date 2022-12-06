package com.webSpring.gbProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webSpring.gbProject.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
 
    Optional<User> findByEmail(String email);
}
