/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implService;

import Service.workshopService;
import Entity.Workshop;
import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
/**
 *
 * @author dell
 */
public class implWorkshopService implements workshopService<Workshop>{

    private Connection con;
    

    public implWorkshopService() {
        con = Connexion.getIstance().getConnection();
    }
    private void dialog(Alert.AlertType alertType,String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }
    
    @Override
    public void ajouter(Workshop t) throws SQLException {
        String req= "INSERT INTO workshop (`nomEvent`, `dateDebut`, `dateFin`, `hDebut`, `hFin`, `lieu`, `nbParticipant`, `type`, `description`, `prix`) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        
        ps.setString(1, t.getNomEvent() );
        ps.setDate(2, (Date)t.getDateDebut());
        ps.setDate(3, (Date)t.getDateFin());
        ps.setInt(4, t.gethDebut());
        ps.setInt(5, t.gethFin());
        ps.setString(6, t.getLieu() );
        ps.setInt(7, t.getNbParticipant());
        ps.setString(8, t.getType() );
        ps.setString(9, t.getDescription() );
        ps.setFloat(10, t.getPrix());
        dialog(Alert.AlertType.INFORMATION, "Ajouter Avec Success");
        ps.executeUpdate();
        
    }

    @Override
    public void delete(int t) throws SQLException {
            
            PreparedStatement ps;
 
            ps = con.prepareStatement("delete from workshop where id = ?");
            ps.setInt(1, t);
            dialog(Alert.AlertType.INFORMATION, "Delete avec sucee");
            ps.executeUpdate();
    }

    @Override
    public void update(Workshop t) throws SQLException {
        PreparedStatement ps;
        
            ps = con.prepareStatement("update workshop SET `nomEvent`=?,`dateDebut`=?,"
                    + "`dateFin`=?,`hDebut`=?,`hFin`=?,"
                    + "`lieu`=?,`nbParticipant`=?,`type`=?,"
                    + "`description`=?,`prix`=? where id =?");
            ps.setString(1, t.getNomEvent() );
            ps.setDate(2, (Date)t.getDateDebut());
            ps.setDate(3, (Date)t.getDateFin());
            ps.setInt(4, t.gethDebut());
            ps.setInt(5, t.gethFin());
            ps.setString(6, t.getLieu() );
            ps.setInt(7, t.getNbParticipant());
            ps.setString(8, t.getType() );
            ps.setString(9, t.getDescription() );
            ps.setFloat(10, t.getPrix());
            ps.setInt(11, t.getId() );
            
            dialog(Alert.AlertType.INFORMATION, "Modifier Avec Success");
            ps.executeUpdate();

    }

    @Override
    public ObservableList<Workshop> readAll() throws SQLException {
         ObservableList<Workshop> workshops = FXCollections.observableArrayList();
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from workshop");
     while (rs.next()) {                
               int id = rs.getInt(1);
               String nom = rs.getString(2);
               Date dateDebut = rs.getDate(3);
               Date dateFin = rs.getDate(4);
               int hdebut = rs.getInt(5);
               int hfin = rs.getInt(6);
               String lieu = rs.getString(7);
               int nbParticipant = rs.getInt(8);
               String type = rs.getString(9);
               String description = rs.getString(10);
               float prix =  rs.getFloat(11);
               
               
               Workshop w = new Workshop(id, nom, dateDebut, dateFin, hdebut, hfin, lieu, nbParticipant, type, description, prix);
               workshops.add(w);
     }
    return workshops;
    }

    @Override
    public Workshop find(int id) throws SQLException {
        Statement ste=con.createStatement();
        Workshop w = new Workshop();
        ResultSet rs = ste.executeQuery("select * from workshop where id = "+id);
        
     while (rs.next()) {                
               int idu = rs.getInt(1);
               String nom = rs.getString(2);
               Date dateDebut = rs.getDate(3);
               Date dateFin = rs.getDate(4);
               int hdebut = rs.getInt(5);
               int hfin = rs.getInt(6);
               String lieu = rs.getString(7);
               int nbParticipant = rs.getInt(8);
               String type = rs.getString(9);
               String description = rs.getString(10);
               float prix =  rs.getFloat(11);
               
            w.setNomEvent(nom);
            w.setDateDebut(dateDebut);
            w.setDateFin(dateFin);
            w.sethDebut(0);
            w.sethFin(15);
            w.setType(type);
            w.setDescription(description);
            w.setLieu(lieu);
            w.setNbParticipant(nbParticipant);
            w.setPrix(prix);
               
              
              
     }
            return w;
    }
    
}
