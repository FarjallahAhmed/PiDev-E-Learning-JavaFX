/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Formateurs;
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
public class ServiceFormateur implements IServiceUtilisateurs {
    
    Connection cnx;

    public ServiceFormateur(Connection cnx) {
        this.cnx = Connexion.getInstance().getConnexion();
    }

    public ServiceFormateur() {
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
            
            
            
            
            
            
            
            String queryP = "INSERT INTO `formateurs`(`id`,`specialite`, `justificatif`, `etat`)"
                    + "VALUES (?,?,?,?)"; 
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
            psp.setInt(1, insertedID);
            psp.setString(2, ((Formateurs)u).getSpecialite());
            psp.setString(3, ((Formateurs)u).getJustificatif());
            psp.setBoolean(4, (false));
            
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
        
       
            String query = "SELECT * FROM `formateurs`";
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join formateurs ON utilisateurs.id=formateurs.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Formateurs p = new Formateurs();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                
                
                p.setSpecialite(rst.getString(11));
                p.setJustificatif(rst.getString(12));
                p.setEtat(rst.getBoolean(13));
               
                
                
                oblist.add(p);
                utilisateurs.add(p);
                
               
            }
     
                     
        return oblist;    
       
        }
    
    
    
    public void SupprimerUtilisateur(int id) {
        
        
        String queryU = "delete  from utilisateurs where id="+id;
        String queryP = "DELETE FROM `formateurs` WHERE id = "+id;
        
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
        
        String query = "SELECT * FROM `formateurs` WHERE id = "+id;
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join formateurs ON utilisateurs.id=formateurs.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Formateurs p = new Formateurs();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                
                
                p.setSpecialite(rst.getString(11));
                p.setJustificatif(rst.getString(12));
             
                
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
            ps.setDate(3,   u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            
           ps.executeUpdate();
           
            String queryP = "UPDATE `formateurs` SET `specialite`=?,`justificatif`=? WHERE id ="+u.getId();
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
           
            psp.setString(1, ((Formateurs)u).getSpecialite());
            psp.setString(2, ((Formateurs)u).getJustificatif());
            
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            
            
        
    
       
    }

    
    public Formateurs getSelectedUserByEmail(String id) throws SQLException  {
        
            Statement stm;
            stm = cnx.createStatement();
            
            ResultSet rst = stm.executeQuery("Select * from formateurs Inner Join utilisateurs ON utilisateurs.id=formateurs.id WHERE utilisateurs.email = '"+id+"'");
            Formateurs p = new Formateurs();
            
            
        try {
            while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(6));
                p.setPrenom(rst.getString(7));
                p.setDateNaissance(rst.getDate(8));
                p.setCin(rst.getString(9));
                p.setEmail(rst.getString(10));
                p.setLogin(rst.getString(11));
                p.setPassword(rst.getString(12));
                p.setNum(rst.getString(13));
                
                p.setSpecialite(rst.getString(2));
                p.setJustificatif(rst.getString(3));
                p.setEtat(rst.getBoolean(4));
               
                
            }
        } catch (SQLException ex) {
           return null;
        }
               
        return p;  
        
        
     }   
    
    
    
    
    public Formateurs getSelectedUserById(int id) throws SQLException  {
        
            Statement stm;
            stm = cnx.createStatement();
            
            ResultSet rst = stm.executeQuery("Select * from formateurs Inner Join utilisateurs ON utilisateurs.id=formateurs.id WHERE utilisateurs.id = "+id);
            Formateurs p = new Formateurs();
            
            
        try {
            while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(6));
                p.setPrenom(rst.getString(7));
                p.setDateNaissance(rst.getDate(8));
                p.setCin(rst.getString(9));
                p.setEmail(rst.getString(10));
                p.setLogin(rst.getString(11));
                p.setPassword(rst.getString(12));
                p.setNum(rst.getString(13));
                
                p.setSpecialite(rst.getString(2));
                p.setJustificatif(rst.getString(3));
                
               
                
            }
        } catch (SQLException ex) {
           return null;
        }
               
        return p;  
        
        
     } 


        public int CountFormateurs() throws SQLException
            {


                       int total = 0;

                       Statement stm;
                    stm = cnx.createStatement();
                    ResultSet rst = stm.executeQuery("Select count(*) from formateurs Inner Join utilisateurs ON utilisateurs.id=formateurs.id");

                     while (rst.next())
                    {
                       total  = rst.getInt("count(*)");
                    }

                    return total;
            }  
        
        
        public void ActiverFormateurAccount(int id) throws SQLException
            {


                       int total = 0;

                       Statement stm;
                    stm = cnx.createStatement();
                    stm.executeUpdate("UPDATE `formateurs` SET etat = true WHERE id ="+id);

                    

                    
            }  
        
        
        
        
        public String getEmailUserById(int id) throws SQLException  {
        
            Statement stm;
            stm = cnx.createStatement();
            
            ResultSet rst = stm.executeQuery("Select email from formateurs Inner Join utilisateurs ON utilisateurs.id=formateurs.id WHERE utilisateurs.id = "+id);
            String p = new String();
            
            
        try {
            while (rst.next())
            {
                
                
                
                p = rst.getString(1);
               
                
               
                
            }
        } catch (SQLException ex) {
           return null;
        }
               
        return p;  
        
        
     } 
        
        
}
