package com.noithat.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.noithat.entity.Account;
import com.noithat.entity.Authority;
import com.noithat.repository.AccountRepository;
import com.noithat.repository.AuthorityRepository;
import com.noithat.repository.RoleRepository;
import com.noithat.service.AccountService;
import com.noithat.service.MailerService;

@Service
public class AccountServiceImplement implements AccountService {
	@Autowired
	AccountRepository accountRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	AuthorityRepository authorityRepo;
	
	@Autowired
	PasswordEncoder pe;
	
	@Autowired
	MailerService mailer;
	
	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}
	
	@Override
	public Account findByUsername(String username) {
		return accountRepo.findByUsername(username);
	}

	@Override
	public Account create(Account account) {
		return accountRepo.save(account);
	}
	
	@Override
	public Account update(Account account) {
		return accountRepo.save(account);
	}

	
	@Override
	public Account findByEmail(String email) {
		return accountRepo.findByEmail(email);
	}

	@Override
	public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) throws MessagingException {
		String email = oauth2.getPrincipal().getAttribute("email");
		String address = oauth2.getPrincipal().getAttribute("address");
		String phone = oauth2.getPrincipal().getAttribute("phone");
		String password = Long.toHexString(System.currentTimeMillis());
		Account account = accountRepo.findByEmail(email);
		UserDetails user = null;
		if (account == null) {
			account = new Account(email, pe.encode(password), oauth2.getPrincipal().getAttribute("name"), email, null, phone,
					address, true, null, null, null);
			accountRepo.save(account);
			Authority authority = new Authority(null, account, roleRepo.findById("USER").get());
			authorityRepo.save(authority);
			user = User.withUsername(account.getEmail()).password(account.getPassword()).roles("USER").build();
			//send password to email
			String subject = "Ki???m tra m???t kh???u c???a b???n khi ????ng nh???p v???i google";
			String to = email;

			String body = "<p>K??nh g???i " + oauth2.getPrincipal().getAttribute("name")+ ",</p>";

			body += "<p>Ch??o m???ng b???n ???? ????ng nh???p v??o website ch??ng t??i b???ng t??i kho???n google.\r\n"
					+ "M???t kh???u n??y d??ng ????? ????ng nh???p v??o website c???a ch??ng t??i khi b???n kh??ng mu???n ch???n ????ng nh???p v???i google </p>";
			body += "<p> Username c???a b???n: <h2>" + email  + "</h2> </p>";
			body += "<p> M???t kh???u c???a b???n: <h2>" + password  + "</h2> </p>";

			body += "<p>Thank you <br> Team</p>";

			mailer.send(to, subject, body);
			
		} else {
			String[] roles = account.getAuthorities().stream().map(er -> er.getRole().getId())
					.collect(Collectors.toList()).toArray(new String[0]);
			user = User.withUsername(account.getUsername()).password(account.getPassword()).roles(roles).build();
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

}
