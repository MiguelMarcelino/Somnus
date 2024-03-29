package com.somnus.server.backend.config;

import com.somnus.server.backend.auth.firebase.FirebaseAuthenticationProvider;
import com.somnus.server.backend.auth.firebase.FirebaseFilter;
import com.somnus.server.backend.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${rs.pscode.firebase.enabled}")
    private boolean firebaseEnabled;

    @Autowired
    private UserService userService;

    @Autowired
    private FirebaseAuthenticationProvider authenticationProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    private FirebaseFilter tokenAuthorizationFilter() {
        return new FirebaseFilter(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(firebaseEnabled) {
            http.addFilterBefore(tokenAuthorizationFilter(), BasicAuthenticationFilter.class)
                    .authorizeRequests()
                    .and()
                    .cors()
                    .and()
                    // we don't need CSRF because our token is invulnerable
                    .csrf().disable()
                    // All urls must be authenticated (filter for token always fires (/**)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers("/auth/**").authenticated()
                    .and()
                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //.and()
        } else {
            http.httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .anyRequest().permitAll();
        }
    }
}
