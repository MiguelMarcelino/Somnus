package com.somnus.server.backend.config.auth.firebase;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author Anthony Waters
 * https://github.com/awaters1/spring-security-firebase/tree/46ff34ce0d44bb3728420046b6d1f20fa8ef4f8b
 */

public class FirebaseAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private final String token;

	public FirebaseAuthenticationToken(final String token) {
		super(null, null);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

//	public Object getPrincipal() {
//		return this.principal;
//	}

}
