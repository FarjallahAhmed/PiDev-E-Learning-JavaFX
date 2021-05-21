/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.projet;
import Service.impProjet;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author NMEDIA
 */
public class cardProjet implements Initializable {

    @FXML
    private HBox hbox;
    @FXML
    private AnchorPane anachorCard;
    @FXML
    private Label nom;
    @FXML
    private Rating rating;
    @FXML
    private Button supprimer;
    @FXML
    private Label sujet;
    @FXML
    private Label description;
    @FXML
    private Label dateC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        
        
    }    


    
    
    void setData(String nom1,String desc,String sujet1,Date datec,int id)
    {
        nom.setText(nom1);
        description.setText(desc);
        sujet.setText(sujet1);
        dateC.setText(String.valueOf(datec));
        supprimer.setId(String.valueOf(id));
    }

    @FXML
    private void supprimerProjet(ActionEvent event) throws SQLException {
        
        System.out.println("test");
          impProjet pService = new impProjet();
          pService.delete(Integer.parseInt(supprimer.getId()));
          afficherProjet a = new afficherProjet();
         hbox.getChildren().clear();
    }
    
}
