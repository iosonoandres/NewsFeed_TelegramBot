package com.example.inferno_fx;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToFeedManager(ActionEvent event) throws IOException {
        DatiAdmin datiAdmin = new DatiAdmin();
        datiAdmin.aggiungiAdmin("@parsssa13");
        datiAdmin.aggiungiAdmin("@Massimomanonpericolo");
        datiAdmin.aggiungiAdmin("@Francibo");
        datiAdmin.setPassword("123ADMIN");

        //if(//se username e password sono corretti) {
            Parent root = FXMLLoader.load(getClass().getResource("FeedManager.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        //}
        /*else{
            if(!(datiAdmin.getListaAdmin()).contains(usernameBox.getText())){
                appare il testo "utente admin non riconosciuto"
            else{
                  appare il testo "password errato"
            }
        }*/
    }
    public void switchToAutenticazioneAdmin(ActionEvent event) throws IOException {
        //pop-up di allarme, chiede se sei sicuro di voler fare il logout
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Stai per effettuare il logout");
        alert.setContentText("Considera un salvataggio prima di uscire");
        if(alert.showAndWait().get() == ButtonType.OK) {

            Parent root = FXMLLoader.load(getClass().getResource("AutenticazioneAdmin.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
    }
}