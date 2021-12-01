package com.somnus.server.somnuslb.instances.domain;

import com.netflix.appinfo.InstanceInfo;

public class InstanceData {
    private InstanceInfo.InstanceStatus status;
    private String ipAddr;
    private CircularArray cpuUsage;
    private CircularArray memoryUsage;
    private CircularArray swapUsage;
    private int numFailedRequests;
    private int numSuccesses;
    private int numTotalRequests;

    public InstanceData(String ipAddr) {
        this.ipAddr = ipAddr;
        this.cpuUsage = new CircularArray();
        this.memoryUsage = new CircularArray();
        this.swapUsage = new CircularArray();
        this.numFailedRequests = 0;
        this.numSuccesses = 0;
    }

    public InstanceInfo.InstanceStatus getStatus() {
        return status;
    }

    public void setStatus(InstanceInfo.InstanceStatus status) {
        this.status = status;
    }

    public String getIpAddr() {
        return ipAddr;
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

    public int getNumFailedRequests() {
        return numFailedRequests;
    }

    public int getNumConsecutiveSuccesses() {
        return numSuccesses;
    }

    public int getNumTotalRequests() {
        return numTotalRequests;
    }

    public void incNumSuccesses(int amount) {
        numSuccesses += amount;
//        if (!(numSuccesses == SUCCESSFUL_THRESHOLD)) {
//            int total = numSuccesses + amount;
//            numSuccesses = (total > SUCCESSFUL_THRESHOLD) ? SUCCESSFUL_THRESHOLD : total;
//            if (numSuccesses == 1 && state == MirrorState.MIRROR_DOWN) {
//                state = MirrorState.MIRROR_RECOVERY;
//            } else if (numSuccesses == SUCCESSFUL_THRESHOLD) {
//                state = MirrorState.NORMAL_OPERATION;
//            }
//            numFailedHealthChecks -= (numFailedHealthChecks - amount < 0) ? 0 : amount;
//        }
    }

    public void incNumFailedRequests(int amount) {
        numFailedRequests += amount;
//        if (!(numFailedHealthChecks == MAX_FAILED_REQUESTS)) {
//            int total = numFailedHealthChecks + amount;
//            this.numFailedHealthChecks = (total > MAX_FAILED_REQUESTS) ? MAX_FAILED_REQUESTS : total;
//            if (this.numFailedHealthChecks == MAX_FAILED_REQUESTS) {
//                this.state = MirrorState.MIRROR_DOWN;
//            }
//        }
//        numSuccesses = 0;
    }

    public void incNumTotalRequests(int amount) {
        numTotalRequests += amount;
    }

}
