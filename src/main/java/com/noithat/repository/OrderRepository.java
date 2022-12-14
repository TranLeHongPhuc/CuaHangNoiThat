package com.noithat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.noithat.entity.Order;
import com.noithat.entity.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	@Query("SELECT o FROM Order o WHERE o.account.username=?1")
	List<Order> findByUsername(String username);
	
	@Query("SELECT o FROM Order o WHERE o.orderstatus.id=?1")
	List<Order> findByOrderstatus(String orderStatusId);
}
