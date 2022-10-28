package com.noithat.service;

import java.util.List;

import com.noithat.entity.Account;

public interface AccountService {
	List<Account> findAll();

	Account findByEmail(String email);
	
	
}
