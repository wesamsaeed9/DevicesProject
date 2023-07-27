package com.ssi.devicemonitor.entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JavaObjectDeviceSerializer implements SerializeEngine {

    private final String fileName;

    public JavaObjectDeviceSerializer(String fileName)
    {
       this.fileName = fileName;
    }

    @Override
    public void serializeData(List<Device> devices) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(devices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Device> DeserializeData() {
        List<Device> devices = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            devices = (List<Device>) ois.readObject();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return devices;
    }

}
