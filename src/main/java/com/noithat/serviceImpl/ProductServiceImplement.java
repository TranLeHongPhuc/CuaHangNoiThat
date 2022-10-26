package com.noithat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.noithat.entity.Product;
import com.noithat.service.ProductService;
import com.noithat.repository.ProductRepository;

@Service
public class ProductServiceImplement implements ProductService{
	@Autowired
	ProductRepository pdao;

	@Override
	public List<Product> findAll() {
		return pdao.findAll();
	}

	@Override
	public List<Product> findByAllCategoryId(String cid) {
		return pdao.findAllByCategoryId(cid);
	}

	@Override
	public Product findById(Integer id) {
		return pdao.findById(id).get();
	}

}
