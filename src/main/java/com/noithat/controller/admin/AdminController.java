package com.noithat.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Account;
import com.noithat.service.AccountService;
import com.noithat.service.StatsService;

@Controller
public class AdminController {
	
	@Autowired
	private StatsService statsService;
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/admin")
	public String doGetIndex(Model model,Authentication auth) {
		String chartData[][] = statsService.getTotalPriceLast6Months();
		Account account=accountService.findByUsername(auth.getName());
		model.addAttribute("user",account);
		model.addAttribute("chartData",chartData);
		return "admin/index";
	}
}
