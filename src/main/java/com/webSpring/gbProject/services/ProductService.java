package com.webSpring.gbProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.webSpring.gbProject.entities.Product;
import com.webSpring.gbProject.repositories.ProductRepository;
import com.webSpring.gbProject.services.exceptions.DataBaseException;
import com.webSpring.gbProject.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public Product save(Product product) {
        boolean existProduct = repository.findById(product.getId()).stream().anyMatch(p -> !p.equals(product));

        if (existProduct) {
            throw new DataBaseException("This product already exists in use");
        }

        return repository.save(product);

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
