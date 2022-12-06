package com.webSpring.gbProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webSpring.gbProject.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
