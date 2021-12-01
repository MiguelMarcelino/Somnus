package com.somnus.server.bakend.authservice.auth.firebase;

import com.somnus.server.bakend.authservice.exceptions.ErrorMessage;
import com.somnus.server.bakend.authservice.exceptions.SomnusException;
import com.somnus.server.bakend.authservice.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author prvoslav
 */
@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	public boolean supports(Class<?> authentication) {
		return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}

		FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
		UserDetails userDetails = userService.loadUserByUsername(authenticationToken.getName());
		if (userDetails == null) {
			throw new SomnusException(ErrorMessage.FIREBASE_USER_DOES_NOT_EXIST);
		}

		authenticationToken = new FirebaseAuthenticationToken(userDetails, authentication.getCredentials(),
				userDetails.getAuthorities());

		return authenticationToken;
	}
}
