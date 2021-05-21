/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.Activite;
import UserSession.UserSession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author NMEDIA
 */
public class activiteController {

    @FXML
    private TextField nom;
    @FXML
    private TextField idsupp;
    
    public static Activite a;
    @FXML
    private TextField resp;
    @FXML
    private TextField periode;
    
    
  

    @FXML
    private void onajouterActivite(ActionEvent event) {
        
        String periodeA = periode.getText();
        String nomA = nom.getText();
        String respA = resp.getText();
        
        Activite a1 = new Activite(respA, periodeA, nomA);
        
        a=a1;
        
        System.out.println(a+"activiter static");
        
        
        
        
    }
    
}
