/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package participant;

import Entities.Participants;
import Service.ServiceParticipant;
import UserSession.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import static home.CardController.p;
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class ProfileNewController implements Initializable {

    @FXML
    private Label type;
    @FXML
    private Label num;
    @FXML
    private Label bDate;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField number;
    @FXML
    private JFXComboBox<String> cbNiveau;
    @FXML
    private JFXCheckBox seePassword;
    @FXML
    private Label interesse;
    
    private  ServiceParticipant sp  = new ServiceParticipant();
        private Participants p = new Participants();
    @FXML
    private Label fullName;
    @FXML
    private JFXButton save;
    @FXML
    private ImageView avatar;
    @FXML
    private Button uploadIm;
    @FXML
    private TextField image;
    
    public static String imageAbsolute = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        try {
            p = sp.getParticipants(UserSession.getInstace("",0,"").getId());
        } catch (SQLException ex) {
           
        }
        cbNiveau.getItems().addAll("Eleve","Etudiant");
        System.out.println(p);
        fullName.setText(p.getNom()+" "+p.getPrenom());
        email.setText(p.getEmail());
        password.setText(p.getPassword());
        number.setText(p.getNum());
        cbNiveau.setValue(p.getNiveauEtude());
        interesse.setText(p.getInterssePar());
        type.setText(p.getNiveauEtude());
        num.setText(p.getNum());
        bDate.setText(p.getDateNaissance().toString());
        
        System.out.println(p.getImage());
        
        
       // File file = new File("/media/avatar.png");
        //Image image = new Image(file.toURI().toString());
        
      //  Image imProfile = new Image(getClass().getResourceAsStream("C:\\Utilisateurs\\Mehdi\\Bureau\\user.png"));
      if (p.getImage()==null)
              {
                  Image imProfile = new Image(getClass().getResourceAsStream("/media/avatar.png"));
                  avatar.setImage(imProfile);
              }
      else 
      {
          
          File f = new File("C:\\Users\\Mehdi\\Desktop\\uploadProjet\\"+p.getImage());
           Image imProfile = new Image(f.toURI().toString());
           
      //     System.out.println();
          avatar.setImage(imProfile);
      }
      
      
      uploadIm.setStyle("-fx-background-color: transparent;");
      
      image.setVisible(false);
      
      
        
        
        
        
        
        
       // avatar.setImage();
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
    private void see(ActionEvent event) {
        
      
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        
        p.setEmail(email.getText());
        p.setPassword(password.getText());
        p.setNum(number.getText());
        p.setNiveauEtude(cbNiveau.getValue());
        
        sp.ModifierUtilisateur(p);
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void upload(ActionEvent event) throws SQLException {
        
          FileChooser fil_chooser = new FileChooser();
        
        File file = fil_chooser.showOpenDialog(null); 
  
                if (file != null) { 
                                     
                    image.setText(file.getName());
                    
                    sp.setImageUser(image.getText(),UserSession.getInstace("",0,"").getId());
                    imageAbsolute = file.getAbsolutePath();
                    
                    sp.uploadtp(image.getText(),imageAbsolute);

                    System.out.println(image.getText());
                    
                       File f = new File("C:\\Users\\Mehdi\\Desktop\\uploadProjet\\"+p.getImage());
                      Image imProfile = new Image(f.toURI().toString());
           
      //     System.out.println();
                     avatar.setImage(imProfile);
                                         
                } 
                
              
                
    }
        
        
       
    
    
}
