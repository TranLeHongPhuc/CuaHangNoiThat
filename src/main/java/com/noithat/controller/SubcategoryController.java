package com.noithat.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Account;
import com.noithat.entity.Product;
import com.noithat.entity.Subcategory;
import com.noithat.service.AccountService;
import com.noithat.service.ProductService;
import com.noithat.service.SubcategoryService;

@Controller
public class SubcategoryController {
	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/category/subcategory/{id}")
	public String getSubcategoryByCategory(@ModelAttribute("item") Product product, Model model,
			@PathVariable("id") Optional<Integer> productId,Authentication auth) {
		List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
		Account account=accountService.findByUsername(auth.getName());
		if(productId.get().equals(-999)) {
			product.setImage1("default-product.png");
			model.addAttribute("sub",subcategories);
			model.addAttribute("item",product);
			
			model.addAttribute("user",account);
			return "admin/product-add";
		}else {
			Product productImages = productService.findById(productId.get());
			product.setImage1(productImages.getImage1());
			
			model.addAttribute("sub",subcategories);
			model.addAttribute("item", product);
			model.addAttribute("user",account);
			return "admin/product-add";
		}
	}
}
