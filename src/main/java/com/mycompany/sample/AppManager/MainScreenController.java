package com.mycompany.sample.AppManager;

import javafx.fxml.FXML;

public class MainScreenController {
    private Computer computer;
    
    @FXML
    public void teste() {
        System.out.println("Teste");
    }

    @FXML
    public void initialize() {
        computer = MainApp.db.getComputers().get(0);
        System.out.println("Computer initialized: " + computer);
    }

    @FXML
    public void on() {
        computer.turnOn();
    }

    @FXML
    public void off() {
        computer.turnOff();
    }

    @FXML
    public void lock() {
        computer.lock();
    }

    @FXML
    public void reboot() {
        computer.reboot();
    }

    @FXML
    private void goToAddComputerScreen() {
        MainApp.setScene("/addComputer.fxml");
    }
}
