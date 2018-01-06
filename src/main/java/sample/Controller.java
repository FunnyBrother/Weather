package sample;

import classes.IWeatherDataService;
import classes.Location;
import classes.WeatherData;
import classes.WeatherDataServiceFactory;
import exceptions.WeatherDataServiceException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import usbdrive.USBClass;
import usbdrive.USBDeviceDetectorManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Application {
    public static Stage stage;
    public IWeatherDataService dataService;
    public WeatherData data;
    public USBClass usbClass;
    public USBDeviceDetectorManager driveDetector;

    public Label Temperature;
    public Label Humidity;
    public Label Pressure;
    public Label Wind;
    public Label usbflash;
    public Button SaveButton;


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
            primaryStage.setTitle("Weather Information");
            primaryStage.setScene(new Scene(root, 600, 350));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initialize() {
        dataService = WeatherDataServiceFactory
                .getWeatherDataService(WeatherDataServiceFactory.service.OPEN_WEATHER_MAP);
        driveDetector = new USBDeviceDetectorManager();

        try
        {
            data = dataService.getWeatherData(new Location("Kiev", "UKRAINE"));

            // Set labels
            Temperature.setText(data.getTemperature().toString());
            Humidity.setText(data.getHumidity().toString());
            Pressure.setText(data.getPressure().toString());
            Wind.setText(data.getWind().toString());

            // Get information about USB flash
            driveDetector.getRemovableDevices();
            usbClass = new USBClass();
            driveDetector.addDriveListener(usbClass);

            // Flash is it
            if(!driveDetector.getRemovableDevices().isEmpty()) {
                usbflash.setText("File was uploaded into USB flash!");
                usbflash.setVisible(true);
                SaveFileFlash(data.toString(), driveDetector.getRemovableDevices().get(0).getRootDirectory());
            } else {
                // Flash not is it
                usbflash.setVisible(true);
                SaveButton.setVisible(true);
            }

        } catch (WeatherDataServiceException e) {
            e.printStackTrace();
        }
    }

    public void PathToSaveFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            SaveFile(data.toString(), file);
        }
    }

    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file + ".properties");
            fileWriter.write(stringToBinary(content));
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void SaveFileFlash(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file + "/weather.properties");
            fileWriter.write(stringToBinary(content));
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String stringToBinary(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }
}


