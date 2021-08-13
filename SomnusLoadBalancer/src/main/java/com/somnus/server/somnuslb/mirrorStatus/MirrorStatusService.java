package com.somnus.server.somnuslb.mirrorStatus;

import com.somnus.server.somnuslb.mirrorStatus.domain.MirrorInfo;
import com.somnus.server.somnuslb.mirrors.domain.Mirror;
import com.somnus.server.somnuslb.observer.Observer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MirrorStatusService implements Observer {

    private List<MirrorInfo> mirrors;

    private MirrorStatusComparator mirrorStatusComparator;

    public MirrorStatusService() {
        mirrors = new ArrayList<>();
        mirrorStatusComparator = new MirrorStatusComparator();
    }

    /**
     * Checks the health of all Mirrors and gathers information
     * about their current status
     */
    public void checkMirrorHealth() {
        // TODO
    }

    public List<MirrorInfo> getMirrors() {
        return this.mirrors;
    }

    public void sortMirrorList() {
        Collections.sort(this.mirrors, (o1, o2) -> mirrorStatusComparator.compare(o1, o2));
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
