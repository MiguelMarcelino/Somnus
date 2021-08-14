package com.somnus.server.somnuslb.mirrorStatus.domain;

import com.somnus.server.somnuslb.mirrors.domain.Mirror;

public class MirrorInfo {
    private Mirror mirror;
    private MirrorState state;
    private CircularArray cpuUsage;
    private CircularArray memoryUsage;
    private CircularArray swapUsage;
    private int numFailedHealthChecks; // should it be numConsecutiveFailedHealthChecks?
    private int numConsecutiveSuccesses;
    private int numTotalHealthChecks;

    public static final int MAX_FAILED_REQUESTS = 5;
    public static final int SUCCESSFUL_THRESHOLD = 5;

    public MirrorInfo(Mirror mirror) {
        this.mirror = mirror;
        this.state = MirrorState.NEW_MIRROR;
        this.cpuUsage = new CircularArray();
        this.memoryUsage = new CircularArray();
        this.swapUsage = new CircularArray();
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

    public void addCPUUsageMetric(int cpuUsage) {
        this.cpuUsage.addNewValue(cpuUsage);
    }

    public double getAverageMemoryUsage() {
        return memoryUsage.getAverage();
    }

    public void addMemoryUsageMetric(int memoryUsage) {
        this.cpuUsage.addNewValue(memoryUsage);
    }

    public double getAverageSwapUsage() {
        return swapUsage.getAverage();
    }

    public void addSwapUsageMetric(int value) {
        swapUsage.addNewValue(value);
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

    public void incNumConsecutiveSuccesses(int amount) {
        if (!(numConsecutiveSuccesses == SUCCESSFUL_THRESHOLD)) {
            int total = numConsecutiveSuccesses + amount;
            numConsecutiveSuccesses = (total > SUCCESSFUL_THRESHOLD) ? SUCCESSFUL_THRESHOLD : total;
            if (numConsecutiveSuccesses == 1 && state == MirrorState.MIRROR_DOWN) {
                state = MirrorState.MIRROR_RECOVERY;
            } else if (numConsecutiveSuccesses == SUCCESSFUL_THRESHOLD) {
                state = MirrorState.NORMAL_OPERATION;
            }
            numFailedHealthChecks -= (numFailedHealthChecks - amount < 0) ? 0 : amount;
        }
    }

    public void incNumFailedHealthChecks(int amount) {
        if (!(numFailedHealthChecks == MAX_FAILED_REQUESTS)) {
            int total = numFailedHealthChecks + amount;
            this.numFailedHealthChecks = (total > MAX_FAILED_REQUESTS) ? MAX_FAILED_REQUESTS : total;
            if (this.numFailedHealthChecks == MAX_FAILED_REQUESTS) {
                this.state = MirrorState.MIRROR_DOWN;
            }
        }
        numConsecutiveSuccesses = 0;
    }

    public void incNumTotalHealthChecks(int amount) {
        numTotalHealthChecks += amount;
    }
}
