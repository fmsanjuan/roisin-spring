package com.roisin.spring.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.roisin.spring.forms.EditProfileForm;
import com.roisin.spring.forms.SignupForm;
import com.roisin.spring.model.User;
import com.roisin.spring.repositories.UserRepository;
import com.roisin.spring.security.Authority;
import com.roisin.spring.security.LoginService;
import com.roisin.spring.security.UserAccount;

@Service
@Transactional
public class UserService {

	/**
	 * User repository
	 */
	@Autowired
	private transient UserRepository userRepository;

	/**
	 * Md5 password encoder
	 */
	private transient Md5PasswordEncoder encoder;

	public UserService() {
		super();
	}

	public boolean amIMySelf(final int userId) {
		return LoginService.getPrincipalId(LoginService.getPrincipal()) == userId;
	}

	public boolean iAmACustomer() {
		return checkRole(Authority.USER);
	}

	public boolean iAmAnAdmin() {
		return checkRole(Authority.ADMIN);
	}

	public boolean amIAGuest() {
		return false;
	}

	private boolean checkRole(final String role) {
		final Collection<Authority> authorities = LoginService.getPrincipal().getAuthorities();

		boolean res = false;

		for (final Authority auth : authorities)
			res = res || auth.getAuthority().toUpperCase().compareTo(role) == 0;

		return res;
	}

	public User findByPrincipal() {

		User result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public User findByUserAccount(final UserAccount userAccount) {

		Assert.notNull(userAccount);
		User result;
		result = userRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public User create() {
		final User user = new User();

		final Authority auth = new Authority();
		final Collection<Authority> lAuthorty = new ArrayList<Authority>();
		final UserAccount usserA = new UserAccount();

		auth.setAuthority("USER");
		lAuthorty.add(auth);
		usserA.setAuthorities(lAuthorty);
		user.setUserAccount(usserA);

		return user;
	}

	public User save(final User user, final boolean passwordEncode) {
		Assert.notNull(user);
		if (passwordEncode) {
			encoder = new Md5PasswordEncoder();
			final String oldPassword = user.getUserAccount().getPassword();
			final String newPassword = encoder.encodePassword(oldPassword, null);
			user.getUserAccount().setPassword(newPassword);
		}
		return userRepository.save(user);
	}

	public SignupForm constructNew() {

		final SignupForm form = new SignupForm();
		final User user = create();

		form.setCity(user.getCity());
		form.setEmail(user.getUserAccount().getUsername());
		form.setId(user.getId());
		form.setName(user.getName());
		form.setNationality(user.getNationality());
		form.setPassword(user.getUserAccount().getPassword());
		form.setSurname(user.getSurname());
		form.setVersion(user.getVersion());

		return form;
	}

	public EditProfileForm constructEditForm() {

		final EditProfileForm form = new EditProfileForm();
		final User user = findByPrincipal();

		form.setCity(user.getCity());
		form.setEmail(user.getUserAccount().getUsername());
		form.setId(user.getId());
		form.setName(user.getName());
		form.setNationality(user.getNationality());
		form.setSurname(user.getSurname());
		form.setVersion(user.getVersion());

		return form;
	}

	public User reconstruct(final SignupForm form) {

		final User user = create();

		user.setCity(form.getCity());
		user.setEmail(form.getEmail());
		user.setId(form.getId());
		user.setName(form.getName());
		user.setNationality(form.getNationality());
		user.setSurname(form.getSurname());
		user.setVersion(form.getVersion());

		user.getUserAccount().setUsername(form.getEmail());
		user.getUserAccount().setPassword(form.getPassword());
		user.getUserAccount().setEnabled(false);
		user.getUserAccount().setLocked(false);
		user.getUserAccount().setActivation(new Date());

		return user;
	}

	public User reconstruct(final EditProfileForm form) {

		final User user = findByPrincipal();

		user.setCity(form.getCity());
		user.setEmail(form.getEmail());
		user.setId(form.getId());
		user.setName(form.getName());
		user.setNationality(form.getNationality());
		user.setSurname(form.getSurname());
		user.setVersion(form.getVersion());

		user.getUserAccount().setUsername(form.getEmail());
		user.getUserAccount().setEnabled(true);
		user.getUserAccount().setLocked(false);
		user.getUserAccount().setActivation(new Date());
		if (StringUtils.isNotBlank(form.getNewPassword())) {
			user.getUserAccount().setPassword(form.getNewPassword());
		}

		return user;
	}

}
