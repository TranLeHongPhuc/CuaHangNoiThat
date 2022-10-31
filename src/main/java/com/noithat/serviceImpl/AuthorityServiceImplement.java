package com.noithat.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noithat.entity.Authority;
import com.noithat.repository.AuthorityRepository;
import com.noithat.service.AuthorityService;

@Service
public class AuthorityServiceImplement implements AuthorityService{
	@Autowired
	AuthorityRepository authorityRepo;

	@Override
	public Authority create(Authority authority) {
		
		return authorityRepo.save(authority);
	}
	
	
}
