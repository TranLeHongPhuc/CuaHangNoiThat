package com.noithat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingCartController {
	@RequestMapping("/cart")
	public String view() {
		return "cart/view";
	}
}
