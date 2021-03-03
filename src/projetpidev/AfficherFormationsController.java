/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetpidev;

import Entities.Formations;
import Service.ServiceFormations;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class AfficherFormationsController implements Initializable {
    
    @FXML
      private TableView<Formations> tabform;
    @FXML
    private TableColumn<Formations, Integer> coid;
    @FXML
    private TableColumn<Formations, String> coobjet;
    @FXML
    private TableColumn<Formations, String> cotype;
    @FXML
    private TableColumn<Formations, String> coobjectif;
    @FXML
    private TableColumn<Formations, Integer> conbparticp;
    @FXML
    private TableColumn<Formations, Float> cocouthj;
    @FXML
    private TableColumn<Formations, Integer> conbjour;
    @FXML
    private TableColumn<Formations, Float> cocoutfin;
    @FXML
    private TableColumn<Formations, Date> codaterelle;
    @FXML
    private TableColumn<Formations, Date> codateprevu;
    @FXML
    private TextField idsupp;
    @FXML
    private Button btnsupp;
public void Afficher_formations()
    {
        ServiceFormations sp=new ServiceFormations();
        ObservableList<Formations> list=sp.Get_Formations();
        coid.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("Id"));
         coobjet.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
          cotype.setCellValueFactory(new PropertyValueFactory<Formations,String>("Type"));
           coobjectif.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objectif"));
           conbparticp.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
           cocouthj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
           conbjour.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_jour"));
           cocoutfin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
           codaterelle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_reelle"));
           codateprevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
           tabform.setItems(list);
           
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Afficher_formations();
    }    

    @FXML
    private void Predelete(MouseEvent event) {
       Formations form= tabform.getSelectionModel().getSelectedItem();
      idsupp.setText(String.valueOf(form.getId()));
    }

    @FXML
    private void DeleteFormation(ActionEvent event) {
        ServiceFormations sv=new ServiceFormations();
        sv.Supprimer_formation(Integer.parseInt(idsupp.getText()));
         Afficher_formations();
    }

    
}
