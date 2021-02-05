package com.somnus.server.backend.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author prvoslav
 */
@Component
public class FirebaseParser {
    public static FirebaseTokenHolder parseToken(String idToken) {
        if (StringUtils.isBlank(idToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            return new FirebaseTokenHolder(firebaseToken);
        } catch (Exception e) {
            throw new SomnusException(ErrorMessage.INVALID_AUTH_TOKEN, e.getMessage());
        }
    }
}
