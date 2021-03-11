/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import Entities.Participants;
import Entities.Utilisateurs;
import Service.ServiceParticipant;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class password implements Initializable {

    @FXML
    private TextField emailUpdate;
    @FXML
    private JFXPasswordField passwordUpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void updateAccount(ActionEvent event) throws SQLException {
        
        Utilisateurs P = new Utilisateurs();
        ServiceParticipant sp = new ServiceParticipant();
        
        if (emailUpdate.getText().isEmpty())
        {
                System.out.println("Remplir Email");
        }
        else 
        {
            P=sp.getSelectedUsersByEmail(emailUpdate.getText());
             if (P==null)
                {
                       System.out.println("Email n'existe Pas");
                }
                else
                {
                    if (passwordUpdate.getText().isEmpty())
                    {
                        System.out.println("Remplir Password");
                    }
                    else
                    {
                        //Travail
                        
                        System.out.println(P);
                        P.setPassword(passwordUpdate.getText());
                        sp.ModifierUtilisateurOnly(P);
                        
                        //Platform.exit();
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                        
                    }
                }
        }
        
        
    }

    
}
