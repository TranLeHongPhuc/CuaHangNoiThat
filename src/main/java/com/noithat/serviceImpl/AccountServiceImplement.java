package com.noithat.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noithat.entity.Account;
import com.noithat.repository.AccountRepository;
import com.noithat.service.AccountService;

@Service
public class AccountServiceImplement implements AccountService{
	@Autowired
	AccountRepository accountRepo;
	
	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepo.findByEmail(email);
	}

}
