package com.noithat.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.entity.Category;
import com.noithat.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategory(){
		return ResponseEntity.ok(categoryService.findAll());
	}

	@GetMapping("/{id}")
	public Category getOne(@PathVariable("id") String id) {
		return categoryService.findById(id);
	}
	
	@PostMapping
	public Category create(@RequestBody Category category) {
		return categoryService.create(category);
	}
	
	@PutMapping("/{id}")
	public Category update(@RequestBody Category category) {
		return categoryService.update(category);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		categoryService.delete(id);
	}
}
