package com.noithat.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Product;
import com.noithat.entity.Subcategory;
import com.noithat.service.ProductService;
import com.noithat.service.SubcategoryService;

@Controller
public class ProductManagementController {
	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;

	@RequestMapping("/product/list")
	public String getAll() {
		return "admin/product-list.html";
	}
	
	@RequestMapping("/product/{id}")
	public String getEdit(Model model, @PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
		model.addAttribute("item",product);
		model.addAttribute("sub",subcategories);
		return "admin/product-add.html";
	}
	
	@RequestMapping("/product/add")
	public String addProduct(Model model, @ModelAttribute("item") Product product) {
		product = new Product();
		product.setId(-999);
		model.addAttribute("item", product);
		return "admin/product-add.html";
	}
}
