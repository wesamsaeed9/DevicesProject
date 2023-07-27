package com.ssi.devicemonitor.controller;

import com.ssi.devicemonitor.entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class DeviceMonitorController implements Initializable {
    @FXML
    private Button savePropertiesButton;
    @FXML
    private VBox propertiesContainer;
    @FXML
    private TextField manufacturerTextBox;
    @FXML
    private TextField deviceTypeTextBox;
    @FXML
    private TextField locationTextBox;
    @FXML
    private TextField versionTextBox;
    @FXML
    private TextField macAddressTextBox;
    @FXML
    private TextField installationDateTimeTextBox;
    @FXML
    private ListView<Device> deviceListView;

    @FXML
    private TextField deviceNameTextField;

    @FXML
    private Button addDeviceButton;

    private DeviceMonitor deviceMonitor;

    private SerializeEngine serializeEngine;
    @FXML
    private ComboBox<DeviceType> deviceTypeComboBox;
    private Timer dataUpdateTimer;

    public void setSerializeEngine(SerializeEngine serializeEngine) {
        this.serializeEngine = serializeEngine;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Device> devices = getDevicesFromFile();

        deviceMonitor = new DeviceMonitor(devices);

        deviceMonitor.addDevice(new GeneralDevice("Device 1"));
        deviceMonitor.addDevice(new GeneralDevice("Device 2"));
        deviceMonitor.addDevice(new GeneralDevice("Device 3"));

        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceListView.setCellFactory(deviceListView ->
                {
                    ListCell<Device> cell = new ListCell<Device>() {
                        @Override
                        protected void updateItem(Device device, boolean empty) {
                            super.updateItem(device, empty);

                            if (device == null || empty) {
                                setText(null);
                                setGraphic(null);
                                setStyle(""); // Reset the cell style
                            } else {
                                setText(device.getName() + " - " + device.getStatus());

                                // Set the cell style based on the device status
                                if (device.getStatus().equals("Online")) {
                                    setStyle("-fx-text-fill: green;");
                                } else if (device.getStatus().equals("Offline")) {
                                    setStyle("-fx-text-fill: red;");
                                } else {
                                    setStyle(""); // Reset the cell style
                                }
                            }
                        }
                    };

                    AddContextMenusToCell(cell);
                    return cell;
                });


        deviceTypeComboBox.getItems().addAll(DeviceType.values());
        //deviceTypeComboBox.setValue(null);
        deviceTypeComboBox.setPromptText("Pick Device type");

        propertiesContainer.setVisible(false);

        dataUpdateTimer = new Timer();
        dataUpdateTimer.schedule(new DataUpdateTask(), 0, 500);

    }

    private List<Device> getDevicesFromFile() {
        return serializeEngine.DeserializeData();
    }

    public void SaveDevicesToFile() {
        serializeEngine.serializeData(deviceMonitor.getDevices());
    }

    private void AddContextMenusToCell(ListCell<Device> cell)
    {
        // Add context menu to ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove");

        MenuItem showItem = new MenuItem("Show Properties");

        MenuItem hideItem = new MenuItem("Hide properties");

        removeItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                hideDeviceProperties();
                deviceMonitor.removeDevice(selectedDevice);
                //deviceListView.getItems().remove(selectedDevice);
            }
        });

        //showItem.setOnAction(e -> showDeviceProperties(cell.getItem()));
        //hideItem.setOnAction(e -> hideDeviceProperties(cell.getItem()));

        contextMenu.getItems().addAll(removeItem/*, showItem, hideItem*/);
        cell.setContextMenu(contextMenu);
    }

    private void hideDeviceProperties() {
        installationDateTimeTextBox.setText("");
        locationTextBox.setText("");
        macAddressTextBox.setText("");
        versionTextBox.setText("");
        deviceTypeTextBox.setText("");
        manufacturerTextBox.setText("");
        propertiesContainer.setVisible(false);
    }

    private void showDeviceProperties(Device device) {
        Class classType = device.getClass();
        if(classType == SoftwareDevice.class) {
            SoftwareDevice softwareDevice = (SoftwareDevice) device;
            installationDateTimeTextBox.setText(String.valueOf(softwareDevice.getInstallationDateTime()));
        } else if(classType == HardwareDevice.class) {
            HardwareDevice hardwareDevice = (HardwareDevice) device;
            locationTextBox.setText(hardwareDevice.getLocation());
            macAddressTextBox.setText(hardwareDevice.getMacAddress());
        }
        if(device instanceof GeneralDevice) {
            GeneralDevice GeneralDevice = (GeneralDevice) device;
            versionTextBox.setText(GeneralDevice.getVersion());
            deviceTypeTextBox.setText(GeneralDevice.getDeviceType().toString());
            manufacturerTextBox.setText(GeneralDevice.getManufacturer());
        }
        propertiesContainer.setVisible(true);
    }

    //@FXML
    /*private void OnSavePropertiesButtonClicked(ActionEvent actionEvent) {
        Device device = deviceListView.getSelectionModel().getSelectedItem();
        if(device != null) {
            Class classType = device.getClass();
            if(classType == SoftwareDevice.class) {
                SoftwareDevice softwareDevice = (SoftwareDevice) device;
                //softwareDevice.setInstallationDateTime(installationDateTimeTextBox.getText());
            } else if(classType == HardwareDevice.class) {
                HardwareDevice hardwareDevice = (HardwareDevice) device;
                hardwareDevice.setLocation(locationTextBox.getText());
                hardwareDevice.setMacAddress(macAddressTextBox.getText());
            }
            if(device instanceof GeneralDevice) {
                GeneralDevice GeneralDevice = (GeneralDevice) device;
                GeneralDevice.setVersion(versionTextBox.getText());
                GeneralDevice.setManufacturer(manufacturerTextBox.getText());
            }
        }
    }*/

    @FXML
    private void OnDeviceListViewSelected(MouseEvent mouseEvent) {
        Device device = deviceListView.getSelectionModel().getSelectedItem();
        if(device != null) {
            showDeviceProperties(device);
        } else {
            hideDeviceProperties();
        }
    }

    @FXML
    private void OnClosePropertiesContainerButtonClicked(ActionEvent mouseEvent)
    {
        hideDeviceProperties();
    }

    private class DataUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            Platform.runLater(() -> refreshListView());
        }
    }

    @FXML
    private void addDevice() {
        String deviceName = deviceNameTextField.getText();
        DeviceType selectedDeviceType = deviceTypeComboBox.getValue();
        if(selectedDeviceType != null) {
            Device newDevice = null;
            switch(selectedDeviceType)
            {
                case General:
                    newDevice = new GeneralDevice(deviceName);
                    break;
                case Software:
                    newDevice = new SoftwareDevice(deviceName, "", "", null);
                    break;
                case Hardware:
                    newDevice = new HardwareDevice(deviceName, "", "", "", "");
                    break;
            }
            deviceMonitor.addDevice(newDevice);
            //deviceListView.getItems().add(newDevice);
            deviceNameTextField.clear();
            //deviceTypeComboBox.setValue(null);
            //deviceTypeComboBox.setPromptText(DeviceType_ComboBox_Prompt);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing device type");
            alert.setHeaderText("Please select a device type");
            alert.showAndWait();
        }
    }

    public void refreshListView() {
        deviceListView.refresh();
    }

    /*private class DeviceListCell extends ListCell<Device> {
        @Override
        protected void updateItem(Device device, boolean empty) {
            super.updateItem(device, empty);

            if (device == null || empty) {
                setText(null);
                setGraphic(null);
                setStyle(""); // Reset the cell style
            } else {
                setText(device.getName() + " - " + device.getStatus());

                // Set the cell style based on the device status
                if (device.getStatus().equals("Online")) {
                    setStyle("-fx-text-fill: green;");
                } else if (device.getStatus().equals("Offline")) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset the cell style
                }
            }
        }
    }*/
}
