package com.roisin.spring.forms;

/**
 * Change password form for password recovery email
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class ChangePasswordForm {

	/**
	 * New password
	 */
	private String newPassword;

	/**
	 * Repeat new password
	 */
	private String repeatNewPassword;

	/**
	 * User account identifier
	 */
	private Integer userAccountId;

	/**
	 * Generated token
	 */
	private String key;

	public ChangePasswordForm() {

	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(final String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public Integer getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(final Integer userAccountId) {
		this.userAccountId = userAccountId;
	}

}
