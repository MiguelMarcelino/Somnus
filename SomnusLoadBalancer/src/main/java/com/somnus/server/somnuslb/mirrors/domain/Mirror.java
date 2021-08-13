package com.somnus.server.somnuslb.mirrors.domain;

import javax.persistence.*;

@Entity
public class Mirror {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "registration_timestamp_in_millis")
    private String registrationTimestampInMillis;

    public Mirror() { }

    public Mirror(String ipAddress, String registrationTimestampInMillis) {
        this.ipAddress = ipAddress;
        this.registrationTimestampInMillis = registrationTimestampInMillis;
    }

    public Integer getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getRegistrationTimestampInMillis() {
        return registrationTimestampInMillis;
    }
}
