package com.roisin.spring.security;

import static com.roisin.spring.utils.Constants.SLASH_SYMBOL;

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

	/**
	 * Http protocol
	 */
	private static final String HTTP = "http://";

	/**
	 * Security activate path
	 */
	private static final String SECURITY_ACTIVATE = "/security/activate/";

	/**
	 * Security password recovery path
	 */
	private static final String SECURITY_RECOVER = "/security/recover/";

	/**
	 * Mail sender
	 */
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Activation message from bean
	 */
	@Autowired
	@Qualifier("activationMessage")
	private transient SimpleMailMessage activationMessage;

	/**
	 * Password recovery message
	 */
	@Autowired
	@Qualifier("passwordRecoverMessage")
	private transient SimpleMailMessage passRecoverMsg;

	/**
	 * Login service
	 */
	@Autowired
	private transient LoginService loginService;

	/**
	 * Domain variable
	 */
	@Value("${domain}")
	private String domain;

	public void sendActivationEmail(final User user) {
		// A copy of the message established in mail.xml is modified and sent.
		final SimpleMailMessage message = new SimpleMailMessage(activationMessage);
		final String activationKey = loginService.generateUserAccountActivationKey(user
				.getUserAccount());
		final StringBuilder activationUrl = new StringBuilder();
		activationUrl.append(HTTP).append(domain).append(SECURITY_ACTIVATE)
				.append(user.getUserAccount().getId()).append(SLASH_SYMBOL);
		activationUrl.append(activationKey);

		message.setSentDate(new Date());
		message.setText(String.format(message.getText(), user.getName(), activationUrl.toString()));
		message.setTo(user.getUserAccount().getUsername());
		mailSender.send(message);
	}

	public void sendPasswordRecoverEmail(final UserAccount userAccount) {
		// A copy of the message established in mail.xml is modified and sent.
		final SimpleMailMessage message = new SimpleMailMessage(passRecoverMsg);
		final String passRecoveryKey = loginService.generatePasswordRecoveryKey(userAccount);
		final StringBuilder strb = new StringBuilder();
		strb.append(HTTP).append(domain).append(SECURITY_RECOVER).append(userAccount.getId())
				.append(SLASH_SYMBOL);
		strb.append(passRecoveryKey);

		message.setSentDate(new Date());
		message.setText(String.format(message.getText(), strb.toString()));
		message.setTo(userAccount.getUsername());
		mailSender.send(message);
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(final String domain) {
		this.domain = domain;
	}
}
