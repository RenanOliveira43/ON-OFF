package com.mycompany.sample.AppManager;

import com.mycompany.sample.DataBaseManager.Database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApp extends Application {
    protected static Stage stage;
    protected static Database db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        db = new Database(true);
    
        if (db.getComputers().isEmpty()) {
            setScene("/addComputer.fxml");
        } else {
            setScene("/mainScreen.fxml");
        }
        
        // setScene("/addComputer.fxml");
        primaryStage.show();
    }

    public static void setScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root); 

            URL stylesheet = MainApp.class.getResource("/style.css");
            if (stylesheet != null) {
                scene.getStylesheets().add(stylesheet.toExternalForm());
            }

            stage.setScene(scene);
            stage.setFullScreen(true);         // Ativa o modo fullscreen
            stage.setFullScreenExitHint("");   // Remove a mensagem "Press ESC to exit full screen"
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
