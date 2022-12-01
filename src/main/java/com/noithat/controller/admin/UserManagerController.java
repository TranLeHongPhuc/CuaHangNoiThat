package com.noithat.controller.admin;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import com.noithat.service.AccountService;

@Controller
public class UserManagerController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/admin/users")
	public String getUser(Model model) {
		return "admin/users";
	}
	
	
	
	
}
