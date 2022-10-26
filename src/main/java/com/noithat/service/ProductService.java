package com.noithat.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.noithat.entity.Product;

public interface ProductService {
	List<Product> findAll();
	
	List<Product> findByAllCategoryId(String cid);
	
	Product findById(Integer id);
}
