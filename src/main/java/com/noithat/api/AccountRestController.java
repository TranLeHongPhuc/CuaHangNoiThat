package com.noithat.api;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.entity.Account;
import com.noithat.entity.Authority;
import com.noithat.repository.AuthorityRepository;
import com.noithat.repository.RoleRepository;
import com.noithat.service.AccountService;
import com.noithat.service.MailerService;

import net.bytebuddy.utility.RandomString;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {
	@Autowired
	AccountService accountService;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	AuthorityRepository authorityRepo;
	
	@Autowired
	MailerService mailer;

	@Autowired
	PasswordEncoder pe;
		 

	@GetMapping
	public List<Account> getAll() {
		return accountService.findAll();
	}
	
	@GetMapping("{username}")
	public Account getByUsername(@PathVariable("username") String username) {
		return accountService.findByUsername(username);
	}	
	
	@GetMapping("/email/{email}")
	public Account getByEmail(@PathVariable("email") String email) {
		return accountService.findByEmail(email);
	}	
	
	@PutMapping("/change")
	public Account changePassword( @RequestBody Account account) {
		account.setPassword(pe.encode(account.getPassword()));
		return accountService.update(account);
	}
	
	//forget password
	@PutMapping("/forget/{username}")
	public Account updateverification(@PathVariable("username") String username, @RequestBody Account account) throws MessagingException {
		String randomCode = RandomString.make(8);
		String subject = "Please check YOUR Vevification Code for ForgetPassword";
		String to = account.getEmail();

		String body = "<p>Dear " + account.getFullname() + ",</p>";

		body += "<p> Please get this code log back type Confirm Code </p>";

		body += "<p> Your code <h2>" + randomCode + "</h2> </p>";

		body += "<p>Thank you <br> Team</p>";

		mailer.send(to, subject, body);
		account.setVerificationCode(randomCode);
		return accountService.update(account);
	}
	
	// register account
	String checkCode = "";
	String random = "";
	
	@PutMapping("{username}")
	public Account update(@PathVariable("username") String username, @RequestBody Account account) {
//		Account accountnew = accountService.getOne(id);
		account.setChecked(true);
		return accountService.update(account);
	}

	@PostMapping("/register/{email}")
	public Account registerSendEmail(@PathVariable("email") String email, @RequestBody Account account, Model model)
			throws MessagingException {
		String randomCode = RandomString.make(8);
		random = randomCode;
		account.setVerificationCode(random);
		account.setPassword(pe.encode(account.getPassword()));
		sendRandomCodeEmail(account, randomCode);
		return accountService.create(account);
	}

	public void sendRandomCodeEmail(Account item, String randomCode) throws MessagingException {
		String subject = "Please check YOUR Vevification Code";
		String to = item.getEmail();

		String body = "<p>Dear " + item.getFullname() + ",</p>";

		body += "<p> Please get this code log back type Confirm Code </p>";

		body += "<p> Your code <h2>" + randomCode + "</h2> </p>";

		body += "<p>Thank you <br> Team</p>";

		mailer.send(to, subject, body);
	}
}
