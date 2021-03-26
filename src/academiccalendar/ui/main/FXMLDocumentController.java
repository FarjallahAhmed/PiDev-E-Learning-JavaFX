/*
 * @Academic Calendar Version 1.0 3/7/2017
 * @owner and @author: FrumbugSoftware
 * @Team Members: Paul Meyer, Karis Druckenmiller, Darick Cayton, Rudolfo Madriz
 */

package academiccalendar.ui.main;

import Entities.Workshop;
import academiccalendar.data.model.Model;
import academiccalendar.database.DBHandler;
import academiccalendar.ui.addcalendar.AddCalendarController;
import academiccalendar.ui.addevent.AddEventController;
import academiccalendar.ui.addrule.AddRuleController;
import academiccalendar.ui.editevent.EditEventController;
import academiccalendar.ui.listcalendars.ListCalendarsController;
import academiccalendar.ui.listrules.ListRulesController;
import academiccalendar.ui.listterms.ListTermsController;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import Service.implWorkshopService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FXMLDocumentController implements Initializable {
    
    // Calendar fields
    @FXML
    private Label monthLabel;
    @FXML
    private HBox weekdayHeader;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private ScrollPane scrollPane;
    
    // Selections boxes
    @FXML
    private JFXComboBox<String> selectedYear;
    @FXML
    private JFXListView<String> monthSelect;   
    
    //--------- Database Handler -----------------------------------------
    DBHandler databaseHandler;
    //--------------------------------------------------------------------
    
    // Color pickers
    @FXML
    private JFXColorPicker fallSemCP;
    @FXML
    private JFXColorPicker springSemCP;
    @FXML
    private JFXColorPicker summerSemCP;
    @FXML
    private JFXColorPicker allQtrCP;
    @FXML
    private JFXColorPicker allMbaCP;
    @FXML
    private JFXColorPicker allHalfCP;
    @FXML
    private JFXColorPicker allCampusCP;
    @FXML
    private JFXColorPicker allHolidayCP;
    @FXML
    private JFXColorPicker traTrbCP;
    
    
    // Check Boxes for filtering
    @FXML
    private JFXCheckBox fallSemCheckBox;
    @FXML
    private JFXCheckBox springSemCheckBox;
    @FXML
    private JFXCheckBox summerSemCheckBox;
    @FXML
    private JFXCheckBox allQtrCheckBox;
    @FXML
    private JFXCheckBox allMbaCheckBox;
    @FXML
    private JFXCheckBox allHalfCheckBox;
    @FXML
    private JFXCheckBox allCampusCheckBox;
    @FXML
    private JFXCheckBox allHolidayCheckBox;
    @FXML
    private JFXCheckBox allTraTrbCheckBox;  
    @FXML
    private JFXCheckBox selectAllCheckBox; 
    
    // Other global variables for the class
    public static boolean workingOnCalendar = false;
    public static boolean checkBoxesHaveBeenClicked = false;
    
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox colorRootPane;
    @FXML
    private VBox toolsRootPane;
    @FXML
    private VBox centerArea;
    @FXML
    private Label calendarNameLbl;
    
    //**************************************************************************
    //**************************************************************************
    //**************************************************************************
    
    // Events
    private void addEvent(VBox day) {
        
        // Purpose - Add event to a day
        
        // Do not add events to days without labels
        if(!day.getChildren().isEmpty()) {
            
            // Get the day number
            Label lbl = (Label) day.getChildren().get(0);
            System.out.println(lbl.getText());
            
            // Store event day and month in data singleton
            Model.getInstance().event_day = Integer.parseInt(lbl.getText());
            Model.getInstance().event_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());      
            Model.getInstance().event_year = Integer.parseInt(selectedYear.getValue());
            
            // Open add event view
            try {
               // Load root layout from fxml file.
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("/academiccalendar/ui/addevent/add_event.fxml"));
               AnchorPane rootLayout = (AnchorPane) loader.load();
               Stage stage = new Stage(StageStyle.UNDECORATED);
               stage.initModality(Modality.APPLICATION_MODAL); 

               // Pass main controller reference to view
               AddEventController eventController = loader.getController();
               eventController.setMainController(this);
               
               // Show the scene containing the root layout.
               Scene scene = new Scene(rootLayout);
               stage.setScene(scene);
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }    
    
    private void editEvent(VBox day, String descript, String termID) {
        
        // Store event fields in data singleton
        Label dayLbl = (Label)day.getChildren().get(0);
        Model.getInstance().event_day = Integer.parseInt(dayLbl.getText());
        Model.getInstance().event_month = Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());      
        Model.getInstance().event_year = Integer.parseInt(selectedYear.getValue());
        Model.getInstance().event_subject = descript;
        Model.getInstance().event_term_id = Integer.parseInt(termID);

        // When user clicks on any date in the calendar, event editor window opens
        try {
           // Load root layout from fxml file.
            
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/academiccalendar/ui/editevent/edit_event.fxml"));
           
           AnchorPane rootLayout = (AnchorPane) loader.load();
           
           Stage stage = new Stage(StageStyle.UNDECORATED);
           stage.initModality(Modality.APPLICATION_MODAL); 

           // Pass main controller reference to view
           EditEventController eventController = loader.getController();
           eventController.setMainController(this);
           
           // Show the scene containing the root layout.
           Scene scene = new Scene(rootLayout);
           stage.setScene(scene);
           stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    public void newCalendarEvent() {
        // New Calendar view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/academiccalendar/ui/addcalendar/add_calendar.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            AddCalendarController calendarController = loader.getController();
            calendarController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listCalendarsEvent() {
        // List Calendar view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/academiccalendar/ui/listcalendars/list_calendars.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            ListCalendarsController listController = loader.getController();
            listController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void manageTermsEvent() {
        // Manage Terms view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/academiccalendar/ui/listterms/list_terms.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            ListTermsController listController = loader.getController();
            listController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listRulesEvent() {
        // List Rules view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/academiccalendar/ui/listrules/list_rules.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            ListRulesController listController = loader.getController();
            listController.setMainController(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void newRuleEvent() {
        // New Rule view
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/academiccalendar/ui/addrule/add_rule.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL); 

            // Pass main controller reference to view
            AddRuleController ruleController = loader.getController();
            ruleController.setMainController(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initializeMonthSelector(){
        
        // Add event listener to each month list item, allowing user to change months
        monthSelect.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                    // Necessary to check for null because change listener will
                    // also detect clear() calls
                    if (newValue != null) {
                        
                        // Show selected/current month above calendar
                        monthLabel.setText(newValue);
                        
                        // Update the VIEWING MONTH
                        Model.getInstance().viewing_month = Model.getInstance().getMonthIndex(newValue);

                        // Update view
                        repaintView();
                    }
                    
                }
            });
        
        // Add event listener to each year item, allowing user to change years
        selectedYear.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                    if (newValue != null){
                        
                        // Update the VIEWING YEAR
                        Model.getInstance().viewing_year = Integer.parseInt(newValue);
                        
                        // Update view
                        repaintView();
                    }
                }
            });
    }
    
    private void loadCalendarLabels(){
        
        // Get the current VIEW
        int year = Model.getInstance().viewing_year;
        int month = Model.getInstance().viewing_month;
        
        // Note: Java's Gregorian Calendar class gives us the right
        // "first day of the month" for a given calendar & month
        // This accounts for Leap Year
        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // We are "offsetting" our start depending on what the
        // first day of the month is.
        // For example: Sunday start, Monday start, Wednesday start.. etc
        int offset = firstDay;
        int gridCount = 1;
        int lblCount = 1;
        
       // Go through calendar grid
       for(Node node : calendarGrid.getChildren()){
           
           VBox day = (VBox) node;
           
           day.getChildren().clear();
           day.setStyle("-fx-backgroud-color: white");
           day.setStyle("-fx-font: 14px \"System\" ");
           
           // Start placing labels on the first day for the month
           if (gridCount < offset) {
               gridCount++;
               // Darken color of the offset days
               day.setStyle("-fx-background-color: #E9F2F5"); 
           } else {
            
            // Don't place a label if we've reached maximum label for the month
            if (lblCount > daysInMonth) {
                // Instead, darken day color
                day.setStyle("-fx-background-color: #E9F2F5"); 
            } else {
                
                // Make a new day label   
                Label lbl = new Label(Integer.toString(lblCount));
                lbl.setPadding(new Insets(5));
                lbl.setStyle("-fx-text-fill:darkslategray");

                day.getChildren().add(lbl);
            }
               
            lblCount++;           
           }
       }
    }
    
    public void calendarGenerate(){
        
        // Load year selection
        selectedYear.getItems().clear(); // Note: Invokes its change listener
        selectedYear.getItems().add(Integer.toString(Model.getInstance().calendar_start));
        selectedYear.getItems().add(Integer.toString(Model.getInstance().calendar_end));

        // Select the first YEAR as default     
        selectedYear.getSelectionModel().selectFirst();
        
        // Update the VIEWING YEAR
        Model.getInstance().viewing_year = Integer.parseInt(selectedYear.getSelectionModel().getSelectedItem());
        
        // Enable year selection box
        selectedYear.setVisible(true);
        
        // Set calendar name label
        calendarNameLbl.setText(Model.getInstance().calendar_name);
        
        // Get a list of all the months (1-12) in a year
        DateFormatSymbols dateFormat = new DateFormatSymbols();
        String months[] = dateFormat.getMonths();
        String[] spliceMonths = Arrays.copyOfRange(months, 0, 12);
        
        // Load month selection
        monthSelect.getItems().clear();
        monthSelect.getItems().addAll(spliceMonths);   
        
        // Select the first MONTH as default
        monthSelect.getSelectionModel().selectFirst();
        monthLabel.setText(monthSelect.getSelectionModel().getSelectedItem());
        
        // Update the VIEWING MONTH
        Model.getInstance().viewing_month = 
                Model.getInstance().getMonthIndex(monthSelect.getSelectionModel().getSelectedItem());
        
        // Update view
        repaintView();
    }
    
    public void repaintView(){
        // Purpose - To be usable anywhere to update view
        // 1. Correct calendar labels based on Gregorian Calendar 
        // 2. Display events known to database
        
        loadCalendarLabels();
        if (!checkBoxesHaveBeenClicked)
        {
            populateMonthWithEvents();
        }
        else
        {
            ActionEvent actionEvent = new ActionEvent();
            handleCheckBoxAction(actionEvent);
        }
       // populateMonthWithEvents();
    }
    
    private void populateMonthWithEvents(){
        
        // Get viewing calendar
        String calendarName = Model.getInstance().calendar_name;
        String currentMonth = monthLabel.getText();
        
        int currentMonthIndex = Model.getInstance().getMonthIndex(currentMonth) + 1;
        int currentYear = Integer.parseInt(selectedYear.getValue());
        
        // Query to get ALL Events from the selected calendar!!
       // String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "'";
        ObservableList<Workshop> listWorkshop;
        // Store the results here
       // ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
       implWorkshopService wService = new implWorkshopService();
        
        
        try {
            listWorkshop = wService.readAllW(calendarName);
            System.out.println(listWorkshop);
            for(int i =0; i< listWorkshop.size();i++){
                Date eventDate = listWorkshop.get(i).getDateDebut();
                String eventDescript = listWorkshop.get(i).getDescription();
                int eventTermID =  listWorkshop.get(i).getId();
                if (currentYear == eventDate.toLocalDate().getYear()){
                     // Check for the month we already have selected (we are viewing)
                    if (currentMonthIndex == eventDate.toLocalDate().getMonthValue()){

                        // Get day for the month
                        int day = eventDate.toLocalDate().getDayOfMonth();

                        // Display decription of the event given it's day
                        showDate(day, eventDescript, eventTermID,listWorkshop.get(i).getType());
                        
                    }         
                
                
               
            }
                } 
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void showDate(int dayNumber, String descript, int termID,String type){
        
        Image img = new Image(getClass().getClassLoader().getResourceAsStream("academiccalendar/ui/icons/icon2.png"));
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        
        for (Node node: calendarGrid.getChildren()) {
                
            // Get the current day    
            VBox day = (VBox) node;
            
            // Don't look at any empty days (they at least must have a day label!)
            if (!day.getChildren().isEmpty()) {
                
                // Get the day label for that day
                Label lbl = (Label) day.getChildren().get(0);
                
                // Get the number
                int currentNumber = Integer.parseInt(lbl.getText());
                
                // Did we find a match?
                if (currentNumber == dayNumber) {
                    
                    // Add an event label with the given description
                    Label eventLbl = new Label(descript); 
                    eventLbl.setGraphic(imgView);
                    eventLbl.getStyleClass().add("event-label");
                    
                    // Save the term ID in accessible text
                    eventLbl.setAccessibleText(Integer.toString(termID));
                    System.out.println(eventLbl.getAccessibleText());
                    
                    eventLbl.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                        editEvent((VBox)eventLbl.getParent(), eventLbl.getText(), eventLbl.getAccessibleText());
                        
                    });
                    
                    // Get term color from term's table
                    String eventRGB = databaseHandler.getTermColor(termID);
                    
                    // Parse for rgb values
                    String[] colors = eventRGB.split("-");
                    /*String red = colors[0];
                    String green = colors[1];
                    String blue = colors[2];*/
                    if(type.equals("Conference")){
                        String red = "250";
                    String green = "128";
                    String blue = "114";
                    System.out.println("Color; " + red + green + blue);
                                      
                    eventLbl.setStyle("-fx-background-color: rgb(" + red+ 
                            ", " + green + ", " + blue + ", " + 1 + ");");
                        
                    }else if(type.equals("Soft Skills")){
                          String red = " 235";
                    String green = "152";
                    String blue = "78";
                    System.out.println("Color; " + red + green + blue);
                                      
                    eventLbl.setStyle("-fx-background-color: rgb(" + red+ 
                            ", " + green + ", " + blue + ", " + 1 + ");");
                        
                    }else if(type.equals("Team building")){
                        
                          String red = "187";
                    String green = "143";
                    String blue = "206";
                    System.out.println("Color; " + red + green + blue);
                                      
                    eventLbl.setStyle("-fx-background-color: rgb(" + red+ 
                            ", " + green + ", " + blue + ", " + 1 + ");");
                        
                    }else if(type.equals("Seminaire")){
                        
                          String red = "52";
                    String green = "152";
                    String blue = " 219";
                    System.out.println("Color; " + red + green + blue);
                                      
                    eventLbl.setStyle("-fx-background-color: rgb(" + red+ 
                            ", " + green + ", " + blue + ", " + 1 + ");");
                        
                    }
                   
        
         
                     
                    
                  
                    
                    
                    
                    // Stretch to fill box
                    eventLbl.setMaxWidth(Double.MAX_VALUE);
                    
                    // Mouse effects
                    eventLbl.addEventHandler(MouseEvent.MOUSE_ENTERED, (e)->{
                        eventLbl.getScene().setCursor(Cursor.HAND);
                    });
                    
                    eventLbl.addEventHandler(MouseEvent.MOUSE_EXITED, (e)->{
                        eventLbl.getScene().setCursor(Cursor.DEFAULT);
                    });
                    
                    // Add label to calendar
                    day.getChildren().add(eventLbl);
                }
            }
        }
    }
    
    public void exportCalendarPDF()
    {
         TableView<Workshop> table = new TableView<Workshop>();
         implWorkshopService wService = new implWorkshopService();
         ObservableList<Workshop> data =FXCollections.observableArrayList();  
   
        
        double w = 500.00;
        // set width of table view
        table.setPrefWidth(w);
        // set resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // intialize columns
        TableColumn<Workshop,String> nomEvent  = new TableColumn<Workshop,String>("nomEvent");
        TableColumn<Workshop,String> dateD  = new TableColumn<Workshop,String>("Debut");
        TableColumn<Workshop,String> dateF = new TableColumn<Workshop,String>("Fin");
        TableColumn<Workshop,String> hDebut  = new TableColumn<Workshop,String>("hDebut");
        TableColumn<Workshop,String> hFin  = new TableColumn<Workshop,String>("hFin");
        TableColumn<Workshop,String> lieu = new TableColumn<Workshop,String>("lieu");
        TableColumn<Workshop,String> nbP  = new TableColumn<Workshop,String>("nb P");
               
        // set width of columns
        nomEvent.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        dateD.setMaxWidth( 1f * Integer.MAX_VALUE * 60 ); // 50% width
        dateF.setMaxWidth( 1f * Integer.MAX_VALUE * 60 ); // 50% width
        hDebut.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        hFin.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        lieu.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        nbP.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width       
        
        
        // 
        nomEvent.setCellValueFactory(new PropertyValueFactory<Workshop,String>("nomEvent"));
        dateD.setCellValueFactory( new PropertyValueFactory<Workshop,String>("dateDebut"));
        dateF.setCellValueFactory(new PropertyValueFactory<Workshop,String>("dateFin"));
        hDebut.setCellValueFactory(new PropertyValueFactory<Workshop,String>("hDebut"));
        hFin.setCellValueFactory( new PropertyValueFactory<Workshop,String>("hFin"));
        lieu.setCellValueFactory(new PropertyValueFactory<Workshop,String>("lieu"));
        nbP.setCellValueFactory(new PropertyValueFactory<Workshop,String>("nbParticipant")); 
        
        
        
        // Add columns to the table
        table.getColumns().addAll(nomEvent,dateD,dateF,hDebut,hFin,lieu,nbP);
        
        
        String calendarName = Model.getInstance().calendar_name;
        
        try {
            // Query to get ALL Events from the selected calendar!!
            //String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "' ORDER BY EventDate";
            
            // Store the results here
            //ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
            data = wService.readAllW(calendarName);
            
            
            /*
            try {
            
            while(result.next()){
            //initalize temporarily strings
            String tempTerm="";
            
            
            //***** Get term, Event Description and Date *****
            
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
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
       
        table.getItems().setAll(data);
        // open dialog window and export table as pdf
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println(table);
        if(job != null){
          
          job.printPage(table);
          job.endJob();
        }
       }
    
    
     public void exportCalendarExcel() 
    {
        
         FileChooser fileChooser = new FileChooser();

              //Set extension filter
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xlsx)", "*.xlsx");
              fileChooser.getExtensionFilters().add(extFilter);

              //Show save file dialog
              File file = fileChooser.showSaveDialog(new Stage());

              if(file != null){
                  createExcelSheet(file);
                  System.out.println("hi");
              }
    }        
   public void createExcelSheet(File file){
       implWorkshopService wService = new implWorkshopService();
       ObservableList<Workshop> data =FXCollections.observableArrayList();  
        String calendarName = Model.getInstance().calendar_name;
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet =wb.createSheet(calendarName);
        
        
        XSSFRow row = sheet.createRow(1);
        XSSFCell cell;
        
        cell = row.createCell(1);
        cell.setCellValue("Nom Event");
        cell = row.createCell(2);
        cell.setCellValue("Date Debut");
        cell = row.createCell(3);
        cell.setCellValue("Date Fin");
        cell = row.createCell(4);
        cell.setCellValue("Heure Debut");
        cell = row.createCell(5);
        cell.setCellValue("Heure Fin");
        cell = row.createCell(6);
        cell.setCellValue("nombre participant");
        cell = row.createCell(7);
        cell.setCellValue("Type");
        cell = row.createCell(8);
        cell.setCellValue("Prix");
        
       
        
         try {
             int counter=2;
                data = wService.readAllW(calendarName);
            
                
                for(int i=0;i<data.size();i++ ){
                    row = sheet.createRow(counter);
                    cell = row.createCell(1);
                    cell.setCellValue(data.get(i).getNomEvent());
                    cell = row.createCell(2);
                    cell.setCellValue(data.get(i).getDateDebut().toString());
                    cell = row.createCell(3);
                    cell.setCellValue(data.get(i).getDateFin().toString());
                    cell = row.createCell(4);
                    cell.setCellValue(data.get(i).gethDebut().toString());
                    cell = row.createCell(5);
                    cell.setCellValue(data.get(i).gethFin().toString());
                    cell = row.createCell(6);
                    cell.setCellValue(data.get(i).getNbParticipant());
                    cell = row.createCell(7);
                    cell.setCellValue(data.get(i).getType());
                    cell = row.createCell(8);
                    cell.setCellValue(data.get(i).getPrix());
                    counter++;
                }
                
              for (int i=0; i<3; i++){
               sheet.autoSizeColumn(i);
            }
               
                
        } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        } 
         try{
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.flush();
        out.close();
        
       
         }
         catch(Exception e) {
            e.printStackTrace();
         }  
    }
   
    private String getRGB(Color c){
        
        String rgb = Integer.toString((int)(c.getRed() * 255)) + "-"
                + Integer.toString((int)(c.getGreen() * 255)) + "-"
                + Integer.toString((int)(c.getBlue() * 255));
        
        return rgb;       
    }
    
    private void changeColors(){
        
        // Purpose - Take all chosen colors in the Colors menu and
        // update each term's color in the database
        
        Color fallSemColor = fallSemCP.getValue();
        String fallSemRGB = getRGB(fallSemColor);
        databaseHandler.setTermColor("FA SEM", fallSemRGB);
        
        Color springSemColor = springSemCP.getValue();
        String springSemRGB = getRGB(springSemColor);
        databaseHandler.setTermColor("SP SEM", springSemRGB);
        
        Color summerSemColor = summerSemCP.getValue();
        String summerSemRGB = getRGB(summerSemColor);
        databaseHandler.setTermColor("SU SEM", summerSemRGB);
        
        Color allQtrColor = allQtrCP.getValue();
        String allQtrRGB = getRGB(allQtrColor);
        databaseHandler.setTermColor("QTR", allQtrRGB);
        
        Color allMbaColor = allMbaCP.getValue();
        String allMbaRGB = getRGB(allMbaColor);
        databaseHandler.setTermColor("MBA", allMbaRGB);
        
        Color allHalfColor = allHalfCP.getValue();
        String allHalfRGB = getRGB(allHalfColor);
        databaseHandler.setTermColor("Half", allHalfRGB);
        
        Color allCampusColor = allCampusCP.getValue();
        String allCampusRGB = getRGB(allCampusColor);
        databaseHandler.setTermColor("Campus", allCampusRGB);
        
        Color allHolidayColor = allHolidayCP.getValue();
        String allHolidayRGB = getRGB(allHolidayColor);
        databaseHandler.setTermColor("Holiday", allHolidayRGB);
        
        Color allTraTrbColor = traTrbCP.getValue();
        String allTraTrbRGB = getRGB(allTraTrbColor);
        databaseHandler.setTermColor("TRA", allTraTrbRGB);
        databaseHandler.setTermColor("TRB", allTraTrbRGB);
        
    }
    
    private void initalizeColorPicker(){
        
        String fallSemRGB = databaseHandler.getTermColor(databaseHandler.getTermID("FA SEM"));
        String springSemRGB = databaseHandler.getTermColor(databaseHandler.getTermID("SP SEM"));
        String summerSemRGB = databaseHandler.getTermColor(databaseHandler.getTermID("SU SEM"));
        String mbaRGB = databaseHandler.getTermColor(databaseHandler.getTermID("FA I MBA"));
        String qtrRGB = databaseHandler.getTermColor(databaseHandler.getTermID("FA QTR"));
        String halfRGB = databaseHandler.getTermColor(databaseHandler.getTermID("FA 1st Half"));
        String campusRGB = databaseHandler.getTermColor(databaseHandler.getTermID("Campus General"));        
        String holidayRGB = databaseHandler.getTermColor(databaseHandler.getTermID("Holiday"));
        String traTrbRGB = databaseHandler.getTermColor(databaseHandler.getTermID("FA TRA"));
        
        // Parse for rgb values for fall sem
        String[] colors = fallSemRGB.split("-");
        String red = colors[0]; String green = colors[1]; String blue = colors[2];
        Color c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        fallSemCP.setValue(c);
        
        // Parse for rgb values for spring sem
        colors = summerSemRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        summerSemCP.setValue(c);
        
        // Parse for rgb values for summer sem
        colors = springSemRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        springSemCP.setValue(c);
        
        // Parse for rgb values for MBA
        colors = mbaRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        allMbaCP.setValue(c);
        
        // Parse for rgb values for QTR
        colors = qtrRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        allQtrCP.setValue(c);
        
        // Parse for rgb values for Half
        colors = halfRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        allHalfCP.setValue(c);
        
        // Parse for rgb values for Campus
        colors = campusRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        allCampusCP.setValue(c);
        
        // Parse for rgb values for Holiday
        colors = holidayRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        allHolidayCP.setValue(c);
        
        // Parse for rgb values for TRA/TRB
        colors = traTrbRGB.split("-");
        red = colors[0]; green = colors[1]; blue = colors[2];
        c = Color.rgb(Integer.parseInt(red),Integer.parseInt(green) ,Integer.parseInt(blue));
        traTrbCP.setValue(c);
        
    }
   
    public void initializeCalendarGrid(){
        
        // Go through each calendar grid location, or each "day" (7x6)
        int rows = 6;
        int cols = 7;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                
                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.getStyleClass().add("calendar_pane");
                vPane.setMinWidth(weekdayHeader.getPrefWidth()/7);
                
                vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
                    addEvent(vPane);
                });
                
                GridPane.setVgrow(vPane, Priority.ALWAYS);
                
                // Add it to the grid
                calendarGrid.add(vPane, j, i);  
            }
        }       
        
        // Set up Row Constraints
        for (int i = 0; i < 7; i++) {
         RowConstraints row = new RowConstraints();
         row.setMinHeight(scrollPane.getHeight()/7);
         calendarGrid.getRowConstraints().add(row);
        }
    }

    
    public void initializeCalendarWeekdayHeader(){
    
        // 7 days in a week
        int weekdays = 7;
        
        // Weekday names
        String[] weekAbbr = {"Sun","Mon","Tue", "Wed", "Thu", "Fri", "Sat"};
        
        for (int i = 0; i < weekdays; i++){
            
            // Make new pane and label of weekday
            StackPane pane = new StackPane();
            pane.getStyleClass().add("weekday-header");
            
            // Make panes take up equal space
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);
            
            // Note: After adding a label to this, it tries to resize itself..
            // So I'm setting a minimum width.
            pane.setMinWidth(weekdayHeader.getPrefWidth()/7);
            
            // Add it to the header
            weekdayHeader.getChildren().add(pane);
            
            // Add weekday name
            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Make empty calendar
        initializeCalendarGrid();
        initializeCalendarWeekdayHeader();
        initializeMonthSelector();
    
        // Set Depths
        JFXDepthManager.setDepth(scrollPane, 1);

        //*** Instantiate DBHandler object *******************
        databaseHandler = new DBHandler();
        //****************************************************

        //Initialize all Color Pickers. Show saved colors for specific terms
        initalizeColorPicker();
     
        //If the user is not working on any new or existing calendar, disable the filtering check boxes and rules buttons
        disableCheckBoxes();
        // I am still working on this function and issue
        //disableButtons();  
        
    }

    //**********************************************************************************
    //**********************************************************************************
    //**********************************************************************************
    
    // Side - menu buttons 
    @FXML
    private void newCalendar(MouseEvent event) {
        newCalendarEvent();
    }
    
    @FXML
    private void manageCalendars(MouseEvent event) {
        listCalendarsEvent();
    }
    
    @FXML
    private void manageRules(MouseEvent event) {
        listRulesEvent();
    }
    
    @FXML
    private void newRule(MouseEvent event) {
        newRuleEvent();
    }

    @FXML
    private void pdfBtn(MouseEvent event) {
        exportCalendarPDF();
    }

    @FXML
    private void excelBtn(MouseEvent event) {
        exportCalendarExcel();
    }

    @FXML
    private void updateColors(MouseEvent event) {
        changeColors();
        
        if (Model.getInstance().calendar_name != null)
            repaintView();
    }

    @FXML
    private void manageTermDates(MouseEvent event) {
        manageTermsEvent();
    }

    //******************************************************************************************
    //******************************************************************************************
    //******************************************************************************************
    
     ///******* I am still working on these functions and issues  ********
    /*
    public void disableButtons(){
        
        manageRulesButton.setDisable(true);
    }
    
    public void enableButtons(){
        
        manageRulesButton.setDisable(false);
    }
    */
    
    public void disableCheckBoxes(){
        
        //Disable all check boxes for filtering events
        fallSemCheckBox.setDisable(true);
        springSemCheckBox.setDisable(true);
        summerSemCheckBox.setDisable(true);
        allQtrCheckBox.setDisable(true);
        allMbaCheckBox.setDisable(true);
        allHalfCheckBox.setDisable(true);
        allCampusCheckBox.setDisable(true);
        allHolidayCheckBox.setDisable(true);
        fallSemCheckBox.setDisable(true);
        allTraTrbCheckBox.setDisable(true);
        selectAllCheckBox.setDisable(true);
    }
    
    public void enableCheckBoxes(){
        
        //Enable all check boxes for filtering events
        fallSemCheckBox.setDisable(false);
        springSemCheckBox.setDisable(false);
        summerSemCheckBox.setDisable(false);
        allQtrCheckBox.setDisable(false);
        allMbaCheckBox.setDisable(false);
        allHalfCheckBox.setDisable(false);
        allCampusCheckBox.setDisable(false);
        allHolidayCheckBox.setDisable(false);
        fallSemCheckBox.setDisable(false);
        allTraTrbCheckBox.setDisable(false);
        selectAllCheckBox.setDisable(false);
        //Set selection for select all check box to true
        selectAllCheckBox.setSelected(true);
    }
    
    public void selectCheckBoxes(){
        
        //Set ALL check boxes for filtering events to be selected
        fallSemCheckBox.setSelected(true);
        springSemCheckBox.setSelected(true);
        summerSemCheckBox.setSelected(true);
        allQtrCheckBox.setSelected(true);
        allMbaCheckBox.setSelected(true);
        allHalfCheckBox.setSelected(true);
        allCampusCheckBox.setSelected(true);
        allHolidayCheckBox.setSelected(true);
        fallSemCheckBox.setSelected(true);
        allTraTrbCheckBox.setSelected(true);
    }
    
    public void unSelectCheckBoxes(){
        
        //Set ALL check boxes for filtering events to be selected
        fallSemCheckBox.setSelected(false);
        springSemCheckBox.setSelected(false);
        summerSemCheckBox.setSelected(false);
        allQtrCheckBox.setSelected(false);
        allMbaCheckBox.setSelected(false);
        allHalfCheckBox.setSelected(false);
        allCampusCheckBox.setSelected(false);
        allHolidayCheckBox.setSelected(false);
        fallSemCheckBox.setSelected(false);
        allTraTrbCheckBox.setSelected(false);
    }
    
    //******************************************************************************************
    //******************************************************************************************
    //******************************************************************************************
    
    //Function filters all events. Make them all show up or disappear from the calendar
    @FXML
    private void selectAllCheckBoxes(ActionEvent e)
    {
        if (selectAllCheckBox.isSelected())
        {
            selectCheckBoxes();
        }
        else
        {
            unSelectCheckBoxes();
        }
        
        handleCheckBoxAction(new ActionEvent());
    }
    
    
    
    //This function is constantly checking if any of the checkboxes is selected or deselected
    //and therefore, populate the calendar with the events of the terms that are selected
    
    @FXML
    private void handleCheckBoxAction(ActionEvent e)
    {
        System.out.println("have check boxes been cliked: " + checkBoxesHaveBeenClicked);
        if (!checkBoxesHaveBeenClicked)
        {
            checkBoxesHaveBeenClicked = true;
            System.out.println("have check boxes been cliked: " + checkBoxesHaveBeenClicked);
        }
        
        //ArrayList that will hold all the filtered events based on the selection of what terms are visible
        ArrayList<String> termsToFilter = new ArrayList();
        
        //Check each of the checkboxes and call the appropiate queries to
        //show only the events that belong to the term(s) the user selects
        
        //FA SEM
        if (fallSemCheckBox.isSelected())
        {
            System.out.println("Fall Sem checkbox is selected");
            termsToFilter.add("FA SEM");
        }
        
        if (!fallSemCheckBox.isSelected())
        {
            System.out.println("Fall Sem checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("FA SEM");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        
        //SP SEM
        if (springSemCheckBox.isSelected())
        {
            System.out.println("Spring Sem checkbox is selected");
            termsToFilter.add("SP SEM");
        }
        if (!springSemCheckBox.isSelected())
        {
            System.out.println("Spring Sem checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("SP SEM");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        //SU SEM
        if (summerSemCheckBox.isSelected())
        {
            System.out.println("SUMMER Sem checkbox is selected");
            termsToFilter.add("SU SEM");
        }
        if (!summerSemCheckBox.isSelected())
        {
            System.out.println("SUMMER Sem checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("SU SEM");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        // ALL QTR
        if (allQtrCheckBox.isSelected())
        {
            System.out.println("All QTR checkbox is selected");
            termsToFilter.add("QTR");
        }
        if (!allQtrCheckBox.isSelected())
        {
            System.out.println("All QTR checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("QTR");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        // All MBA
        if (allMbaCheckBox.isSelected())
        {
            System.out.println("All MBA checkbox is selected");
            termsToFilter.add("MBA");
        }
        if (!allMbaCheckBox.isSelected())
        {
            System.out.println("All MBA checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("MBA");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        // All Half
        if (allHalfCheckBox.isSelected())
        {
            System.out.println("All Half checkbox is selected");
            termsToFilter.add("Half");
        }
        if (!allHalfCheckBox.isSelected())
        {
            System.out.println("All Half checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("Half");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        
        // All Campus
        if (allCampusCheckBox.isSelected())
        {
            System.out.println("All Campus checkbox is selected");
            termsToFilter.add("Campus");
        }
        if (!allCampusCheckBox.isSelected())
        {
            System.out.println("All Campus checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("Campus");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
        
        // All Holiday
        if (allHolidayCheckBox.isSelected())
        {
            System.out.println("All Holiday checkbox is selected");
            termsToFilter.add("Holiday");
        }
        if (!allHolidayCheckBox.isSelected())
        {
            System.out.println("All Holiday checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("Holiday");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
        }
       
        // All TRA/TRB
        if (allTraTrbCheckBox.isSelected())
        {
            System.out.println("All TRA/TRB checkbox is selected");
            termsToFilter.add("TRA");
            termsToFilter.add("TRB");
        }
        if (!allTraTrbCheckBox.isSelected())
        {
            System.out.println("All Holiday checkbox is now deselected");
            int auxIndex = termsToFilter.indexOf("TRA");
            int auxIndex2 = termsToFilter.indexOf("TRB");
            if (auxIndex != -1)
            {
                termsToFilter.remove(auxIndex);
            }
            if (auxIndex2 != -1)
            {
                termsToFilter.remove(auxIndex2);
            }
        }
        
        
        System.out.println("terms to filter list: " + termsToFilter);
        
        //Get name of the current calendar that the user is working on
        String calName = Model.getInstance().calendar_name;
        
        System.out.println("and calendarName is: " + calName);
        
        if (termsToFilter.isEmpty())
        {
            System.out.println("terms are not selected. No events have to appear on calendar. Just call loadCalendarLabels method in the RepaintView method");
            selectAllCheckBox.setSelected(false);
            loadCalendarLabels();
        }
        else
        {
            System.out.println("Call the appropiate function to populate the month with the filtered events");
            //Get List of Filtered Events and store all events in an ArrayList variable
            ArrayList<String> filteredEventsList = databaseHandler.getFilteredEvents(termsToFilter, calName);
            
            System.out.println("List of Filtered events is: " + filteredEventsList);
        
            //Repaint or reload the events based on the selected terms
            showFilteredEventsInMonth(filteredEventsList);
        }
        
    
    }
    
    
    
    public void showFilteredEventsInMonth(ArrayList<String> filteredEventsList) {
        
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("I am in the show filtered events in month function");
        System.out.println("The list of filted events is: " + filteredEventsList);
        System.out.println("****------******-------******--------");
        
        
        
        String calendarName = Model.getInstance().calendar_name;
        
        String currentMonth = monthLabel.getText();
        System.out.println("currentMonth is: " + currentMonth);
        int currentMonthIndex = Model.getInstance().getMonthIndex(currentMonth) + 1;
        System.out.println("currentMonthIndex is: " + currentMonthIndex);
        
        int currentYear = Integer.parseInt(selectedYear.getValue());
        System.out.println("CurrentYear is: " + currentYear);
        System.out.println("****------******-------******--------");
        System.out.println("****------******-------******--------");
        
       
        System.out.println("Now the labels on the calendar are cleared");
        loadCalendarLabels();
        System.out.println("****------******-------******--------");
        System.out.println("****------******-------******--------");
        System.out.println("Now, the filtered events/labels will be shown/put on the calendar");
        System.out.println("****------******-------******--------");
        
        for (int i=0; i < filteredEventsList.size(); i++)
        {
            String[] eventInfo = filteredEventsList.get(i).split("~");
            String eventDescript = eventInfo[0];
            String eventDate = eventInfo[1];
            int eventTermID = Integer.parseInt(eventInfo[2]);
            String eventCalName = eventInfo[3];
            System.out.println(eventDescript);
            System.out.println(eventDate);
            System.out.println(eventTermID);
            System.out.println(eventCalName);
            
            String[] dateParts = eventDate.split("-");
            int eventYear = Integer.parseInt(dateParts[0]);
            int eventMonth = Integer.parseInt(dateParts[1]);
            int eventDay = Integer.parseInt(dateParts[2]);
            
            
            System.out.println("****------******-------******--------");
            System.out.println("Now I will check if currentYear equals eventYear. Result is:");
            if (currentYear == eventYear)
            {
                System.out.println("Yes, both year match.");
                System.out.println("Now I will check if the month index equals the event's month. REsult is");
                if (currentMonthIndex == eventMonth)
                {
                    System.out.println("Yes they are the same. Now I will call showDate function");
                    //showDate(eventDay, eventDescript, eventTermID);
                }
            }
        }
    }

    
    
    @FXML
    private void deleteAllEvents(MouseEvent event){
        
        //Show confirmation dialog to make sure the user want to delete the selected rule
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("All Events Deletion");
        alert.setContentText("Are you sure you want to delete all events in this calendar?");
        //Customize the buttons in the confirmation dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        //Set buttons onto the confirmation dialog
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        //Get the user's answer on whether deleting or not
        Optional<ButtonType> result = alert.showAndWait();
        
        //If the user wants to delete the calendar, call the function that deletes the calendar. Otherwise, close the window
        if (result.get() == buttonTypeYes){
            deleteAllEventsInCalendar();
        } 
        
        
    }
    
    // Function that deletes all the events in the current calendar
    public void deleteAllEventsInCalendar() {
        
        //Variable that holds the name of the current calendar
        String calName = Model.getInstance().calendar_name;
        
        //Query that will delete all events that belong to the selected calendar
       // String deleteAllEventsQuery = "DELETE FROM EVENTS "
                                // + "WHERE EVENTS.CalendarName='" + calName + "'";
        
        //Execute query that deletes all events associated to the selected calendar
       // boolean eventsWereDeleted = databaseHandler.executeAction(deleteAllEventsQuery);
        implWorkshopService wService = new implWorkshopService();
        //Check if events were successfully deleted and indicate the user if so
        try {
            wService.delete(calName);
            repaintView();
            
            //Show message indicating that the selected calendar was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("All events were successfully deleted");
            alertMessage.showAndWait();
       
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
            //Update the calendar. Show that events were actually deleted
            
    }

    @FXML
    private void pdf(ActionEvent event) {
        
        this.exportCalendarPDF();
    }

    @FXML
    private void backDash(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        
    }
    
    
} //End of FXMLDocumentController class
