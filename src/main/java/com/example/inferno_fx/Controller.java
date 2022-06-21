package com.example.inferno_fx;


import com.example.inferno_fx.MapSample.MapSample;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;


public class Controller implements Initializable {

    @FXML
    private PasswordField passwordBox;
    @FXML
    private TextField usernameBox;
    @FXML
    private Text inputSbagliato;
    @FXML
    private Text asteriscoUsername;
    @FXML
    private Text asteriscoPassword;
    @FXML
    private ListView<String> myListView;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToFeedManager(ActionEvent event) throws IOException {
        DatiAdmin datiAdmin = new DatiAdmin();
        datiAdmin.aggiungiAdmin("@parsssa13");
        datiAdmin.aggiungiAdmin("@Massimomanonpericolo");
        datiAdmin.aggiungiAdmin("@Francibo");
        datiAdmin.setPassword("123ADMIN");
        String inputPassword = passwordBox.getText();
        String inputUsername = usernameBox.getText();

        if(datiAdmin.getListaAdmin().contains(inputUsername) && datiAdmin.getPassword().equals(inputPassword)) {
            Parent root = FXMLLoader.load(getClass().getResource("FeedManager.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        } else if (inputUsername.equals("")||inputPassword.equals("")) {
            inputSbagliato.setText("Completare tutti i campi");
            asteriscoPassword.setText("*");
            asteriscoUsername.setText("*");
        } else{
            if(!(datiAdmin.getListaAdmin()).contains(usernameBox.getText())) {
                inputSbagliato.setText("Utente non riconosciuto");
                asteriscoPassword.setText("");
                asteriscoUsername.setText("*");
            }else{
                inputSbagliato.setText("password errato");
                asteriscoUsername.setText("");
                asteriscoPassword.setText("*");
            }
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeMap<String, ArrayList<String>> mappa = new TreeMap<>();
        ArrayList<String> URLSport = new ArrayList<>();
        URLSport.add("https://www.gazzetta.it/rss/home.xml");
        URLSport.add("https://www.ansa.it/sito/notizie/sport/sport_rss.xml");

        mappa.put("SPORT",URLSport);

        myListView = new ListView<>();
        myListView.getItems().addAll(mappa.keySet());
    }
}