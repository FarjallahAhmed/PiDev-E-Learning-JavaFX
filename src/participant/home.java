/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package participant;

import Entities.Participants;
import Front.ListeFormationsController;
import Service.ServiceParticipant;
import UserSession.UserSession;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class home implements Initializable {

    @FXML
    private Button btnDashboard;
    @FXML
    private Button btn_Timetable;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClasses;
    @FXML
    private Label name;
    @FXML
    private Label type;
    @FXML
    private FontAwesomeIconView btnjeu;
    private static Stage primaryStageObj;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // UserSession.getInstace(userName, 0, type);
        System.out.println(UserSession.getInstace("",0,"").getId());
        
        
        ServiceParticipant sp  = new ServiceParticipant();
        Participants p = new Participants();
        
        try {
            p = sp.getParticipants(UserSession.getInstace("",0,"").getId());
        } catch (SQLException ex) {
           
        }
        System.out.println(p);
        name.setText(p.getNom()+" "+p.getPrenom());
        type.setText(p.getNiveauEtude());
        
        
        
        
        
        
    }    

    @FXML
    private void handleButtonClicks(ActionEvent event) throws IOException {
        
                 Parent root = FXMLLoader.load(getClass().getResource("/participant/profile.fxml")); 
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
    private void afficherFormation(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/Front/ListeFormations.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
                 
                
        
       
         
        
    }

    @FXML
    private void AfficherPanier(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/Front/Panier.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void gotogame(ActionEvent event) throws IOException {
                   
               ;
                    Stage stage =new Stage();
   Parent root = FXMLLoader.load(getClass().getResource("/Style/MainView.fxml")); 
    Scene scene = new Scene(root);
         stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			
        stage.setScene(scene);
        stage.show();
    }
 
    
}
