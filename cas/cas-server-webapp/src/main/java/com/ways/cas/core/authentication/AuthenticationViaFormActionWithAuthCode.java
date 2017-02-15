package com.ways.cas.core.authentication;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.RootCasException;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.RequestContext;

import com.google.code.kaptcha.Constants;
import com.ways.cas.core.authentication.exception.BadAuthcodeAuthenticationException;
import com.ways.cas.core.authentication.exception.NullAuthcodeAuthenticationException;

/**
 * 验证码校验类
 * 
 * @author Vincent
 * 
 */
public class AuthenticationViaFormActionWithAuthCode extends AuthenticationViaFormAction {
	/**
	 * authcode check
	 */
	public final String validatorCode(final RequestContext context, final Credential credentials,
			final MessageContext messageContext) throws Exception {
		
		
		final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
		HttpSession session = request.getSession();
		String authcode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		System.out.println("coming................."+session.getAttributeNames());
		
		Enumeration e = session.getAttributeNames();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement()/*+":"+session.getAttribute(e.nextElement())*/);
		}
		

		UsernamePasswordCredentialWithAuthCode upc = (UsernamePasswordCredentialWithAuthCode) credentials;
		String submitAuthcode = upc.getAuthcode();
		if (StringUtils.isEmpty(submitAuthcode) || StringUtils.isEmpty(authcode)) {
			populateErrorsInstance(new NullAuthcodeAuthenticationException(), messageContext);
			return "error";
		}
		if (submitAuthcode.equals(authcode)) {
			return "success";
		}
		populateErrorsInstance(new BadAuthcodeAuthenticationException(), messageContext);
		return "error";
	}

	private void populateErrorsInstance(final RootCasException e, final MessageContext messageContext) {

		try {
			messageContext.addMessage(new MessageBuilder().error().code(e.getCode()).defaultText(e.getCode()).build());
		} catch (final Exception fe) {
			logger.error(fe.getMessage(), fe);
		}
	}
}