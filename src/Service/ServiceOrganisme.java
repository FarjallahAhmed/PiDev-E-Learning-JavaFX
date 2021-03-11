/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Organismes;
import Entities.Participants;
import Entities.Utilisateurs;
import Services.IServiceUtilisateurs;
import Utils.Connexion;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mehdi
 */
public class ServiceOrganisme implements IServiceUtilisateurs {
    
    Connection cnx;

    public ServiceOrganisme(Connection cnx) {
        this.cnx = Connexion.getInstance().getConnexion();
    }

    public ServiceOrganisme() {
       this.cnx = Connexion.getInstance().getConnexion();
    }
    
    

    
    public int AjouterUtilisateur(Utilisateurs u) {
        try {
            
            
            //Statement stm = cnx.createStatement();
            
            
            String queryU = "INSERT INTO `utilisateurs`(`nom`, `prenom`, `dateNaissance`, `cin`, `email`, `login`, `pwd`, `num`)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(queryU,Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setDate(3, u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            
           ps.executeUpdate();
            
            int insertedID ;
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                insertedID = (generatedKeys.getInt(1));
                System.out.println(insertedID);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
            
            
            
            
            
            
            
            String queryP = "INSERT INTO `organismes`(`id`,`nomorganisme`, `type`)"
                    + "VALUES (?,?,?)"; 
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
            psp.setInt(1, insertedID);
            psp.setString(2, ((Organismes)u).getNomOrganisme());
            psp.setString(3, ((Organismes)u).getTypeOrganisme());
            
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            return(insertedID);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return(0);
    
    
    }

    @Override
    public ObservableList<Utilisateurs> AfficherUtlisaterus() throws SQLException  {
        
       
            String query = "SELECT * FROM `organismes`";
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join organismes ON utilisateurs.id=organismes.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Organismes p = new Organismes();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                
                
                p.setNomOrganisme(rst.getString(11));
                p.setTypeOrganisme(rst.getString(13));
                
                
                oblist.add(p);
                utilisateurs.add(p);
                
               
            }
     
                     
        return oblist;    
       
        }
    
    
    
    public void SupprimerUtilisateur(int id) {
        
        
        String queryU = "delete  from utilisateurs where id="+id;
        String queryP = "DELETE FROM `organismes` WHERE id = "+id;
        
        System.out.println(queryP);
        Statement stm;
        
        try {
            stm = cnx.createStatement();
            stm.executeUpdate(queryP);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
         try {
            stm = cnx.createStatement();
            stm.executeUpdate(queryU);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println("Failed");
        }
        
        
        
        }

    public List<Utilisateurs> AfficherUtlisaterus(Participants p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Utilisateurs> getSelectedUser(int id) throws SQLException {
        
        String query = "SELECT * FROM `organismes` WHERE id = "+id;
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join participants ON utilisateurs.id=participants.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Organismes p = new Organismes();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                
                
                
                p.setNomOrganisme(rst.getString(11));
                
                p.setTypeOrganisme(rst.getString(13));
               
                
                oblist.add(p);
                utilisateurs.add(p);
                
               
            }
     
                     
        return oblist;    
      
    }

    @Override
    public void ModifierUtilisateur(Utilisateurs u) throws SQLException {
        
       
            
            
            //Statement stm = cnx.createStatement();
            
            
            String queryU = "UPDATE `utilisateurs` SET `nom`=?,`prenom`=?,`dateNaissance`=?,`cin`=?,`email`=?,`login`=?,`pwd`=?,`num`=? WHERE id = "+u.getId();
            PreparedStatement ps = cnx.prepareStatement(queryU);
            
            
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setDate(3, u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            
           ps.executeUpdate();
           
            String queryP = "UPDATE `organismes` SET `nomorganisme`=?,`typeorganisme`=? WHERE id ="+u.getId();
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
           
            psp.setString(1, ((Organismes)u).getNomOrganisme());
     
            psp.setString(2, ((Organismes)u).getTypeOrganisme());
        
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            
            
        
    
       
    }

}
