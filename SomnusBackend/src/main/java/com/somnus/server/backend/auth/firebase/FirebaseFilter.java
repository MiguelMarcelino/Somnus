package com.somnus.server.backend.auth.firebase;

import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.users.UserService;
import com.somnus.server.backend.users.domain.User;
import com.somnus.server.backend.users.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.somnus.server.backend.auth.SecurityConstants.FIREBASE_HEADER_NAME;

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
        if (StringUtils.isBlank(xAuth)) {
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
