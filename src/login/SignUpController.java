/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Entities.Formateurs;
import Entities.Participants;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import UserSession.UserSession;
import Utils.Alerts;
import Utils.Mask;
import static Utils.validate_code_source.isAddressValid;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import Utils.Alerts;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import static participant.ProfileNewController.imageAbsolute;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField cin;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField number;
    @FXML
    private TextField email;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<String> niveau;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private TextField interesse;
    @FXML
    private HBox vBoxParticipant;
    @FXML
    private HBox vBoxFormateur;
    @FXML
    private TextField specialite;
    @FXML
    private Label path;
    
    public static String emailGlobal = null;
    
    
    public static String pathAbsolute = null;
    
    public static boolean confirmationCodeParticipant = false;
    
   private Alert alert = new Alert(AlertType.WARNING);
   private Alert alertI = new Alert(AlertType.INFORMATION);

    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        // TODO
        
        type.getItems().addAll("Participant","Formateur","Organisme");
        niveau.getItems().addAll("Etudiant","Eleve");
        vBoxParticipant.setVisible(false);
        vBoxFormateur.setVisible(false);
        path.setVisible(false);
        
        
        //Controle De Saisie
        Mask.noSymbolsAndNumbers(nom);
        Mask.noSymbolsAndNumbers(prenom);
        Mask.noSymbols(username);
        Mask.noLetters(number);
        Mask.noLetters(cin);
        //Mask.isEmail(email);
 
        
        
        
    }    

    @FXML
    private void add(ActionEvent event) throws IOException {
        
        Participants p = new Participants();
        Formateurs f = new Formateurs();
        ServiceParticipant sp = new ServiceParticipant();
         ServiceFormateur spF = new ServiceFormateur();
        //    Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
       // Alert alertE = new Alert(Alert.AlertType.ERROR);
        
        
        
        
        
         
         if (type.getSelectionModel().isEmpty())
         {
             System.out.println("Selectionner un type de compte");
         }
         else
             if (type.getValue()=="Participant")
             {
                 
                  if (!((nom.getText().isEmpty())||(prenom.getText().isEmpty())||(date.getValue()==null)||(cin.getText().isEmpty())
                           ||(email.getText().isEmpty())||(username.getText().isEmpty())||(password.getText().isEmpty())||(number.getText().isEmpty())
                           ||(niveau.getSelectionModel().isEmpty())||(interesse.getText().isEmpty())))
                  {
                 
                      
                      if( isAddressValid(email.getText()))
                      {
                        p.setNom(nom.getText());
                        p.setPrenom(prenom.getText());
                        p.setDateNaissance(Date.valueOf(date.getValue()));
                        p.setCin(cin.getText());
                        p.setEmail(email.getText());
                        p.setLogin(username.getText());
                        p.setPassword(password.getText());
                        p.setNum(number.getText());       
                        p.setNiveauEtude(niveau.getValue());
                        p.setCertificatsObtenus(0);
                        p.setInterssePar(interesse.getText());
                        p.setNombreDeFormation(0);
                        emailGlobal = email.getText();
                        Parent root = FXMLLoader.load(getClass().getResource("/login/confirmationCode.fxml"));
                        Scene scene = new Scene(root);
                        Stage code = new Stage();
                        code.setScene(scene);
                        code.initStyle(StageStyle.TRANSPARENT);
                        code.setScene(scene);
                        code.showAndWait();
                        
                          System.out.println(confirmationCodeParticipant);
                        
                        if (confirmationCodeParticipant==true)
                        {
                             sp.AjouterUtilisateur(p);
                             alertI.setTitle("Information Dialog");
                             alertI.setHeaderText(null);
                             alertI.setContentText("Prticipant Added!");
                             alertI.showAndWait();
                        }
                            
                        
                      }
                      else 
                      {
                        alert.setTitle("Warning");
                        alert.setHeaderText("Invalid Email !");
                        alert.setContentText("Your Email Is Invalid.");

                        alert.showAndWait();
                      }
                  }
                  else
                  {
                      //ALERT
                       //Alerts.info("Info", "Lorem ipsum dolor color.");
                        alert.setTitle("Warning");
                        alert.setHeaderText("Invalid Information !");
                        alert.setContentText("Your Information Are Invalid.");

                        alert.showAndWait();
                      
                      
                    
                  }
                   
                   
             }
             else if (type.getValue()=="Formateur")
             {
                 
                 if (!((nom.getText().isEmpty())||(prenom.getText().isEmpty())||(date.getValue()==null)||(cin.getText().isEmpty())
                           ||(email.getText().isEmpty())||(username.getText().isEmpty())||(password.getText().isEmpty())||(number.getText().isEmpty())
                           ||(specialite.getText().isEmpty())||(path.getText().isEmpty())))
                  {
                 
                      
                      if( isAddressValid(email.getText()))
                      {
                          
                          f.setNom(nom.getText());
                          f.setPrenom(prenom.getText());
                          f.setDateNaissance(Date.valueOf(date.getValue()));
                          f.setCin(cin.getText());
                          f.setEmail(email.getText());
                          f.setLogin(username.getText());
                          f.setPassword(password.getText());
                          f.setNum(number.getText());       


                          f.setSpecialite(specialite.getText());
                          f.setJustificatif(path.getText());

                          spF.AjouterUtilisateur(f);
                        
                             
                             
                             
                                alertI.setTitle("Information Dialog");
                                alertI.setHeaderText(null);
                                alertI.setContentText("Formateur Added!");

                                alertI.showAndWait();
                      }
                      else 
                      {
                        alert.setTitle("Warning");
                        alert.setHeaderText("Invalid Email !");
                        alert.setContentText("Your Email Is Invalid.");

                        alert.showAndWait();
                      }
                  }
                  else
                  {
                      //ALERT
                       //Alerts.info("Info", "Lorem ipsum dolor color.");
                        alert.setTitle("Warning");
                        alert.setHeaderText("Invalid Information !");
                        alert.setContentText("Your Information Are Invalid.");

                        alert.showAndWait();
                      
                      
                    
                  }
                
             }
         /*
          f.setNom(nom.getText());
                 f.setPrenom(prenom.getText());
                   f.setDateNaissance(Date.valueOf(date.getValue()));
                   f.setCin(cin.getText());
                   f.setEmail(email.getText());
                   f.setLogin(username.getText());
                   f.setPassword(password.getText());
                   f.setNum(number.getText());       
                   

                   f.setSpecialite(specialite.getText());
                   f.setJustificatif(path.getText());
                   
                 spF.AjouterUtilisateur(f);
             
         */
        
        
    }

    @FXML
    private void choose(MouseEvent event) {
        
       
    }


   

    @FXML
    private void chooseTypeAccount(ActionEvent event) {
        
            System.out.println("ture");
        if (type.getValue()=="Participant")
        {
            vBoxParticipant.setVisible(true);
            vBoxFormateur.setVisible(false);
            
        }
        else if (type.getValue()=="Formateur")
        {
            vBoxParticipant.setVisible(false);
            
            vBoxFormateur.setVisible(true);
            
            
        }
        
    }

    @FXML
    private void getPath(ActionEvent event) {
        
        ServiceParticipant sp = new ServiceParticipant();
        FileChooser fil_chooser = new FileChooser();
        
        File file = fil_chooser.showOpenDialog(null); 
  
                if (file != null) { 
                                     
                    path.setText(file.getName());

                    pathAbsolute = file.getAbsolutePath();
                    
                    sp.uploadtp(path.getText(),pathAbsolute);
                    
                    
                                         
                } 
    }
    
    
}
