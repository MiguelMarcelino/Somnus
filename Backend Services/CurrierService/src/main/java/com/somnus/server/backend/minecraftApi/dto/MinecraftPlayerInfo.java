package com.somnus.server.backend.minecraftApi.dto;

import java.io.Serializable;

public class MinecraftPlayerInfo implements Serializable {

    private String name;
    private String uuid;

    public MinecraftPlayerInfo(){ }

    public MinecraftPlayerInfo(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }
}
