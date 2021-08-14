package com.somnus.server.somnuslb.mirrors.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Mirror {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "registration_timestamp_in_millis")
    private long registrationTimestampInMillis;

    @Column(name = "num_failed_requests")
    private int numFailedRequests;

    @Column(name = "average_request_response_time")
    private long averageRequestResponseTime;

    @Column(name = "requests_sent")
    private int numSentRequests;

    @Column(name = "request_response_time_sum")
    private long requestResponseTimeSum;

    // For logging purposes
    @ElementCollection
    private List<Long> responseTimeHistory;

    public Mirror() {
    }

    public Mirror(String ipAddress) {
        this.ipAddress = ipAddress;
        this.registrationTimestampInMillis = System.currentTimeMillis();
        this.numFailedRequests = 0;
        this.averageRequestResponseTime = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public long getRegistrationTimestampInMillis() {
        return registrationTimestampInMillis;
    }

    public int getNumFailedRequests() {
        return numFailedRequests;
    }

    public long getAverageRequestResponseTime() {
        return averageRequestResponseTime;
    }

    public void incFailedRequests(int amount) {
        this.numFailedRequests += amount;
    }

    public void incNumSentRequests(int amount) {
        this.numSentRequests += amount;
    }

    public void addResponseTime(long responseTime) {
        responseTimeHistory.add(responseTime);
        requestResponseTimeSum += responseTime;
    }

    public void updateAverageResponseTime() {
        averageRequestResponseTime = requestResponseTimeSum/ numSentRequests;
    }
}
