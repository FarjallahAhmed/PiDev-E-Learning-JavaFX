/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetpidev;

import Entities.Evaluation;
import Entities.Formations;
import Entities.formeval;
import Service.ServiceEvaluation;
import Service.ServiceFormations;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class EvaluerController implements Initializable {

    @FXML
    private ComboBox<Integer> comboeval;
    @FXML
    private Button btneval;
    @FXML
    private TextArea txtcommentaire;
    @FXML
    private TextField txtidform;
    @FXML
   
      private TableView<formeval> tabform;
    @FXML
    private TableColumn<formeval, String> coobjet;
    @FXML
    private TableColumn<formeval, String> cotype;
    @FXML
    private TableColumn<formeval, String> coobjectif;
    @FXML
    private TableColumn<formeval, Integer> conbparticp;
    @FXML
    private TableColumn<formeval, Float> cocouthj;
    @FXML
    private TableColumn<formeval, Integer> conbjour;
    @FXML
    private TableColumn<formeval, Float> cocoutfin;
    @FXML
    private TableColumn<formeval, String> corapport;
    @FXML
    private TableColumn<formeval, Integer> conote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboeval.getItems().addAll(0,1,2,3,4,5);
        Afficher_Evaluations();
        
        
    }    

    @FXML
    private void onclickbtneval(ActionEvent event) throws IOException {
        ServiceEvaluation sv=new ServiceEvaluation();
        Evaluation ev=new Evaluation();
        ev.setRapport(txtcommentaire.getText());
        ev.setNote(comboeval.getValue());
        ev.setId_formation(Integer.parseInt(txtidform.getText()));
          sv.Ajouter_Evaluation(ev);
            
            
        
        
       
    }
     public void setval(String s)
        {
            txtidform.setText(s);
        }
     public void Afficher_Evaluations()
    {
        ServiceEvaluation sp=new ServiceEvaluation();
        ObservableList<formeval> list=sp.Get_Evaluation();
        
         coobjet.setCellValueFactory(new PropertyValueFactory<formeval,String>("Objet"));
          cotype.setCellValueFactory(new PropertyValueFactory<formeval,String>("Type"));
           coobjectif.setCellValueFactory(new PropertyValueFactory<formeval,String>("Objectif"));
           conbparticp.setCellValueFactory(new PropertyValueFactory<formeval,Integer>("nb_participants"));
           cocouthj.setCellValueFactory(new PropertyValueFactory<formeval,Float>("cout_hj"));
           conbjour.setCellValueFactory(new PropertyValueFactory<formeval,Integer>("nb_jour"));
           cocoutfin.setCellValueFactory(new PropertyValueFactory<formeval,Float>("cout_fin"));
           corapport.setCellValueFactory(new PropertyValueFactory<formeval,String>("Rapport"));
           conote.setCellValueFactory(new PropertyValueFactory<formeval,Integer>("Note"));
           tabform.setItems(list);
           
        
    }

    @FXML
    private void Predelete(MouseEvent event) {
    }
        
    
}
