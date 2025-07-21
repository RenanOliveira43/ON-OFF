package com.mycompany.sample.AppManager;

import javafx.fxml.FXML;

public class MainScreenController {
    private Computer computer;
    @FXML private Label statusLabel;
    @FXML private Label statusServerLabel;
    
    @FXML
    public void initialize() {
        computer = MainApp.db.getComputers().get(0);
        ping();
    }

    @FXML
    public void on() {
        int responseCode = computer.turnOn();
        printStatus(responseCode);
    }

    @FXML
    public void off() {
        int responseCode = computer.turnOff();
        printStatus(responseCode);
    }

    @FXML
    public void lock() {
        int responseCode = computer.lock();
        printStatus(responseCode);
    }

    @FXML
    public void reboot() {
        int responseCode = computer.reboot();
        printStatus(responseCode);
    }

    @FXML
    private void goToAddComputerScreen() {
        MainApp.setScene("/addComputer.fxml");
    }

    @FXML
    private void ping() {
        int status = computer.ping();

        if (status == 200) {
            statusServerLabel.setText("Online");
            statusServerLabel.setStyle("-fx-text-fill: green;");
        } 
        else {
            statusServerLabel.setText("Offline");
            statusServerLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void printStatus(int responseCode) {
        if (responseCode == 200) {
            statusLabel.setText("Comando executado com Sucesso");
        }
        else {
            statusLabel.setText("Erro ao executar comando");
        }
    }
}
