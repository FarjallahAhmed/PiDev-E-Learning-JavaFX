
package academiccalendar.ui.listrules;

import academiccalendar.data.model.Model;

import academiccalendar.database.DBHandler;
import academiccalendar.ui.editrule.EditRuleController;
import academiccalendar.ui.main.FXMLDocumentController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

public class ListRulesController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label topLabel;
    @FXML
    private TableView<academiccalendar.ui.main.Rule> tableView;
    @FXML
    private TableColumn<academiccalendar.ui.main.Rule, String> eventCol;
    @FXML
    private TableColumn<academiccalendar.ui.main.Rule, String> termCol;
    @FXML
    private TableColumn<academiccalendar.ui.main.Rule, String> daysCol;
    
    // Main Controller -------------------------------
    private FXMLDocumentController mainController;
    // -------------------------------------------------------------------
    
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    DBHandler databaseHandler;
    //--------------------------------------------------------------------
    
    ObservableList<academiccalendar.ui.main.Rule> list = FXCollections.observableArrayList();

    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
     public void setMainController(FXMLDocumentController mainController) {
        this.mainController = mainController ;
    }
    
    public void initCol() {
        eventCol.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        termCol.setCellValueFactory(new PropertyValueFactory<>("termID"));
        daysCol.setCellValueFactory(new PropertyValueFactory<>("daysFromStart"));
    }
   
    public void loadData(){
        
        // wipe current rules to add updates rules from database
        tableView.getItems().clear();
        list.clear();
        
        //Load all rules into the Rule View Table
        String qu = "SELECT RULES.EventDescription, TERMS.TermName, RULES.DaysFromStart FROM RULES, TERMS WHERE RULES.TermID=TERMS.TermID";
        //String qu = "SELECT * FROM RULES";
        ResultSet result = databaseHandler.executeQuery(qu);
        
        try {
            while (result.next()) {
                
                String eventSubject = result.getString("EventDescription");
                
                String termIDAux = result.getString("TermName");
                //String termIDAux = Integer.toString(result.getInt("TermID"));
                //This line checks that a umber or string is at least gotten from the result that was gotten from th Results table
                System.out.println("termIDAux is: " + termIDAux);
                
                String daysFromStart = Integer.toString(result.getInt("DaysFromStart"));
                
                //these lines check each result gotten from the RULES table
                System.out.println("result is:  " + eventSubject + " - " + termIDAux + " - " + daysFromStart);
                System.out.println();
                /*
                Rule ruleObject = new Rule(eventSubject, termIDAux, daysFromStart);
                String auxID = ruleObject.getTermIDOfRule();
                System.out.println("auxID is: " + auxID);
                */
                
                list.add(new academiccalendar.ui.main.Rule(eventSubject, termIDAux, daysFromStart));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListRulesController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void addSelectedRule(MouseEvent event) {
        
        if (!tableView.getSelectionModel().isEmpty()){
            // Get the information of selected rule on the table view
            academiccalendar.ui.main.Rule rule = tableView.getSelectionModel().getSelectedItem();        
            String eventSubject = rule.getEventDescription();
            String auxTermName = rule.getTermID();
            int auxTermID = databaseHandler.getTermID(auxTermName);
            String auxDaysFromStart = rule.getDaysFromStart();
            System.out.println(eventSubject);
            System.out.println(auxTermID);
            System.out.println("days from starts are: " + auxDaysFromStart);

            // Get the calendar name
            String auxCalName = Model.getInstance().calendar_name;
            
            //Create event based on selected rule
            boolean eventWasCreated = createEventFromRule(eventSubject, auxTermID, auxDaysFromStart, auxCalName);
            
            //Check if event was create and show the correspondent message to the user
            if (eventWasCreated)
            {
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("Event was created based on a rule successfully");
                alertMessage.showAndWait();    
            }
            else
            {
                Alert alertMessage = new Alert(Alert.AlertType.ERROR);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("Creating event based on a rule failed!\nThere is an event already created based on this rule");
                alertMessage.showAndWait();
            }
        }
        
    }

    @FXML
    private void addAllRules(MouseEvent event) {
                
        // Get the calendar name
        String auxCalName = Model.getInstance().calendar_name;
        
        //Get list of rules from database and store it in an ArrayList variable
        ArrayList<String> listOfRules = databaseHandler.getListOfRules();
        
        //Store number of rules (size of array list)
        int listSize = listOfRules.size();
        //Variable that keeps track of the number of events created
        int auxEventsCreated = 0;
        
        //Check if array list of rules is not empty
        if (!listOfRules.isEmpty())
        {
            //Loop that creates all events based on rules
            for (int i=0; i < listSize; i++)
            {
                //Splits rule by EventDescription, TermID, and DaysFromStart
                String[] auxRule = listOfRules.get(i).split("~");
                //Create event based on this rule
                boolean eventWasCreated = createEventFromRule(auxRule[0], Integer.parseInt(auxRule[1]), auxRule[2], auxCalName);
                
                //Increase number of events created
                if (eventWasCreated)
                {
                    auxEventsCreated++;
                }      
            }
            
            if (auxEventsCreated > 0)
            {
                //Show message letting the user know that events were created successfully based on all rules
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText(auxEventsCreated + " Events Were Successfully Created Based On All Rules!");
                alertMessage.showAndWait();
            }
            if (auxEventsCreated == 0)
            {
                //Show message letting the user know that no events were created because they already exist
                Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
                alertMessage.setHeaderText(null);
                alertMessage.setContentText("No events were created based on the rules because they already exist!");
                alertMessage.showAndWait();
            }
        }
        else
        {
            //Show message letting the user know that events were created successfully based on all rules
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Action Failed! There aren't any saved rules!");
            alertMessage.showAndWait();
        }
    }
    
    
    @FXML
    private void editRule(MouseEvent event) {
        editRuleEvent();
    }

    
    @FXML
    private void deleteRule(MouseEvent event) {
        
        if(!tableView.getSelectionModel().isEmpty()) {
            //Show confirmation dialog to make sure the user want to delete the selected rule
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Rule Deletion");
            alert.setContentText("Are you sure you want to delete this rule?");
            //Customize the buttons in the confirmation dialog
            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");
            //Set buttons onto the confirmation dialog
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            //Get the user's answer on whether deleting or not
            Optional<ButtonType> result = alert.showAndWait();

            //If the user wants to delete the rule, call the function that deletes the rule. Otherwise, close the window
            if (result.get() == buttonTypeYes){
                deleteSelectedRule();
            }
            
        }
        
    }
    
    private void editRuleEvent() {
        
        if (!tableView.getSelectionModel().isEmpty()){
            // Get selected rule data
            academiccalendar.ui.main.Rule rule = tableView.getSelectionModel().getSelectedItem();

            // Store rule data
            Model.getInstance().rule_term = rule.getTermID();
            Model.getInstance().rule_descript = rule.getEventDescription();
            Model.getInstance().rule_days = Integer.parseInt(rule.getDaysFromStart());

            // When user clicks on any date in the calendar, event editor window opens
            try {
               // Load root layout from fxml file.
               FXMLLoader editRuleLoader = new FXMLLoader();
               editRuleLoader.setLocation(getClass().getResource("/academiccalendar/ui/editrule/edit_rule.fxml"));
               AnchorPane rootLayout = (AnchorPane) editRuleLoader.load();
               Stage stage = new Stage(StageStyle.UNDECORATED);
               stage.initModality(Modality.APPLICATION_MODAL); 

               EditRuleController ruleController = editRuleLoader.getController();
               ruleController.setMainController(mainController);
               ruleController.setListController(this);

               // Show the scene containing the root layout.
               Scene scene = new Scene(rootLayout);
               stage.setScene(scene);
               stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void deleteSelectedRule() {
        
        // Get the information of selected rule on the table view
        academiccalendar.ui.main.Rule rule = tableView.getSelectionModel().getSelectedItem();        
        String eventSubject = rule.getEventDescription();
        String auxTermName = rule.getTermID();
        int auxTermID = databaseHandler.getTermID(auxTermName);
        System.out.println(eventSubject);
        System.out.println(auxTermID);

        //Query that will delete the selected rule
        String deleteRulesQuery = "DELETE FROM RULES "
                                 + "WHERE RULES.EventDescription='" + eventSubject + "' "
                                 + "AND RULES.TermID=" + auxTermID;

        System.out.println(deleteRulesQuery);


        //Execute query that deletes the selected rule
        boolean ruleWasDeleted = databaseHandler.executeAction(deleteRulesQuery);

        if (ruleWasDeleted)
        {
            //Show message indicating that the selected rule was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Selected rule was successfully deleted");
            alertMessage.showAndWait();
            
            
            // Close the window, so that when user clicks on "Manage Rules" only the remaining existing rules appear
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
        else
        {
            //Show message indicating that the rule could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Deleting Rule Failed!");
            alertMessage.showAndWait();
        }  
    }
    
    //**************************************************************************************************************************
    //************************    Auxiliary Functions For Creating Events Based On Rules    ************************************
    //**************************************************************************************************************************
    
    //Function that creates event based on rule and inserts it into the database in the EVENTS table
    //and return a boolean variable indicating if it was created or not
    public boolean createEventFromRule(String evtDescription, int auxTermID, String auxDaysFromStart, String auxCalName) {
        
        //Booolean variable to be returned whether or not the event creation was successful
        boolean eventWasCreated = false;
        
        //Get the start date of the term specified by the rule
        String termStartDate = databaseHandler.getTermStartDate(auxTermID);
        
        //Calculate the new date for the event by adding auxDaysFromStart to the termStartDate
        String newEventDate = getNewEventDate(termStartDate, auxDaysFromStart);
        
        
        //Create Query that will insert the event into the database table EVENTS
        String insertEventQuery = "INSERT INTO EVENTS VALUES ("
                                + "'" + evtDescription + "', "
                                + "'" + newEventDate + "', "
                                + auxTermID + ", "
                                + "'" + auxCalName + "'"
                                + ")";
        
        System.out.println(insertEventQuery);
        
        //Execute the query that insert the new event
        //Check if insertion into database was successful, and show message either if it was or not
        if(databaseHandler.executeAction(insertEventQuery)) 
        {
            //Change boolean variable to true if event was created
            eventWasCreated = true;
            
            //Show the new event in the table view
            mainController.repaintView();
        }
        else //if there is an error
        {
            return eventWasCreated;   
        }
        
        return eventWasCreated;
    }
    
    //Function that returns a new date for the event created based on a rule: add days from start to term's start date
    public String getNewEventDate (String auxTermStartDate, String auxDays) {
        
        //Variable that holds the new date for the event that will be created based on a rule
        String newEventDate = "none";
        //Format needed to be saved in the database
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("auxTermStartDate value is: " + auxTermStartDate);
        
        //Convert number of days from Start to Integer type
        int auxDaysFromStart = Integer.parseInt(auxDays);
        
        //ADD DAYS to the term's start date
        try {
            //Create Date object that holds the start date of a term
            Date auxDate = myDateFormat.parse(auxTermStartDate);
            System.out.println("auxDate value is: " + auxDate);
            
            //Create Calendar object to use its add method for adding days to a date
            Calendar auxCal = Calendar.getInstance();
            //Set the calendar's date to the auxDate (which is the term's start date)
            auxCal.setTime(auxDate);
            //Add the days from the start to the TERM's start date to get new date for an event
            auxCal.add(Calendar.DATE, auxDaysFromStart);
            //Get the new date that resulted from adding the days from the start to the term start date
            auxDate = auxCal.getTime();
        
            
            System.out.println("The new date is: " + auxDate);
            
            //Assign new date value to the String variable that will be returned
            newEventDate = myDateFormat.format(auxDate);
            System.out.println("Formatted new event date is: " + auxDate);
            
            System.out.println("newEventDate is: " + newEventDate);
                 
        } catch (ParseException ex) {
            Logger.getLogger(ListRulesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return newEventDate;
    }
    //**************************************************************************************************************************
    //************************************    End Of Auxiliary Functions    ****************************************************
    //**************************************************************************************************************************
    
}
