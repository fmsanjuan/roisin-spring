package com.roisin.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Custom authentication failure handler for Roisin.
 * 
 * @author Félix Miguel Sanjuán Segovia <felsanseg@alum.us.es>
 *
 */
public class RoisinAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
		implements AuthenticationFailureHandler {

	private static final String SECURITY_ACTIVATION = "/security/activation";

	private static final String SECURITY_CREDENTIALS = "/security/credentials";

	protected final Log logger = LogFactory.getLog(getClass());

	public RoisinAuthenticationFailureHandler() {
		super();
	}

	public RoisinAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		saveException(request, exception);

		if (exception instanceof AuthenticationCredentialsNotFoundException) {
			if (isUseForward()) {
				logger.debug("Forwarding to " + SECURITY_CREDENTIALS);

				request.getRequestDispatcher(SECURITY_CREDENTIALS).forward(request, response);
			} else {
				logger.debug("Redirecting to " + SECURITY_CREDENTIALS);
				getRedirectStrategy().sendRedirect(request, response, SECURITY_CREDENTIALS);
			}
		} else if (exception instanceof DisabledException) {
			if (isUseForward()) {
				logger.debug("Forwarding to " + SECURITY_ACTIVATION);

				request.getRequestDispatcher(SECURITY_ACTIVATION).forward(request, response);
			} else {
				logger.debug("Redirecting to " + SECURITY_ACTIVATION);
				getRedirectStrategy().sendRedirect(request, response, SECURITY_ACTIVATION);
			}
		} else if (exception instanceof BadCredentialsException) {
		if (isUseForward()) {
			logger.debug("Forwarding to " + SECURITY_CREDENTIALS);

			request.getRequestDispatcher(SECURITY_CREDENTIALS).forward(request, response);
		} else {
			logger.debug("Redirecting to " + SECURITY_CREDENTIALS);
			getRedirectStrategy().sendRedirect(request, response, SECURITY_CREDENTIALS);
		}
	}

	}

}
