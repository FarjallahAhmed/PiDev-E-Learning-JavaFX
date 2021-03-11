/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Activite;
import Services.ActiviteService;
import Utils.Connexion;

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
        con =  Connexion.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Activite t) throws SQLException {
        String req= "INSERT INTO activite (`idP`,`responsable`,`periode`,`nom`) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        
        
        ps.setString(4, t.getNom() );
        ps.setString(2, t.getResponsable() );
        ps.setInt(1, t.getId());
        ps.setString(3, t.getPeriode() );
        
        
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
            + "`responsable`=?,`periode`=?"
            + "`where id_activite ="+t.getId());
            
        
        ps.setString(1, t.getNom());
        ps.setString(3, t.getPeriode());
        ps.setString(2, t.getResponsable());
        
        ps.executeUpdate();
    }

    @Override
    public ObservableList<Activite> readAll() throws SQLException {
        ObservableList<Activite> activites = FXCollections.observableArrayList();
        
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from activite");
      while (rs.next()) {
          int id = rs.getInt(1);
          String responsable = rs.getString(2);
          String nom = rs.getString(3);
          String periode = rs.getString(4);
          
          
          Activite a = new Activite(id,responsable, nom, periode);
          activites.add(a);
      }
      return activites;
    }
    
    public Activite get(int id) throws SQLException {
        return null;
         /*
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
      return a;*/
    }




    
}
