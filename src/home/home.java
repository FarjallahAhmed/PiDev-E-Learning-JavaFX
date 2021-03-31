/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class home implements Initializable {

    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnStudents;
    @FXML
    private Button btn_Timetable;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClasses;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonClicks(ActionEvent event) {
    }

    @FXML
    private void showGestionUtilisateurs(ActionEvent event) throws IOException {
        Stage stage = new Stage();
       // Parent root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml"));
       // Scene scene = new Scene(root,1100,600);
        //stage.setScene(scene);
        //stage.show();
        
        Parent root = FXMLLoader.load(getClass().getResource("Utilisateurs.fxml")); 
                 Scene scene = new Scene(root,1250,700);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }
    
    @FXML
    private void updateButtonClicks(ActionEvent event) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Front/reclamation.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
          //  stage.getIcons().add(new Image("/home/icons/icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/login/Main.fxml")); 
                 Scene scene = new Scene(root);
                 scene.setFill(Color.TRANSPARENT);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }
    
}
