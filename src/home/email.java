/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import Service.ServiceParticipant;
import com.jfoenix.controls.JFXTextArea;
import static home.CardController.p;
import static home.utilisateurs.emailD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class email implements Initializable {

    @FXML
    private Button btnsupp;
    @FXML
    private Button btnajouter;
    @FXML
    private TextField email;
    @FXML
    private TextField objet;
    @FXML
    private JFXTextArea contenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("++++++++"+emailD);
        
        
            email.setText(p.getEmail());
        
        
        
       // email.setEditable(false);
        
    }    

    @FXML
    private void Supprimer_btn(ActionEvent event) {
        
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void onpasser(ActionEvent event) {
        ServiceParticipant sp = new ServiceParticipant();
        
        if (sp.sendMail(email.getText(), objet.getText(),contenu.getText()))
        {
            Notifications n =Notifications.create().title("SUCCESS").text("Email Sent!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
        }
        else
        {
            
        }
        
        
    }
    
}
