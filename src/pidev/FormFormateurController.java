/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import Etities.Formateurs;
import Etities.Participants;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class FormFormateurController extends PIDEV implements Initializable  {

    @FXML
    private GridPane gridAffiche;
    @FXML
    private TextField tfIdSelected;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private DatePicker dateDeNaissance;
    @FXML
    private TextField tfCin;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNum;
    @FXML
    private Button confirmer;
    @FXML
    private Button annuler;
    @FXML
    private TableView<?> tableAffiche;
    @FXML
    private TableColumn<?, ?> cId;
    @FXML
    private TableColumn<?, ?> cNom;
    @FXML
    private TableColumn<?, ?> cPrenom;
    @FXML
    private TableColumn<?, ?> cDate;
    @FXML
    private TableColumn<?, ?> cCin;
    @FXML
    private TableColumn<?, ?> cEmail;
    @FXML
    private TableColumn<?, ?> cLogin;
    @FXML
    private TableColumn<?, ?> cPwd;
    @FXML
    private TableColumn<?, ?> cNum;
    @FXML
    private TableColumn<?, ?> cNiveau;
    @FXML
    private TableColumn<?, ?> cCertificat;
    @FXML
    private TableColumn<?, ?> cInteresse;
    @FXML
    private TableColumn<?, ?> cNombre;
    @FXML
    private Button bDelete;
    @FXML
    private Button add;
    @FXML
    private Button afficher;
    
    final FileChooser fileChooser = new FileChooser();
    @FXML
    private Button uCv;
    @FXML
    private VBox stage;
    @FXML
    private Label labelupload;
    @FXML
    private Button uPf;
    @FXML
    private Button uJ;
    @FXML
    private Label labelPf;
    @FXML
    private Label labelJ;
    @FXML
    private TextField tfspecialite;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       final FileChooser fileChooser = new FileChooser();
    }    

    @FXML
    private void confirmerModification(ActionEvent event) {
    }

    @FXML
    private void supprimerParticipant(ActionEvent event) {
    }

    @FXML
    private void ajouterParticipant(ActionEvent event) {
        
        Formateurs p = new Formateurs();
        ServiceFormateur sp = new ServiceFormateur();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        p.setNom(tfNom.getText());
        p.setPrenom(tfPrenom.getText());
        p.setDateNaissance(Date.valueOf(dateDeNaissance.getValue()));
        p.setCin(tfCin.getText());
        p.setEmail(tfEmail.getText());
        p.setLogin(tfLogin.getText());
        p.setPassword(tfPassword.getText());
        p.setNum(tfNum.getText());
        
        p.setSpecialite(tfspecialite.getText());
        p.setJustificatif(labelJ.getText());
        p.setPortefolio(labelPf.getText());
        p.setCv(labelupload.getText());
        
        if (sp.AjouterUtilisateur(p))
        {
            
            alert.setTitle("Ajout Participant");
            alert.setHeaderText(null);
            alert.setContentText("Ajouté Avec Succes!");
            alert.showAndWait();
        }
        else
        {
            alert.setTitle("Ajout Participant");
            alert.setHeaderText("Ajout Participant");
            alert.setContentText("Participant n'a pas ete ajouté!");

            alert.showAndWait();
        }
       
    }

    @FXML
    private void afficherParticipant(ActionEvent event) {
    }

    @FXML
    private void afficherModifyInformation(ActionEvent event) {
    }

    @FXML
    private void uploadCv(ActionEvent event) {
        FileChooser fil_chooser = new FileChooser();
        
        File file = fil_chooser.showOpenDialog(null); 
  
                if (file != null) { 
                      
                   
                    labelupload.setText(file.getAbsolutePath());  
                                       
                } 

    }

    @FXML
    private void uploadPf(ActionEvent event) {
        
        FileChooser fil_chooser = new FileChooser();
        
        File file = fil_chooser.showOpenDialog(null); 
  
                if (file != null) { 
                      
                   
                    labelPf.setText(file.getAbsolutePath() );
                                        
                } 

    }

    @FXML
    private void uploadJ(ActionEvent event) {
        FileChooser fil_chooser = new FileChooser();
        
        File file = fil_chooser.showOpenDialog(null); 
  
                if (file != null) { 
                      
                   
                    labelJ.setText(file.getAbsolutePath());
                                         
                } 

    }
    
}
