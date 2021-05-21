/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;



import Entities.Activite;
import Entities.projet;
import sms.Sms;
import Service.impProjet;
import Service.implActiviteService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sms.Sms;

/**
 *
 * @author NMEDIA
 */
public class projetController implements Initializable{

    @FXML
    private Button btnajouter;
    @FXML
    private TextField idsupp;
    @FXML
    private TextField nom;
    @FXML
    private TextField sujet;
    @FXML
    private TextArea desc;
    @FXML
    private DatePicker dateCreation;
    private ComboBox<String> listactiviter;
    @FXML
    private Button addActivite;

    @FXML
    private AnchorPane idupd;
   
    
    
    
    private projet pj = new projet();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    
        
        
    }
    
    
    
   

    
    


    

    @FXML
    private void onajouterProjet(ActionEvent event) {
        
        Date dateC = Date.valueOf(dateCreation.getValue());
        String nomp = nom.getText();
        String description = desc.getText();
        String sujetp = sujet.getText();
        impProjet pService = new impProjet();
        implActiviteService aService = new implActiviteService();
        projet p = new projet(nomp, sujetp, description, dateC);
        Sms s = new Sms();
        //Activite a1 = new Activite();
        
        try {
            pService.ajouter(p);
            activiteController.a.setId( impProjet.insertedId);
            aService.ajouter(activiteController.a);
            s.sendSMS(p);
            
            

        try {
            ObservableList<projet> list = pService.readAll();
            System.out.println(list);
        } catch (SQLException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        } catch (SQLException ex) {
            Logger.getLogger(projetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void showActivite(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("activite.fxml"));
        
        Scene scene = new Scene(root,1100,600);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        
           Parent root = FXMLLoader.load(getClass().getResource("/projet/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }





    

    
    
    
}
