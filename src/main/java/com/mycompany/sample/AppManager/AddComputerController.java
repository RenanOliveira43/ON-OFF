package com.mycompany.sample.AppManager;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.fxml.FXML;

public class AddComputerController {
    @FXML private TextField namePCTextField;
    @FXML private TextField macTextField;
    @FXML private TextField ipTextField;
    @FXML private Label confirmLabel;
    protected Computer computer;

    @FXML
    private void initialize() {
        if (!MainApp.db.getComputers().isEmpty()) {
            computer = MainApp.db.getComputers().get(0);
            namePCTextField.setText(computer.getNamePC());
            macTextField.setText(computer.getMacAddr());
            ipTextField.setText(computer.getIp());
            confirmLabel.setText("Computador carregado para edição.");
        }
        else{
            confirmLabel.setText("Insira o nome, endereço MAC e IP do computador");
        }
    }
    
    @FXML
    private void createOrEditComputer() {
        try{
            if (MainApp.db.getComputers().isEmpty()) {
                computer = new Computer(namePCTextField.getText(), macTextField.getText(), ipTextField.getText());
                MainApp.db.insert(computer);
                confirmLabel.setText("Computador criado com sucesso!");
            }
            else {
                editComputer();
            }
        } catch (Exception e) {
            confirmLabel.setText("Erro ao criar ou editar computador");
            return;
        }
    }

    @FXML
    private void editComputer() {
        try {
            computer.setNamePC(namePCTextField.getText());
            computer.setMacAddr(macTextField.getText());
            computer.setIp(ipTextField.getText());
            MainApp.db.update(computer);
            
            confirmLabel.setText("Computador editado com sucesso!");
        } catch (Exception e) {
            confirmLabel.setText("Erro ao editar computador");
            return;
        }
    }

    @FXML
    private void goToMainScreen() {
        if (MainApp.db.getComputers().isEmpty()){
            confirmLabel.setText("Por favor, crie um computador primeiro.");
        }
        else {
            MainApp.setScene("/mainScreen.fxml");
        }
    }
}
