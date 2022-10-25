package com.noithat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noithat.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

}
