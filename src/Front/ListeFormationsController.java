/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Commande;
import Entities.Formations;
import Entities.categorie;
import Service.Servicecategorie;
import Service.ServiceCommande;
import Service.ServiceFormations;
import UserSession.UserSession;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class ListeFormationsController implements Initializable {

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
    private TableColumn<Formations, Date> codaterelle;
    @FXML
    private TableColumn<Formations, Date> codateprevu;
    @FXML
    private TableColumn<Formations, String> copath;
    @FXML
    private TableColumn<Formations, String> coobject;
FileChooser fileChooser=new FileChooser();
    @FXML
    private ComboBox<String> filtrercombo;
    @FXML
    private TableColumn<Formations, String> cocat;
    @FXML
    private TextField chercher;
    
    private static String idTxt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fileChooser.setInitialDirectory(new File("C:\\Users\\AMINE N\\Desktop\\downloads"));
          Servicecategorie sv=new Servicecategorie();
          ServiceFormations sv2= new ServiceFormations();
        ObservableList<categorie> list=sv.Get_Categories();
        Afficher_formations();
        for(categorie c:list)
        filtrercombo.getItems().add(c.getNom());
         filtrercombo.getItems().add("All");
    }   
     public void Afficher_formations()
    {
        TableColumn<Formations, Void> colBtn = new TableColumn("Action");
          Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>> cellFactory = new Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>>() {
            @Override
            public TableCell<Formations, Void> call(final TableColumn<Formations, Void> param) {
                final TableCell<Formations, Void> cell = new TableCell<Formations, Void>() {

                    private final Button btn = new Button("Evaluer");
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                           
                               
                               
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Evaluer.fxml"));
                                    Parent root =(Parent) loader.load();
                                    EvaluerController f=loader.getController();
            
                                     //afficher_eval();
                                    f.setval(String.valueOf(data.getId()));
                                    //idTxt = String.valueOf(data.getId());
                                    
                                    
                                    //System.out.println(idupdate.getText());
                                      Stage stage = new Stage();
                                      stage.setScene(new Scene(root));
                                      stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                        }
                    }
                };
                return cell;
            }
        
           
        };
          
          
          TableColumn<Formations, Void> colBtn2 = new TableColumn("Action2");
          Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>> cellFactory2 = new Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>>() {
            @Override
            public TableCell<Formations, Void> call(final TableColumn<Formations, Void> param) {
                final TableCell<Formations, Void> cell = new TableCell<Formations, Void>() {

                    private final Button btn = new Button("Commander");
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                                 Commande c=new Commande();
        c.setId_client(UserSession.getInstace("", 0, "").getId());
        c.setId_formation(data.getId());
        c.setEtat("non valider");
        c.setPrix(data.getCout_fin());
        ServiceCommande s=new ServiceCommande();
        s.AjouterCommande(c);
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                        }
                    }
                };
                return cell;
            }
        
           
        };
          TableColumn<Formations, Void> colBtn3 = new TableColumn("Action3");
          Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>> cellFactory3 = new Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>>() {
            @Override
            public TableCell<Formations, Void> call(final TableColumn<Formations, Void> param) {
                final TableCell<Formations, Void> cell = new TableCell<Formations, Void>() {

                    private final Button btn3 = new Button("Download");
                   

                    {
                        btn3.setOnAction((ActionEvent event) -> {
                            Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
             new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",data.getPath()).start();
        } catch (IOException ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn3);
                            
                        }
                    }
                };
                return cell;
            }
        
           
        };
          
        
        colBtn.setCellFactory(cellFactory);
        colBtn2.setCellFactory(cellFactory2);
        colBtn3.setCellFactory(cellFactory3);
        ServiceFormations sp=new ServiceFormations();
        ObservableList<Formations> list=sp.Get_Formations2();
        coid.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("Id"));
         coobject.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
          cotype.setCellValueFactory(new PropertyValueFactory<Formations,String>("Type"));
           coobjectif.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objectif"));
           conbparticp.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
           cocouthj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
           conbjour.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_jour"));
           cocoutfin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
           codaterelle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_reelle"));
           codateprevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
           copath.setCellValueFactory(new PropertyValueFactory<Formations,String>("path"));
           cocat.setCellValueFactory(new PropertyValueFactory<Formations,String>("categorie"));
           colBtn.setCellFactory(cellFactory);
           tabform.setItems(list);
           tabform.getColumns().add(colBtn);
           tabform.getColumns().add(colBtn2);
           tabform.getColumns().add(colBtn3);
           
        
    }

    @FXML
    private void SearchKey(KeyEvent event) {
        ServiceFormations sv=new ServiceFormations();
         ObservableList <Formations> comformations =  FXCollections.observableArrayList();
         
          if (chercher.getText().isEmpty())
          {
              comformations = sv.Get_Formations();
              tabform.setItems(comformations);
          }
          else
          {
              comformations=sv.search(chercher.getText());
                tabform.setItems(comformations);      
          }
    }

    @FXML
    private void onfiltrer(ActionEvent event) {
         ServiceFormations sv=new ServiceFormations();
         ObservableList <Formations> comformations =  FXCollections.observableArrayList();
         if(filtrercombo.getValue().equals("All"))
         {
             comformations = sv.Get_Formations2();
             tabform.setItems(comformations);
         }
         
         else
         {
             comformations=sv.filtrer_forma(filtrercombo.getValue());
             tabform.setItems(comformations);
         }
    }

    @FXML
    private void backDashboard(ActionEvent event) throws IOException {
        
                 Parent root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }
    
}
