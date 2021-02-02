package com.somnus.server.backend.config.auth.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Anthony Waters
 * https://github.com/awaters1/spring-security-firebase/tree/46ff34ce0d44bb3728420046b6d1f20fa8ef4f8b
 */

@Component
public class FirebaseAuthentication {

    @Autowired
    private Environment environment;

    public FirebaseAuthentication(){}

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {

        FileInputStream serviceAccount = new FileInputStream(
                environment.getProperty("firebase.service.account.file.path"));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(environment.getProperty("firebase.database.url")).build();

        FirebaseApp.initializeApp(options);

        return FirebaseAuth.getInstance();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // you USUALLY want this
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedHeader("x-firebase-auth");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
