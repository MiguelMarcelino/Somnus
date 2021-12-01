package com.somnus.server.postservice.post;

import com.somnus.server.postservice.exceptions.ErrorMessage;
import com.somnus.server.postservice.exceptions.SomnusException;
import com.somnus.server.postservice.user.Role;
import com.somnus.server.postservice.user.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PostService {

    public String normalizeName(String articleName) {
        return Arrays.stream(articleName.toLowerCase()
                        .split(" "))
                .reduce((a, b) -> a.concat("-").concat(b)).get();
    }

    public void postCreateAuthCheck(User user) {
        if (!user.getRole().equals(Role.ADMIN) && !user.getRole().equals(Role.MANAGER) &&
                !user.getRole().equals(Role.EDITOR)) {
            throw new SomnusException(ErrorMessage.ROLE_NOT_ALLOWED);
        }
    }
}
