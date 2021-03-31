
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import Entities.Participants;
import Entities.Utilisateurs;
import Service.ServiceParticipant;
import com.gn.Main;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class CardController implements Initializable {

    @FXML
    private Label nom;
    @FXML
    private Label email;
    @FXML
    private Label type;
    @FXML
    private Label bdate;
    @FXML
    private Rating rating;
    @FXML
    private JFXButton contacter;
    @FXML
    private JFXButton view;
    @FXML
    private Button supprimer;
    @FXML
    private Label prenom;
    @FXML
    private HBox hbox;
    @FXML
    private AnchorPane anachorCard;
    
    private Parent root;
    
    public static Participants emailD = new Participants();
    
    public static Utilisateurs p = new Utilisateurs();
    @FXML
    private Circle circle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
    
    public void setData(String Nom,String Prenom,String Email,int id,Date date,String niveau,String image)
    {
        nom.setText(Nom);
        prenom.setText(Prenom);
        email.setText(Email);
        supprimer.setId(String.valueOf(id));
        bdate.setText(String.valueOf(date));
        type.setText(niveau);
        anachorCard.setStyle("-fx-border-color: black");
       
        if (image==null)
              {
                  Image imProfile = new Image(getClass().getResourceAsStream("/media/avatar.png"));
                  circle.setFill(new ImagePattern(imProfile));
                  
              }
         else 
         {
           /*
             File f = new File(p.getImage());
             Image imProfile = new Image(f.toURI().toString());
             circle.setFill(new ImagePattern(imProfile));
             */ File f = new File("C:\\Users\\Mehdi\\Desktop\\uploadProjet\\"+image);
           Image imProfile = new Image(f.toURI().toString());
           
           circle.setFill(new ImagePattern(imProfile));
        }
    }

    @FXML
    private void supprimerParticipant(ActionEvent event) {
        
         ServiceParticipant sp = new ServiceParticipant();
         sp.SupprimerUtilisateur(Integer.parseInt(supprimer.getId()));
         
          Notifications n =Notifications.create().title("SUCCESS").text("Participant Supprimé!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
         hbox.getChildren().clear();
         hbox.setMaxSize(0, 0);
         hbox.setMinSize(0, 0);
        
    }

    @FXML
    private void contacter(ActionEvent event) throws SQLException, IOException {
       
        
        
         
       
        ServiceParticipant sp = new ServiceParticipant();
        p = sp.getParticipantsUtilisateurs(Integer.parseInt(supprimer.getId()));
        
       
        root =  FXMLLoader.load(getClass().getResource("/home/mail.fxml"));
         Stage stage = new Stage();
                                Scene scene = new Scene(root);
                                scene.setFill(Color.TRANSPARENT);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.TRANSPARENT);
                                stage.setScene(scene);
                                stage.show();
        
        
    }

    @FXML
    private void showProfile(ActionEvent event) {
        
        
        
        
        
        
        
    }
    
    
    
    
    
    
    
    
}
