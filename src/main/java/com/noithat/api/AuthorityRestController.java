package com.noithat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.entity.Authority;
import com.noithat.service.AuthorityService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/authority")
public class AuthorityRestController {
	@Autowired
	AuthorityService authorityService;
	
	@PostMapping
	public Authority create(@RequestBody Authority authority) {
		return authorityService.create(authority);
	}
	
}
