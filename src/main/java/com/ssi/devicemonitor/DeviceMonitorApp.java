package com.ssi.devicemonitor;

import com.ssi.devicemonitor.controller.DeviceMonitorController;
import com.ssi.devicemonitor.entity.JavaObjectDeviceSerializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DeviceMonitorApp extends Application {
    private DeviceMonitorController controller;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(DeviceMonitorApp.class.getResource("device_monitor.fxml"));
        controller = new DeviceMonitorController();
        loader.setController(controller);
        controller.setSerializeEngine(new JavaObjectDeviceSerializer("data.bin"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Device Monitor");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
       controller.SaveDevicesToFile();
       super.stop();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
