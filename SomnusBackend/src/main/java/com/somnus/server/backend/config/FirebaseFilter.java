package com.somnus.server.backend.config;

import com.somnus.server.backend.config.auth.firebase.FirebaseAuthenticationToken;
import com.somnus.server.backend.config.auth.firebase.FirebaseParser;
import com.somnus.server.backend.config.auth.firebase.FirebaseTokenHolder;
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

/**
 * @author prvoslav
 */
public class FirebaseFilter extends OncePerRequestFilter {
    private static String HEADER_NAME = "X-Authorization-Firebase";

    public FirebaseFilter() { }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String xAuth = request.getHeader(HEADER_NAME);
        if (StringUtils.isBlank(xAuth)) {
            filterChain.doFilter(request, response);
            return;
        } else {
            try {
                FirebaseTokenHolder holder = FirebaseParser.parseToken(xAuth);

                String userName = holder.getUid();

                Authentication auth = new FirebaseAuthenticationToken(userName, holder);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            } catch (BadCredentialsException e) {
                throw new SecurityException(e);
            }
        }
    }
}
