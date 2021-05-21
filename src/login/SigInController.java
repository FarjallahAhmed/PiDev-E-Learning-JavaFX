/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Entities.Formateurs;
import Entities.Participants;
import Entities.Utilisateurs;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import UserSession.UserSession;
import Utils.ScreenController;
import com.gn.Main;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class SigInController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private JFXComboBox<String> cbType;
    
    private Stage PrimaStage;
    
    private AnchorPane rootLayout;

    /**
     * Initializes the controller class.
     */
   
    
     private Alert alert = new Alert(Alert.AlertType.WARNING);
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        cbType.getItems().setAll("Participant","Formateur","Admin");
    }    

    @FXML
    private void entrer(ActionEvent event) throws SQLException, IOException {
 
        if (cbType.getValue()=="Participant")
        {
            
             Participants P = new Participants();
             ServiceParticipant sp = new ServiceParticipant();
             
             P = sp.getSelectedUserByEmail(email.getText());
            
             if (password.getText().equals(P.getPassword()))
             {
                 System.out.println("Existe");
                 UserSession.getInstace(P.getLogin(),P.getId(),"Participant");
                 
                 
                 //Stage stage = new Stage();
                 Parent root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml")); 
                 
                 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
                 
                 
                 //stage.setScene(scene);
                 //stage.show();
                 
                 
                 
                 
             }
             else 
                 System.out.println("Verifier Information"); 
            
        }
        else if(cbType.getValue()=="Formateur")
            {
                 Formateurs P = new Formateurs();
                 ServiceFormateur sp = new ServiceFormateur();
             
                 P = sp.getSelectedUserByEmail(email.getText());
            
             if (password.getText().equals(P.getPassword()))
             {
                 System.out.println("Existe");
                 
                 
                 if (P.getEtat()==true)
                 {
                     
                 UserSession.getInstace(P.getLogin(),P.getId(),"Formateur");
                 
                 
                 Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
                 }
                 else
                 {
                        alert.setTitle("Warning");
                        alert.setHeaderText("Invalid Account !");
                        alert.setContentText("Your Account Is Not Activated .");

                        alert.showAndWait();
                 }
                 
                 
                 
                 
             }
             else 
                 System.out.println("Verifier Information"); 
            
                
            }
        
            else if (cbType.getValue()=="Admin")
            {
                    if ((email.getText().equals("admin@admin.tn"))&&(password.getText().equals("12345678")))
                    {
                        UserSession.getInstace("Admin",0,"Admin");
                        System.out.println("Admin");
                        Parent root = FXMLLoader.load(getClass().getResource("/home/Home.fxml")); 
                        Scene scene = new Scene(root);
                        pidevfinal.PidevFinal.parentWindow.setScene(scene);
                    }
            }
          
        
        
    }

    @FXML
    private void changePassword(ActionEvent event) throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/login/forgetPassword.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
          stage.show();
        
        
    }
    
    
                
}
    
    
      

    

