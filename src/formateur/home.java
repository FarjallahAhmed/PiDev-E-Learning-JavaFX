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
<<<<<<< HEAD
import java.io.File;
=======
import home.fxml.PromotionsController;
>>>>>>> 212350c038dd9459e4316f83d88f1179ef1a2dfa
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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

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
    @FXML
<<<<<<< HEAD
    private Circle circle;
=======
    private Button btnPromo;
>>>>>>> 212350c038dd9459e4316f83d88f1179ef1a2dfa

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
        type.setText("Formateur");
        System.out.println(UserSession.getInstace("", 0, "").toString());
        
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
        
                  Parent root = FXMLLoader.load(getClass().getResource("/formateur/ProfileNew.fxml")); 
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

    @FXML
    private void showPromotion(ActionEvent event) {
        PromotionsController p = new PromotionsController();
            
            
                 //Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/formulaireform.fxml")); 
                 Scene scene;
        try {
            scene = new Scene(p.page());
            pidevfinal.PidevFinal.parentWindow.setScene(scene);
        } catch (SQLException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
    }
    
}
