package com.webSpring.gbProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.webSpring.gbProject.entities.User;
import com.webSpring.gbProject.repositories.UserRepository;
import com.webSpring.gbProject.services.exceptions.DataBaseException;
import com.webSpring.gbProject.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public User save(User user) {
        boolean userExist = repository.findByEmail(user.getEmail())
                .stream().anyMatch(x -> !x.equals(user));

        if (userExist) {
            throw new DataBaseException("This email already exists in use");
        }

        return repository.save(user);

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
