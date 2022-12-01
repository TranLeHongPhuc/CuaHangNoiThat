package com.noithat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Orders")
public class Order implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "CreateDate")
	Date createDate = new Date();
	
	String address;
	String description;
	String phone;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "OrderStatusId")
	OrderStatus orderstatus;
	
	@ManyToOne
	@JoinColumn(name = "username")
	Account account;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;
	
}
