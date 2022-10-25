package com.noithat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noithat.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("Select p From Product p where p.category.id=?1")
    List<Product> findByCategoryId(String cid);
}
