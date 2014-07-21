package com.roisin.spring.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.roisin.spring.model.Administrator;
import com.roisin.spring.repositories.AdministratorRepository;
import com.roisin.spring.security.Authority;
import com.roisin.spring.security.LoginService;
import com.roisin.spring.security.UserAccount;

public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	public AdministratorService() {
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

	public Administrator findByPrincipal() {

		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccount(UserAccount adminAccount) {

		Assert.notNull(adminAccount);
		Administrator result;
		result = administratorRepository.findByUserAccountId(adminAccount.getId());

		return result;
	}
}
