package com.webSpring.gbProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webSpring.gbProject.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
