package com.noithat.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

	@Override
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		String password = Long.toHexString(System.currentTimeMillis());
		Account account = accountRepo.findByEmail(email);
		UserDetails user=null;
		if (account == null) {
			account = new Account(email, password, oauth2.getPrincipal().getAttribute("name"), email, null, null, null);
			accountRepo.save(account);
			user = User.withUsername(account.getEmail()).password(account.getPassword()).roles("USER").build();
		}else {
			String[] roles = account.getAuthorities().stream()
					.map(er -> er.getRole().getId())
					.collect(Collectors.toList())
					.toArray(new String[0]);
			user = User.withUsername(account.getEmail()).password(account.getPassword()).roles(roles).build();
		}
		

		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	

}
