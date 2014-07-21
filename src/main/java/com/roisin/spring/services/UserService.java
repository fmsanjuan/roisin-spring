package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

}
