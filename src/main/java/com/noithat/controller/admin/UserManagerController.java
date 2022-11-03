package com.noithat.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.noithat.entity.Account;
import com.noithat.service.AccountService;

@Controller
public class UserManagerController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/admin/users")
	public String getUsers(Model model) {
		List<Account> ac = accountService.findAll();
		model.addAttribute("account", ac);
		return "admin/users";
	}
	
}
