/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.fxml;

import Entities.Formations;
import Entities.categorie;
import Service.Servicecategorie;
import Service.ServiceFormations;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class FormulaireformController implements Initializable {

    @FXML
    private TextField txtobjet;
    @FXML
    private TextField txttype;
    @FXML
    private TextField txtobjectif;
    @FXML
    private TextField txtparticipants;
    @FXML
    private TextField txtcout;
    @FXML
    private TextField txtnbjours;
    @FXML
    private TextField txtcoutfin;
    @FXML
    private DatePicker pickerdate;
    @FXML
    private DatePicker pickerdateprevu;
    @FXML
    private Button btnajouter;
    @FXML
    private ListView<String> listupload;
    @FXML
    private Button uploaderfichier;
    @FXML
    private TableView<Formations> tabform;
    @FXML
    private TableColumn<Formations, Integer> coid;
    
    @FXML
    private TableColumn<Formations, String> cotype;
    @FXML
    private TableColumn<Formations, String> coobjectif;
    @FXML
    private TableColumn<Formations, Integer> conbparticp;
    @FXML
    private TableColumn<Formations, Float> cocouthj;
    @FXML
    private TableColumn<Formations, Integer> conbjour;
    @FXML
    private TableColumn<Formations, Float> cocoutfin;
    @FXML
    private TableColumn<Formations, java.util.Date> codaterelle;
    @FXML
    private TableColumn<Formations, java.util.Date> codateprevu;
    @FXML
    private TableColumn<Formations, String> copath;
    @FXML
    private TableColumn<Formations, String> coobject;
    @FXML
    private TextField idsupp;
    @FXML
    private Button btnsupp;
    @FXML
    private Button btnupdate;
    @FXML
    private ComboBox<String> categoriebox;
    @FXML
    private TextField txtcategorie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Afficher_formations();
        Servicecategorie sv=new Servicecategorie();
        ObservableList<categorie> list=sv.Get_Categories();
        for(categorie c:list)
        categoriebox.getItems().add(c.getNom());
        categoriebox.getItems().add("autres");
        categoriebox.valueProperty().addListener((e) -> {
            if(categoriebox.getValue().equals("autres"))
                {
                  txtcategorie.setVisible(true);
                }
            else txtcategorie.setVisible(false);
              
        });
        
        
    }    


    @FXML
    private void onajouterformation(ActionEvent event) {
        ServiceFormations sv=new ServiceFormations();
        Formations f=new Formations();
       
         if(event.getSource().equals(btnajouter))
        {
             String cate;
        if(categoriebox.getValue().equals("autres"))
            cate=txtcategorie.getText();
        else cate=categoriebox.getValue();
               String path="";
            ObservableList<String> files; 
            files = listupload.getItems();
                for (String each: files)
                {
                path=path+each;
                }   
             System.out.println(path);
            f.setPath(path);
             //System.out.println("aaaa");
            f.setType(txttype.getText());
            f.setCout_fin(Float.parseFloat(txtcoutfin.getText()));
            f.setObjet(txtobjet.getText());
            f.setNb_jour(Integer.parseInt(txtnbjours.getText()));
            f.setObjectif(txtobjectif.getText());
            f.setNb_participants(Integer.parseInt(txtparticipants.getText()));
            f.setCout_hj(Float.parseFloat(txtcout.getText()));
            f.setDate_reelle(Date.valueOf(pickerdate.getValue()));
            
            f.setDate_prevu(Date.valueOf(pickerdateprevu.getValue()));
            f.setCategorie(cate);
            //System.out.println("aaasba");
            sv.Ajouter_Formation(f);
             
           
            
            
        }
          if(event.getSource().equals(uploaderfichier))
        {
            FileChooser fc =new FileChooser();
            //fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files",".pdf"));
            List<File> selectedFiles =fc.showOpenMultipleDialog(null);
            if(selectedFiles!=null)
            {
                for(int i=0;i<selectedFiles.size();i++)
                {
                    listupload.getItems().add(selectedFiles.get(i).getAbsolutePath());
                    
                }
            }
            else System.out.println("File is not valid");
            
            
        }
    }
     public void Afficher_formations()
    {
        ServiceFormations sp=new ServiceFormations();
        ObservableList<Formations> list=sp.Get_Formations();
        coid.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("Id"));
         coobject.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
          cotype.setCellValueFactory(new PropertyValueFactory<Formations,String>("Type"));
           coobjectif.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objectif"));
           conbparticp.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
           cocouthj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
           conbjour.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_jour"));
           cocoutfin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
           codaterelle.setCellValueFactory(new PropertyValueFactory<Formations,java.util.Date>("date_reelle"));
           codateprevu.setCellValueFactory(new PropertyValueFactory<Formations,java.util.Date>("date_prevu"));
           copath.setCellValueFactory(new PropertyValueFactory<Formations,String>("path"));
           tabform.setItems(list);
           
        
    }

    @FXML
    private void SupprimerFormation(MouseEvent event) {
          Formations form= tabform.getSelectionModel().getSelectedItem();
      idsupp.setText(String.valueOf(form.getId()));
     // Formations form= tabform.getSelectionModel().getSelectedItem();
        //form2=form;
        txtobjet.setText(form.getObjet());
        txttype.setText(form.getType());
        txtobjectif.setText(form.getObjectif());
        txtparticipants.setText(String.valueOf(form.getNb_participants()));
        txtcout.setText(String.valueOf(form.getCout_hj()));
        txtnbjours.setText(String.valueOf(form.getNb_jour()));
        txtcoutfin.setText(String.valueOf(form.getCout_fin()));
         java.sql.Date datejdida=convertUtilToSql(form.getDate_reelle());
         java.sql.Date datejdida2=convertUtilToSql(form.getDate_prevu());
        pickerdate.setValue(datejdida.toLocalDate());
        pickerdateprevu.setValue(datejdida2.toLocalDate());
       ObservableList<String> sList = FXCollections.<String>observableArrayList(form.getPath());
       listupload.setItems(sList);
       //idupdate.setText(String.valueOf(form.getId()));
    }
     private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @FXML
    private void Supprimer_btn(ActionEvent event) {
        ServiceFormations sv=new ServiceFormations();
        sv.Supprimer_formation(Integer.parseInt(idsupp.getText()));
         Afficher_formations();
         Afficher_formations();
    }

    @FXML
    private void onmodifier(ActionEvent event) {
         ServiceFormations sv=new ServiceFormations();
        Formations f=new Formations();
            f.setType(txttype.getText());
            f.setCout_fin(Float.parseFloat(txtcoutfin.getText()));
            f.setObjet(txtobjet.getText());
            f.setNb_jour(Integer.parseInt(txtnbjours.getText()));
            f.setObjectif(txtobjectif.getText());
            f.setNb_participants(Integer.parseInt(txtparticipants.getText()));
            f.setCout_hj(Float.parseFloat(txtcout.getText()));
            f.setDate_reelle(Date.valueOf(pickerdate.getValue()));
            f.setDate_prevu(Date.valueOf(pickerdateprevu.getValue()));
            f.setId(Integer.parseInt(idsupp.getText()));
            String path="";
            ObservableList<String> files; 
            files = listupload.getItems();
                for (String each: files)
                {
                path=path+each;
                }   
                f.setPath(path);
                sv.Modifier_formation(f);
            
    }

    private void onajouterform(ActionEvent event) {

    }

    @FXML
    private void backAjoutFormation(ActionEvent event) throws IOException {
        
                 Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/Ajout_formation.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    
}
