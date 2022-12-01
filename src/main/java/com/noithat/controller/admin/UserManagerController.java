package com.noithat.controller.admin;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Account;
import com.noithat.service.AccountService;

@Controller
public class UserManagerController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/admin/users")
	public String getUser(Model model,Authentication auth) {
		Account account=accountService.findByUsername(auth.getName());
		model.addAttribute("user",account);
		return "admin/users";
	}
	
	
	
	
}
