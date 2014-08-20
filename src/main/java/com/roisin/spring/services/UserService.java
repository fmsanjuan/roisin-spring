package com.roisin.spring.services;

import java.util.ArrayList;
import java.util.Collection;

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

	@Autowired
	private UserRepository userRepository;

	private Md5PasswordEncoder encoder;

	public UserService() {
		super();
	}

	public boolean AmIMySelf(int userId) {
		return LoginService.getPrincipal().getId() == userId;
	}

	public boolean IAmACustomer() {
		return checkRole(Authority.USER);
	}

	public boolean IAmAnAdmin() {
		return checkRole(Authority.ADMIN);
	}

	public boolean AmIAGuest() {
		return false;
	}

	private boolean checkRole(String role) {
		Collection<Authority> authorities = LoginService.getPrincipal().getAuthorities();

		boolean res = false;

		for (Authority auth : authorities)
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

	public User findByUserAccount(UserAccount userAccount) {

		Assert.notNull(userAccount);
		User result;
		result = userRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public User create() {
		User user = new User();

		Authority auth = new Authority();
		Collection<Authority> lAuthorty = new ArrayList<Authority>();
		UserAccount usserA = new UserAccount();

		auth.setAuthority("USER");
		lAuthorty.add(auth);
		usserA.setAuthorities(lAuthorty);
		user.setUserAccount(usserA);

		return user;
	}

	public void save(User user, boolean passwordEncode) {
		Assert.notNull(user);
		if (passwordEncode) {
			encoder = new Md5PasswordEncoder();
			String oldPassword = user.getUserAccount().getPassword();
			String newPassword = encoder.encodePassword(oldPassword, null);
			user.getUserAccount().setPassword(newPassword);
		}
		userRepository.save(user);
	}

	public SignupForm constructNew() {

		SignupForm form = new SignupForm();
		User user = create();

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

		EditProfileForm form = new EditProfileForm();
		User user = findByPrincipal();

		form.setCity(user.getCity());
		form.setEmail(user.getUserAccount().getUsername());
		form.setId(user.getId());
		form.setName(user.getName());
		form.setNationality(user.getNationality());
		form.setSurname(user.getSurname());
		form.setVersion(user.getVersion());

		return form;
	}

	public User reconstruct(SignupForm form) {

		User user = create();

		user.setCity(form.getCity());
		user.setEmail(form.getEmail());
		user.setId(form.getId());
		user.setName(form.getName());
		user.setNationality(form.getNationality());
		user.setSurname(form.getSurname());
		user.setVersion(form.getVersion());

		user.getUserAccount().setUsername(form.getEmail());
		user.getUserAccount().setPassword(form.getPassword());

		return user;
	}

	public User reconstruct(EditProfileForm form) {

		User user = findByPrincipal();

		user.setCity(form.getCity());
		user.setEmail(form.getEmail());
		user.setId(form.getId());
		user.setName(form.getName());
		user.setNationality(form.getNationality());
		user.setSurname(form.getSurname());
		user.setVersion(form.getVersion());

		user.getUserAccount().setUsername(form.getEmail());
		if (StringUtils.isNotBlank(form.getNewPassword())) {
			user.getUserAccount().setPassword(form.getNewPassword());
		}

		return user;
	}

}
