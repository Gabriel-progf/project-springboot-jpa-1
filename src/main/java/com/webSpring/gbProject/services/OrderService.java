package com.webSpring.gbProject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.webSpring.gbProject.entities.Order;
import com.webSpring.gbProject.repositories.OrderRepository;
import com.webSpring.gbProject.services.exceptions.DataBaseException;
import com.webSpring.gbProject.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();

    }

    public Order findById(Long id) {
        Optional<Order> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));

    }

    @Transactional
    public Order save(Order order) {
        boolean existOrder = repository.findById(order.getId()).stream().anyMatch(o -> !o.equals(order));

        if (existOrder) {
            throw new DataBaseException("This order already exist.");
        }

        return repository.save(order);

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
