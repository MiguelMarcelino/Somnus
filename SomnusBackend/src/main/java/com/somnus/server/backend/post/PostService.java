package com.somnus.server.backend.post;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PostService {

    public String normalizeName(String articleName) {
        return Arrays.stream(articleName.toLowerCase()
                        .split(" "))
                .reduce((a, b) -> a.concat("-").concat(b)).get();
    }
}
