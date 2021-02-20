package com.somnus.server.backend.minecraftApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

public class MinecraftInfoDto implements Serializable {

    private boolean online;
    private String version;
    private String hostname;
    private String software;
    private Integer numOnlinePlayers;
    @JsonProperty(value = "players")
    private List<MinecraftPlayerInfo> players;

    public MinecraftInfoDto() {}

    public MinecraftInfoDto(boolean online, String version,
                            String hostname, String software,
                            Integer numOnlinePlayers,
                            List<MinecraftPlayerInfo> players) {
        this.online = online;
        this.version = version;
        this.hostname = hostname;
        this.software = software;
        this.numOnlinePlayers = numOnlinePlayers;
        this.players = players;
    }

    public boolean getIsOnline() {
        return this.online;
    }

    public String getVersion() {
        return version;
    }

    public String getHostname() {
        return hostname;
    }

    public String getSoftware() {
        return software;
    }

    public Integer getNumOnlinePlayers() {
        return numOnlinePlayers;
    }

    public List<MinecraftPlayerInfo> getPlayers() {
        return players;
    }
}
