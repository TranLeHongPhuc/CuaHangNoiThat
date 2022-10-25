package com.noithat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
	@RequestMapping("/product/home")
	public String productHome() {
		return "product/home";
	}

	@RequestMapping("/product/detail")
	public String detail() {
		return "product/detail";
	}
}
