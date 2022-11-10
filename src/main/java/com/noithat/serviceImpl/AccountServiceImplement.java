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
		return accountRepo.findById(username).get();
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
			String subject = "Kiểm tra mật khẩu của bạn khi đăng nhập với google";
			String to = email;

			String body = "<p>Kính gửi " + oauth2.getPrincipal().getAttribute("name")+ ",</p>";

			body += "<p>Chào mừng bạn đã đăng nhập vào website chúng tôi bằng tài khoản google.\r\n"
					+ "Mật khẩu này dùng để đăng nhập vào website của chúng tôi khi bạn không muốn chọn đăng nhập với google </p>";
			body += "<p> Username của bạn: <h2>" + email  + "</h2> </p>";
			body += "<p> Mật khẩu của bạn: <h2>" + password  + "</h2> </p>";

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
