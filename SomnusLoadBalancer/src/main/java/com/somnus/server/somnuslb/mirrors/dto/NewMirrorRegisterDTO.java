package com.somnus.server.somnuslb.mirrors.dto;

import java.io.Serializable;

public class NewMirrorRegisterDTO implements Serializable {
    private String ipAddress;
    /*
    Consider also the type of Mirror that is being registered (maybe for later when using Microservices)
    and also the performance category. The latter will help the Load Balancer understand the capabilities
    of the machines it is dealing with.
    A token might also be necessary to register machines in a safe way.
     */

}
