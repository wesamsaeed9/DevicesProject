package com.ssi.devicemonitor.entity;

public class HardwareDevice extends GeneralDevice {

    private String macAddress;

    private String location;

    public HardwareDevice(String name, String manufacturer, String version, String location, String macAddress) {

        super(name, manufacturer, version, DeviceType.Hardware);
        this.macAddress = macAddress;
        this.location = location;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
