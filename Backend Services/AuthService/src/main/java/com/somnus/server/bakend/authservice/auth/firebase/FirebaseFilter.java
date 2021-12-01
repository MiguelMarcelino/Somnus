package com.somnus.server.bakend.authservice.auth.firebase;

import com.somnus.server.bakend.authservice.users.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.somnus.server.bakend.authservice.auth.SecurityConstants.AUTHENTICATE_USER_HEADER_NAME;
import static com.somnus.server.bakend.authservice.auth.SecurityConstants.FIREBASE_HEADER_NAME;

/**
 * @author prvoslav
 */
public class FirebaseFilter extends OncePerRequestFilter {

    private UserService userService;

    public FirebaseFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String xAuth = request.getHeader(FIREBASE_HEADER_NAME);
        String loginHeader = request.getHeader(AUTHENTICATE_USER_HEADER_NAME);
        if (StringUtils.isBlank(xAuth) || !StringUtils.isBlank(loginHeader)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            try {
                FirebaseTokenHolder holder = FirebaseParser.parseToken(xAuth);

                String userName = holder.getUid();

                Authentication auth = new FirebaseAuthenticationToken(userService.loadUserByUsername(userName), holder);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            } catch (BadCredentialsException e) {
                throw new SecurityException(e);
            }
        }
    }


}
