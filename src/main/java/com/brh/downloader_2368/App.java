package com.brh.downloader_2368;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class App extends Application {

    private static Stage currentStage;
    private static Controller currentController;
    private static FileHandler logFileHandler;

    @Override
    public void start(Stage stage) throws IOException {
        logFileHandler = new FileHandler("logfile%g.log", 1000, 3);
        logFileHandler.setFormatter( new SimpleFormatter());

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("dowload-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Downloader");
        stage.setScene(scene);
        stage.show();
        currentStage = stage;
        currentController = fxmlLoader.getController();
    }

    public static Stage getStage(){
       return currentStage;
    }

    public static Controller getController(){
        return currentController;
    }

    public static FileHandler getLogFileHandler(){
        return logFileHandler;
    }

}
