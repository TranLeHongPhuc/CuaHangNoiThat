package com.noithat.service;

import java.util.List;

import com.noithat.entity.Subcategory;

public interface SubcategoryService {
	List<Subcategory> findAll();
	
	List<Subcategory> findByCategoryId(String categoryId);
	
	Subcategory findById(String subcategoryId);
	
	Subcategory create(Subcategory subcategory);
	
	Subcategory update(Subcategory subcategory);
	
	void delete(String id);
}
