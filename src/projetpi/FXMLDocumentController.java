/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetpi;

import Entities.categorie;
import Entities.reclamation;
import Service.Servicecategorie;
import Service.Servicereclamation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author rania
 */
public class FXMLDocumentController implements Initializable {
    
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
    private TableColumn<reclamation, Integer> idut;
    @FXML
    private TableColumn<reclamation, String> obt;
    @FXML
    private TableColumn<reclamation, String> msgt;
    @FXML
    private TextField idsup;
    private ActionEvent event;
    @FXML
    private TextField idC;
    @FXML
    private TextField nomC;
    @FXML
    private TextField typeC;
    @FXML
    private TextField descC;
    @FXML
    private Button addC;
    @FXML
    private Button modifC;
    @FXML
    private Button suppC;
    @FXML
    private TableView<categorie> tableC;
    @FXML
    private TableColumn<categorie, Integer> tabid;
    @FXML
    private TableColumn<categorie, String> tabnom;
    @FXML
    private TableColumn<categorie, String> tabtype;
    @FXML
    private TableColumn<categorie, String> tabdesc;
    @FXML
    private TextField idsupp;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          this.afficher_reclamation(event);
          this.afficher_categorie(event);
        // TODO
    }    

    @FXML
    private void ajouter_reclamation(ActionEvent event) {
        Servicereclamation sr= new Servicereclamation();
        reclamation r = new reclamation();
        r.setId_user(Integer.parseInt(tfuser.getText()));
        r.setObjet(tfobjet.getText());
        r.setMessage(tfmessage.getText());
sr.ajouter_reclamation(r);
  this.afficher_reclamation(event);
    }

    private void afficher_reclamation(ActionEvent event) {
          Servicereclamation sr= new Servicereclamation();
          ObservableList<reclamation> reclamations =sr.Afficher_reclamation();
          idrect.setCellValueFactory(new PropertyValueFactory<reclamation,Integer>("id_reclamation"));
          idut.setCellValueFactory(new PropertyValueFactory<reclamation,Integer>("id_user"));
          obt.setCellValueFactory(new PropertyValueFactory<reclamation,String>("objet"));
          msgt.setCellValueFactory(new PropertyValueFactory<reclamation,String>("message"));
          table.setItems(reclamations);
          
    }

    @FXML
    private void selectionner(MouseEvent event) {
        reclamation r =table.getSelectionModel().getSelectedItem();
        idsup.setText(String.valueOf(r.getId_reclamation()));        
        tfuser.setText(String.valueOf(r.getId_user()));
        tfobjet.setText(r.getObjet());
        tfmessage.setText(r.getMessage());
       
    }

    @FXML
    private void supprimer_reclamation(ActionEvent event) {
        Servicereclamation sr= new Servicereclamation();
        sr.supprimer_reclamation(Integer.parseInt(idsup.getText()));
        this.afficher_reclamation(event);
    }

    @FXML
    private void modifier_reclamation(ActionEvent event) {
        Servicereclamation sr = new Servicereclamation();
         reclamation r = new reclamation();
         r.setId_reclamation(Integer.parseInt(idsup.getText()));
         r.setId_user(Integer.parseInt(tfuser.getText()));
         r.setObjet(tfobjet.getText());
         r.setMessage(tfmessage.getText());
         sr.modifier_reclamation(r);
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
    }
      private void afficher_categorie(ActionEvent event) {
          Servicecategorie sc= new Servicecategorie();
          ObservableList<categorie> categories =sc.Afficher_categorie();
          tabid.setCellValueFactory(new PropertyValueFactory<categorie,Integer>("id_categorie"));
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
        
    }

    @FXML
    private void supprimer_categorie(ActionEvent event) {
            Servicecategorie sr= new Servicecategorie();
        sr.supprimer_categorie(Integer.parseInt(idsupp.getText()));
        this.afficher_categorie(event);
    }

    @FXML
    private void selectionnerC(MouseEvent event) {
         categorie c =tableC.getSelectionModel().getSelectedItem();
        idsupp.setText(String.valueOf(c.getId_categorie()));        
        nomC.setText(c.getNom());
        typeC.setText(c.getType());
        descC.setText(c.getDescription());
    }
    
}
