package com.noithat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.noithat.entity.Product;
import com.noithat.entity.Subcategory;
import com.noithat.service.ProductService;
import com.noithat.service.SubcategoryService;

@Controller
public class SubcategoryController {
	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;
	
	public String getSubcategoryByCategory(@ModelAttribute("item") Product product, Model model,
			@PathVariable("id") Optional<Integer> productId) {
		List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
		model.addAttribute("sub",subcategories);
		model.addAttribute("item",product);
		return "redirect:/assets/admin/index.html";
		
	}
}
