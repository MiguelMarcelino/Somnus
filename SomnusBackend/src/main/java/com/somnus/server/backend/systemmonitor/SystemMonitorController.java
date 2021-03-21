package com.somnus.server.backend.systemmonitor;

import com.somnus.server.backend.systemmonitor.dto.SystemInformationDto;
import com.somnus.server.backend.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/system-monitor-api")
public class SystemMonitorController {

    @Autowired
    private SystemMonitorService systemMonitorService;

    @GetMapping(value = "/get-system-usage")
    public SystemInformationDto getSystemUsage(Principal principal) {
        User user = (User) ((Authentication) principal).getPrincipal();

        return systemMonitorService.getSystemInfo();
    }
}
