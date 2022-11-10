package com.noithat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.noithat.entity.Order;

@Service
public interface OrderService {
	Order create(JsonNode orderData);

	Order findById(Integer id);

	List<Order> findByUsername(String username);
}
