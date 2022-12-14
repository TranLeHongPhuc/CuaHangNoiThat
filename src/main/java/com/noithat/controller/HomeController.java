package com.noithat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.noithat.entity.Product;
import com.noithat.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;
		
	@RequestMapping("/home")
	public String home(Model model) {
        Page<Product> list = productService.homePage(1);
        model.addAttribute("items", list);
		return "home/view";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}
	
	
}
