/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import Etities.Participants;
import Etities.Utilisateurs;
import Service.ServiceParticipant;
import static java.lang.String.format;
import static java.lang.String.format;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class FormParticipantController implements Initializable {

    
    final Pattern pattern = Pattern.compile("\\d{0,5}");
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
    private TextField tfNiveau;
    @FXML
    private TextField tfCertificat;
    @FXML
    private TextField tfInteresse;
    @FXML
    private TextField tfNombre;
    @FXML
    private Button add;
    @FXML
    private Button afficher;
    @FXML
    private TableView<Utilisateurs> tableAffiche;
    @FXML
    private TableColumn<Utilisateurs ,Integer> cId;
    @FXML
    private TableColumn<Utilisateurs ,String> cNom;
    @FXML
    private TableColumn<Utilisateurs ,String> cPrenom;
    @FXML
    private TableColumn<Utilisateurs ,Date> cDate;
    @FXML
    private TableColumn<Utilisateurs ,String> cCin;
    @FXML
    private TableColumn<Utilisateurs ,String> cEmail;
    @FXML
    private TableColumn<Utilisateurs ,String> cLogin;
    @FXML
    private TableColumn<Utilisateurs ,String> cPwd;
    @FXML
    private TableColumn<Utilisateurs ,String> cNum;
    @FXML
    private TableColumn<Utilisateurs ,String> cNiveau;
    @FXML
    private TableColumn<Utilisateurs ,Integer> cCertificat;
    @FXML
    private TableColumn<Utilisateurs ,String> cInteresse;
    @FXML
    private TableColumn<Utilisateurs ,Integer> cNombre;
    @FXML
    private Button bDelete;
    @FXML
    private Button confirmer;
    @FXML
    private Button annuler;
    @FXML
    private TextField tfIdSelected;
   
        
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        // TODO
        
                        TextFormatter<?> formatter = new TextFormatter<>(change -> {
                    if (pattern.matcher(change.getControlNewText()).matches()) {
                        // todo: remove error message/markup
                        return change; // allow this change to happen
                    } else {
                        // todo: add error message/markup
                        return null; // prevent change
                    }
                });
                        
                        
        confirmer.setVisible(false);
        annuler.setVisible(false);
        tfIdSelected.setVisible(false);
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        cPrenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        cDate.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        cCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        cPwd.setCellValueFactory(new PropertyValueFactory<>("password"));
        cNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        cNiveau.setCellValueFactory(new PropertyValueFactory<>("niveauEtude"));
        cCertificat.setCellValueFactory(new PropertyValueFactory<>("certificatsObtenus"));
        cInteresse.setCellValueFactory(new PropertyValueFactory<>("interssePar"));
        cNombre.setCellValueFactory(new PropertyValueFactory<>("NombreDeFormation"));
        
        tfCin.setTextFormatter(formatter);
    }    

    @FXML
    private void ajouterParticipant(ActionEvent event) {
        
        Participants p = new Participants();
        ServiceParticipant sp = new ServiceParticipant();
        Alert alert = new Alert(AlertType.INFORMATION);
        
        p.setNom(tfNom.getText());
        p.setPrenom(tfPrenom.getText());
        p.setDateNaissance(Date.valueOf(dateDeNaissance.getValue()));
        p.setCin(tfCin.getText());
        p.setEmail(tfEmail.getText());
        p.setLogin(tfLogin.getText());
        p.setPassword(tfPassword.getText());
        p.setNum(tfNum.getText());
        p.setNiveauEtude(tfNiveau.getText());
        p.setCertificatsObtenus(Integer.parseInt(tfCertificat.getText()));
        p.setInterssePar(tfInteresse.getText());
        p.setNombreDeFormation(Integer.parseInt(tfNombre.getText()));
        
        if (sp.AjouterUtilisateur(p))
        {
            
            alert.setTitle("Ajout Participant");
            alert.setHeaderText(null);
            alert.setContentText("Ajouté Avec Succes!");
            alert.showAndWait();
        }
        else
            
            alert.setTitle("Ajout Participant");
            alert.setHeaderText("Ajout Participant");
            alert.setContentText("Participant n'a pas ete ajouté!");

            alert.showAndWait();
        
       
        
        
    }

    @FXML
    private void afficherParticipant(ActionEvent event) throws SQLException {
        
        ObservableList <Utilisateurs> L =  FXCollections.observableArrayList();
      
        ServiceParticipant sp = new ServiceParticipant();
        tableAffiche.setItems(sp.AfficherUtlisaterus());
        
        
        
        
       
    }

    @FXML
    private void supprimerParticipant(ActionEvent event) {
      
        ServiceParticipant sp = new ServiceParticipant(); 
        System.out.println(tableAffiche.getSelectionModel().getSelectedItem().getId());
        sp.SupprimerUtilisateur(tableAffiche.getSelectionModel().getSelectedItem().getId());
        tableAffiche.getItems().removeAll(tableAffiche.getSelectionModel().getSelectedItem());
        
    }

    @FXML
    private void afficherModifyInformation(ActionEvent event) {
        
        ObservableList <Utilisateurs> L =  FXCollections.observableArrayList();
        ServiceParticipant sp = new ServiceParticipant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        
        if (tableAffiche.getSelectionModel().getSelectedItem() != null)
        {
            confirmer.setVisible(true);
            annuler.setVisible(true);
            
            tfIdSelected.setText(String.valueOf(((Participants)tableAffiche.getSelectionModel().getSelectedItem()).getId()));
            tfNom.setText(tableAffiche.getSelectionModel().getSelectedItem().getNom());
            tfPrenom.setText(tableAffiche.getSelectionModel().getSelectedItem().getPrenom());       
            dateDeNaissance.setValue(LocalDate.parse((tableAffiche.getSelectionModel().getSelectedItem().getDateNaissance()).toString(),formatter));
            tfCin.setText(tableAffiche.getSelectionModel().getSelectedItem().getCin()); 
            tfEmail.setText(tableAffiche.getSelectionModel().getSelectedItem().getEmail());
            tfLogin.setText(tableAffiche.getSelectionModel().getSelectedItem().getLogin());
            tfPassword.setText(tableAffiche.getSelectionModel().getSelectedItem().getPassword());
            tfNum.setText(tableAffiche.getSelectionModel().getSelectedItem().getNum());
            tfNiveau.setText(((Participants)tableAffiche.getSelectionModel().getSelectedItem()).getNiveauEtude());
            tfCertificat.setText(String.valueOf(((Participants)tableAffiche.getSelectionModel().getSelectedItem()).getCertificatsObtenus()));
            tfInteresse.setText(((Participants)tableAffiche.getSelectionModel().getSelectedItem()).getInterssePar());
            tfNombre.setText(String.valueOf(((Participants)tableAffiche.getSelectionModel().getSelectedItem()).getNombreDeFormation()));

            
        }
    }

    @FXML
    private void confirmerModification(ActionEvent event) throws SQLException {
        
        Participants p = new Participants();
        ServiceParticipant sp = new ServiceParticipant();
        
        p.setId(Integer.parseInt(tfIdSelected.getText()));
        p.setNom(tfNom.getText());
        p.setPrenom(tfPrenom.getText());
        p.setDateNaissance(Date.valueOf(dateDeNaissance.getValue()));
        p.setCin(tfCin.getText());
        p.setEmail(tfEmail.getText());
        p.setLogin(tfLogin.getText());
        p.setPassword(tfPassword.getText());
        p.setNum(tfNum.getText());
        
        p.setNiveauEtude(tfNiveau.getText());
        p.setCertificatsObtenus(Integer.parseInt(tfCertificat.getText()));
        p.setInterssePar(tfInteresse.getText());
        p.setNombreDeFormation(Integer.parseInt(tfNombre.getText()));
        
        sp.ModifierUtilisateur(p);
    }



    
   
   

    
}
