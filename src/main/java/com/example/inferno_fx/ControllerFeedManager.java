package com.example.inferno_fx;

import com.example.inferno_fx.OperazioniJSON.MappaCategorie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFeedManager implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TreeView myTree;

    @FXML
    private Label cosaVorrestiAggiungere;

    private boolean salvato = true;


    private ContextMenu myContext;




    Image categorieBox = new Image(getClass().getResourceAsStream("categorie.png"), 20, 20, false, false);
    Image folderImage = new Image(getClass().getResourceAsStream("folder.png"), 15, 15, false, false);
    Image urlImage = new Image(getClass().getResourceAsStream("url.png"), 14, 14, false, false);

    public void switchToAutenticazioneAdmin(ActionEvent event) throws IOException {
        if(!salvato) {
            //pop-up di allarme, chiede se sei sicuro di voler fare il logout
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("Stai per effettuare il logout");
            alert.setContentText("Considera un salvataggio prima di uscire");
            if (alert.showAndWait().get() == ButtonType.OK) {

                Parent root = FXMLLoader.load(getClass().getResource("AutenticazioneAdmin.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                String css = this.getClass().getResource("application.css").toExternalForm();
                scene.getStylesheets().add(css);
            }
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("AutenticazioneAdmin.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);        }
    }

    public void switchToUserManager(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("UserManager.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        String css = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(css);
    }

    public void mostraFinestraNotizie(/*MouseEvent event*/MouseEvent event) {
           try {
               Parent root = FXMLLoader.load(getClass().getResource("NotiziaManager.fxml"));
               Stage stage = new Stage();
               stage.setTitle("NotiziaManager");
               scene = new Scene(root);
               stage.setScene(scene);
               String css = this.getClass().getResource("application.css").toExternalForm();
               scene.getStylesheets().add(css);
               stage.show();
           }
           catch (Exception e){
               System.out.println("impossibile caricare finestra NotiziaManager");
           }
    }

    //questo initializie serve per inizializzare la TreeView, altrimenti lo lascia vuoto
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //codice di prova per inizializzare la context menu e riempirla id MenuItem
        this.myContext = new ContextMenu();
        myTree.setContextMenu(myContext);
        MenuItem itemCancella = new MenuItem("Cancella");
        //entry1.setOnAction(ae -> ...);
        MenuItem itemAggiungi = new MenuItem("Aggiungi");
        //entry2.setOnAction(ae -> ...);
        this.myContext.getItems().add(itemCancella);
        this.myContext.getItems().add(itemAggiungi);

        itemCancella.setOnAction(event -> {
            System.out.println("Ho cancellato, nigga");
        });

        itemAggiungi.setOnAction(event -> {

        });

        //inizializzo le imageview e modifico la grandezza



        TreeItem<String> rootItem = new TreeItem<>("Categorie",new ImageView(categorieBox));
        myTree.setRoot(rootItem);
        rootItem.setExpanded(true);
        MappaCategorie mappaCategorie = new MappaCategorie("outputCategorie.json");
        for(String nomeCategoria: mappaCategorie.getMappa().keySet()){

            TreeItem thisCategoria = new TreeItem(nomeCategoria, new ImageView(folderImage));
            rootItem.getChildren().add(thisCategoria);
            for(String link:mappaCategorie.getMappa().get(nomeCategoria)){
                TreeItem thisLink = new TreeItem(link, new ImageView(urlImage));


                thisLink.getGraphic().setOnMouseClicked(event -> System.out.println("OnAction {}"));



                thisCategoria.getChildren().add(thisLink);
            }
        }

        this.myTree.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            mostraFinestraNotizie(event);
        };

        myTree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
    }

    public void salvataggio(MouseEvent event){
        salvato = true;
        //l'idea e' che -premuto il tasto salvataggio- scorro gli elementi della TreeView e li paragono a quelli della mappa generata dal file, se c'e'
        //qualcosa di nuovo lo aggiungo al file

    }

}
