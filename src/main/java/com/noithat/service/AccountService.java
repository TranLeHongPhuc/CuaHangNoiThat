package com.noithat.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import com.noithat.entity.Account;

public interface AccountService {
	List<Account> findAll();
	
	Account findByUsername(String username);

	Account findByEmail(String email);

	void loginFromOAuth2(OAuth2AuthenticationToken oauth2) throws MessagingException;

	Account create(Account account) ;
		
	Account update(Account account) ;
	
	
}
