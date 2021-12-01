package com.somnus.server.backend.minecraftApi;

import com.somnus.server.backend.minecraftApi.dto.MinecraftInfoDto;
import com.somnus.server.backend.minecraftApi.minecraftApiCom.MinecraftApiCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamesComService {

    @Autowired
    private MinecraftApiCommunication minecraftApiCommunication;

    public MinecraftInfoDto getMinecraftInfo() {
        return minecraftApiCommunication.getMinecraftServerInfo();
    }
}
