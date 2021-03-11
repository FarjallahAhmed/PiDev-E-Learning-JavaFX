
package academiccalendar.ui.listterms;

import academiccalendar.data.model.Model;
import academiccalendar.database.DBHandler;
import academiccalendar.ui.editterm.EditTermController;
import academiccalendar.ui.main.FXMLDocumentController;
import academiccalendar.ui.main.Term;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ListTermsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label topLabel;
    @FXML
    private TableView<Term> tableView;
    @FXML
    private TableColumn<Term, String> termCol;
    @FXML
    private TableColumn<Term, String> dateCol;
    
    // Main Controller -------------------------------
    private FXMLDocumentController mainController;
    
    public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController ;
    }
    // -------------------------------------------------------------------
    
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    DBHandler databaseHandler;
    //--------------------------------------------------------------------
    
     ObservableList<Term> list = FXCollections.observableArrayList();
    
    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    private void editTermEvent() {
        
        if (!tableView.getSelectionModel().isEmpty()){
            // Get selected term data
            Term term = tableView.getSelectionModel().getSelectedItem();

            // Store term data
            Model.getInstance().term_name = term.getTermName();
            Model.getInstance().term_date = term.getTermDate();

            // When user clicks on any date in the calendar, event editor window opens
            try {
               // Load root layout from fxml file.
               FXMLLoader editTermLoader = new FXMLLoader();
               editTermLoader.setLocation(getClass().getResource("/academiccalendar/ui/editterm/edit_term.fxml"));
               AnchorPane rootLayout = (AnchorPane) editTermLoader.load();
               Stage stage = new Stage(StageStyle.UNDECORATED);
               stage.initModality(Modality.APPLICATION_MODAL); 

               EditTermController termController = editTermLoader.getController();
               termController.setListController(this);

               // Show the scene containing the root layout.
               Scene scene = new Scene(rootLayout);
               stage.setScene(scene);
               stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void initCol() {
        termCol.setCellValueFactory(new PropertyValueFactory<>("termName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("termDate"));
    }
    
    public void loadData(){
        
        // wipe current rules to add updates rules from database
        tableView.getItems().clear();
        list.clear();
        
        //Load all calendars into the Calendar View Table
        String qu = "SELECT * FROM TERMS";
        ResultSet result = databaseHandler.executeQuery(qu);
        
        try {
            while (result.next()) {
                String termName = result.getString("TermName");
                String termDate = result.getString("TermStartDate");
                
                list.add(new Term(termName, termDate));

               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListTermsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableView.getItems().setAll(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //*** Instantiate DBHandler object *******************
        databaseHandler = new DBHandler();
        //****************************************************
        
        initCol();
        loadData();
        
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

    @FXML
    private void editTerm(MouseEvent event) {
        editTermEvent();
    }
    
    /*
    @FXML
    private void deleteTerm(MouseEvent event) {
        
        if(!tableView.getSelectionModel().isEmpty()) {
            //Show confirmation dialog to make sure the user want to delete the selected rule
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Term Deletion");
            alert.setContentText("WARNING!\nThis action cannot be undone!\nAre you sure you want to permanently delete this term?");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            //Set buttons onto the confirmation dialog
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();

            //If the user wants to delete the rule, call the function that deletes the rule. Otherwise, close the window
            if (result.get() == buttonTypeYes){
                deleteSelectedTerm();
            } 
            else 
            {
                // Close the window
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close(); 
            }
        }
    }*/
    
    /*
    public void deleteSelectedTerm(){
        
        // Get selected term data
        Term term = tableView.getSelectionModel().getSelectedItem();

        // Store term data
        String termName = term.getTermName();
        String termStartDate = term.getTermDate();
        //Get termID that corresponds to the selected term to be deleted
        int auxTermID = databaseHandler.getTermID(termName);
        
        //***************************************************************
        //***  First, Delete all events related to the selected term  ***
        //***  Second, Delete all rules related to the selected term  ***
        //***  Third, Delete the selected term                        ***
        //***************************************************************
        
        //Query that will delete all events that are associated to to the selected term
        String deleteEventsQuery = "DELETE FROM EVENTS "
                                 + "WHERE EVENTS.TermID=" + auxTermID;
        
        System.out.println(deleteEventsQuery);
        
        //Query that will delete all rules that are associated to the selected term
        String deleteRulesQuery = "DELETE FROM RULES "
                                 + "WHERE RULES.TermID=" + auxTermID;
        
        System.out.println(deleteRulesQuery);
        
        //Query that will delete the selected calendar, AFTER all its events had been deleted
        String deleteTermQuery = "DELETE FROM TERMS "
                                    + "WHERE TERMS.TermID=" + auxTermID;
        
        System.out.println(deleteTermQuery);
        
        //Execute query that deletes all events associated to the selected term
        boolean eventsWereDeleted = databaseHandler.executeAction(deleteEventsQuery);
        //Execute query that deletes all events associated to the selected term
        boolean rulesWereDeleted = databaseHandler.executeAction(deleteRulesQuery);
        
        if (eventsWereDeleted && rulesWereDeleted)
        {
            System.out.println("All events and rules associated to the selected term were successfully deleted. Deleting TERM is next");
            //Execute query that deletes the selected term
            boolean termWasDeleted = databaseHandler.executeAction(deleteTermQuery);
            
            //Check if the selected term was deleted
            if (termWasDeleted)
            {
                //Show message indicating that the selected term was deleted
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("Term was successfully deleted");
                alertMessage.showAndWait();
                
                // Close the window, so that when user clicks on "Manage Your Terms" only the remaining existing terms appear
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.close();
            }
            else
            {
                //Show message indicating that the term could not be deleted
                Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("Deleting Term Failed!");
                alertMessage.showAndWait();
            }
        }
        else
        {
            //Show message indicating that the term could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Deleting Term Failed!");
            alertMessage.showAndWait();
            System.out.println("Deleting Events and Rules associated with Selected Term Failed!!!");
        }     
    }*/
    
}
