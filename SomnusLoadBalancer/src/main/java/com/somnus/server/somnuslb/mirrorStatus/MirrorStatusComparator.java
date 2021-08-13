package com.somnus.server.somnuslb.mirrorStatus;

import com.somnus.server.somnuslb.mirrorStatus.domain.MirrorInfo;

import java.util.Comparator;

public class MirrorStatusComparator implements Comparator<MirrorInfo> {

    public MirrorStatusComparator() {
    }

    @Override
    public int compare(MirrorInfo o1, MirrorInfo o2) {
        int result = 0;
        result += normalizeValue(o1.getMirror().getAverageRequestResponseTime(), o2.getMirror().getAverageRequestResponseTime());
        result += normalizeValue(o1.getMirror().getNumFailedRequests(), o2.getMirror().getNumFailedRequests());
        result += normalizeValue(o1.getAverageCPUUsage(), o2.getAverageCPUUsage());
        result += normalizeValue(o1.getAverageMemoryUsage(), o2.getAverageMemoryUsage());

        // changed order on purpose for normalize
        result += normalizeValue(o2.getNumConsecutiveSuccesses(), o1.getNumConsecutiveSuccesses());

        return result;
    }

    // TODO: Same method structure, find possible way to reduce code

    public int normalizeValue(int n1, int n2) {
        if (n1 == 0 && n2 == 0)
            return 0;
        else if (n1 == 0 && n2 != 0)
            return -1;
        else if (n1 != 0 && n2 == 0)
            return 1;

        return n1 < n2 ? -n1 / n2 : n2 / n1;
    }

    public double normalizeValue(double n1, double n2) {
        if (n1 == 0 && n2 == 0)
            return 0;
        else if (n1 == 0 && n2 != 0)
            return -1;
        else if(n1 != 0 && n2 == 0)
            return 1;

        return n1 < n2 ? -n1 / n2 : n2 / n1;
    }

}
