package com.roisin.spring.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	private static final String SECURITY_RECOVER = "/security/recover/";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	@Qualifier("activationMessage")
	private SimpleMailMessage activationMessage;

	@Autowired
	@Qualifier("passwordRecoverMessage")
	private SimpleMailMessage passwordRecoverMessage;

	@Autowired
	private LoginService loginService;

	@Value("${domain}")
	private String domain;

	public void sendActivationEmail(User user) {
		String activationKey = loginService.generateUserAccountActivationKey(user.getUserAccount());
		StringBuilder activationUrl = new StringBuilder();
		activationUrl.append(HTTP);
		activationUrl.append(domain);
		activationUrl.append(SECURITY_ACTIVATE);
		activationUrl.append(user.getUserAccount().getId());
		activationUrl.append(SLASH);
		activationUrl.append(activationKey);

		activationMessage.setSentDate(new Date());
		activationMessage.setText(String.format(activationMessage.getText(), user.getName(),
				activationUrl.toString()));
		activationMessage.setTo(user.getUserAccount().getUsername());
		mailSender.send(activationMessage);
	}

	public void sendPasswordRecoverEmail(UserAccount userAccount) {
		String passwordRecoveryKey = loginService.generatePasswordRecoveryKey(userAccount);
		StringBuilder sb = new StringBuilder();
		sb.append(HTTP);
		sb.append(domain);
		sb.append(SECURITY_RECOVER);
		sb.append(userAccount.getId());
		sb.append(SLASH);
		sb.append(passwordRecoveryKey);

		passwordRecoverMessage.setSentDate(new Date());
		passwordRecoverMessage.setText(String.format(passwordRecoverMessage.getText(),
				sb.toString()));
		passwordRecoverMessage.setTo(userAccount.getUsername());
		mailSender.send(passwordRecoverMessage);
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
