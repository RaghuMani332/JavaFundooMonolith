package com.bl.fundoo.mailsender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	
	@Autowired
	private JavaMailSender mailsender;
	
	public void sendEmail(String toEmail ,String subject ,String body)
	{
		SimpleMailMessage smm=new SimpleMailMessage();
		smm.setFrom("raghumani11154@gmail.com");
		smm.setTo(toEmail);
		smm.setSubject(subject);
		smm.setText(body);
		mailsender.send(smm);
		System.out.println("mail sent to "+ toEmail +" successfully");
	}
}
