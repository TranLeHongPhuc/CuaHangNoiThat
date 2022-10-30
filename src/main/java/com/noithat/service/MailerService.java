package com.noithat.service;

import javax.mail.MessagingException;

import com.noithat.entity.MailInfo;

public interface MailerService {
	void send(MailInfo mail) throws MessagingException;
	void send(String to,String subject, String body) throws MessagingException;
	void queue(MailInfo mail);
	void queue(String to,String subject,String body);
}
