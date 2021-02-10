package com.somnus.server.backend.minecraftApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class MinecraftInfoDto implements Serializable {

    private boolean online;
    private String version;
    private String hostname;
    private String software;
    private Integer numOnlinePlayers;
    private List<String> playerNames;

    public MinecraftInfoDto() {}

    public MinecraftInfoDto(boolean online, String version,
                            String hostname, String software,
                            Integer numOnlinePlayers,
                            List<String> playerNames) {
        this.online = online;
        this.version = version;
        this.hostname = hostname;
        this.software = software;
        this.numOnlinePlayers = numOnlinePlayers;
        this.playerNames = playerNames;
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

    public List<String> getPlayerNames() {
        return playerNames;
    }
}
