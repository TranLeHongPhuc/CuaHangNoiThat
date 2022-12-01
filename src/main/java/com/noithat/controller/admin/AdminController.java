package com.noithat.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.service.StatsService;

@Controller
public class AdminController {
	
	@Autowired
	private StatsService statsService;
	
	@GetMapping("/admin")
	public String doGetIndex(Model model,Authentication auth) {
		String chartData[][] = statsService.getTotalPriceLast6Months();
		model.addAttribute("user",auth);
		model.addAttribute("chartData",chartData);
		return "admin/index";
	}
}
