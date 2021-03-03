/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implService;

import Entity.Activite;
import Entity.projet;
import Service.ActiviteService;
import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;

/**
 *
 * @author Asus
 */
public class impProjet implements ActiviteService<projet>{
    
    
    private Connection con;
    private int id_activite;
    
    public impProjet() {
        con =  Connexion.getIstance().getConnection();
    }

    @Override
    public void ajouter(projet t) throws SQLException {
        
        
          String req= "INSERT INTO activite (`id_activite`,`nom`,`sujet`,`description`,`date_creation`) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
        int insertedId;
        
        
        ps.setInt(1, t.getId_activite() );
        ps.setString(2, t.getNom() );
        ps.setString(3, t.getSujet() );
        ps.setString(4, t.getDescription() );
        ps.setDate(5, (java.sql.Date)(Date)t.getDate_creation() );
        
        ps.executeUpdate();
        
        try (ResultSet generatedKeys = ps.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                insertedId = (generatedKeys).getInt(1);
                
            }
        
         else
         {
                 throw new SQLException("failed");
                 
                 }
         
    }
        String reqP= "INSERT INTO projet (`id_projet`,`nbmax`,`h_rencontre`) VALUES (?,?,?)";
        PreparedStatement psP = con.prepareStatement(reqP);
        
        psP.setInt(1,insertedId);
        psP.setInt(2,t.getNombreMax());
        psP.setString(3,t.getHeureRencontre());
        System.out.println(t);
        psP.executeUpdate();
        
        
        
        
        
    }
        
        
    
        

    @Override
    public void delete(int t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(projet t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Activite> readAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }
    

}

    