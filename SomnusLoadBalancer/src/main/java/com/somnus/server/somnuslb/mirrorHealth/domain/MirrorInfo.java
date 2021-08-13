package com.somnus.server.somnuslb.mirrorHealth.domain;

import com.somnus.server.somnuslb.mirrors.domain.Mirror;

public class MirrorInfo {
    private Mirror mirror;
    private MirrorState state;
    private double averageCPUUsage;
    private double[] measuredCPUUsages;
    private double averageMemoryUsage;
    private double[] measuredMemoryUsages;
    private double averageRequestResponseTimeInMillis;
    private int numFailedRequests;
    private int numConsecutiveSuccesses;

    public MirrorInfo(Mirror mirror) {
        this.mirror = mirror;
        this.state = MirrorState.NEW_MIRROR;
        this.averageCPUUsage = 0.0;
        this.measuredCPUUsages = new double[5]; // maybe add size to properties file
        this.averageMemoryUsage = 0.0;
        this.measuredMemoryUsages = new double[5]; // maybe add size to properties file
        this.averageRequestResponseTimeInMillis = 0.0;
        this.numFailedRequests = 0;
        this.numConsecutiveSuccesses = 0;
    }

    private static void calculateNewCPUAverage() {
        // TODO
    }

    private static void calculateNewMemoryAverage() {
        // TODO
    }

    public Mirror getMirror() {
        return mirror;
    }

    public MirrorState getState() {
        return state;
    }

    public double getAverageCPUUsage() {
        return averageCPUUsage;
    }

    public double getAverageMemoryUsage() {
        return averageMemoryUsage;
    }

    public double getAverageRequestResponseTimeInMillis() {
        return averageRequestResponseTimeInMillis;
    }

    public int getNumFailedRequests() {
        return numFailedRequests;
    }

    public int getNumConsecutiveSuccesses() {
        return numConsecutiveSuccesses;
    }
}
