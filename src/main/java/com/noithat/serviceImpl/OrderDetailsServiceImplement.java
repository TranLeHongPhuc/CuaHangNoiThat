package com.noithat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noithat.entity.OrderDetail;
import com.noithat.repository.OrderDetailRepository;
import com.noithat.service.OrderDetailsService;

@Service
public class OrderDetailsServiceImplement implements OrderDetailsService{
	@Autowired
	OrderDetailRepository orderDetailDAO;
	
	@Override
	public List<OrderDetail> findAll() {
		return orderDetailDAO.findAll();
	}


}
