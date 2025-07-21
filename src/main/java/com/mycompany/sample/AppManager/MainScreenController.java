package com.mycompany.sample.AppManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MainScreenController {
    private Computer computer;
    @FXML private Label statusLabel;
    @FXML private Label namePCLabel;
    @FXML private Label statusServerLabel;
    
    @FXML
    public void initialize() {
        computer = MainApp.db.getComputers().get(0);
        namePCLabel.setText(computer.getNamePC());
        computer.ping();
        startPingScheduler();
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

    private void startPingScheduler() {
        Timeline pingTimeline = new Timeline(
            new KeyFrame(Duration.seconds(4), event -> {
                Task<Integer> pingTask = new Task<>() {
                    @Override
                    protected Integer call() {
                        return computer.ping(); // executa em background
                    }
                };

                pingTask.setOnSucceeded(e -> {
                    int status = pingTask.getValue();
                    updatePingStatus(status);
                });

                new Thread(pingTask).start(); // inicia a task
            })
        );

        pingTimeline.setCycleCount(Timeline.INDEFINITE);
        pingTimeline.play();
    }

    private void updatePingStatus(int status) {
    if (status == 200) {
        statusServerLabel.setText("Online");
        statusServerLabel.setStyle("-fx-text-fill: green;");
    } else {
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
