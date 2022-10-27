package com.noithat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noithat.entity.Product;
import com.noithat.service.ProductService;

@Controller
public class HomeController {
	@Autowired
	ProductService productService;
	
    @GetMapping("/home")
    public String getAllPages(Model model) {
        return home(model, 1);
    }
	
	@RequestMapping("/home/{pageNumber}")
	public String home(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Product> list = productService.homePage(currentPage);
        model.addAttribute("items", list);
		return "home/view";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}
	
	
}
