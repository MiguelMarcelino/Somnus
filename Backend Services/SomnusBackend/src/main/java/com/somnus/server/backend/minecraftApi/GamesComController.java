package com.somnus.server.backend.minecraftApi;

import com.somnus.server.backend.minecraftApi.dto.MinecraftInfoDto;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/game-api")
public class GamesComController {

    @Autowired
    private GamesComService gamesComService;

    @GetMapping(value = "/get-minecraft-info")
    public MinecraftInfoDto getMinecraftInfo(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return gamesComService.getMinecraftInfo();
    }
}
