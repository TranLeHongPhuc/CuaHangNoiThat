package com.noithat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.noithat.entity.Order;
import com.noithat.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {
	@Autowired
	OrderService orderService;

	@PostMapping()
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}
}
