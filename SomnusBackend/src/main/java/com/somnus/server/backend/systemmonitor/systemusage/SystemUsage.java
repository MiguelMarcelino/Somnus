package com.somnus.server.backend.systemmonitor.systemusage;

import com.somnus.server.backend.systemmonitor.dto.SystemInformationDto;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.List;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
    import com.profesorfalken.jsensors.model.sensors.Temperature;

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

        // Uses the JSensors library to get information on temperature
        Components components = JSensors.get.components();
        List<Cpu> cpus = components.cpus;

        double temperature = 0;

        // This library works by returning a list of the CPUs in the Computer, each of which contains
        // a list of sensors each of which contains temperatures
        // Thus, we need to get the average per sensor, to get the average per cpu, to get the system's
        // average temperature
        if (cpus != null) {
            int i=0;
            for (final Cpu cpu : cpus) {
                if (cpu.sensors != null) {
                    //Print temperatures
                    List<Temperature> temps = cpu.sensors.temperatures;
                    double cpuTemp = 0;
                    for (final Temperature temp : temps) {
                        cpuTemp += temp.value;
                    }
                    temperature += cpuTemp/temps.size();
                }
            }
            temperature = temperature / cpus.size();
        }


        SystemInformationDto systemInformationDto = new SystemInformationDto(numCpuThreads, totalMemory, cpuUsage,
                memoryUsage, swapSize, swapUsage, temperature);
        return systemInformationDto;
    }
}
