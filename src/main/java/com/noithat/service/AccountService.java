package com.noithat.service;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.noithat.entity.Account;

public interface AccountService {
	List<Account> findAll();

	Account findByEmail(String email);

	void loginFromOAuth2(OAuth2AuthenticationToken oauth2);

	
	
	
}
