package com.webSpring.gbProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.webSpring.gbProject.entities.Category;
import com.webSpring.gbProject.repositories.CategoryRepository;
import com.webSpring.gbProject.services.exceptions.DataBaseException;
import com.webSpring.gbProject.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));

    }

    @Transactional
    public Category save(Category category) {
        boolean existCategory = repository.findByName(category.getName()).stream().anyMatch(c -> !c.equals(category));

        if (existCategory) {
            throw new DataBaseException("This category alredy exist");
        }

        return repository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public Boolean existById(Long id) {
        return repository.existsById(id);
    }

}
