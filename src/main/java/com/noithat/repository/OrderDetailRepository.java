package com.noithat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noithat.entity.Order;
import com.noithat.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	
	List<OrderDetail> findByOrder(Order order);
}
