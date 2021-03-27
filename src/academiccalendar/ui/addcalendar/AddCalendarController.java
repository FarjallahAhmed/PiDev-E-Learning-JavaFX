
//Packages and Imports

package academiccalendar.ui.addcalendar;

import Entities.Calendar;
import Entities.Workshop;
import academiccalendar.data.model.Model;
import academiccalendar.ui.main.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import Service.implCalendarService;
import Service.implWorkshopService;
import Utils.Mask;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddCalendarController implements Initializable {
    
    
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    //--------------------------------------------------------------------

    // Controllers
    private FXMLDocumentController mainController ;

    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController ;
    }
    
    @FXML
    private Label topLabel;
    @FXML
    private Label exit;
    @FXML
    private JFXTextField calendarName;
    @FXML
    private JFXComboBox<String> startYear;
    @FXML
    private JFXComboBox<String> endYear;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXButton generate;
    @FXML
    private JFXButton cancel;
    
    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    void generateNewCalendar(MouseEvent event) {
        
        //Variable that holds the calendar name entered by the user
        String calName = calendarName.getText();
        
        //Check if the user actually gave input for the calendar name and the start date of the calendar
        if ( (date.getValue() != null) && (!calendarName.getText().isEmpty())) {
            
            //Check if the calendar name contains the character ~ because it cannot contain it due to database and filtering issues
            if (calName.contains("~"))
            {
                //Show message indicating that the calendar cannot contain the character ~
                Alert alertMessage = new Alert(Alert.AlertType.WARNING);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("Calendar name cannot contain the character ~");
                alertMessage.showAndWait();
            }
            else
            {
               // Set the starting and ending years, and the starting date, and store them in a Model object
                Model.getInstance().calendar_start_date = "" + date.getValue();
                Model.getInstance().calendar_start = date.getValue().getYear();
                Model.getInstance().calendar_end = date.getValue().getYear() + 1;
                Model.getInstance().calendar_name = calendarName.getText();

                // Define date format
                DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // Get the date value from the date picker
                String startingDate = date.getValue().format(myFormat);

                //Store calendar's information in String variables that will be used to build the query to insert it into the database
                String startingYear = Integer.toString(Model.getInstance().calendar_start);
                String endingYear = Integer.toString(Model.getInstance().calendar_end);
                String calName2 = calendarName.getText();

                //************************************************************************
                //************************************************************************
                //
                //********  Inserting the new calendar data into the database  ***********

                //*** Instantiate DBHandler object *******************
                //****************************************************

                // Query that inserts the new calendar into the database
                implCalendarService CService = new implCalendarService();
                Calendar c = new Calendar(3,calName2, startingYear, endingYear, startingDate);
                System.out.println(c);
                try {
                    CService.ajouter(c);
                     Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("Calendar was created successfully");
                    alertMessage.showAndWait();

                    // Load the calendar in the main window
                    mainController.calendarGenerate();

                    //Enable the checkboxes for filtering events, now that the user is actually working on a calendar
                    mainController.enableCheckBoxes();
                    
                    this.cancel(event);

                    //Enable the buttons that work with rules
                    //mainController.enableButtons();
                } catch (SQLException ex) {
                    Logger.getLogger(AddCalendarController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Insert the new calendar into the database and show a message wheher the insertion was successful or not
              /*  if(databaseHandler.executeAction(calendarQuery)) 
                {
                    Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("Calendar was created successfully");
                    alertMessage.showAndWait();

                    // Load the calendar in the main window
                    mainController.calendarGenerate();

                    //Enable the checkboxes for filtering events, now that the user is actually working on a calendar
                    mainController.enableCheckBoxes();

                    //Enable the buttons that work with rules
                    //mainController.enableButtons();
                }
                else //if there is an error
                {
                    Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                    alertMessage.setHeaderText(null);
                    alertMessage.setContentText("Creating Calendar Failed!\nPlease use a different name for the calendar");
                    alertMessage.showAndWait();
                }
*/
            }
            
        }
        else 
        {
            Alert alert = new Alert(AlertType.WARNING, "Please fill out all fields.");
            alert.showAndWait();
        }        
    }
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // ******** Code below is for Draggable windows **********    
        
        
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
        //Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(MouseEvent event) {
        //Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
}
