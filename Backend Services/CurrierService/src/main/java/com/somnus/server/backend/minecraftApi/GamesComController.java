package com.somnus.server.backend.minecraftApi;

import com.somnus.server.backend.minecraftApi.dto.MinecraftInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-api")
public class GamesComController {

    @Autowired
    private GamesComService gamesComService;

    @GetMapping(value = "/get-minecraft-info")
    public MinecraftInfoDto getMinecraftInfo() {
        return gamesComService.getMinecraftInfo();
    }
}
