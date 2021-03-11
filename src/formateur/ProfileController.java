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
import com.jfoenix.controls.JFXComboBox;
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

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class ProfileController implements Initializable {

    @FXML
    private Label fullName;
    @FXML
    private Label interesse;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField number;
    @FXML
    private Button saveChanges;
    @FXML
    private TextField specialite;
    
    private  ServiceFormateur sp  = new ServiceFormateur();
        private Formateurs p = new Formateurs();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         try {
            p = sp.getSelectedUserById(UserSession.getInstace("",0,"").getId());
        } catch (SQLException ex) {
           
        }
        
      
        fullName.setText(p.getNom()+" "+p.getPrenom());
        email.setText(p.getEmail());
        password.setText(p.getPassword());
        number.setText(p.getNum());
        
        specialite.setText(p.getSpecialite());
        
    }    

    @FXML
    private void backToMainParticipant(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {
        
        
        p.setEmail(email.getText());
        p.setPassword(password.getText());
        p.setNum(number.getText());
        p.setSpecialite(specialite.getText());
        System.out.println(p);
        sp.ModifierUtilisateur(p);
    }
    
}
