package com.somnus.server.somnuslb.mirrors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mirrors")
public class MirrorRegisterController {

    @GetMapping("/register")
    public void registerMirror() {

    }
}
