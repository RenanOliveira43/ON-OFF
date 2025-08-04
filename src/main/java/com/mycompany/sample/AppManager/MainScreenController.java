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
        startPingScheduler();
    }

    @FXML
    public void on() {
        printStatus(computer.turnOn());
    }

    @FXML
    public void off() {
        printStatus(computer.turnOff());
    }

    @FXML
    public void lock() {
        printStatus(computer.lock());
    }

    @FXML
    public void reboot() {
        printStatus(computer.reboot());
    }

    @FXML
    private void goToAddComputerScreen() {
        MainApp.setScene("/addComputer.fxml");
    }

    private void startPingScheduler() {
        Timeline pingTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> runPingTask()) 
        );
        pingTimeline.setCycleCount(Timeline.INDEFINITE);
        pingTimeline.play();
    }

    private void runPingTask() {
        Task<Integer> pingTask = new Task<>() {
            @Override
            protected Integer call() {
                return computer.ping();
            }
        };

        pingTask.setOnSucceeded(e -> updatePingStatus(pingTask.getValue()));
        new Thread(pingTask).start();
    }

    private void updatePingStatus(int status) {
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
        switch (responseCode) {
            case 200 -> statusLabel.setText("Comando executado com sucesso");
            case 401 -> statusLabel.setText("Não autorizado");
            case -1 -> statusLabel.setText("Falha na comunicação com o servidor");
            default -> statusLabel.setText("Erro ao executar comando");
        }
    }
}
