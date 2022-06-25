package com.example.inferno_fx;

import com.example.inferno_fx.OperazioniJSON.Categoria;
import com.example.inferno_fx.OperazioniJSON.MappaCategorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFeedManager implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TreeView myTree;

    Image categorieBox = new Image(getClass().getResourceAsStream("categorie.png"), 20, 20, false, false);
    Image folderImage = new Image(getClass().getResourceAsStream("folder.png"), 15, 15, false, false);
    Image urlImage = new Image(getClass().getResourceAsStream("url.png"), 14, 14, false, false);

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

    //questo initializie serve per inizializzare la TreeView, altrimenti lo lascia vuoto
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //inizializzo le imageview e modifico la grandezza
        ImageView categorieImageView = new ImageView(categorieBox);



        TreeItem<String> rootItem = new TreeItem<>("Categorie",categorieImageView);
        myTree.setRoot(rootItem);
        MappaCategorie mappaCategorie = new MappaCategorie("outputCategorie.json");
        for(String nomeCategoria: mappaCategorie.getMappa().keySet()){

            TreeItem thisCategoria = new TreeItem(nomeCategoria, new ImageView(folderImage));
            rootItem.getChildren().add(thisCategoria);
            for(String link:mappaCategorie.getMappa().get(nomeCategoria)){
                TreeItem thisLink = new TreeItem(link, new ImageView(urlImage));
                thisCategoria.getChildren().add(thisLink);
            }
        }
    }


    public void onCliccaDestra(){
        System.out.println("hai cliccato destra");
    }
}
