
//Packages and Imports

package academiccalendar.ui.editevent;

import Entities.Workshop;
import academiccalendar.data.model.Model;
import academiccalendar.ui.addevent.AddEventController;
import academiccalendar.ui.main.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import Service.implWorkshopService;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EditEventController implements Initializable {
    
    // Main Controller -------------------------------
    private FXMLDocumentController mainController;
    // -------------------------------------------------------------------
    
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    //--------------------------------------------------------------------
    @FXML
    private Label topLabel;
    private JFXTextField subject;
    private JFXComboBox<String> termSelect;
    private JFXDatePicker date;
    @FXML
    private AnchorPane rootPane;
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
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField prix;
    @FXML
    private JFXButton deleteW;
    
    //Set main controller
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController ;
    }

    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    
    //Function that fills the date picker based on the clicked event's date
    void autofillDatePicker() {
        
        // Get selected day, month, and year and autofill date selection
        int day = Model.getInstance().event_day;
        int month = Model.getInstance().event_month + 1;
        int year = Model.getInstance().event_year;
        int termID = Model.getInstance().event_term_id;
        String descript = Model.getInstance().event_subject;
       

       
        
        Workshop w = new Workshop();
        implWorkshopService wService = new implWorkshopService(); 
        
        
        try {
            System.out.println("id "+termID);
            w = wService.find(termID);
            System.out.println(w);
            dateED.setValue(LocalDate.of(year, month, day));
            dateEF.setValue(w.getDateFin().toLocalDate());
            title.setText(w.getNomEvent());
            type.setValue(w.getType());
            timeED.setValue(w.gethDebut().toLocalTime());
            timeEF.setValue(w.gethFin().toLocalTime());
            nbP.setText(String.valueOf(w.getNbParticipant()));
            
            prix.setText(String.valueOf(w.getPrix()));
            desc.setText(w.getDescription());
        } catch (SQLException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
       // Set default value for datepicker
       
       
       //Fill term drop-down menu with current event's term
       //type.getSelectionModel().select(chosenTermName);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        

        
        //Fill the date picker
        autofillDatePicker();
        type.getItems().add("Soft Skills");
         type.getItems().add("Team building");
         type.getItems().add("Conference");
         type.getItems().add("Seminaire");
        
        //Get the list of exisitng terms from the database and show them in the correspondent drop-down menu
       /* ObservableList<String> termsList;
        try {
            //Get terms from database and store them in the ObservableList variable "termsList"
            termsList = databaseHandler.getListOfTerms();
            //Show list of terms in the drop-down menu
           // termSelect.setItems(termsList);
        } catch (SQLException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        */
        
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

    @FXML
    private void exit(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void update(MouseEvent event) {
        updateEvent();
    }

    //Function that deletes a selected event
    private void delete(MouseEvent event) {
        /*
        //Show confirmation dialog to make sure the user want to delete the selected event
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Event Deletion");
        alert.setContentText("Are you sure you want to delete this event?");
        //Customize the buttons in the confirmation dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        //Set buttons onto the confirmation window
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        //Get the user's answer on whether deleting or not
        Optional<ButtonType> result = alert.showAndWait();
        
        //If the user wants to delete the event, call the function that deletes the event. Otherwise, close the window
        if (result.get() == buttonTypeYes){
            deleteEvent();
        } 
        else 
        {
            // Close the window
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close(); 
        }*/implWorkshopService wService = new implWorkshopService();
        
        try {
            wService.delete(93);
             mainController.repaintView();
            
           
        } catch (SQLException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        
        
    }
    
    
    //Function that updates the information of a selected event from the calendar
    public void updateEvent(){
        
        // Define date format
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        //**********************************************************************
        //*******   Get OLD INFO of the Event to be edited/upated         ******
        //*******   which is the term ID, event description, event date,  ******
        //*******   and calendar name of the event to be edited           ******
        //**********************************************************************
       
        int day = Model.getInstance().event_day;
        int month = Model.getInstance().event_month + 1;
        int year = Model.getInstance().event_year;
        String eventDate = year + "-" + month + "-" + day;
        int termID = Model.getInstance().event_term_id;
         System.out.println("id"+termID);
        String descript = Model.getInstance().event_subject;
        String calName = Model.getInstance().calendar_name; 
        
        
        
        
        
        //Get the original date of the event to be updated in the format yyyy-mm-dd
        SimpleDateFormat auxDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String auxDate = "empty";
        Date auxEventDate = new Date();
        try {
            auxEventDate = auxDateFormat.parse(eventDate);
            auxDate = auxDateFormat.format(auxEventDate);
        } catch (ParseException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        //**********************************************************************
        //******    Get NEW INFO for the Event to be edited/updated   **********
        //**********************************************************************
        
        // Get the date value from the date picker
        String newCalendarDate = dateED.getValue().format(myFormat);
        // Subject for the event
        String newEventSubject = title.getText();
        // Get term that was selected by the user
        String term = (String) type.getValue();
        
        
        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (newEventSubject.contains("~"))
        {
            //Show message indicating that the event description cannot contain the character ~
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Event Description cannot contain the character ~");
            alertMessage.showAndWait();
            return;
        }
        
        
        //Get the ID of the new term selected by the user when editing the event's information
        
        //Query to will update the selected event with the new information
       /* String updateEventQuery = "UPDATE EVENTS"
                                + " SET "
                                + "EventDescription='" + newEventSubject + "', "
                                + "EventDate='" + newCalendarDate + "', "
                                + "TermID=" + newTerm
                                + " WHERE "
                                + "EVENTS.EventDescription='" + descript + "' AND "
                                + "EVENTS.EventDate='" + auxDate + "' AND "
                                + "EVENTS.TermID=" + termID + " AND "
                                + "EVENTS.CalendarName='" + calName + "' ";
        
        
        //Execute query in otder to update the info for the selected event
        //and
        //Check if the update of the event in the database was successful, and show message either if it was or not
        if(databaseHandler.executeAction(updateEventQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Event was updated successfully");
            alertMessage.showAndWait();
            
            // Update view
            mainController.repaintView();
            
        }
        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Updating Event Failed!\nThere is already an event with the same information");
            alertMessage.showAndWait();
        }
        */
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        
    }
    
    
    @FXML
    public void deleteEvent() {
        
       implWorkshopService wService = new implWorkshopService();
        
        try {
            wService.delete(Model.getInstance().event_term_id);
             mainController.repaintView();
            
           
        } catch (SQLException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        
    }

    @FXML
    private void save(MouseEvent event) {
        
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
       
        
        //If all data is inputted correctly and validated, then add the event:
        if(title.getText().isEmpty()|| type.getSelectionModel().isEmpty()
                ||dateED.getValue() == null ||dateEF.getValue() == null
                ||timeEF.getValue() == null ||timeED.getValue() == null
                ||prix.getText().isEmpty()||desc.getText().isEmpty()
                ||dateED.getValue().isAfter(dateEF.getValue())){
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Please fill out all fields");
            alertMessage.showAndWait();
            return;
        }else{
            String dateD = dateED.getValue().format(myFormat);
        String dateF = dateEF.getValue().format(myFormat);
        String titleE = title.getText();
        String typeE = type.getValue();       
        Time timeD = Time.valueOf(timeED.getValue());
        Time timeF = Time.valueOf(timeEF.getValue());
        float prix = Float.parseFloat(this.prix.getText());
        String description = desc.getText();
        int nbparticipant  = 50;
        
        implWorkshopService wService = new implWorkshopService();
        Workshop w = new Workshop(0, titleE,java.sql.Date.valueOf(dateD),java.sql.Date.valueOf(dateF), timeD, timeF, typeE, nbparticipant, typeE, description, prix);

        w.setId(Model.getInstance().event_term_id);
        
        try {
            wService.update(w);
            mainController.repaintView();
        } catch (SQLException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
            
        }

        
    }

    @FXML
    private void cancel(MouseEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}// end of EditEventController class
