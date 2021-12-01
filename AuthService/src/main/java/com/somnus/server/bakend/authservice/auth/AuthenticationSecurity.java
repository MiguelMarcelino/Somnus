package com.somnus.server.bakend.authservice.auth;

import com.somnus.server.bakend.authservice.auth.firebase.FirebaseAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author prvoslav
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserDetailsService userService;

    @Value("${rs.pscode.firebase.enabled}")
    private Boolean firebaseEnabled;

    @Autowired
    private FirebaseAuthenticationProvider firebaseProvider;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        if (firebaseEnabled) {
            auth.authenticationProvider(firebaseProvider);
        }
    }
}