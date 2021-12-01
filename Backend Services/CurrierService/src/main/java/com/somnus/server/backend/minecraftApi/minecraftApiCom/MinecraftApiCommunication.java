package com.somnus.server.backend.minecraftApi.minecraftApiCom;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.somnus.server.backend.minecraftApi.dto.MinecraftInfoDto;
import com.somnus.server.backend.minecraftApi.dto.MinecraftPlayerInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MinecraftApiCommunication {

    @Value("${minecraft.api.link}")
    private String minecraftApiUrl;
//
//    private static final String minecraftApiUrl = "https://api.mcsrvstat.us/2/somnus.ddns.net";

    public MinecraftInfoDto getMinecraftServerInfo(){
        WebClient webClient = WebClient.create(minecraftApiUrl);
//        this.webClient.get()
//                 .uri(uriBuilder -> uriBuilder)
//                 .header("Authorization", auth_token)
//                 .build();

        String response = webClient.get()
                .retrieve().bodyToMono(String.class).block();

        JSONObject minecraftInfo = new JSONObject(response);

        boolean online = minecraftInfo.getBoolean("online");
        if(!online) {
            return new MinecraftInfoDto(false, "No version available", "somnus",
                    "No software available", 0, new ArrayList<>());
        }

        String version = minecraftInfo.getString("version");
        String hostname = minecraftInfo.getString("hostname");
        String software = minecraftInfo.getString("software");
        Integer numOnlinePlayers = minecraftInfo.getJSONObject("players").getInt("online");

        List<String> playerNames = new ArrayList<>();
        if(numOnlinePlayers > 0) {
             Iterator<Object> iterator = minecraftInfo.getJSONObject("players").getJSONArray("list").iterator();
             while (iterator.hasNext()) {
                 Object playerName = iterator.next();
                 if(playerName instanceof String){
                     playerNames.add((String) playerName);
                 }
             }
        }

        List<MinecraftPlayerInfo> players = new ArrayList<>();
        for (String playerName : playerNames) {
            String uuid = minecraftInfo.getJSONObject("players").getJSONObject("uuid").getString(playerName);
            players.add(new MinecraftPlayerInfo(playerName, uuid));
        }

        return new MinecraftInfoDto(online, version, hostname, software, numOnlinePlayers, players);
    }
}
