//******************************************************************************************
//******************************************************************************************

//Packages and imports

package academiccalendar.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author RodolfoPC
 */
public class DBHandler {
    
    private static DBHandler handler;
    
    private static final String DB_URL = "jdbc:derby:calendarDatabase;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    //Arrays that contain the default terms of the University
    private static String[] terms = {"FA SEM","SP SEM", "SU SEM", 
                                    "FA I MBA", "FA II MBA", "SP I MBA", "SP II MBA", "SU MBA",
                                    "FA QTR", "WIN QTR", "SP QTR", "SU QTR",
                                    "FA 1st Half", "FA 2nd Half", "SP 1st Half", "SP 2nd Half",
                                    "Campus General", "Campus STC", "Campus BV",
                                    "Holiday",
                                    "FA TRA", "SP TRA", "SU TRA", "FA TRB", "SP TRB", "SU TRB"};
    
    //Variable that contains the default color for the labels of events
    private static String defaultColor = "255-255-255";
    
    //Variable that controls whether or not the tables have to be created and populated
    private static boolean tablesAlreadyExist = false;
    
    //Variable that contains the default starting date for all terms
    private static String defaultTermStartDate = "2017-08-28";
    
    
    //Constructor
    public DBHandler() {
        
        //call to createConnection method that creates the connection between the database and the Java application
        createConnection();
        
        //checks if tables have been already created by an instantatiation of another object in the program, and if
        //the tables have not being created, then they are created and filled with the correspondent default records
        if (tablesAlreadyExist)
        {
            System.out.println("Tables already exist, so connection was the only thing created and now you are ready to go!");
        }
        else {
            
            // Creates all tables for the database
            createCalendarTable();
            createTermsTable();
            createEventsTable();
            createRulesTable();
        
            //Insert default values into the tables and print them so programmer can check they were added correctly
            insertDefaultValuesIntoTables();
            //The line below can be uncommented to allow programmer to check the default records present in tables
            //printAllDefaultRecords();
            
            //Switched boolean variable tablesAlreadyExist to true because tables were just created
            tablesAlreadyExist = true;
            
            System.out.println("the static variable tablesAlreadyExist was changed to true. THEREFORE, NO other table should try to be created");
            
            // the following lines are just here to test the correct functionality of the getListOfTermIDs method
            ArrayList<String> auxList = new ArrayList();
            auxList.add("SEM");
            auxList.add("MBA");
            ArrayList<String> auxListOfTermIDs= this.getListOfTermIDs(auxList);
            
            // the following lines are just here to test the correct functionality of the getFilteredEvents method
            ArrayList<String> auxList2 = new ArrayList();
            auxList2.add("SEM");
            auxList2.add("MBA");
            ArrayList<String> auxListOfFilteredEvents= this.getFilteredEvents(auxList2, "Test Name");   
        }   
    }
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************
    //Create Connection between Java Application and the JDBC
    void createConnection() {
    
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************
    //********** Functions that create the tables if they do not exist ***************************
    
    //**************************  CALENDARS Table  ***********************************************
    //Function that creates CALENDARS Table
    void createCalendarTable(){
        
        String TableName = "CALENDARS";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "CalendarName varchar(200) primary key not null,\n"
                        + "StartYear integer,\n"
                        + "EndYear integer,\n"
                        + "StartDate date"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }
    
    //***************************************************************************************************************************************************************
    //**************************  TERMS Table  ***********************************************
    //Function that creates TERMS Table
    void createTermsTable(){
        
        String TableName = "TERMS";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "TermID integer primary key not null,\n"
                        + "TermName varchar(20),\n"
                        + "TermColor varchar(20),\n"
                        + "TermStartDate date"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************    
    //**************************  RULES Table  ***********************************************
    //Function that creates RULES Table
    void createRulesTable(){
        
        String TableName = "RULES";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "EventDescription varchar(200) not null,\n"
                        + "TermID integer not null,\n"
                        + "DaysFromStart integer,\n"
                        + "constraint " + TableName + "_PK primary key(EventDescription, TermID)"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************
    //**************************  EVENTS Table  ***********************************************
    //Function that creates EVENTS Table
    void createEventsTable(){
        
        String TableName = "EVENTS";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "EventDescription varchar(200) not null,\n"
                        + "EventDate date not null,\n"
                        + "TermID integer not null,\n"
                        + "CalendarName varchar(200) not null,\n"
                        + "constraint " + TableName + "_PK primary key(EventDescription, EventDate, TermID, CalendarName),\n"
                        + "constraint " + TableName + "_FK1 foreign key (TermID) references TERMS(TermID),\n"
                        + "constraint " + TableName + "_FK2 foreign key (CalendarName) references CALENDARS(CalendarName)"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    } 
    //***************************************************************************************************************************************************************
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************
    //Function that checks if a table in the database is empty (has no records), and return a boolean values based on the checking result
    boolean checkIfTableIsEmpty(String tableName) {
        boolean checkingResult = false;
        try {
            stmt = conn.createStatement();
            
            ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName);
            while (res.next()){
                checkingResult = true;
                break;
            }    
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- checking Table failed/error");
            return false;
        } finally {
        }
        return checkingResult;
    }
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************
    //Function that populates the tables TERMS and CALENDARS with default values
    void insertDefaultValuesIntoTables() {
        
        // Inserting default values in the TERMS table
        String TableName = "TERMS";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                
                boolean dataExistsInTable = checkIfTableIsEmpty(TableName);
                if (!dataExistsInTable)
                {
                    int id = 1;
                    for(int i=0; i < terms.length; i++)
                    {
                        String query2 = "INSERT INTO " + TableName + " VALUES(" + id + ", '" + terms[i]+ "', '" + defaultColor +"', '2017-08-28')";
                        stmt.execute(query2);
                        id++;
                    }
                    System.out.println("Default values SUCCESSFULLY inserted Table " + TableName + "!!!");
                }
                else {
                    System.out.println("Default values already exist in Table " + TableName);
                }
                
            }
            else {
                System.out.println("Table " + TableName + " does not exist");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
        
        
        // Inserting default values in the CALENDARS table. Creating a test calendar from the start
        // This test calendar can be deleted by the user.
        TableName = "CALENDARS";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);
            
            if (listOfTables.next()) {
                
                boolean dataExistsInTable = checkIfTableIsEmpty(TableName);
                if (!dataExistsInTable)
                {
                    String query2 = "INSERT INTO " + TableName + " VALUES('Test Name', 2017, 2018, '2017-08-01')";
                    stmt.execute(query2);
                    System.out.println("Default values SUCCESSFULLY inserted Table " + TableName + "!!!");
                }
                else {
                    System.out.println("Default values already exist in Table " + TableName);
                }
                
            }
            else {
                System.out.println("Table " + TableName + " does not exist");
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
           
    }
    //***************************************************************************************************************************************************************
    
    //***************************************************************************************************************************************************************
    // This function is for testing purposes. Helps the programmer see all the terms in the TERMS Table and all the Rules in the RULES table
    void printAllDefaultRecords(){
        try {
            
            // Print default records from TERMS table
            stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM TERMS");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("Table TERMS default records:");
            while (res.next()){
                System.out.println(res.getString("TermID") + " - " + res.getString("TermName") + ", color: " + res.getString("TermColor"));
            }
            
            // Print default records from RULES table
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("Table RULES default records:");
            res = stmt.executeQuery("SELECT * FROM RULES");
            while (res.next()){
                System.out.println("Record has values: " +res.getString("EventDescription") + " - " + res.getString("TermID") + " - " + res.getString("DaysFromStart"));
            }  
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- Error when printing");
        } finally {
        }
    }
    //***************************************************************************************************************************************************************
    
    //*****************************************************************************************************************************
    //Function that executes a SELECT query and returns the requested values/data from the database
    public ResultSet executeQuery(String query) {
        ResultSet result;
        
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at executeQuery:dataHandler --> ERROR: " + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        
        return result;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    //Function that executes an insertion, deletion, or update query
    public boolean executeAction(String query2) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query2);
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Exception at executeQuery:dataHandler  --> ERROR: " + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    //Function that return the complete list of terms that exist in the database
    public ObservableList<String> getListOfTerms() throws SQLException {
        
        //ArrayList that will contain all terms saved in the TERMS Tables
        ObservableList<String> listOfTerms = FXCollections.observableArrayList();// = new ObservableList();
        
        //Query that will obtain all available Term Names from the database table TERMS
        String queryListOfTerms = "SELECT TermName FROM TERMS";
        //Variable that will hold the result of executing the previous query
        ResultSet rs = executeQuery(queryListOfTerms);
        
        try
        {
            //While there are Term Names in the ResultSet variable, add each one of them to the ObservableList of Strings
           while(rs.next()) 
            {
                //get the term name and store it in a String variable
                String termName = rs.getString("TermName");
                //add Term Name to list of terms
                listOfTerms.add(termName);
            } 
        }
        catch (SQLException e) 
        {
            System.err.println(e.getMessage() + "--- error at getListOfTerms method in DBHandler class");
        }
        
        return listOfTerms;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************        
    //Function that returns the Term ID based on a given term name        
    public int getTermID(String termName) {
        
        int termID = 0;
        String getTermIDQuery = "Select TermID From TERMS WHERE TermName='" + termName + "'";
        ResultSet res = executeQuery(getTermIDQuery);
        
        try
        {
            while(res.next())
            {
                termID = res.getInt("TermID");
            }
        }
        catch (SQLException e) 
        {
            System.err.println(e.getMessage() + "--- error at getTermID method in DBHandler class");
        } 
        
        return termID;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that returns a term's name based on a given term ID
    public String getTermName(int termIDAux) {
        
        //Declare variable that will contain the name of the term
        String nameOfTerm = "x";
        //Create query that will find a matching result for the termName based on the term's ID
        String getTermNameQuery = "SELECT TermName FROM TERMS "
                                + "WHERE TERMS.TermID=" + termIDAux;
        
        //Execute query to get the name of the term based on the given term ID
        ResultSet res = executeQuery(getTermNameQuery);
        
        // get the name of the term and store it in the String variable nameOfTerm
        // if the query obtained a result for the given term ID
        try
        {
            while(res.next())
            {
                nameOfTerm = res.getString("TermName");
            }
        }
        catch (SQLException e) 
        {
            System.err.println(e.getMessage() + "--- error at getTermID method in DBHandler class");
        } 
        
        return nameOfTerm;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that returns the starting date of term based on a given term ID
    public String getTermStartDate(int auxTermID){
        
        //Declare and initialize String variable that will hold the start date of a term to be returned 
        String termStartDate = defaultTermStartDate;
        
        //Query that will look for the start date of the term specified by the given term ID
        String getTermDateQuery = "SELECT TermStartDate FROM TERMS "
                            + "WHERE TERMS.TermID=" + auxTermID;
        
        // Execute query to get the term's starting date
        ResultSet res = executeQuery(getTermDateQuery);
        
        // Store the term's starting date in the String variable if the query obtained a result
        try
        {
            while(res.next())
            {
                termStartDate = res.getString("TermStartDate");
            }
        }
        catch (SQLException e) 
        {
            System.err.println(e.getMessage() + "--- error at getTermID method in DBHandler class");
        } 
        
        return termStartDate;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that returns the list of all rules in the RULES table (all existing records). Takes no arguments
    public ArrayList<String> getListOfRules() {
        
        //ArrayList Object that will hold all the rows (records) of rules to be returned
        ArrayList<String> listOfRules = new ArrayList<>();
        
        //Query that select all rows (records) of rules from the RULES table
        String queryListOfRules = "SELECT * FROM RULES";
        
        //Variable that will hold the result of executing the previous query
        ResultSet rs = executeQuery(queryListOfRules);
        
        try
        {
            //While there are Rules in the ResultSet variable, add each one of them to the ArrayList of Strings
           while(rs.next()) 
            {
                //get the full row of the rule and store it in a String variable
                String ruleDataRow = rs.getString("EventDescription") + "~" + rs.getInt("TermID") + "~" + rs.getString("DaysFromStart");
                //add rule to list of rules
                listOfRules.add(ruleDataRow);
            } 
        }
        catch (SQLException e) 
        {
            System.err.println(e.getMessage() + "--- error at getListOfRules method in DBHandler class");
        }
        
        return listOfRules; 
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that returns the color for a term based on a given term ID
    public String getTermColor(int auxTermID) {
        
        //Declare variable that will contain the color of the term to be returned
        String termColor = "x";
      
        //Create query that will find a matching result for the termColor based on the term's ID
        String getTermColorQuery = "SELECT TermColor FROM TERMS "
                                + "WHERE TERMS.TermID=" + auxTermID;
        
        //Execute query to get the color of a term based a given term ID
        ResultSet res = executeQuery(getTermColorQuery);
        
        // store color in a String variable of the query obtained a result
        try
        {
            while(res.next())
            {
                termColor = res.getString("TermColor");
            }
        }
        catch (SQLException e) 
        {
            System.err.println(e.getMessage() + "--- error at getTermColor method in DBHandler class");
        } 
        
        return termColor;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that sets (updates) the color of a term. Takes as arguments: the new color and the term ending (e.g. SEM, QTR, etc.)
    public void setTermColor(String termIdentifier, String termRGB){
     
        //Query that will update the color of the terms that end with the term ending specified by variable "termIdentifier"
        String setTermColorAction = "UPDATE TERMS "
                        + "SET TermColor ='" + termRGB + "' "
                        + "WHERE TERMS.TermName LIKE " 
                        + "'%" + termIdentifier + "%'";
        
        //Execute query to update the color for the speficied terms
        executeAction(setTermColorAction);
    
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that returns a list of Term IDs based on a list of term endings (identifiers)
    public ArrayList<String> getListOfTermIDs(ArrayList<String> auxTermIdentifiersList) {
        
        //Object that will hold all the list of Term IDs to be returned
        ArrayList<String> listOfTermIDs = new ArrayList<>();
        
        //Loop that will add to the ArrayList listOfTermIDs all the term IDs needed for filtering events
        for (int i=0; i < auxTermIdentifiersList.size(); i++)
        {
            //Query that will select the term IDs that end with the each term identifier
            // in the ArrayList auxTermIdentifiersList
            String TermIDsQuery = "SELECT TermID FROM TERMS "
                                + "WHERE TermName LIKE '%" + auxTermIdentifiersList.get(i) + "%'";
        
            //Variable that will hold the result of executing the previous query
            ResultSet rs = executeQuery(TermIDsQuery);
        
            try
            {
                //While there are Term IDs in the ResultSet variable, add each one of them to the ArrayList of Strings
                while(rs.next()) 
                {
                    //get the Term ID and store it in a String variable
                    String auxTermID = rs.getString("TermID");
                    //add term ID to list of term IDs
                    listOfTermIDs.add(auxTermID);
                } 
            }
            catch (SQLException e) 
            {
                System.err.println(e.getMessage() + "--- error at getListOfTermIDs method in DBHandler class");
            }  
        }
        
        return listOfTermIDs;
    }
    //*****************************************************************************************************************************
    
    //*****************************************************************************************************************************
    // Function that returns list of filtered events to be shown in the calendar
    // this function takes as arguments: the list of term identiftiers and the current calendar
    public ArrayList<String> getFilteredEvents(ArrayList<String> auxTermIdentifiersList, String calName){
        
        //Declare and instantiate ArrayList object that will hold all events for the requested term(s)
        ArrayList<String> filteredEventsList = new ArrayList();
        
        //has to call getListOfTermIDs first to know which events to get from EVENTS table
        ArrayList<String> listOfTermIDs = this.getListOfTermIDs(auxTermIdentifiersList);
        
        //Continue to get the events if the list of term IDs is not empty, i.e., if the user selected at least one filter/term
        if (!listOfTermIDs.isEmpty())
        {
            for(int i=0; i < listOfTermIDs.size(); i++)
            {
                //Query that will select all events that match the term ID and the calendar the user is working on
                String getEventsQuery = "SELECT * FROM EVENTS "
                                        + "WHERE EVENTS.TermID=" + listOfTermIDs.get(i)
                                        + " AND EVENTS.CalendarName='" + calName + "'";
                
                //Variable that will hold the result of executing the previous query
                ResultSet rs = executeQuery(getEventsQuery);
        
                try
                {
                    //While there are events in the ResultSet variable, add each one of them to the ArrayList of Strings
                    while(rs.next()) 
                    {
                        //get the full row of the event info and store it in a String variable
                        String filteredEvent = rs.getString("EventDescription") + "~"
                                            + rs.getString("EventDate") + "~"
                                            + rs.getInt("TermID") + "~" 
                                            + rs.getString("CalendarName");
                        //add event to list of filtered events
                        filteredEventsList.add(filteredEvent);
                    } 
                }
                catch (SQLException e) 
                {
                    System.err.println(e.getMessage() + "--- error at getListOfRules method in DBHandler class");
                }
            }
        }
        
        return filteredEventsList;
    } //end of getFilteredEvents function
    
} // end of public class DBHandler
