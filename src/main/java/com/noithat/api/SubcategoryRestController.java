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
	
	@PostMapping
	public Subcategory create(@RequestBody Subcategory subcategory) {
		return subcategoryService.create(subcategory);
	}
	
	@PutMapping("/{id}")
	public Subcategory update(@PathVariable("id") String id,
			@RequestBody Subcategory subcategory) {
		return subcategoryService.update(subcategory);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		subcategoryService.delete(id);
	}
	
	
//	@GetMapping("{id}")
//	public List<Subcategory> getListId(@PathVariable("id") String id) {
//		return subcategoryService.findListById(id);
//	}
	
}
