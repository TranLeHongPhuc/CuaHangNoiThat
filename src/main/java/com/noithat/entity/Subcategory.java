package com.noithat.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Subcategories")
public class Subcategory implements Serializable{
	
	@Id
	String id;
	String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subcategory")
	List<Product> products;
	
	@ManyToOne
	@JoinColumn(name = "Categoryid")
	Category category;
}
