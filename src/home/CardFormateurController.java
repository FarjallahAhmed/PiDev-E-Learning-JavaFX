/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import Entities.Formateurs;
import Entities.Participants;
import Entities.Utilisateurs;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import static home.CardController.p;
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
import javafx.scene.layout.Border;
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
public class CardFormateurController implements Initializable {

    @FXML
    private HBox hbox;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
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
    private AnchorPane anachorCard;
    @FXML
    private JFXButton activer;
    @FXML
    private Button desactiver;
    
    private Parent root;
    
     public static Formateurs emailD = new Formateurs();
    
    public static Utilisateurs  F = new Utilisateurs();
    @FXML
    private Circle circle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    
     public void setData(String Nom,String Prenom,String Email,int id,Date date,Boolean etat,String image)
    {
        nom.setText(Nom);
        prenom.setText(Prenom);
        email.setText(Email);
        supprimer.setId(String.valueOf(id));
        bdate.setText(String.valueOf(date));
       view.setId(String.valueOf(id));
        
        if (etat == true)
        {
            anachorCard.setStyle("-fx-border-color: green; -fx-border-width: 5px;");
            activer.setVisible(false);
            desactiver.setVisible(true);
           
        }
        else
        {
            anachorCard.setStyle("-fx-border-color: red; -fx-border-width: 5px;");

            activer.setVisible(true);
            desactiver.setVisible(false);
        }
        
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
           */
          File f = new File("C:\\Users\\Mehdi\\Desktop\\uploadProjet\\"+image);
           Image imProfile = new Image(f.toURI().toString());
           
           circle.setFill(new ImagePattern(imProfile));
         
      }
        
    }

    @FXML
    private void supprimerFormateur(ActionEvent event) {
        
        ServiceFormateur sp = new ServiceFormateur();
         sp.SupprimerUtilisateur(Integer.parseInt(supprimer.getId()));
         
          Notifications n =Notifications.create().title("SUCCESS").text("Participant Supprimé!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
         hbox.getChildren().clear();
         hbox.setMaxSize(0, 0);
         hbox.setMinSize(0, 0);
    }

    @FXML
    private void activerCompte(ActionEvent event) throws SQLException {
        
        ServiceFormateur sp = new ServiceFormateur();
        sp.ActiverFormateurAccount(Integer.parseInt(supprimer.getId()));
        
        Notifications n =Notifications.create().title("SUCCESS").text("Activation avec succes!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
         anachorCard.setStyle("-fx-border-color: green; -fx-border-width: 5px;");
         
         activer.setVisible(false);
            desactiver.setVisible(true);
         
    }

    @FXML
    private void desactiver(ActionEvent event) throws SQLException {
        
        ServiceFormateur sp = new ServiceFormateur();
        sp.DesactiverFormateurAccount(Integer.parseInt(supprimer.getId()));
        
        Notifications n =Notifications.create().title("SUCCESS").text("Desactivation avec succes!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
       anachorCard.setStyle("-fx-border-color: red; -fx-border-width: 5px;");
       
       activer.setVisible(true);
            desactiver.setVisible(false);
        
        
    }

    @FXML
    private void contacter(ActionEvent event) throws SQLException, IOException {
        
        
          
       
        ServiceFormateur sp = new ServiceFormateur();
        F = sp.getFormateursUtilisateurs(Integer.parseInt(supprimer.getId()));
        
        System.out.println();
        root =  FXMLLoader.load(getClass().getResource("/home/mailFormateur.fxml"));
         Stage stage = new Stage();
                                Scene scene = new Scene(root);
                                scene.setFill(Color.TRANSPARENT);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.TRANSPARENT);
                                stage.setScene(scene);
                                stage.show();
        
        
        
    }

    @FXML
    private void viewFile(ActionEvent event) throws SQLException {
        
        
        ServiceParticipant sp = new ServiceParticipant();
        ServiceFormateur spF = new ServiceFormateur();
        
       Formateurs F = spF.getSelectedUserById(Integer.parseInt(view.getId()));
        System.out.println(F);
        System.out.println(view.getId());
        System.out.println(F.getJustificatif()); 
        sp.donwload_ftp(F.getJustificatif());
        
    }
    
    
    
    
}
