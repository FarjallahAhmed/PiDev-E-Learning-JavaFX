/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.Activite;
import Utils.Mask;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;

/**
 *
 * @author NMEDIA
 */
public class activiteController implements Initializable{

    @FXML
    private TextField nom;
    @FXML
    private TextField idsupp;
    
    public static Activite a;
    @FXML
    private TextField resp;
    @FXML
    private TextField periode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Mask.noSymbolsAndNumbers(resp);
       Mask.noLetters(periode);
       Mask.noSymbolsAndNumbers(nom);
       
        
       
    }

    @FXML
    private void onajouterActivite(ActionEvent event) {
        
        String periodeA = periode.getText();
        String nomA = nom.getText();
        String respA = resp.getText();
        
        Activite a1 = new Activite(respA, periodeA, nomA);
        
        a=a1;
        
        System.out.println(a+"activiter static");
        ((Node)(event.getSource())).getScene().getWindow().hide();
        
         Notifications n =Notifications.create().title("SUCCESS").text("Activite Ajoutée!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
        
        
        
        
    }
    
}
