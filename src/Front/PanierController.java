/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Commande;
import Entities.comformation;
import Entities.panier;
import Service.ServiceCommande;
import Service.ServicePanier;
import UserSession.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class PanierController implements Initializable {
    private int  vartri=0;
    @FXML
    private TableView<comformation> tabcommande;
    @FXML
    private TableColumn<comformation, String> coetat;
    @FXML
    private TableColumn<comformation, Float> coprix;
    @FXML
    private TextField txtprix;
    @FXML
    private TextField txtnbarticles;
       @FXML
    private TableColumn<comformation,String> cobjet;
    @FXML
    private TableColumn<comformation,String> cobjectif;
    @FXML
    private TableColumn<comformation,String> cotype;
    @FXML
    private Button btnsupp;
    @FXML
    private TextField suppanier;
    @FXML
    private TableColumn<comformation, Integer> coidform;
    @FXML
    private TextField tfsearch;
    @FXML
    private Button btnpasser;

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
       Afficher_Commandes();
       update_panier();
       
    }    
    public void Afficher_Commandes()
    {
        ServiceCommande sp=new ServiceCommande();
        ObservableList<comformation> list=sp.Get_formvomm(UserSession.getInstace("", 0, "").getId());
        
          coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
          coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
          coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
          cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
          cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
          cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
          
           tabcommande.setItems(list);
           
        
    }
    public void update_panier()
    {
       txtnbarticles.setText(String.valueOf(tabcommande.getItems().size()));
      float prix_total=0;
       for(int i=0;i<tabcommande.getItems().size();i++)
       {
           prix_total=prix_total+tabcommande.getItems().get(i).getPrix();
          
       }
       txtprix.setText(String.valueOf(prix_total));
       panier p=new panier();
       p.setId_client(UserSession.getInstace("", 0, "").getId());
       p.setNombre(tabcommande.getItems().size());
       p.setPrix_total(prix_total);
       ServicePanier sv= new ServicePanier();
       sv.Update_panier(p);
    }  

    @FXML
    private void Supprimer_btn(ActionEvent event) {
      ServiceCommande sc=new ServiceCommande();
         
         Commande c=new Commande();
         
         //suppanier.setText(String.valueOf(c.getId_formation()));
        sc.supprimercommande(Integer.parseInt(suppanier.getText()));
        this.Afficher_Commandes();
        update_panier();
    }

    @FXML
    private void presupp(MouseEvent event) {
        comformation c =tabcommande.getSelectionModel().getSelectedItem();
        suppanier.setText(String.valueOf(c.getId_formation()));
    }

    @FXML
    private void SearchKey(KeyEvent event) {
               ServiceCommande sc=new ServiceCommande();
        ObservableList<comformation> comformations =sc.search(tfsearch.getText(), UserSession.getInstace("", 0, "").getId());
         coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
          coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
          coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
          cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
          cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
          cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
        
           tabcommande.setItems(comformations);
    }

    @FXML
    private void tribtn(MouseEvent event) {
           ServiceCommande sc=new ServiceCommande();
        ObservableList<comformation> formationstab;
        if(vartri==1){
            vartri=0;
            formationstab=sc.triasc(1);
            coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
            coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
            coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
            cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
            cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
            cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));

            tabcommande.setItems(formationstab);
        }
        else{
            vartri=1;
            formationstab=sc.tridsc(UserSession.getInstace("", 0, "").getId());
            coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
          coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
          coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
          cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
          cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
          cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
        
           tabcommande.setItems(formationstab);
        }
    }

    @FXML
    private void onfinalisercommande(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/finalisercommande.fxml"));
            Parent root =(Parent) loader.load();
            //EvaluerController f=loader.getController();
            
            //afficher_eval();
           // f.setval(String.valueOf(data.getId()));
            //System.out.println(idupdate.getText());
             Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.show();
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }
    
}
