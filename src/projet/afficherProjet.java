/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.projet;
import Service.impProjet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author NMEDIA
 */
public class afficherProjet implements Initializable {

    @FXML
    private HBox anProjet;
    private HBox haffiche = new HBox();
    @FXML
    private TextField rechercher;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         impProjet pService = new impProjet();
        
         
          ObservableList<projet> list = FXCollections.observableArrayList();
         
         
        try {
            list = pService.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(cardProjet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(list.size());
        
       
      for (int i=0;i<list.size();i++)
      {
          
          
         FXMLLoader fxmlloader = new FXMLLoader();
         fxmlloader.setLocation(getClass().getResource("Card.fxml"));
             try {
               
           HBox hh = fxmlloader.load();
          
          cardProjet cc = fxmlloader.getController();
          cc.setData(list.get(i).getNom(),list.get(i).getDescription(),list.get(i).getSujet(),list.get(i).getDate_creation(),list.get(i).getId());
          
        anProjet.getChildren().add(hh); 
           
             } catch (IOException ex) {
                 
             }
                
           
      }
        
     
      
      
    
    }    

    
    @FXML
    private void logOut(ActionEvent event) {
    
        
    }
    public  void refresh(){
        impProjet pService = new impProjet();
         Pagination pagination = new Pagination(4,0);
         
          ObservableList<projet> list = FXCollections.observableArrayList();
         
         
        try {
            list = pService.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(cardProjet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(list.size());
        
       
      for (int i=0;i<list.size();i++)
      {
          
          
         FXMLLoader fxmlloader = new FXMLLoader();
         fxmlloader.setLocation(getClass().getResource("Card.fxml"));
             try {
               
           HBox hh = fxmlloader.load();
          
          cardProjet cc = fxmlloader.getController();
          cc.setData(list.get(i).getNom(),list.get(i).getDescription(),list.get(i).getSujet(),list.get(i).getDate_creation(),list.get(i).getId());
          
          haffiche.getChildren().add(hh);
           
             } catch (IOException ex) {
                 
             }
                
           
      }
        
     
      anProjet.getChildren().add(pagination); 
    }

    @FXML
    private void ajouterProjet(ActionEvent event) throws IOException {
        
        
        
                Parent root = FXMLLoader.load(getClass().getResource("/projet/formulaireform.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void recherecherProjet(KeyEvent event) {
        
        if (rechercher.getText().isEmpty())
        {
            anProjet.getChildren().clear();
             impProjet pService = new impProjet();
        
         
          ObservableList<projet> list = FXCollections.observableArrayList();
         
         
        try {
            list = pService.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(cardProjet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(list.size());
        
       
      for (int i=0;i<list.size();i++)
      {
          
          
         FXMLLoader fxmlloader = new FXMLLoader();
         fxmlloader.setLocation(getClass().getResource("Card.fxml"));
             try {
               
           HBox hh = fxmlloader.load();
          
          cardProjet cc = fxmlloader.getController();
          cc.setData(list.get(i).getNom(),list.get(i).getDescription(),list.get(i).getSujet(),list.get(i).getDate_creation(),list.get(i).getId());
          
        anProjet.getChildren().add(hh); 
           
             } catch (IOException ex) {
                 
             }
                
           
      }
        }
        
        
        else 
        {
            anProjet.getChildren().clear();
             impProjet pService = new impProjet();
        
         
          ObservableList<projet> list = FXCollections.observableArrayList();
         
         
        try {
            list = pService.chercherProjet(rechercher.getText());
        } catch (SQLException ex) {
            Logger.getLogger(cardProjet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(list.size());
        
       
      for (int i=0;i<list.size();i++)
      {
          
          
         FXMLLoader fxmlloader = new FXMLLoader();
         fxmlloader.setLocation(getClass().getResource("Card.fxml"));
             try {
               
           HBox hh = fxmlloader.load();
          
          cardProjet cc = fxmlloader.getController();
          cc.setData(list.get(i).getNom(),list.get(i).getDescription(),list.get(i).getSujet(),list.get(i).getDate_creation(),list.get(i).getId());
          
        anProjet.getChildren().add(hh); 
           
             } catch (IOException ex) {
                 
             }
                
           
      }
            
            
            
        }
        
        
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        
           Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        
    }
    

    
}
