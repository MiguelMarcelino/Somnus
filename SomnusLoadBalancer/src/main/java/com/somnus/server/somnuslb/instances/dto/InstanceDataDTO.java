package com.somnus.server.somnuslb.instances.dto;

import java.io.Serializable;

public class InstanceDataDTO implements Serializable {
    private int measuredCPUUsage; // in percentage
    private int measuredMemoryUsage; // in megabytes
    private int measuredSwapUsage; // in megabytes

    public InstanceDataDTO() { }

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
