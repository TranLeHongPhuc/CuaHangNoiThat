package com.noithat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noithat.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{

}
