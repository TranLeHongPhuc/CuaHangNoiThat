package com.noithat.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable{
	@Id
	@NotBlank(message = "Vui lòng nhập Mã danh mục")
	@Size(max = 10, message = "Tối đa 10 kí tự")
	String id;
	@NotBlank(message = "Vui lòng nhập tên danh mục")
	@Size(max = 40, message = "Tối đa 40 kí tự")
	String name;
	String icon;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Subcategory> subcategories;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Product> products;
}
