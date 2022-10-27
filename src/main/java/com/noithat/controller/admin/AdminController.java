package com.noithat.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.service.StatsService;

@Controller
public class AdminController {
	
	@Autowired
	private StatsService statsService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		String chartData[][] = statsService.getToTalPriceLast6Months();
		model.addAttribute("chartData",chartData);
		return "admin/index";
	}
	
	@RequestMapping("/admin")
	public String getAdmin() {
		return "redirect:/assets/admin/index.html";
	}
}
