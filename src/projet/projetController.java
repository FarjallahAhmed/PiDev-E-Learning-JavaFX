/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;



import Entities.Activite;
import Entities.Utilisateurs;
import Entities.projet;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import Service.impProjet;
import Service.implActiviteService;
import Utils.Mask;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author NMEDIA
 */
public class projetController implements Initializable{

    @FXML
    private Button btnajouter;
    @FXML
    private TextField idsupp;
    @FXML
    private Button btnupdate;
    @FXML
    private TextField nom;
    @FXML
    private TextField sujet;
    @FXML
    private TextArea desc;
    @FXML
    private DatePicker dateCreation;
    private ComboBox<String> listactiviter;
    @FXML
    private Button addActivite;
    @FXML
    private TableView<projet> tabview;
    @FXML
    private TableColumn<projet, String> nomPro;
    @FXML
    private TableColumn<projet, String> sujetPro;
    @FXML
    private TableColumn<projet, String> descriptionPro;
    @FXML
    private TableColumn<projet, Date> dateCPro;
    @FXML
    private AnchorPane idupd;
    @FXML
    private Button update;
    @FXML
    private TextField nomup;
   
    
    
    
    private projet pj = new projet();
    @FXML
    private TableColumn<projet, String> nomActivite;
    @FXML
    private TextField chercher;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        Mask.noSymbolsAndNumbers(nom);
        Mask.noSymbolsAndNumbers(sujet);
   
        
        
        
        
        
        impProjet pService = new impProjet();
        sujetPro.setCellValueFactory(new PropertyValueFactory<projet,String>("sujet"));
        descriptionPro.setCellValueFactory(new PropertyValueFactory<projet,String>("description"));
        nomPro.setCellValueFactory(new PropertyValueFactory<projet,String>("nom"));
        dateCPro.setCellValueFactory(new PropertyValueFactory<projet,Date>("date_creation"));
      
        try {
            ObservableList<projet> list = pService.readAll();
            //System.out.println(list);
            System.out.println(pService.readAllActivite());
            tabview.setItems(list);
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            System.out.println(pService.readAllActivite());
        } catch (SQLException ex) {
            Logger.getLogger(projetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        
    }
    
    
    
   

    
    


    @FXML
    private void onmodifier(ActionEvent event) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        impProjet aService = new impProjet();
        projet a = new projet();
       
        try {
               
                pj = aService.get(nomup.getText());
               
            
            nom.setText(pj.getNom());
            sujet.setText(pj.getSujet());
            desc.setText(pj.getDescription());
            dateCreation.setValue(LocalDate.parse(pj.getDate_creation().toString(),formatter));
          
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    

    @FXML
    private void onajouterProjet(ActionEvent event) {
        
        Date dateC = Date.valueOf(dateCreation.getValue());
        String nomp = nom.getText();
        String description = desc.getText();
        String sujetp = sujet.getText();
        impProjet pService = new impProjet();
        implActiviteService aService = new implActiviteService();
        projet p = new projet(nomp, sujetp, description, dateC);
        //Activite a1 = new Activite();
        
        try {
            pService.ajouter(p);
            activiteController.a.setId( impProjet.insertedId);
            aService.ajouter(activiteController.a);
            ServiceFormateur sp = new ServiceFormateur();
            
            pService.sendMail(sp.getEmailUserById(UserSession.UserSession.getInstace("", 0,"").getId()), "Ajout Projet", "Projet Ajoute Avec Success!");
        sujetPro.setCellValueFactory(new PropertyValueFactory<projet,String>("sujet"));
        descriptionPro.setCellValueFactory(new PropertyValueFactory<projet,String>("description"));
        nomPro.setCellValueFactory(new PropertyValueFactory<projet,String>("nom"));
        dateCPro.setCellValueFactory(new PropertyValueFactory<projet,Date>("date_creation"));
        try {
            ObservableList<projet> list = pService.readAll();
            System.out.println(list);
            tabview.setItems(list);
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (SQLException ex) {
            Logger.getLogger(projetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void showActivite(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("activite.fxml"));
        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void supprimerProjet(MouseEvent event) {
        
        impProjet pService = new impProjet();
        projet a = tabview.getSelectionModel().getSelectedItem();
        System.out.println(a);
        
        try {
            pService.delete(a.getId());
            
        sujetPro.setCellValueFactory(new PropertyValueFactory<projet,String>("sujet"));
        descriptionPro.setCellValueFactory(new PropertyValueFactory<projet,String>("description"));
        nomPro.setCellValueFactory(new PropertyValueFactory<projet,String>("nom"));
        dateCPro.setCellValueFactory(new PropertyValueFactory<projet,Date>("date_creation"));
        try {
            ObservableList<projet> list = pService.readAll();
            System.out.println(list);
            tabview.setItems(list);
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        } catch (SQLException ex) {
        }
    }

    @FXML
    private void updateProjet(ActionEvent event) throws SQLException {
        
        impProjet pService = new impProjet();
        
       
        Date dateC = Date.valueOf(dateCreation.getValue());
        String nomp = nom.getText();
        String description = desc.getText();
        String sujetp = sujet.getText();
       //  projet p = new projet(nomp, sujetp, description, dateC);
        System.out.println(pj.getId());
         pj.setDate_creation(dateC);
         pj.setNom(nomp);
         pj.setDescription(description);
         pj.setSujet(sujetp);
         pService.update(pj);
         
         ObservableList<projet> list = pService.readAll();
            System.out.println(list);
            tabview.setItems(list);
        
        
        
    }

    private String LocalDate(java.util.Date date_creation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void BackToDashboard(ActionEvent event) throws IOException {
        
                 Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void chercherProjet(KeyEvent event) throws SQLException {
        
        impProjet pService = new impProjet();
           //     ObservableList <Utilisateurs> comformations =  FXCollections.observableArrayList();
                ObservableList<projet> list = FXCollections.observableArrayList();
          if (chercher.getText().isEmpty())
          {
              list = pService.readAll();
              tabview.setItems(list);
          }
          else
          {
               
                list =pService.chercherProjet(chercher.getText());
                  tabview.setItems(list);
          }
        
        
        
        
    }

    

    
    
    
}
