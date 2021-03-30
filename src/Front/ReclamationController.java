/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Message;
import Entities.Utilisateurs;
import Entities.categorie;
import Entities.reclamation;
import Service.Servicecategorie;
import Service.Servicereclamation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author rania
 */
public class ReclamationController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tfuser;
    @FXML
    private TextField tfobjet;
    @FXML
    private TextField tfmessage;
    @FXML
    private TableView<reclamation> table;
    @FXML
    private TableColumn<reclamation, Integer> idrect;
    @FXML
    private TableColumn<reclamation, Utilisateurs> idut;
    @FXML
    private TableColumn<reclamation, String> obt;
    @FXML
    private TableColumn<reclamation, Message> msgt;
    
    @FXML
    private ComboBox<Utilisateurs> cbUser;
    @FXML
    private TextField idsup;
    private ActionEvent event;
    @FXML
    private TextField nomC;
    @FXML
    private TextField typeC;
    @FXML
    private TextField descC;
    @FXML
    private Button addC;
    
    @FXML
    private Tab tabR;
    @FXML
    private Tab tabC;
    
    @FXML
    private Button modifC;
    @FXML
    private Button suppC;
    @FXML
    private TableView<categorie> tableC;
    private TableColumn<categorie, Integer> tabid;
    @FXML
    private TableColumn<categorie, String> tabnom;
    @FXML
    private TableColumn<categorie, String> tabtype;
    @FXML
    private TableColumn<categorie, String> tabdesc;
    @FXML
    private TextField idsupp;
    private TextField search;
    @FXML
    private TabPane tp;
    
     @FXML
    private Button btnAjouter;

    @FXML
    private Button btnSupprimer;
    
    private int id_message;
    @FXML
    private Button btnStat;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnStat.visibleProperty().set(false);
        idsup.visibleProperty().set(false);
        idsupp.visibleProperty().set(false);
          this.afficher_reclamation(event);
          this.afficher_categorie(event);        
          Servicereclamation sr= new Servicereclamation();
          cbUser.getItems().addAll(sr.getAllUsers());
        // TODO
        if(UserSession.UserSession.getInstace("", 0, "").getType().equals("Admin")) {
            tfobjet.disableProperty().set(true);
            tfmessage.disableProperty().set(true);
            btnAjouter.disableProperty().set(true);
            btnSupprimer.disableProperty().set(true);
            btnStat.visibleProperty().set(true);
            tfobjet.visibleProperty().set(false);
            tfmessage.visibleProperty().set(false);
            btnAjouter.visibleProperty().set(false);
            btnSupprimer.visibleProperty().set(false);
        }
    }    
    
    public void closeCategorie() {
        tp.getTabs().remove(tabC);
    }
    
    public void closeReclamation() {
        tp.getTabs().remove(tabR);
    }

    @FXML
    private void ajouter_reclamation(ActionEvent event) {
        Servicereclamation sr= new Servicereclamation();
        reclamation r = new reclamation();
        r.setId_user(cbUser.getValue());
        r.setObjet(tfobjet.getText());
        Message m = new Message();
        m.setContenu(tfmessage.getText());
        r.setMessage(m);
sr.ajouter_reclamation(r);
  this.afficher_reclamation(event);
   Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Reclamation ajoutée")
                              .position(Pos.TOP_CENTER) 
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
    sr.sendmail(r);
        this.afficher_reclamation(event);
    }

    private void afficher_reclamation(ActionEvent event) {
          Servicereclamation sr= new Servicereclamation();
          ObservableList<reclamation> reclamations =sr.Afficher_reclamation();
          idrect.setCellValueFactory(new PropertyValueFactory<reclamation,Integer>("id_reclamation"));
          idut.setCellValueFactory(new PropertyValueFactory<reclamation,Utilisateurs>("id_user"));
          obt.setCellValueFactory(new PropertyValueFactory<reclamation,String>("objet"));
          msgt.setCellValueFactory(new PropertyValueFactory<reclamation,Message>("message"));
          table.setItems(reclamations);
          
    }

    @FXML
    private void selectionner(MouseEvent event) {
        reclamation r =table.getSelectionModel().getSelectedItem();
        idsup.setText(String.valueOf(r.getId_reclamation()));        
        tfuser.setText(String.valueOf(r.getId_user()));
        cbUser.setValue(r.getId_user());
        tfobjet.setText(r.getObjet());
        id_message = r.getMessage().getId();
        tfmessage.setText(r.getMessage().getContenu());
    }

    @FXML
    private void supprimer_reclamation(ActionEvent event) {
        Servicereclamation sr= new Servicereclamation();
        //sr.deleteMessage(id_message);
        sr.archiveRec(Integer.parseInt(idsup.getText()));
        sr.supprimer_reclamation(Integer.parseInt(idsup.getText()));
         Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Reclamation supprimée")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        this.afficher_reclamation(event);
    }

    @FXML
    private void modifier_reclamation(ActionEvent event) {
        Servicereclamation sr = new Servicereclamation();
         reclamation r = new reclamation();
         r.setId_reclamation(Integer.parseInt(idsup.getText()));
         r.setId_user(cbUser.getValue());
         r.setObjet(tfobjet.getText());
         Message m = new Message();
         m.setId(id_message);
         m.setContenu(tfmessage.getText());
         r.setMessage(m);
         sr.modifier_reclamation(r);
           this.afficher_reclamation(event);
            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Reclamation modifiée")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        this.afficher_reclamation(event);
    }

    @FXML
    private void ajouter_categorie(ActionEvent event) {
          Servicecategorie sr= new Servicecategorie();
        categorie c= new categorie();
        //c.setId_categorie(Integer.parseInt(tfuser.getText()));
        c.setNom(nomC.getText());
        c.setType(typeC.getText());
        c.setDescription(descC.getText());
sr.ajouter_categorie(c);
   this.afficher_categorie(event);
    Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  categorie supprimée")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        this.afficher_reclamation(event);
    }
      private void afficher_categorie(ActionEvent event) {
          Servicecategorie sc= new Servicecategorie();
          ObservableList<categorie> categories =sc.Afficher_categorie();
//          tabid.setCellValueFactory(new PropertyValueFactory<categorie,Integer>("id_categorie"));
          tabnom.setCellValueFactory(new PropertyValueFactory<categorie,String>("Nom"));
          tabtype.setCellValueFactory(new PropertyValueFactory<categorie,String>("Type"));
          tabdesc.setCellValueFactory(new PropertyValueFactory<categorie,String>("Description"));
          tableC.setItems(categories);
          
    }

    @FXML
    private void modifier_categorie(ActionEvent event) {
         Servicecategorie sc = new Servicecategorie();
         categorie c = new categorie();
         c.setId_categorie(Integer.parseInt(idsupp.getText()));
          c.setNom(nomC.getText());
        c.setType(typeC.getText());
        c.setDescription(descC.getText());
         sc.modifier_categorie(c);
           this.afficher_categorie(event);
            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Categorie supprimée")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        this.afficher_reclamation(event);
        
    }

    @FXML
    private void supprimer_categorie(ActionEvent event) {
            Servicecategorie sr= new Servicecategorie();
        sr.supprimer_categorie(Integer.parseInt(idsupp.getText()));
        this.afficher_categorie(event);
         Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  Categorie supprimée")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        this.afficher_reclamation(event);
    }

    @FXML
    private void selectionnerC(MouseEvent event) {
         categorie c =tableC.getSelectionModel().getSelectedItem();
        idsupp.setText(String.valueOf(c.getId_categorie()));        
        nomC.setText(c.getNom());
        typeC.setText(c.getType());
        descC.setText(c.getDescription());
    }
    
    void SearchKey(KeyEvent event) {
            Servicereclamation sr= new Servicereclamation();
          ObservableList<reclamation> reclamations =sr.search(search.getText());
          idrect.setCellValueFactory(new PropertyValueFactory<reclamation,Integer>("id_reclamation"));
          idut.setCellValueFactory(new PropertyValueFactory<reclamation,Utilisateurs>("id_user"));
          obt.setCellValueFactory(new PropertyValueFactory<reclamation,String>("objet"));
          msgt.setCellValueFactory(new PropertyValueFactory<reclamation,Message>("message"));
          table.setItems(reclamations);
    }
    
    @FXML
    private Button trj;
    
    private int varTri = 0;
            
    @FXML
    void triiiiiii(MouseEvent event) {
        Servicereclamation sr= new Servicereclamation();
        ObservableList<reclamation> reclamations;
        if (varTri == 1) {
            varTri = 0;
            reclamations =sr.triASC();
        }
        else {
            varTri = 1;
            reclamations =sr.triDSC();
        }
          idrect.setCellValueFactory(new PropertyValueFactory<reclamation,Integer>("id_reclamation"));
          idut.setCellValueFactory(new PropertyValueFactory<reclamation,Utilisateurs>("id_user"));
          obt.setCellValueFactory(new PropertyValueFactory<reclamation,String>("objet"));
          msgt.setCellValueFactory(new PropertyValueFactory<reclamation,Message>("message"));
          table.setItems(reclamations);
    }
    
     @FXML
    private Button triiiiccc;
     
     private int varTriC = 0;
     
     @FXML
    void triCCCCCC(MouseEvent event) {
         Servicecategorie sr= new Servicecategorie();
        ObservableList<categorie> categories;
        if (varTriC == 1) {
            varTriC = 0;
            categories =sr.triASC();
        }
        else {
            varTriC = 1;
            categories =sr.triDSC();
        }
          tabid.setCellValueFactory(new PropertyValueFactory<categorie,Integer>("id_categorie"));
          tabnom.setCellValueFactory(new PropertyValueFactory<categorie,String>("Nom"));
          tabtype.setCellValueFactory(new PropertyValueFactory<categorie,String>("Type"));
          tabdesc.setCellValueFactory(new PropertyValueFactory<categorie,String>("Description"));
          tableC.setItems(categories);
    }
    
    @FXML
    private TextField searchC;
    
    @FXML
    void searchCCkey(KeyEvent event) {
          Servicecategorie sc= new Servicecategorie();
          ObservableList<categorie> categories =sc.search(searchC.getText());
          tabid.setCellValueFactory(new PropertyValueFactory<categorie,Integer>("id_categorie"));
          tabnom.setCellValueFactory(new PropertyValueFactory<categorie,String>("Nom"));
          tabtype.setCellValueFactory(new PropertyValueFactory<categorie,String>("Type"));
          tabdesc.setCellValueFactory(new PropertyValueFactory<categorie,String>("Description"));
          tableC.setItems(categories);
    }

   @FXML
    void statClicked(MouseEvent event) {
        loadStage("/Front/StatRec.fxml");
    }
    
    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
          //  stage.getIcons().add(new Image("/home/icons/icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void archiveClicked(MouseEvent event) {
        loadStage("/Front/archiveRec.fxml");
    }
    
    @FXML
    void btnAfficherAction(ActionEvent event) {
        loadStage("/Front/afficherRec.fxml");
    }

    @FXML
    private void afficherCategorie(MouseEvent event) {
        loadStage("/Front/afficherCat.fxml");

    }
}
