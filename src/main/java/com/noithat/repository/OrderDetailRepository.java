package com.noithat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noithat.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

}
