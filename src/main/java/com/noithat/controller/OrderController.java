package com.noithat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class OrderController {
	@RequestMapping("/checkout")
	public String checkout() {
		return "order/checkout";
	}
}
