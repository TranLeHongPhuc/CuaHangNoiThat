package com.noithat.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.entity.OrderDetail;
import com.noithat.service.OrderDetailsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/orderdetails")
public class OrderDetailsRestController {
	@Autowired
	OrderDetailsService orderDetailsService;
	
	@GetMapping()
	public List<OrderDetail> getAll(){
		return orderDetailsService.findAll();
	}
}
