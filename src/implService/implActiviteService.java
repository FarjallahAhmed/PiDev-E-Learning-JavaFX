/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implService;

import Entity.Activite;
import Service.ActiviteService;
import connection.Connexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Asus
 */
public class implActiviteService implements ActiviteService<Activite>{
    
    private Connection con;
    private int id_activite;
    
    public implActiviteService() {
        con =  Connexion.getIstance().getConnection();
    }

    @Override
    public void ajouter(Activite t) throws SQLException {
        String req= "INSERT INTO activite (`id_activite`,`nom`,`sujet`,`description`,`date_creation`) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        
        ps.setInt(1, t.getId_activite() );
        ps.setString(2, t.getNom() );
        ps.setString(3, t.getSujet() );
        ps.setString(4, t.getDescription() );
        ps.setDate(5, (java.sql.Date)(Date)t.getDate_creation() );
        
        ps.executeUpdate();
        
        
    }

    @Override
    public void delete(int t) throws SQLException {
        
        PreparedStatement ps;
        
        ps = con.prepareStatement("delete from activite where id_activite= ?");
        ps.setInt(1, t);
        
        ps.executeUpdate();
    }

    @Override
    public void update(Activite t) throws SQLException {
        
        PreparedStatement ps;
        
            ps = con.prepareStatement("update activite SET `nom`=?,"
            + "`sujet`=?,`description`=?,"
            + "`date_creation`=? where id_activite ="+t.getId_activite());
            
        
        ps.setString(1, t.getNom());
        ps.setString(2, t.getSujet());
        ps.setString(3, t.getDescription());
        ps.setDate(4, (java.sql.Date)(Date)t.getDate_creation());
        ps.executeUpdate();
    }

    @Override
    public ObservableList<Activite> readAll() throws SQLException {
        ObservableList<Activite> activites = FXCollections.observableArrayList();
        
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from activite");
      while (rs.next()) {
          int id = rs.getInt(1);
          String nom = rs.getString(2);
          String sujet = rs.getString(3);
          String description = rs.getString(4);
          Date date_creation = rs.getDate(5);
          
          Activite a = new Activite(id, nom, sujet, description, date_creation);
          activites.add(a);
      }
      return activites;
    }
    
    public Activite get(int id) throws SQLException {
         
        Activite a = new Activite();
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery("select * from activite where id_activite = "+id);
        while(rs.next()){
          int idu = rs.getInt("id_activite");
          String nom = rs.getString("nom");
          String sujet = rs.getString("sujet");
          String description = rs.getString("description");
          Date date_creation = rs.getDate("date_creation");
          a.setId_activite(idu);
          a.setNom(nom);
          a.setSujet(sujet);
          a.setDescription(description);
          a.setDate_creation(date_creation);
        }
      return a;
    }




    
}
