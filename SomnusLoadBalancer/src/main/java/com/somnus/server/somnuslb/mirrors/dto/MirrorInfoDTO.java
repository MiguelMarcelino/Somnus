package com.somnus.server.somnuslb.mirrors.dto;

import java.io.Serializable;

public class MirrorInfoDTO implements Serializable {
    private double measuredCPUUsage;
    private double measuredMemoryUsage;
    private double measuredSwapUsage;

    public MirrorInfoDTO() { }

    public double getMeasuredCPUUsage() {
        return measuredCPUUsage;
    }

    public double getMeasuredMemoryUsage() {
        return measuredMemoryUsage;
    }

    public double getMeasuredSwapUsage() {
        return measuredSwapUsage;
    }
}
