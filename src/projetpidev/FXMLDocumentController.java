/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetpidev;

import Entities.Formations;
import Service.ServiceFormations;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author AMINE N
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button btnajouter;
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
    private DatePicker pickerdate;
    @FXML
    private DatePicker pickerdateprevu;
    @FXML
    private TextField txtcoutfin;
    @FXML
    private TableView<Formations> tabform;
    @FXML
    private TableColumn<Formations, Integer> coid;
    @FXML
    private TableColumn<Formations, String> coobjet;
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
    private TableColumn<Formations, Date> codaterelle;
    @FXML
    private TableColumn<Formations, Date> codateprevu;
    @FXML
    private Button afficherform;
    @FXML
    private Button uploaderfichier;
    @FXML
    public ListView<String> listupload;
    @FXML
    private TableColumn<Formations, String> copath;
    @FXML
    public TextField idupdate;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnevaluer;
    public int aaa=5;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        ServiceFormations sv=new ServiceFormations();
        Formations f=new Formations();
        if(event.getSource().equals(btnajouter))
        {
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
            sv.Ajouter_Formation(f);
             
           
            
            
        }
        if(event.getSource().equals(afficherform))
        {
            afficher_scene2();
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
    
    public void afficher_scene2() throws IOException
{
    Stage stage =new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("Afficher.fxml"));
    Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    
}
    public void Afficher_formations()
    {
        ServiceFormations sp=new ServiceFormations();
        ObservableList<Formations> list=sp.Get_Formations();
        coid.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("Id"));
         coobjet.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
          cotype.setCellValueFactory(new PropertyValueFactory<Formations,String>("Type"));
           coobjectif.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objectif"));
           conbparticp.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
           cocouthj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
           conbjour.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_jour"));
           cocoutfin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
           codaterelle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_reelle"));
           codateprevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
           copath.setCellValueFactory(new PropertyValueFactory<Formations,String>("path"));
           tabform.setItems(list);
           
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Afficher_formations();
       txtobjet.textProperty().addListener(e->{
           if(txtobjet.getText().equals("aaaa"))
           {
               System.out.println("asba");
               Alert alert = new Alert(AlertType.WARNING);
alert.setTitle("Warning Dialog");
alert.setHeaderText("Look, a Warning Dialog");
alert.setContentText("Careful with the next step!");

alert.showAndWait();           }
       });
    }

    private void SetUpdateInfo(MouseEvent event) {
        
        Formations form= tabform.getSelectionModel().getSelectedItem();
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
       idupdate.setText(String.valueOf(form.getId()));
    }
    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    @FXML
    private void onModifierclick(ActionEvent event) {
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
            f.setId(Integer.parseInt(idupdate.getText()));
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

    @FXML
    private void Evaluerev(MouseEvent event) {
        Formations form= tabform.getSelectionModel().getSelectedItem();
        idupdate.setText(String.valueOf(form.getId()));
        System.out.println(idupdate.getText());
        
    }
    public void afficher_eval() throws IOException
{
    Stage stage =new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("Evaluer.fxml"));
    Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    
}

    @FXML
    private void onbtnevaluer(ActionEvent event) {
        try {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("Evaluer.fxml"));
            Parent root =(Parent) loader.load();
            EvaluerController f=loader.getController();
            
            //afficher_eval();
            f.setval(idupdate.getText());
             Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    
}
