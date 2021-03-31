/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package participant;

import Entities.Participants;
import Service.ServiceParticipant;
import UserSession.UserSession;
import java.io.File;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

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
    private ImageView avatar;
    @FXML
    private Circle circle;
    @FXML
    private HBox tst;

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
        
        if (p.getImage()==null)
              {
                  Image imProfile = new Image(getClass().getResourceAsStream("/media/avatar.png"));
                  circle.setFill(new ImagePattern(imProfile));
              }
      else 
      {
          File f = new File("C:\\Users\\Mehdi\\Desktop\\uploadProjet\\"+p.getImage());
           Image imProfile = new Image(f.toURI().toString());
           
           circle.setFill(new ImagePattern(imProfile));
           
         
      }
            
        
    }    

    @FXML
    private void handleButtonClicks(ActionEvent event) throws IOException {
        
                 Parent root = FXMLLoader.load(getClass().getResource("/participant/ProfileNew.fxml")); 
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
    private void chatbot(ActionEvent event) {
        
        Chatbot c = new Chatbot();
        Stage s = new Stage();
        c.start(s);
    }
    
}
