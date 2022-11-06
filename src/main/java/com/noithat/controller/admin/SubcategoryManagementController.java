package com.noithat.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SubcategoryManagementController {
	@RequestMapping("/admin/subcategory")
	public String getAll() {
		return "admin/subcategory.html";
	}
}
