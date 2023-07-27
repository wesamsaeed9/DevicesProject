package com.ssi.devicemonitor.entity;

import java.io.Serializable;

public abstract class Device implements Serializable {

    private static final long serialVersionUID = 1;
    private String name;
    private String status;

    public Device(String name) {
        this.name = name;
        this.status = "Offline"; // Set initial status to Offline
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString()
    {
        return name;
    }
}
