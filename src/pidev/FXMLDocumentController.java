/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Mehdi
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private ComboBox cbSelect;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbSelect.getItems().addAll("Participant", "Formateur", "Organisme");
    }    

    @FXML
    private void showInterface(ActionEvent event) throws IOException {
        
        
        if (cbSelect.getValue()=="Participant")
            
        {
           
             System.out.println("True");
             Parent  r = FXMLLoader.load(getClass().getResource("FormParticipant.fxml"));
             Scene s = new Scene(r);
             Stage x = new Stage();
             x.setScene(s);
             x.show();
        }
         
        if (cbSelect.getValue()=="Formateur")
            
        {
           
             System.out.println("True");
             Parent  r = FXMLLoader.load(getClass().getResource("FormFormateur.fxml"));
             Scene s = new Scene(r);
             Stage x = new Stage();
             x.setScene(s);
             x.show();
        }
        
        
      
    }
    
    
    
}
