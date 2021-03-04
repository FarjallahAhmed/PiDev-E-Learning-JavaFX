/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entity.Activite;
import Entity.projet;
import Utils.Mask;
import implService.impProjet;
import implService.implActiviteService;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import static java.util.Collections.list;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Asus
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField nomA;
    @FXML
    private TextField sujetA;
    @FXML
    private TextField descriptionA;
    @FXML
    private DatePicker dateA;
    @FXML
    private Button ajouterA;
    @FXML
    private Button updateA;
    @FXML
    private TableView<Activite> tableview;
    @FXML
    private Button idUpdate;
    @FXML
    private TextField idUp;
    @FXML
    private TableColumn<Activite, Integer> idtable;
    @FXML
    private TableColumn<Activite, String> nomtable;
    @FXML
    private TableColumn<Activite, String> sujettable;
    @FXML
    private TableColumn<Activite, String> descriptiontable;
    @FXML
    private TableColumn<Activite, Date> datetable;
    @FXML
    private TextField nbrP;
    @FXML
    private TextField hP;
    @FXML
    private Button bAjouter;
    @FXML
    private TextField nomP;
    @FXML
    private TextField sujetP;
    @FXML
    private TextField descriptionP;
    @FXML
    private DatePicker dateP;
    
    private void handleButtonAction(ActionEvent event) {
       
    }
    
    void afficher(){
        
        implActiviteService aService = new implActiviteService();
        
        
        idtable.setCellValueFactory(new PropertyValueFactory<Activite,Integer>("id_activite"));
        sujettable.setCellValueFactory(new PropertyValueFactory<Activite,String>("sujet"));
        descriptiontable.setCellValueFactory(new PropertyValueFactory<Activite,String>("description"));
        nomtable.setCellValueFactory(new PropertyValueFactory<Activite,String>("nom"));
        datetable.setCellValueFactory(new PropertyValueFactory<Activite,Date>("date_creation"));
        try {
            ObservableList<Activite> list = aService.readAll();
            tableview.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.afficher();
        Mask.noSymbolsAndNumbers(nomA);
        Mask.noSymbolsAndNumbers(sujetA);
        Mask.noSymbolsAndNumbers(descriptionA);
        

        
    }    

    @FXML
    private void ajouterActivite(ActionEvent event) {
        
        implActiviteService aService = new implActiviteService();
        Activite a = new Activite();
        
        //a.setId_activite(0);
        a.setNom(nomA.getText());
        a.setSujet(sujetA.getText());
        a.setDescription(descriptionA.getText());
        a.setDate_creation(Date.valueOf(dateA.getValue()));
        
        try {
            aService.ajouter(a);
            this.afficher();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void updateActivite(ActionEvent event) {
        implActiviteService aService = new implActiviteService();
        Activite a = new Activite();
        
        try {
            a = aService.get(Integer.parseInt(idUp.getText()));
            System.out.println(a);
        //a.setId_activite(Integer.parseInt(idUp.getText()));
        a.setNom(nomA.getText());
        a.setSujet(sujetA.getText());
        a.setDescription(descriptionA.getText());
        a.setDate_creation(Date.valueOf(dateA.getValue()));
        
        aService.update(a);
        this.afficher();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @FXML
    private void deleteActivite(MouseEvent event) {
        implActiviteService aService = new implActiviteService();
        Activite a = tableview.getSelectionModel().getSelectedItem();
        
        try {
            aService.delete(a.getId_activite());
            this.afficher();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void afficherUpdateItem(ActionEvent event) {
        
        implActiviteService aService = new implActiviteService();
        Activite a = new Activite();
        try {
               System.out.println("test"+idUp.getText());
            a = aService.get(Integer.parseInt(idUp.getText()));
            
            
            nomA.setText(a.getNom());
            sujetA.setText(a.getSujet());
            descriptionA.setText(a.getDescription());
            //dateA.setValue(Date.valueOf(a.getDate_creation()));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouterProjet(ActionEvent event) {
        
         impProjet aService = new impProjet();
        projet a = new projet();
        
        //a.setId_activite(0);
        a.setNom(nomP.getText());
        a.setSujet(sujetP.getText());
        a.setDescription(descriptionP.getText());
        a.setDate_creation(Date.valueOf(dateP.getValue()));
        
        a.setHeureRencontre(hP.getText());
        a.setNombreMax(Integer.parseInt(nbrP.getText()));
        
        try {
            aService.ajouter(a);
            this.afficher();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
