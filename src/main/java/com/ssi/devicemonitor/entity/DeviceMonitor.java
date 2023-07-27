package com.ssi.devicemonitor.entity;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceMonitor {

    //private final Object lock = new Object();
    private List<Device> devices;
    private Timer statusUpdateTimer;

    public DeviceMonitor(List<Device> devices) {
        if(devices == null) {
            this.devices = new ArrayList<>();
        } else {
            this.devices = devices;
        }

        // Start the timer to simulate status updates every few seconds
        statusUpdateTimer = new Timer();
        statusUpdateTimer.schedule(new StatusUpdateTask(), 0, 5000); // Update every 5 seconds
    }

    public List<Device> getDevices() {

        return devices;
    }
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void addDevice(Device device) {
        //synchronized (lock) {
            this.devices.add(device);
        //}
    }

    public void removeDevice(Device device) {
        //synchronized (lock) {
            //if (devices != null && devices.contains(device)) {
                //devices.remove(device);
            //}
        //}
    }

    private class StatusUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            //synchronized (lock) {
                for (Device device : devices) {
                    // Simulate random status updates
                    boolean isOnline = random.nextBoolean();
                    device.setStatus(isOnline ? "Online" : "Offline");
                }
            //}
        }
    }
}
