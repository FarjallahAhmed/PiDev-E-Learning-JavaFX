/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.fxml;

import Entities.Formations;
import Entities.Promotion;
import Service.ServiceFormations;
import Service.implPromotionService;
import com.jfoenix.controls.JFXButton;
import static home.fxml.Ajout_formationController.affecterP;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Mehdi
 */
public class promotionController implements Initializable{

    @FXML
    private DatePicker dateDP;
    @FXML
    private DatePicker dateDF;
    @FXML
    private TextField pourc;
    @FXML
    private Button btnajouter;
    private TableView<ArrayList<String>> tabformPromo;
     ObservableList<ArrayList<String>> list ;
    private TableColumn<ArrayList<String>, String> coobject;
    private TableColumn<ArrayList<String>, String> cotype;
    private TableColumn<String, String> cocouthj;
    private TableColumn<String, String> cocoutfin;
    private TableColumn<String, String> codaterelle;
    @FXML
    private TextField idsupp;
    @FXML
    private JFXButton back;
    
    
    public void Afficher_formations()
    {
        implPromotionService pService = new implPromotionService();
       
        
        
        
        
         
           /*for(int i=0;i<list.size();i++){
               tabformPromo.setItems(list.get(i));
           }*/
           
           
        
    }

    private void onajouterformation(ActionEvent event) {
        
    }


    @FXML
    private void backDashboard(ActionEvent event) throws IOException {
                 ((Node)(event.getSource())).getScene().getWindow().hide();
                
                
    }

    @FXML
    private void affecterP(ActionEvent event) throws IOException {
        
                implPromotionService pService = new implPromotionService(); 
                Promotion p =new Promotion();
                if(dateDP.getValue().isAfter(dateDF.getValue())){
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Date Verification");
            alertMessage.showAndWait();
            return;
        }else{
                p.setId(Ajout_formationController.affecterP.getId());
                p.setPrix(Float.parseFloat(pourc.getText()));
                p.setDateDebut(Date.valueOf(dateDP.getValue()));
                p.setDateFin(Date.valueOf(dateDF.getValue()));
                
        try {
            pService.affecter(p, Ajout_formationController.affecterP.getId());
            Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/Ajout_formation.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
                 ((Node)(event.getSource())).getScene().getWindow().hide();
            
            System.out.println("affecter bien");
        } catch (SQLException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
                System.out.println(Ajout_formationController.affecterP);    
                }
                

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.Afficher_formations();
    }

    
}
