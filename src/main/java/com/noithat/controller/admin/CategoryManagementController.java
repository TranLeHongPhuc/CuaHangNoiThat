package com.noithat.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryManagementController {
	@RequestMapping("/admin/category")
		public String getCategory() {
			return "admin/category.html";
		}
	}

