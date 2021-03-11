
//Packages and Imports

package academiccalendar.ui.editrule;

import academiccalendar.data.model.Model;
import academiccalendar.database.DBHandler;
import academiccalendar.ui.editevent.EditEventController;
import academiccalendar.ui.listrules.ListRulesController;
import academiccalendar.ui.main.FXMLDocumentController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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

public class EditRuleController implements Initializable {

    // Database object
    DBHandler databaseHandler;
    
    // Main Controller -------------------------------
    private FXMLDocumentController mainController;
    
    //Set main controller
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController ;
    }
    // ------------------------------------------------
    
    // Main Controller -------------------------------
    private ListRulesController listController;
    
    public void setListController(ListRulesController listController) {
        this.listController = listController ;
    }
    // ------------------------------------------------
    
    @FXML
    private AnchorPane rootPane;
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
    
    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    //Auto fill the edit window with the rule's current information
    private void autofill(){
        
        // Retrive rule data
        String days = Integer.toString(Model.getInstance().rule_days);
        String descript = Model.getInstance().rule_descript;
        String term = Model.getInstance().rule_term;
      
        // Show current data
        eventDescript.setText(descript);
        daysFromStart.setText(days);
        termSelect.getSelectionModel().select(term);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //*** Instantiate DBHandler object *******************
        databaseHandler = new DBHandler();
        //****************************************************
        
        //Get the list of exisitng terms from the database and show them in the correspondent drop-down menu
        ObservableList<String> termsList;
        try {
            //Get terms from database and store them in the ObservableList variable "termsList"
            termsList = databaseHandler.getListOfTerms();
            //Show list of terms in the drop-down menu
            termSelect.setItems(termsList);
        } catch (SQLException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
        //Auto fill the edit window with the rule's current information
        autofill();
        
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
        //Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    //update the selected rule
    @FXML
    private void save(MouseEvent event) {
        updateRule();
    }

    @FXML
    private void cancel(MouseEvent event) {
        //Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    private void updateRule(){
        
        // Define date format
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        //**********************************************************************
        //*******   Get OLD INFO of the Rule to be edited/upated which    ******
        //*******   is the term, description, and days from term.         ******
        //**********************************************************************
         
        String term = Model.getInstance().rule_term;
        String descript = Model.getInstance().rule_descript;
        int days = Model.getInstance().rule_days;
        
        //Get the ID of the old term
        int termID = databaseHandler.getTermID(term);

        //**********************************************************************
        //******    Get NEW INFO for the Rule to be edited/updated    **********
        //**********************************************************************
               
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
        
        //Get new info for the rule to be updated
        String newTerm = termSelect.getValue();
        String newDescript = eventDescript.getText();
        int newDays = Integer.parseInt(daysFromStart.getText());
        
        //Get the ID of the new term selected
        int newTermID = databaseHandler.getTermID(newTerm);
        
        //Query will update the selected rule with the new information
        String updateRuleQuery = "UPDATE RULES"
                                + " SET "
                                + "EventDescription='" + newDescript + "', "
                                + "DaysFromStart=" + newDays + ","
                                + "TermID=" + newTermID
                                + " WHERE "
                                + "RULES.EventDescription='" + descript + "' AND "
                                + "RULES.DaysFromStart=" + days + " AND "
                                + "RULES.TermID=" + termID;
        
        
        //Execute query in order to update the info for the selected event
        //and
        //Check if the update of the event in the database was successful, and show message either if it was or not
        if(databaseHandler.executeAction(updateRuleQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Rule was updated successfully");
            alertMessage.showAndWait();
            
            //update view of the list of rules
            listController.loadData();
        }
        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Updating Rule Failed!\nThere is already a rule with the same information");
            alertMessage.showAndWait();
        }
        
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
} //end of EditRuleController class
