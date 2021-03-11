/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Calendar;
import Entities.Workshop;
import Services.workshopService;
import Utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dell
 */
public class implCalendarService implements workshopService<Calendar>{
    
    private Connection con;
    

    public implCalendarService() {
        con = Connexion.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Calendar t) throws SQLException {
        String calendarQuery = "INSERT INTO `calendars`(`CalendarName`, `StartYear`, `EndYear`, `StartDate`)"+ 
        "VALUES (" + "'" + t.getName() + "', " + t.getStartYear() + ", " + t.getEndYear() + ", " + "'" + t.getStartDate() + "')";
        PreparedStatement ps = con.prepareStatement(calendarQuery);
        
       
        ps.executeUpdate();
    }

    @Override
    public void delete(int t) throws SQLException {
        PreparedStatement ps;
 
            ps = con.prepareStatement("delete from calendar where CalendarName = ?");
            ps.setInt(1, t);
            ps.executeUpdate();

    }
    public void delete(String t) throws SQLException {
        PreparedStatement ps;
 
            ps = con.prepareStatement("delete from calendars where CalendarName = ?");
            ps.setString(1, t);
            ps.executeUpdate();

    }

    @Override
    public void update(Calendar t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Calendar> readAll() throws SQLException {
        ObservableList<Calendar> calendars = FXCollections.observableArrayList();
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from calendars");
     while (rs.next()) { 
         int id = rs.getInt(1);
               String calendarName = rs.getString("CalendarName");
                String startYear = Integer.toString(rs.getInt("StartYear"));
                String endYear = Integer.toString(rs.getInt("EndYear"));

                String startingDate = rs.getString("StartDate");
                
                Calendar c = new Calendar(id,calendarName, startYear, endYear, startingDate);
               
               
              calendars.add(c);
     }
    return calendars;
    }

    @Override
    public Calendar find(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
