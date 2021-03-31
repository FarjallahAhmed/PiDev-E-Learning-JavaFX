
//Packages and Imports

package academiccalendar.ui.addevent;

import Entities.Workshop;
import academiccalendar.data.model.Model;
import academiccalendar.ui.main.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import Service.implWorkshopService;
import Service.threadMail;
import Utils.Mask;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddEventController implements Initializable {
    
    // Controllers
     private FXMLDocumentController mainController ;
     
     
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    //--------------------------------------------------------------------
    @FXML
    private HBox header;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private JFXDatePicker dateED;
    @FXML
    private JFXDatePicker dateEF;
    @FXML
    private JFXTimePicker timeED;
    @FXML
    private JFXTimePicker timeEF;
    @FXML
    private JFXTextField nbP;
    @FXML
    private JFXTextArea desc;
    @FXML
    private JFXTextField prix;
    
    
    //Set main controller
    public void setMainController(FXMLDocumentController mainController) {
        
        this.mainController = mainController ;
    }

    // Structure
    @FXML
    private Label topLabel;
    @FXML
    private AnchorPane rootPane;
    
    // Text fields
    
    
    // Buttons
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    
    
    
    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    @FXML
    void exit(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void cancel(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    //Function that inserts a new event in the database
     @FXML
    void save(MouseEvent event) throws WriterException, IOException {
        
        // Get the calendar name
        String calendarName = Model.getInstance().calendar_name;
         System.out.println(calendarName);
         
         
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");      
        
        
        //Check if the user inputted information in all required fields!
        if(title.getText().isEmpty()|| type.getSelectionModel().isEmpty()
                ||dateED.getValue() == null ||dateEF.getValue() == null
                ||timeEF.getValue() == null ||timeED.getValue() == null
                ||prix.getText().isEmpty()||desc.getText().isEmpty()
                ||dateED.getValue().isAfter(dateEF.getValue())){
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Please fill out all fields or Date Verification ");
            alertMessage.showAndWait();
            return;
        }
        
        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (title.getText().contains("~"))
        {
            //Show message indicating that the event description cannot contain the character ~
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Event Description cannot contain the character ~");
            alertMessage.showAndWait();
            return;
        }
        
        //If all data is inputted correctly and validated, then add the event:
        

        String dateD = dateED.getValue().format(myFormat);
        String dateF = dateEF.getValue().format(myFormat);
        String titleE = title.getText();
        String typeE = type.getValue();       
        Time timeD = Time.valueOf(timeED.getValue());
        Time timeF = Time.valueOf(timeEF.getValue());
        float prix = Float.parseFloat(this.prix.getText());
        String description = desc.getText();
        int nbparticipant  = Integer.parseInt(nbP.getText());

        implWorkshopService wService = new implWorkshopService();
        Workshop w = new Workshop(0,Model.getInstance().calendar_name ,titleE,Date.valueOf(dateD),Date.valueOf(dateF), timeD, timeF, typeE, nbparticipant, typeE, description, prix);
        
         try {
             wService.ajouter(w);
             //wService.sendMail(w);
             threadMail mail = new threadMail(w);
             //mail.sendMail(w);
             mail.start();
             Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Event was added successfully");
            alertMessage.showAndWait();
         } catch (SQLException ex) {
              Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Adding Event Failed!\nThere is already an event with the same information");
            alertMessage.showAndWait();
         }

                       
        
        
        

        
        //Show the new event on the calendar according to the selected filters
        mainController.repaintView();
            
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    
    //Function that fills the date picker based on the clicked date 
    void autofillDatePicker() {
       // Get selected day, month, and year and autofill date selection
       int day = Model.getInstance().event_day;
       int month = Model.getInstance().event_month + 1;
       int year = Model.getInstance().event_year;
       
       // Set default value for datepicker
       dateED.setValue(LocalDate.of(year, month, day));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Mask.noLetters(nbP);
        Mask.noLetters(prix);
        
        //*** Instantiate DBHandler object *******************
        //****************************************************
        
        
        //Fill the date picker
        autofillDatePicker();
        type.getItems().add("Soft Skills");
         type.getItems().add("Team building");
         type.getItems().add("Conference");
         type.getItems().add("Seminaire");
        // Define date format
        
        //Get the list of exisitng terms from the database and show them in the correspondent drop-down menu
        /* try {
             //Get terms from database and store them in the ObservableList variable "terms"
             //ObservableList<String> terms = databaseHandler.getListOfTerms();
             //Show list of terms in the drop-down menu
             type.setItems(terms);
         } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
         }*/
   
        //**********************************************************************
        // ************* Everything below is for Draggable Window ********
        
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        // Change cursor when hover over draggable area
        topLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        
        // Change cursor when hover over draggable area
        topLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
    }    
    
}
