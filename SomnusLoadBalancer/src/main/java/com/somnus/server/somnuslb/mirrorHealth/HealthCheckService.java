package com.somnus.server.somnuslb.mirrorHealth;

import com.somnus.server.somnuslb.mirrorHealth.domain.MirrorInfo;
import com.somnus.server.somnuslb.mirrors.domain.Mirror;
import com.somnus.server.somnuslb.observer.Observer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheckService implements Observer {

    List<MirrorInfo> mirrors;

    public HealthCheckService() {
        mirrors = new ArrayList<>();
    }

    /**
     * Checks the health of all Mirrors and gathers information
     * about their current status
     */
    public void checkMirrorHealth() {
        // TODO
    }

    @Override
    public void add(Object o) {
        if (o instanceof Mirror)
            mirrors.add(new MirrorInfo((Mirror) o));
    }

    @Override
    public void remove(Object o) {
        if (o instanceof Mirror)
            mirrors.removeIf(x -> x.getMirror().getId().equals(((Mirror) o).getId()));
    }
}
