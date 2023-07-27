package com.ssi.devicemonitor.entity;

import java.util.List;

public interface SerializeEngine {

    void serializeData(List<Device> devices);
    List<Device> DeserializeData();
}
