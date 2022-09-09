package com.example.inferno_fx;

import com.example.inferno_fx.OperazioniJSON.Utente;
import com.example.inferno_fx.OperazioniJSON.gestoreGsonUtente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerModificaUtente implements Initializable {
    private Stage stage;


    @FXML
    private TextField UsernameBox;

    @FXML
    private TextField PasswordBox;

    @FXML
    private Label titoloUtente;
    @FXML
    private ImageView floppyDisk;
    

    private ArrayList<Utente> UserList = new ArrayList<Utente>();
    private Utente utenteInQuestione;
    private gestoreGsonUtente ggu = new gestoreGsonUtente();
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File txtDaLeggere = new File("usernameUtenteInQuestione.txt");
        String usernameUtenteInQuestione = "";
        try {
            Scanner scanner = new Scanner(txtDaLeggere);
            usernameUtenteInQuestione = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Errore nel leggere nome utente da file txt");
        }
        System.out.println(usernameUtenteInQuestione);



        try {
            this.UserList = ggu.readJsonLista("ListaUtentiVari.json");
        } catch (FileNotFoundException e) {
            System.out.println("JSON Utenti inesistente");
        }

        for (Utente u:UserList){

            if (u.getUserName().equalsIgnoreCase(usernameUtenteInQuestione)){
                this.utenteInQuestione = u;
            }
        }


        titoloUtente.setText("Modifica Utente - "+usernameUtenteInQuestione);
        UsernameBox.setText(utenteInQuestione.getUserName());
        PasswordBox.setText(utenteInQuestione.getPassword());


    }

    public void salvataggio(MouseEvent event){

        //l'idea e' che -premuto il tasto salvataggio- scorro gli elementi della TreeView, li metto in una nuovaLista,
        //e tolgo dalla UserList gli elementi che non ci sono piu' nella nuovaLista

        UserList.remove(utenteInQuestione);
        this.utenteInQuestione.setUserName(UsernameBox.getText());
        this.utenteInQuestione.setPassword(PasswordBox.getText());
        UserList.add(utenteInQuestione);



        try {
            ggu.writeJson(UserList);
        } catch (IOException e) {
            System.out.println("Errore a trovare file json per aggiornare gli utenti");
            throw new RuntimeException(e);
        }

    }


    public void switchToUserManager(MouseEvent event) {

        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            root = FXMLLoader.load(getClass().getResource("UserManager.fxml"));
            stage = new Stage();//(Stage) ((Node) t.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.getIcons().add(new Image(this.getClass().getResource("variLogo/powder-blue-designify.png").toString()));
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            System.out.println("Errore nel caricare UserManager.fxml");
            throw new RuntimeException(e);
        }

    }



}