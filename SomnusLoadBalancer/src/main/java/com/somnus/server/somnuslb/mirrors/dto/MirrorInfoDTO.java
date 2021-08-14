package com.somnus.server.somnuslb.mirrors.dto;

import java.io.Serializable;

public class MirrorInfoDTO implements Serializable {
    private int measuredCPUUsage; // in percentage
    private int measuredMemoryUsage; // in megabytes
    private int measuredSwapUsage; // in megabytes

    public MirrorInfoDTO() { }

    public int getMeasuredCPUUsage() {
        return measuredCPUUsage;
    }

    public int getMeasuredMemoryUsage() {
        return measuredMemoryUsage;
    }

    public int getMeasuredSwapUsage() {
        return measuredSwapUsage;
    }
}
