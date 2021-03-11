/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package participant;

import Entities.Participants;
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
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField number;
    @FXML
    private JFXComboBox<String> cbNiveau;
    @FXML
    private Button saveChanges;
    
   private  ServiceParticipant sp  = new ServiceParticipant();
        private Participants p = new Participants();
    @FXML
    private Label interesse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
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
    private void backToMainParticipant(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }
    
}
