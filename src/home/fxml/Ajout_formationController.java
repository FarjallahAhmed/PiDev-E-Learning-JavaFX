/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.fxml;

import Entities.Formations;
import Service.ServiceFormations;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class Ajout_formationController implements Initializable {

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
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnexporter;
    @FXML
    private Button affecttserPromotion;
    
    public static Formations affecterP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           Afficher_formations();
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
           codaterelle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_reelle"));
           codateprevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
           copath.setCellValueFactory(new PropertyValueFactory<Formations,String>("path"));
           tabform.setItems(list);
           
        
    }


    @FXML
    private void oncreerformation(ActionEvent event) throws IOException {
        if (event.getSource() == btnajouter) {
            
            
            
                 Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/formulaireform.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        }
        
        
        
        
    
    }

    @FXML
    private void on_exporter(ActionEvent event) {
        TableView<Formations> table = new TableView<Formations>();
         ServiceFormations Service = new ServiceFormations();
         ObservableList<Formations> data =FXCollections.observableArrayList();  
   
        
        double w = 500.00;
        // set width of table view
        table.setPrefWidth(w);
        // set resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // intialize columns
        TableColumn<Formations,String> Objet  = new TableColumn<Formations,String>("Objet");
        TableColumn<Formations,String> Type  = new TableColumn<Formations,String>("Type");
        TableColumn<Formations,Integer> nb_participants = new TableColumn<Formations,Integer>("Nb_participants");
        TableColumn<Formations,Float> couht_hj  = new TableColumn<Formations,Float>("Cout_hj");
        TableColumn<Formations,Integer> nb_jour  = new TableColumn<Formations,Integer>("nb_jour");
        TableColumn<Formations,Float> cout_fin = new TableColumn<Formations,Float>("cout_fin");
        TableColumn<Formations,Date> date_relle = new TableColumn<Formations,Date>("date_relle");
        TableColumn<Formations,Date> date_prevu  = new TableColumn<Formations,Date>("date_prevu");
        
        
            
        // set width of columns
        Objet.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        Type.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        nb_participants.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        couht_hj.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        nb_jour.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        cout_fin.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        date_relle.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width       
        date_prevu.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        
        // 
        Objet.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
        Type.setCellValueFactory( new PropertyValueFactory<Formations,String>("Type"));
        nb_participants.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
        couht_hj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
        nb_jour.setCellValueFactory( new PropertyValueFactory<Formations,Integer>("nb_jour"));
        cout_fin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
        date_relle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_relle")); 
        date_prevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
        
        
        // Add columns to the table
        table.getColumns().addAll(Objet,Type,nb_participants,couht_hj,nb_jour,cout_fin,date_relle,date_prevu);
        
        
        // Query to get ALL Events from the selected calendar!!
        //String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "' ORDER BY EventDate";
        
        // Store the results here
        //ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
        data = Service.Get_Formations();
        
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

    @FXML
    private void backDashboard(ActionEvent event) throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void getFormationInfo(MouseEvent event) {
        affecterP = tabform.getSelectionModel().getSelectedItem();
        
        
    }

    @FXML
    private void affPromotions(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        
        System.out.println(affecterP);*/
       
       
       
       Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml"));
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
          stage.show();
    }
        
        
    }

    

