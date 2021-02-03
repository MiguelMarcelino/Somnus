package com.somnus.server.backend.config.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.apache.commons.lang3.StringUtils;
//import com.google.firebase.tasks.Task;
//import com.google.firebase.tasks.Tasks;

/**
 * @author prvoslav
 */
public class FirebaseParser {
    public static FirebaseTokenHolder parseToken(String idToken) {
//        if (StringUtils.isBlank(idToken)) {
//            throw new IllegalArgumentException("FirebaseTokenBlank");
//        }
//        try {
//            Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(idToken);
//
//            Tasks.await(authTask);
//
//            return new FirebaseTokenHolder(authTask.getResult());
//        } catch (Exception e) {
//            throw new SomnusException(ErrorMessage.INVALID_AUTH_TOKEN, e.getMessage());
//        }
        return null;
    }
}
