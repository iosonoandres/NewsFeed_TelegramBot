package com.example.inferno_fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AutenticazioneAdmin.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("FireNews24");
        stage.getIcons().add(new Image(this.getClass().getResource("Template.jpeg").toString()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}