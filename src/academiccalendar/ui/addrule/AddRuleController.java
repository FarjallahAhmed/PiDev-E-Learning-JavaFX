
//Packages and imports

package academiccalendar.ui.addrule;

import academiccalendar.database.DBHandler;
import academiccalendar.ui.addevent.AddEventController;
import academiccalendar.ui.main.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddRuleController implements Initializable {
    
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------

    DBHandler databaseHandler = new DBHandler();


    //--------------------------------------------------------------------
 
     // Controllers -------------------------------------------------------
    private FXMLDocumentController mainController ;
    
    //Set main controller
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController ;
    }
    // -------------------------------------------------------------------
    
    @FXML
    private Label topLabel;
    @FXML
    private JFXTextField eventDescript;
    @FXML
    private JFXComboBox<String> termSelect;
    @FXML
    private JFXTextField daysFromStart;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane rootPane;
    
    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Get the list of exisitng terms from the database and show them in the correspondent drop-down menu
         try {
             //Get terms from database and store them in the ObservableList variable "terms"
             ObservableList<String> terms = databaseHandler.getListOfTerms();
             //Show list of terms in the drop-down menu
             termSelect.setItems(terms);
         } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
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

    //Function that will get the rule's information and store it in the database
    @FXML
    private void save(MouseEvent event) {
        
        //Check if user filled out all fields
        if(eventDescript.getText().isEmpty()||termSelect.getSelectionModel().isEmpty()
                ||daysFromStart.getText().isEmpty()){
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Please fill out all fields");
            alertMessage.showAndWait();
            return;
        }
        
        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (eventDescript.getText().contains("~"))
        {
            //Show message indicating that the rule description cannot contain the character ~
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Rule Description cannot contain the character ~");
            alertMessage.showAndWait();
            return;
        }
        
        //Check if Days From Start is an integer number using the Scanner class
        Scanner auxScanner = new Scanner(daysFromStart.getText());
        if (!auxScanner.hasNextInt())
        {
            //Show message indicating that the days from start should be an integer number
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Days From Start should be an integer number");
            alertMessage.showAndWait();
            return;
        }
        
        // Get fields for rule
        String eventDescription = eventDescript.getText();
        int days = Integer.parseInt(daysFromStart.getText());
        String term = termSelect.getValue();
        

        //*********************************************************************
        //Save rule into the database
        saveRuleInDatabase(eventDescription, term, days);
        //*********************************************************************

    }

    @FXML
    private void cancel(MouseEvent event) {
        //Close window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    

    //Function that sends the query to the database for inserting the rule into the RULES table
    public void saveRuleInDatabase(String eventDescription, String termName, int daysFromStart)
    {
        //Get term ID for the term selected because it is needed to save the rule in the RULES table due int attribute called TermID
        int termID = databaseHandler.getTermID(termName);
        
        //Query that will insert the rule into the RULES table in the database
        String addRuleQuery = "INSERT INTO RULES VALUES ("
                            + "'" + eventDescription + "', "
                            + termID + ", "
                            + daysFromStart
                            + ")";
        
        //save rule into the database and show message whether or not it was saved successfully
        if(databaseHandler.executeAction(addRuleQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Rule was added successfully");
            alertMessage.showAndWait();
        }
        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Adding Rule Failed!\nThere is already a rule with the same information");
            alertMessage.showAndWait();
        } 
    }

}// end of AddRuleController class
