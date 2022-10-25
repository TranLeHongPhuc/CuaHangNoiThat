package com.noithat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noithat.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
