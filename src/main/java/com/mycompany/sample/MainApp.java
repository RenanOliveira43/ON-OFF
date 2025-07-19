package com.mycompany.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {
    protected static Stage stage;
    // protected static  Database db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        // db = new Database(true);
    
        // if (db.getUsers().isEmpty()) {
        //     setScene("/telaInicial.fxml");
        // } else {
        //     setScene("/telaEscolhaUsuario.fxml");
        // }


        setScene("/addComputer.fxml");
        primaryStage.show();
    }
    
    public static void setScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(MainApp.class.getResource(fxml));

            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

            scene.getStylesheets().add(MainApp.class.getResource("/style.css").toExternalForm());
            stage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
