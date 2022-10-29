package com.noithat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.noithat.service.ProductService;

import com.noithat.entity.Product;

@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	
    void page(Model model, Page<Product> list, @PathVariable("pageNumber") int currentPage) {
        int totalPages = list.getTotalPages();
        long totalItems = list.getTotalElements();
        List<Product> products = list.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("countries", products);
    }
    
    @GetMapping("/product/list")
    public String getAllPages(Model model, @RequestParam("cid") Optional<String> cid) {
        return list(model, cid, 1);
    }
	
    @RequestMapping("/product/list/page/{pageNumber}")
    public String list(Model model, @RequestParam("cid") Optional<String> cid, @PathVariable("pageNumber") int currentPage) {
        if (cid.isPresent()) {
            Page<Product> list = productService.findAllByCategoryId(cid.get(), currentPage);
            model.addAttribute("items", list);
            model.addAttribute("cid", cid.get());
            page(model, list, currentPage);
        }else {
            Page<Product> list = productService.findAll(currentPage);
            model.addAttribute("items", list);
            page(model, list, currentPage);
        }
        return "product/home";
    }

	@RequestMapping("/product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Product item = productService.findById(id);
		model.addAttribute("item", item);
		return "product/detail";
	}
}
