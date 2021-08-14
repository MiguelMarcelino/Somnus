package com.somnus.server.somnuslb.mirrors;

import com.somnus.server.somnuslb.mirrors.dto.NewMirrorRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mirrors")
public class MirrorRegisterController {

    @Autowired
    private MirrorRegisterService mirrorService;

    @GetMapping("/register")
    public void registerMirror(NewMirrorRegisterDTO mirrorRegisterDTO) {
        // TODO: Security checks (e.g with token)

        this.mirrorService.addMirror(mirrorRegisterDTO);
    }
}
