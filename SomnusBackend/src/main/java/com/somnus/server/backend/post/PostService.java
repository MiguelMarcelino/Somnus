package com.somnus.server.backend.post;

import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.users.domain.Role;
import com.somnus.server.backend.users.domain.User;
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
