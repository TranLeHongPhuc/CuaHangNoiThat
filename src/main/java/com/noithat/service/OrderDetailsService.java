package com.noithat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.noithat.entity.OrderDetail;

@Service
public interface OrderDetailsService {
	List<OrderDetail> findAll();
}
