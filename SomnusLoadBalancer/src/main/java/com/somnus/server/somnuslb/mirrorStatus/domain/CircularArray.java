package com.somnus.server.somnuslb.mirrorStatus.domain;

import java.util.Arrays;

public class CircularArray {

    // Setup values to calculate averages (could go to properties file in the future)
    private static final int numMeasurements = 5;
    private static double[] weights = {0.4, 0.25, 0.15, 0.1, 0.1};
    private int[] measurements;
    private static int currPos;
    private int average;

    public CircularArray() {
        measurements = new int[numMeasurements];
        Arrays.fill(measurements, 0);
        currPos = 0;
        average = 0;
    }

    public void addNewValue(int value) {
        measurements[currPos] = value;
        currPos = (currPos + 1) % numMeasurements;
    }

    public double getAverage() {
        int pos = currPos == 0 ? numMeasurements - 1 : currPos - 1;
        double average = 0.0;
        for(int i = 0; i < numMeasurements; i++) {
            average = measurements[pos] * weights[i];
            pos += (pos + 1) % numMeasurements;
        }
        return average;
    }

}
