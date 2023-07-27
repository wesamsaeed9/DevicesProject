package com.ssi.devicemonitor.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SoftwareDevice extends GeneralDevice {

    private LocalDateTime installationDateTime;

    public SoftwareDevice(String name, String manufacturer, String version, LocalDateTime installationDateTime) {

        super(name, manufacturer, version, DeviceType.Software);
        this.installationDateTime = installationDateTime;
    }

    public LocalDateTime getInstallationDateTime() {
        return installationDateTime;
    }

    public void setInstallationDateTime(LocalDateTime installationDateTime) {
        this.installationDateTime = installationDateTime;
    }

}
