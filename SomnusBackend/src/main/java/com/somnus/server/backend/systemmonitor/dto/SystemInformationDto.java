package com.somnus.server.backend.systemmonitor.dto;

import java.io.Serializable;

public class SystemInformationDto implements Serializable {

    public int numCpuThreads;
    public double totalMemory;
    public double cpuUsage;
    public double memoryUsage;
    public double swapSize;
    public double swapUsage;

    public SystemInformationDto(){}

    public SystemInformationDto(int numCpuThreads, double totalMemory, double cpuUsage, double memoryUsage, double swapSize, double swapUsage) {
        this.numCpuThreads = numCpuThreads;
        this.totalMemory = totalMemory;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.swapSize = swapSize;
        this.swapUsage = swapUsage;
    }
}
