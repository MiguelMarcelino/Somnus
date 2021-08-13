package com.somnus.server.somnuslb.mirrorStatus.domain;

import com.somnus.server.somnuslb.mirrors.domain.Mirror;

public class MirrorInfo {
    private Mirror mirror;
    private MirrorState state;
    private CircularArray cpuUsage;
    private CircularArray memoryUsage;
    private double averageRequestResponseTimeInMillis;
    private int numFailedHealthChecks; // should it be numConsecutiveFailedHealthChecks?
    private int numConsecutiveSuccesses;
    private int numTotalHealthChecks;

    public MirrorInfo(Mirror mirror) {
        this.mirror = mirror;
        this.state = MirrorState.NEW_MIRROR;
        this.cpuUsage = new CircularArray();
        this.memoryUsage = new CircularArray();
        this.averageRequestResponseTimeInMillis = 0.0;
        this.numFailedHealthChecks = 0;
        this.numConsecutiveSuccesses = 0;

    }

    public Mirror getMirror() {
        return mirror;
    }

    public MirrorState getState() {
        return state;
    }

    public double getAverageCPUUsage() {
        return cpuUsage.getAverage();
    }

    public double getAverageMemoryUsage() {
        return memoryUsage.getAverage();
    }

    public double getAverageRequestResponseTimeInMillis() {
        return averageRequestResponseTimeInMillis;
    }

    public int getNumFailedHealthChecks() {
        return numFailedHealthChecks;
    }

    public int getNumConsecutiveSuccesses() {
        return numConsecutiveSuccesses;
    }

    public int getNumTotalHealthChecks() {
        return numTotalHealthChecks;
    }
}
