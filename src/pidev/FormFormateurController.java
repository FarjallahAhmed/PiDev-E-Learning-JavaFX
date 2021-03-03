/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class FormFormateurController implements Initializable {

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
    private TextField tfInteresse;
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
    }

    @FXML
    private void afficherParticipant(ActionEvent event) {
    }

    @FXML
    private void afficherModifyInformation(ActionEvent event) {
    }
    
}
