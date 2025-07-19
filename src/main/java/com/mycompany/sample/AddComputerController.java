package com.mycompany.sample;

import javafx.scene.control.TextField;

import javafx.fxml.FXML;

public class AddComputerController {
    @FXML private TextField macTextField;
    @FXML private TextField ipTextField;
    protected Computer computer;
    
    @FXML
    public void createComputer() {
        computer = new Computer(macTextField.getText(), ipTextField.getText());

        System.out.println("Computer created: " + computer);
    }
}
