/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formateur;

import Entities.Formateurs;
import Entities.Participants;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import UserSession.UserSession;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class home implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label type;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btn_Timetable;
    @FXML
    private Button btnClasses;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
            System.out.println(UserSession.getInstace("",0,"").getId());
        
        
        ServiceFormateur sp  = new ServiceFormateur();
        Formateurs p = new Formateurs();
        
        try {
            p = sp.getSelectedUserById(UserSession.getInstace("",0,"").getId());
        } catch (SQLException ex) {
           
        }
        System.out.println(p);
        name.setText(p.getNom()+" "+p.getPrenom());
        type.setText("Formateurs");
        System.out.println(UserSession.getInstace("", 0, "").toString());
    }    

    @FXML
    private void handleButtonClicks(ActionEvent event) throws IOException {
        
                  Parent root = FXMLLoader.load(getClass().getResource("/formateur/profile.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }


    @FXML
    private void logOut(ActionEvent event) throws IOException {
        
        UserSession.cleanUserSession();
        
        Parent root = FXMLLoader.load(getClass().getResource("/login/Main.fxml")); 
                 Scene scene = new Scene(root);
                 scene.setFill(Color.TRANSPARENT);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void showGestionFormation(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/Ajout_formation.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        
        
    }

    @FXML
    private void gestionEvenement(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/academiccalendar/ui/main/FXMLDocument.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void showGestionProjet(ActionEvent event) throws IOException {
        
        
                 Parent root = FXMLLoader.load(getClass().getResource("/projet/formulaireform.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        
        
        
    }
    
}
