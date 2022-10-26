package com.nothat.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping("/admin")
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}
}
