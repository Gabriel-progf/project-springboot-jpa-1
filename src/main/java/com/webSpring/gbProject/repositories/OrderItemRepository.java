package com.webSpring.gbProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webSpring.gbProject.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
