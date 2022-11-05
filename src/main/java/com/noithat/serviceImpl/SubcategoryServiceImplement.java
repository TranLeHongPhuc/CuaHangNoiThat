package com.noithat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noithat.entity.Subcategory;
import com.noithat.repository.SubcategoryRepository;
import com.noithat.service.SubcategoryService;

@Service
public class SubcategoryServiceImplement implements SubcategoryService{
	@Autowired
	SubcategoryRepository subcategoryRepo;
	
	@Override
	public List<Subcategory> findAll() {
		return subcategoryRepo.findAll();
	}

	@Override
	public Subcategory findById(String subcategoryId) {
		return subcategoryRepo.findById(subcategoryId).get();
	}

	@Override
	public Subcategory create(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	public Subcategory update(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	public void delete(String id) {
		subcategoryRepo.deleteById(id);
	}

	@Override
	public List<Subcategory> findByCategoryId(String categoryId) {
		return subcategoryRepo.findByCategoryId(categoryId);
	}


}
