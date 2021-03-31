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
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class UtilisateursCardController implements Initializable {

    @FXML
    private Label totalParticipant;
    @FXML
    private Label totalFormateurs;
    @FXML
    private TextField chercher;
    @FXML
    private Button pdf;
    @FXML
    private HBox hboxParticipant;
    @FXML
    private HBox hboxFormateurs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServiceParticipant sp = new ServiceParticipant();
        ObservableList <Utilisateurs> L =  FXCollections.observableArrayList();
        
        
        try {
            L = sp.AfficherUtlisaterus();
            
            totalParticipant.setText(Integer.toString(sp.CountParticipant()));
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i=0;i<L.size();i++)
        {
        
           FXMLLoader fxmlloader = new FXMLLoader();
           fxmlloader.setLocation(getClass().getResource("Card.fxml"));
           
            try {
                HBox hb = fxmlloader.load();
                CardController CC = fxmlloader.getController();
              //  hb.setId(String.valueOf(L.get(i).getId()));
                CC.setData(L.get(i).getNom(),L.get(i).getPrenom(),L.get(i).getEmail(),
                        L.get(i).getId(),L.get(i).getDateNaissance(),((Participants)L.get(i)).getNiveauEtude(),L.get(i).getImage());
                hboxParticipant.getChildren().add(hb);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        
        }
        
        
        
        ServiceFormateur spF = new ServiceFormateur();
        ObservableList <Utilisateurs> F =  FXCollections.observableArrayList();
        
        
        try {
            F = spF.AfficherUtlisaterus();
           // totalParticipant.setText(Integer.toString(sp.CountParticipant()));
           totalFormateurs.setText(Integer.toString(spF.CountFormateurs()));
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursCardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i=0;i<F.size();i++)
        {
        
           FXMLLoader fxmlloader = new FXMLLoader();
           fxmlloader.setLocation(getClass().getResource("CardFormateur.fxml"));
           
            try {
                HBox hb = fxmlloader.load();
                CardFormateurController CC = fxmlloader.getController();
              //  hb.setId(String.valueOf(L.get(i).getId()));
                CC.setData(F.get(i).getNom(),F.get(i).getPrenom(),F.get(i).getEmail(),
                        F.get(i).getId(),F.get(i).getDateNaissance(),((Formateurs)F.get(i)).getEtat(),F.get(i).getImage());
                hboxFormateurs.getChildren().add(hb);
            } catch (IOException ex) {
                System.out.println(ex);

            }


        
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
        
        
         
         
        ObservableList <Utilisateurs> comformations =  FXCollections.observableArrayList();
        ServiceParticipant sc=new ServiceParticipant();
        comformations =sc.chercherParticipants(chercher.getText(), 1);
        ObservableList <Utilisateurs> L =  FXCollections.observableArrayList();
        L = sc.AfficherUtlisaterus();
        
         if (chercher.getText().isEmpty())
         {
             
             hboxParticipant.getChildren().clear();
         hboxParticipant.setMaxSize(0, 0);
         hboxParticipant.setMinSize(0, 0);
              for (int i=0;i<L.size();i++)
        {
        
           FXMLLoader fxmlloader = new FXMLLoader();
           fxmlloader.setLocation(getClass().getResource("Card.fxml"));
           
            try {
                HBox hb = fxmlloader.load();
                CardController CC = fxmlloader.getController();
              //  hb.setId(String.valueOf(L.get(i).getId()));
                CC.setData(L.get(i).getNom(),L.get(i).getPrenom(),L.get(i).getEmail(),
                        L.get(i).getId(),L.get(i).getDateNaissance(),((Participants)L.get(i)).getNiveauEtude(),L.get(i).getImage());
                hboxParticipant.getChildren().add(hb);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        
        }
         }
         else
         {
             
         hboxParticipant.getChildren().clear();
         hboxParticipant.setMaxSize(0, 0);
         hboxParticipant.setMinSize(0, 0);
         for (int i=0;i<comformations.size();i++)
        {
        
           FXMLLoader fxmlloader = new FXMLLoader();
           fxmlloader.setLocation(getClass().getResource("Card.fxml"));
           
            try {
                HBox hb = fxmlloader.load();
                CardController CC = fxmlloader.getController();
              //  hb.setId(String.valueOf(L.get(i).getId()));
                CC.setData(comformations.get(i).getNom(),comformations.get(i).getPrenom(),comformations.get(i).getEmail(),
                        comformations.get(i).getId(),comformations.get(i).getDateNaissance(),((Participants)comformations.get(i)).getNiveauEtude()
                        ,comformations.get(i).getImage());
                hboxParticipant.getChildren().add(hb);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        
        }
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
