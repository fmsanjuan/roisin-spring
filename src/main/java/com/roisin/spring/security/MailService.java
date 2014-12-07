package com.roisin.spring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.User;

@Service
@Transactional
public class MailService {

	private static final String SLASH = "/";

	private static final String HTTP_LOCALHOST_8080_ACTIVATE = "http://localhost:8080/security/activate/";
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Autowired
	private LoginService loginService;

	public void sendActivationEmail(User user) {
		String activationKey = loginService.generateUserAccountActivationKey(user.getUserAccount());
		StringBuilder activationUrl = new StringBuilder();
		activationUrl.append(HTTP_LOCALHOST_8080_ACTIVATE);
		activationUrl.append(user.getUserAccount().getId());
		activationUrl.append(SLASH);
		activationUrl.append(activationKey);
		System.out.println("PREPARANDO EL ENVÍO");

		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setText(String.format(simpleMailMessage.getText(), user.getName(),
				activationUrl.toString()));
		simpleMailMessage.setTo(user.getUserAccount().getUsername());
		mailSender.send(simpleMailMessage);
		System.out.println("SE SUPONE QUE SE HA ENVIADO");
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
