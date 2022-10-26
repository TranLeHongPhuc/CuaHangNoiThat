package com.noithat.service;

import java.util.List;

import com.noithat.entity.Category;

public interface CategoryService {
	List<Category> fillAll();
	
	Category findById(String categoryId);
	
	
}
