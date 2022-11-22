package com.noithat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Order;
import com.noithat.entity.OrderDetail;
import com.noithat.repository.OrderDetailRepository;
import com.noithat.service.OrderService;


@Controller
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@RequestMapping("/order/checkout")
	public String checkout() {
		return "order/checkout";
	}
	
	@GetMapping("/order/view")
	public String viewOrder(Model model,Authentication auth,HttpServletRequest req) {
		String username=auth.getName();
		List<Order> listOrderCXN = new ArrayList<Order>();
		List<Order> listOrderCLH = new ArrayList<Order>();;
		List<Order> listOrderDG = new ArrayList<Order>();;
		List<Order> listOrderDGH = new ArrayList<Order>();;
		List<Order> listOrders=orderService.findByUsername(username);
		for(Order order:listOrders) {
			if(order.getOrderstatus().getId().equals("CXN")) {
				listOrderCXN.add(order);
			}else if(order.getOrderstatus().getId().equals("CLH")) {
				listOrderCLH.add(order);
			}else if(order.getOrderstatus().getId().equals("DG")) {
				listOrderDG.add(order);
			}else {
				listOrderDGH.add(order);
			}
		}
		model.addAttribute("itemCXN", listOrderCXN);
		model.addAttribute("itemCLH", listOrderCLH);
		model.addAttribute("itemDG", listOrderDG);
		model.addAttribute("itemDGH", listOrderDGH);
		return "order/view";
	}
	
	@GetMapping("/order/detail/{id}")
	public String detail(@PathVariable("id")Integer id,Model model) {
		Order order=orderService.findById(id);
		List<OrderDetail> listOrderDetails=orderDetailRepository.findByOrder(order);
		model.addAttribute("items", listOrderDetails);
		return "order/detail";
	}
}
