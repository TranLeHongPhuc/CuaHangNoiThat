package com.noithat.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Account;
import com.noithat.service.AccountService;

@Controller
public class SubcategoryManagementController {
	@Autowired
	AccountService accountService;
	@RequestMapping("/admin/subcategory")
	public String getAll(Authentication auth,Model model) {
		Account account=accountService.findByUsername(auth.getName());
		model.addAttribute("user",account);
		return "admin/subcategory.html";
	}
}
