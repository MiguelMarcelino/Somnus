package com.somnus.server.backend.systemmonitor;

import com.somnus.server.backend.systemmonitor.dto.SystemInformationDto;
import com.somnus.server.backend.systemmonitor.systemusage.SystemUsage;
import org.springframework.stereotype.Service;

@Service
public class SystemMonitorService {

    public SystemInformationDto getSystemInfo() {
        return SystemUsage.getSystemUsage();
    }
}
