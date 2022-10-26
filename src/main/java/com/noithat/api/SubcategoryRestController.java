package com.noithat.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.entity.Subcategory;
import com.noithat.service.SubcategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/subcategories")
public class SubcategoryRestController {
	@Autowired
	SubcategoryService subcategoryService;

	@GetMapping
	public ResponseEntity<List<Subcategory>> getSubcategory(){
		return ResponseEntity.ok(subcategoryService.findAll());
	}
	
	
}
