package com.noithat.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noithat.entity.Account;
import com.noithat.entity.Email;
import com.noithat.service.AccountService;
import com.noithat.service.MailerService;

@Controller
public class AccountController {
	
	@Autowired
	MailerService mailer;
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/user")
	public String profile() {
		return "/user/profile";
	}
	@RequestMapping("/register")
	public String register() {
		return "/security/register";
	}
	
	@GetMapping("/forgetpassword")
	public String forgotPassword(Model model) {
		model.addAttribute("userRequest", new Email());
		return "/security/forgetpassword";
	}
	
	@PostMapping("/forgetpassword")
	public String demo(Model model,@ModelAttribute("userRequest") Email userRequest) {
		if (ObjectUtils.isNotEmpty(userRequest)) {
			for(Account ac : accountService.findAll()) {
				if (userRequest.getUsername().equalsIgnoreCase(ac.getUsername())) {
					mailer.queue(ac.getEmail(), "Lấy lại mật khẩu", ac.getPassword());
					String tb = "Mật khẩu đã được gửi qua email: " + ac.getEmail() + " của bạn";
					model.addAttribute("message",tb);
					return"/security/forgetPassword";
				}
			}
		} 
		model.addAttribute("message","Mời bạn nhập vào username");
		return"/security/forgetPassword";
	}
}
