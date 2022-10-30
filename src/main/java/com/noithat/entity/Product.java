package com.noithat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "products")
public class Product implements Serializable{

	@Id
	Integer id;
	String name;
	String image1;
	String image2;
	String image3;
	String image4;
	Double price;
	Integer discount;
	Integer quantity;
	String description;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	Date createDate = new Date();
	
	Boolean available;
	
	@ManyToOne
	@JoinColumn(name = "Category_Id")
	Category category;
	
	@ManyToOne
	@JoinColumn(name = "SubcategoryId")
	Subcategory subcategory;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;
}
