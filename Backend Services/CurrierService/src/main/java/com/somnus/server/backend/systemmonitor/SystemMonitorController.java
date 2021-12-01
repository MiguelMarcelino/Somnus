package com.somnus.server.backend.systemmonitor;

import com.somnus.server.backend.systemmonitor.dto.SystemInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system-monitor-api")
public class SystemMonitorController {

    @Autowired
    private SystemMonitorService systemMonitorService;

    @GetMapping(value = "/get-system-usage")
    public SystemInformationDto getSystemUsage() {
        return systemMonitorService.getSystemInfo();
    }
}
