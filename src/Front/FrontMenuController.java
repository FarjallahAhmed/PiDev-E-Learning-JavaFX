/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class FrontMenuController implements Initializable {

    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnStudents;
    @FXML
    private Button btn_Timetable;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnClasses;
    @FXML
    private FontAwesomeIconView panierbutton;
    @FXML
    private Button btnpanier;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
  private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnDashboard) {
            loadStage("/home/fxml/Dashboard.fxml");
        } else if (mouseEvent.getSource() == btnStudents) {
            loadStage("/home/fxml/Students.fxml");
        } else if (mouseEvent.getSource() == btn_Timetable) {
            loadStage("/home/fxml/Timetable.fxml");
        }
        else if (mouseEvent.getSource() == btnUpdate) {
            loadStage("/Front/ListeFormations.fxml");
        }
         else if (mouseEvent.getSource() == btnpanier) {
            loadStage("/Front/Panier.fxml");
        }
    }
  private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/home/icons/icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
