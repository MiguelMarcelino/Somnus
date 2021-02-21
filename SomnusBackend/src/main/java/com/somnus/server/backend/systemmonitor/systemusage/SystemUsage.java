package com.somnus.server.backend.systemmonitor.systemusage;

import com.somnus.server.backend.systemmonitor.dto.SystemInformationDto;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

public class SystemUsage {

    public static SystemInformationDto getSystemUsage() {
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        int numCpuThreads = bean.getAvailableProcessors();
        double totalMemory = bean.getTotalPhysicalMemorySize();
//        double cpuUsage = bean.getSystemLoadAverage() / bean.getAvailableProcessors();
        double cpuUsage = bean.getSystemCpuLoad();
        double memoryUsage = bean.getTotalPhysicalMemorySize() - bean.getFreePhysicalMemorySize();
        double swapSize = bean.getTotalSwapSpaceSize();
        double swapUsage = bean.getTotalSwapSpaceSize() - bean.getFreeSwapSpaceSize();

        SystemInformationDto systemInformationDto = new SystemInformationDto(numCpuThreads, totalMemory, cpuUsage,
                memoryUsage, swapSize, swapUsage);
        return systemInformationDto;
    }
}
