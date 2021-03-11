/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import Entities.Formateurs;
import Entities.Participants;
import Entities.Utilisateurs;
import Service.ServiceFormateur;
import Service.ServiceParticipant;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class utilisateurs implements Initializable {

    @FXML
    private TableView<Utilisateurs> tbData;
    @FXML
    private TableColumn<Utilisateurs, String> nom;
    @FXML
    private TableColumn<Utilisateurs, String> prenom;
    @FXML
    private TableColumn<Utilisateurs, String> email;
    
    
     private TableColumn<Utilisateurs, Void> colBtn = new TableColumn("Delete");
     private TableColumn<Utilisateurs, Void> colBtnC = new TableColumn("Contacter");
     private TableColumn<Utilisateurs, Void> colType = new TableColumn("Type");
     
       private TableColumn<Utilisateurs, Void> colActiver = new TableColumn("Afficher Justificatif");
       private TableColumn<Utilisateurs, Void> colActiverAccount = new TableColumn("Activate");
       private TableColumn<Utilisateurs, Void> colDeleteFormateur = new TableColumn("Delete");
    
    /**
     * Initializes the controller class.
     */
       public static Utilisateurs emailD = new Utilisateurs();
    private final Button btn = new Button("Delete");
    @FXML
    private Label totalParticipant;
    @FXML
    private Label totalFormateurs;
    @FXML
    private TableView<Utilisateurs> tbDataF;
    @FXML
    private TableColumn<Utilisateurs, String> nomF;
    @FXML
    private TableColumn<Utilisateurs, String> prenomF;
    @FXML
    private TableColumn<Utilisateurs, String> emailF;
    @FXML
    private TableColumn<Utilisateurs, Boolean> etatF;
    @FXML
    private TextField chercher;
    private Parent root ;
    @FXML
    private Button pdf;

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
          ObservableList <Utilisateurs> L =  FXCollections.observableArrayList();
          ServiceParticipant sp = new ServiceParticipant();
          
          ObservableList <Utilisateurs> LF =  FXCollections.observableArrayList();
          ServiceFormateur spf = new ServiceFormateur();
        
         Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>> cellFactory = new Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>>() {
            
            public TableCell<Utilisateurs, Void> call(final TableColumn<Utilisateurs, Void> param) {
                final TableCell<Utilisateurs, Void> cell = new TableCell<Utilisateurs, Void>() {

                    private final Button btn = new Button("Delete");
                    
                    
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           Utilisateurs data = getTableView().getItems().get(getIndex());
                           // implWorkshopService wService = new implWorkshopService();
        
                          
                                sp.SupprimerUtilisateur(data.getId());
                            try {
                                // wService.delete(data.getId());
                                ObservableList <Utilisateurs> LU =  FXCollections.observableArrayList();
                                LU = sp.AfficherUtlisaterus();
                                tbData.setItems(LU);
                                //  tbData.getItems().removeAll(tbData.getSelectionModel().getSelectedItem());
                                
                                 Notifications n =Notifications.create().title("SUCCESS").text("Participant Supprimé!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
                            } catch (SQLException ex) {
                                Logger.getLogger(utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                           
                        }  );
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
         
         

        
        colBtn.setCellFactory(cellFactory);
        
        
        
        
        
        Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>> cellFactoryC = new Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>>() {
            
            public TableCell<Utilisateurs, Void> call(final TableColumn<Utilisateurs, Void> param) {
                final TableCell<Utilisateurs, Void> cell = new TableCell<Utilisateurs, Void>() {

                    private final Button btn = new Button("Contacter");
                    
                    
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            Utilisateurs data = getTableView().getItems().get(getIndex());
                                emailD = getTableView().getItems().get(getIndex());
                            
                            try {
                                    root =  FXMLLoader.load(getClass().getResource("/home/mail.fxml"));
                                } catch (IOException ex) {
                                    Logger.getLogger(utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Stage stage = new Stage();
                                Scene scene = new Scene(root);
                                scene.setFill(Color.TRANSPARENT);
                                stage.setScene(scene);
                                stage.initStyle(StageStyle.TRANSPARENT);
                                stage.setScene(scene);
                                stage.show();
                             
                            
                            
                            
                            
                           
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
         
         

        
        colBtnC.setCellFactory(cellFactoryC);

      
        try {
            // TODO
           
            L = sp.AfficherUtlisaterus();
            
           nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
           prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
           email.setCellValueFactory(new PropertyValueFactory<>("email"));         
        //   tbData.getColumns().add(colType);
           tbData.getColumns().add(colBtn);
           tbData.getColumns().add(colBtnC);
           
           tbData.setItems(L);

           totalParticipant.setText(Integer.toString(sp.CountParticipant()));
            
            // tableAffiche.setItems(sp.AfficherUtlisaterus());
            
            
            
        } catch (SQLException ex) {
            
        }
        
        
        
       
            
           
        
        Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>> cellFactoryD = new Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>>() {
            
            public TableCell<Utilisateurs, Void> call(final TableColumn<Utilisateurs, Void> param) {
                final TableCell<Utilisateurs, Void> cell = new TableCell<Utilisateurs, Void>() {

                    private final Button btn = new Button("Show");
                    
                    
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            
                            Formateurs data = (Formateurs) getTableView().getItems().get(getIndex());
                            
                            try {
                                new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",data.getJustificatif()).start();
                            } catch (IOException ex) {
                                Logger.getLogger(utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
                            }
                          
                                
                           
                        }  );
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
         
         

        
        colActiver.setCellFactory(cellFactoryD);
        
        
        
        Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>> cellFactoryActivate = new Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>>() {
            
           
            public TableCell<Utilisateurs, Void> call(final TableColumn<Utilisateurs, Void> param) {
                final TableCell<Utilisateurs, Void> cell;
                cell = new TableCell<Utilisateurs, Void>() {
                    
                    private final JFXButton btn = new JFXButton("Activate");
                    
                    
                    
                    
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            Formateurs data = (Formateurs) getTableView().getItems().get(getIndex());
                            ServiceFormateur spf = new ServiceFormateur();
                            
                             ObservableList <Utilisateurs> LFU =  FXCollections.observableArrayList();
                            //  System.out.println(data.getId());
                            try {
                                
                                spf.ActiverFormateurAccount(data.getId());  
                                LFU = spf.AfficherUtlisaterus();
                                etatF.setCellValueFactory(new PropertyValueFactory<>("etat")); 
                                tbDataF.setItems(LFU);   
                                
                                 Notifications n =Notifications.create().title("SUCCESS").text("Activation avec succes!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
                                
                            } catch (SQLException ex) {
                                System.out.println(ex);
                                
                            }
                            
                        }  );
                    }

                    
                
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
         
         

        
        colActiverAccount.setCellFactory(cellFactoryActivate);
        
        
        
        Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>> cellFactoryDeleteFormateur = new Callback<TableColumn<Utilisateurs, Void>, TableCell<Utilisateurs, Void>>() {
            
            public TableCell<Utilisateurs, Void> call(final TableColumn<Utilisateurs, Void> param) {
                final TableCell<Utilisateurs, Void> cell = new TableCell<Utilisateurs, Void>() {

                    private final Button btn = new Button("Delete");
                    
                    
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           Utilisateurs data = getTableView().getItems().get(getIndex());
                           
        
                          
                                spf.SupprimerUtilisateur(data.getId());
                            try {
                                // wService.delete(data.getId());
                                ObservableList <Utilisateurs> LU =  FXCollections.observableArrayList();
                                LU = spf.AfficherUtlisaterus();
                                tbDataF.setItems(LU);
                                //  tbData.getItems().removeAll(tbData.getSelectionModel().getSelectedItem());
                                
                                 Notifications n =Notifications.create().title("SUCCESS").text("Formateur Supprimé!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
                                  n.darkStyle();
                                  n.show();
                            } catch (SQLException ex) {
                                Logger.getLogger(utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                           
                        }  );
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
         
         

        
        colDeleteFormateur.setCellFactory(cellFactoryDeleteFormateur);
        
        
         try {
            LF = spf.AfficherUtlisaterus();
            nomF.setCellValueFactory(new PropertyValueFactory<>("nom"));
            prenomF.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            emailF.setCellValueFactory(new PropertyValueFactory<>("email"));   
            etatF.setCellValueFactory(new PropertyValueFactory<>("etat"));  
            tbDataF.getColumns().add(colActiver);
            tbDataF.getColumns().add(colActiverAccount);
            tbDataF.getColumns().add(colDeleteFormateur);
        //   tbData.getColumns().add(colType);
         //  tbData.getColumns().add(colBtn);
          // tbData.getColumns().add(colBtnC);
           
           tbDataF.setItems(LF);

           totalFormateurs.setText(Integer.toString(spf.CountFormateurs()));
        } catch (SQLException ex) {
            Logger.getLogger(utilisateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void chercherParticipant(KeyEvent event) throws SQLException {
        
       ServiceParticipant sc=new ServiceParticipant();
                ObservableList <Utilisateurs> comformations =  FXCollections.observableArrayList();
          if (chercher.getText().isEmpty())
          {
              comformations = sc.AfficherUtlisaterus();
              tbData.setItems(comformations);
          }
          else
          {
               
                 comformations =sc.chercherParticipants(chercher.getText(), 1);
                  tbData.setItems(comformations);
          }
    }

    @FXML
    private void export(ActionEvent event) throws SQLException {
        TableView<Utilisateurs> table = new TableView<Utilisateurs>();
         ServiceParticipant Service = new ServiceParticipant();
         ObservableList<Utilisateurs> data =FXCollections.observableArrayList();  
   
        
        double w = 500.00;
        // set width of table view
        table.setPrefWidth(w);
        // set resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // intialize columns
        TableColumn<Utilisateurs,String> Nom  = new TableColumn<Utilisateurs,String>("nom");
        TableColumn<Utilisateurs,String> Prenom  = new TableColumn<Utilisateurs,String>("prenom");
        TableColumn<Utilisateurs,String> email  = new TableColumn<Utilisateurs,String>("email");
        TableColumn<Utilisateurs,String> niveau  = new TableColumn<Utilisateurs,String>("niveauEtude");
        
        
        
            
        // set width of columns
        Nom.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        Prenom.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        email.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        niveau.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        
        
        // 
        Nom.setCellValueFactory(new PropertyValueFactory<Utilisateurs,String>("nom"));
        Prenom.setCellValueFactory( new PropertyValueFactory<Utilisateurs,String>("prenom"));
        email.setCellValueFactory( new PropertyValueFactory<Utilisateurs,String>("email"));
        niveau.setCellValueFactory( new PropertyValueFactory<Utilisateurs,String>("niveauEtude"));
      
        
        
        // Add columns to the table
        table.getColumns().addAll(Nom,Prenom,email,niveau);
        
        
        // Query to get ALL Events from the selected calendar!!
        //String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "' ORDER BY EventDate";
        
        // Store the results here
        //ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
        data = Service.AfficherUtlisaterus();
        
        /*
        try {
        
        while(result.next()){
        //initalize temporarily strings
        String tempTerm="";
        
        
        //***** Get term, Event Description and Date ***
        
        //Get Event Description
        String eventDescript = result.getString("EventDescription");
        //Get Term ID for an event
        int termID = result.getInt("TermID");
        
        //Get Event Date and format it as day-month-year
        Date dDate=result.getDate("EventDate");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String eventDate = df.format(dDate);
        
        //Query that will get the term name based on a term ID
        String getTermQuery = "SELECT TermName FROM TERMS WHERE TermID=" + termID + "";
        //Execute query to get TermName and store it in a ResultSet variable
        ResultSet termResult = databaseHandler.executeQuery(getTermQuery);
        
        
        while(termResult.next())
        {
        tempTerm=termResult.getString(1);
        /*
        while(programResult.next())
        {
        tempProgram = programResult.getString(1);
        }
        tempTerm+=" "+tempProgram;
        
        }
        
        
        //Get Term Name for an event
        //tempTerm = termResult.getString(1);
        
        
        //Add event information in a row
        data.add(new Event(tempTerm,eventDescript,eventDate));
        
        }
        } catch (SQLException ex) {
        Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
         
       
        table.getItems().setAll(data);
        // open dialog window and export table as pdf
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println(table);
        if(job != null){
          
          job.printPage(table);
          job.endJob();
        }
        
        
        
    }
    
}
