package com.somnus.server.somnuslb.mirrorStatus;

import com.somnus.server.somnuslb.mirrorStatus.domain.MirrorInfo;
import com.somnus.server.somnuslb.mirrorStatus.domain.MirrorState;

import java.util.Comparator;

public class MirrorStatusComparator implements Comparator<MirrorInfo> {

    public MirrorStatusComparator() {
    }

    @Override
    public int compare(MirrorInfo o1, MirrorInfo o2) {
        if (o1.getState() == MirrorState.NORMAL_OPERATION && o2.getState() != MirrorState.NORMAL_OPERATION) {
            return -1;
        } else if (o1.getState() != MirrorState.NORMAL_OPERATION && o2.getState() == MirrorState.NORMAL_OPERATION) {
            return 1;
        }

        // TODO: check if it maximizes successes and minimizes unwanted actions
        int result = 0;
        result += Long.compare(o1.getMirror().getAverageRequestResponseTime(), o2.getMirror().getAverageRequestResponseTime());
        result += Integer.compare(o1.getMirror().getNumFailedRequests(), o2.getMirror().getNumFailedRequests());
        result += Double.compare(o1.getAverageCPUUsage(), o2.getAverageCPUUsage());
        result += Double.compare(o1.getAverageMemoryUsage(), o2.getAverageMemoryUsage());
        result += Integer.compare(o1.getNumConsecutiveSuccesses(), o2.getNumConsecutiveSuccesses());

//        if(result > 0)
//            return 1;
//        else if(result < 0)
//            return -1;
//
//        return 0;
        return result > 0 ? 1 : (result < 0 ? -1 : 0);
    }

}
