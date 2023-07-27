package com.ssi.devicemonitor.entity;

public class GeneralDevice extends Device {

    private String manufacturer;
    private String version;
    private DeviceType deviceType;

    public GeneralDevice(String name) {
        super(name);
        deviceType = DeviceType.General;
    }

    public GeneralDevice(String name, String manufacturer, String version, DeviceType deviceType) {
        super(name);
        this.manufacturer = manufacturer;
        this.version = version;
        this.deviceType = deviceType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
