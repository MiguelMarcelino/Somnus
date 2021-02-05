package com.somnus.server.backend.auth.firebase;

//import com.google.firebase.tasks.Task;
//import com.google.firebase.tasks.Tasks;

import org.springframework.stereotype.Component;

/**
 * @author prvoslav
 */
@Component
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
