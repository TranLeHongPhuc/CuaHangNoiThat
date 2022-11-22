package com.noithat.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noithat.entity.Order;
import com.noithat.entity.OrderDetail;
import com.noithat.entity.OrderStatus;
import com.noithat.repository.OrderDetailRepository;
import com.noithat.repository.OrderRepository;
import com.noithat.repository.OrderStatusRepository;
import com.noithat.service.OrderService;

@Service
public class OrderServiceImplement implements OrderService{
	@Autowired
	OrderRepository orderDAO;
	
	@Autowired
	OrderDetailRepository orderDetailDAO;
	
	@Autowired
	OrderStatusRepository orderStatusDAO;
	
	@Override
	public Order create(JsonNode orderData) {
		ObjectMapper mapper = new ObjectMapper();
		Order order = mapper.convertValue(orderData, Order.class);
		OrderStatus orderStatus=orderStatusDAO.findById("CXN").get();
		order.setOrderstatus(orderStatus);
		orderDAO.save(order);
		
		TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {
		};
		List<OrderDetail> details = mapper.convertValue(orderData.get("orderDetails"),type)
				.stream().peek(d -> d.setOrder(order)).collect(Collectors.toList());
		orderDetailDAO.saveAll(details);
		return order;
	}

	@Override
	public Order findById(Integer id) {
		return orderDAO.findById(id).get();
	}

	@Override
	public List<Order> findByUsername(String username) {
		return orderDAO.findByUsername(username);
	}

	@Override
	public List<Order> findByOrserStatus(String orderStatusId) {
		return orderDAO.findByOrderstatus(orderStatusId);
	}
}
