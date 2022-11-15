package com.noithat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noithat.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
	@Query("SELECT a FROM Account a WHERE a.email LIKE ?1")
	Account findByEmail(String email);
	
	@Query("SELECT a FROM Account a WHERE a.username LIKE ?1")
	Account findByUsername(String username);
}
