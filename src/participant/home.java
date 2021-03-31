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
<<<<<<< HEAD
import java.io.File;
=======
<<<<<<< HEAD
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
=======
import home.fxml.PromotionsController;
>>>>>>> 5dbda202efa6d7fa28fa53ec0790d110804f8456
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
<<<<<<< HEAD
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
=======
import javafx.scene.layout.VBox;
>>>>>>> 212350c038dd9459e4316f83d88f1179ef1a2dfa
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    private Label name;
    @FXML
    private Label type;
    @FXML
<<<<<<< HEAD
    private ImageView avatar;
    @FXML
    private Circle circle;
    @FXML
    private HBox tst;
=======
<<<<<<< HEAD
    private FontAwesomeIconView btnjeu;
    private static Stage primaryStageObj;
=======
    private Button btnPro;
>>>>>>> 212350c038dd9459e4316f83d88f1179ef1a2dfa

>>>>>>> 5dbda202efa6d7fa28fa53ec0790d110804f8456
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
<<<<<<< HEAD
    private void chatbot(ActionEvent event) {
        
        Chatbot c = new Chatbot();
        Stage s = new Stage();
        c.start(s);
    }
=======
<<<<<<< HEAD
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
 
=======
    private void showPromotions(ActionEvent event) {
          PromotionsController p = new PromotionsController();
            
            
                 //Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/formulaireform.fxml")); 
                 Scene scene;
        try {
            scene = new Scene(p.page());
            pidevfinal.PidevFinal.parentWindow.setScene(scene);
        } catch (SQLException ex) {
            Logger.getLogger(formateur.home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
>>>>>>> 5dbda202efa6d7fa28fa53ec0790d110804f8456
>>>>>>> 212350c038dd9459e4316f83d88f1179ef1a2dfa
    
}
