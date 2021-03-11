/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Evaluation;
import Entities.formeval;
import Service.ServiceEvaluation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static org.omg.CORBA.IDLTypeHelper.id;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class EvaluerController implements Initializable {

    @FXML
    private ComboBox<Integer> comboeval;
    @FXML
    private TextArea txtcommentaire;
    @FXML
    private Button btneval;
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
    @FXML
    private Button passerreclamation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
      
        
        
         passerreclamation.setVisible(false);
        comboeval.getItems().addAll(0,1,2,3,4,5);
        Afficher_Evaluations();
    }    

    @FXML
    private void onclickbtneval(ActionEvent event) {
          ServiceEvaluation sv=new ServiceEvaluation();
        Evaluation ev=new Evaluation();
        ev.setRapport(txtcommentaire.getText());
        ev.setNote(comboeval.getValue());
        ev.setId_formation(Integer.parseInt(txtidform.getText()));
          sv.Ajouter_Evaluation(ev);
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
    public void setval(String s)
        {
            txtidform.setText(s);
        }

    @FXML
    private void on_passerreclamation(ActionEvent event) {
            
            loadStage("/Front/reclamation.fxml");
            
    }
    
    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
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
    private void afficherbtnreclamation(ActionEvent event) {
        if(comboeval.getValue()<3)
        {
            passerreclamation.setVisible(true);
            
        }
        else 
        {
            passerreclamation.setVisible(false);
        }
    }

    @FXML
    private void backToListeFormation(ActionEvent event) throws IOException {
        
                 ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
}
