package com.noithat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;
}
