package com.noithat.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.noithat.entity.Account;
import com.noithat.service.AccountService;

@Controller
public class OrderManagementController {
	@Autowired
	AccountService accountService;
	@GetMapping("/admin/order")
	public String home(Model model,Authentication auth) {
		Account account=accountService.findByUsername(auth.getName());
		model.addAttribute("user",account);
		return "admin/orders-list";
	}
}
