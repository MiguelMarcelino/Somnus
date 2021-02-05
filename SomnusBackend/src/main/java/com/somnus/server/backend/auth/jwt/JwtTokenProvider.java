package com.somnus.server.backend.auth.jwt;

import com.somnus.server.backend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTokenProvider {

    @Autowired
    private UserRepository userRepository;

    private static PublicKey publicKey;
    private static PrivateKey privateKey;
}
