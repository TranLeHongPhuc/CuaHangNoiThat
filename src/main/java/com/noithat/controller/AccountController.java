package com.noithat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {
	@RequestMapping("/user")
	public String profile() {
		return "/user/profile";
	}
	@RequestMapping("/register")
	public String register() {
		return "/security/register";
	}
}
