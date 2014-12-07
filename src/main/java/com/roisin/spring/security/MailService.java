package com.roisin.spring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.User;

@Service
@Transactional
public class MailService {

	private static final String SLASH = "/";

	private static final String HTTP = "http://";
	
	private static final String SECURITY_ACTIVATE = "/security/activate/";
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Autowired
	private LoginService loginService;
	
	@Value("${domain}")
	private String domain;

	public void sendActivationEmail(User user) {
		String activationKey = loginService.generateUserAccountActivationKey(user.getUserAccount());
		StringBuilder activationUrl = new StringBuilder();
		activationUrl.append(HTTP + domain + SECURITY_ACTIVATE);
		activationUrl.append(user.getUserAccount().getId());
		activationUrl.append(SLASH);
		activationUrl.append(activationKey);

		simpleMailMessage.setSentDate(new Date());
		simpleMailMessage.setText(String.format(simpleMailMessage.getText(), user.getName(),
				activationUrl.toString()));
		simpleMailMessage.setTo(user.getUserAccount().getUsername());
		mailSender.send(simpleMailMessage);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
