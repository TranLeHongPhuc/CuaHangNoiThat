package com.noithat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/home")
	public String home() {
		return "home/view";
	}
	@RequestMapping("/login")
	public String login() {
		return "security/login";
	}
}
