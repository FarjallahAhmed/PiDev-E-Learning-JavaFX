/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Etities.Participants;
import Etities.Utilisateurs;
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
public class ServiceParticipant implements IServiceUtilisateurs {
    
    Connection cnx;

    public ServiceParticipant(Connection cnx) {
        this.cnx = Connexion.getInstance().getConnexion();
    }

    public ServiceParticipant() {
       this.cnx = Connexion.getInstance().getConnexion();
    }
    
    

    @Override
    public boolean AjouterUtilisateur(Utilisateurs u) {
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
            
            
            
            
            
            
            
            String queryP = "INSERT INTO `participants`(`id`,`niveauEtude`, `certificatsObtenus`, `interessePar`, `nombreDeFormation`)"
                    + "VALUES (?,?,?,?,?)"; 
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
            psp.setInt(1, insertedID);
            psp.setString(2, ((Participants)u).getNiveauEtude());
            psp.setInt(3, ((Participants)u).getCertificatsObtenus());
            psp.setString(4, ((Participants)u).getInterssePar());
            psp.setInt(5, ((Participants)u).getNombreDeFormation());
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            return(true);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return(false);
    
    
    }

    @Override
    public ObservableList<Utilisateurs> AfficherUtlisaterus() throws SQLException  {
        
       
            String query = "SELECT * FROM `participants`";
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join participants ON utilisateurs.id=participants.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Participants p = new Participants();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                p.setNiveauEtude(rst.getString(11));
                p.setCertificatsObtenus(rst.getInt(12));
                p.setInterssePar(rst.getString(13));
                p.setNombreDeFormation(rst.getInt(14));
                
                oblist.add(p);
                utilisateurs.add(p);
                
               
            }
     
                     
        return oblist;    
       
        }
    
    
    
    public void SupprimerUtilisateur(int id) {
        
        
        String queryU = "delete  from utilisateurs where id="+id;
        String queryP = "DELETE FROM `participants` WHERE id = "+id;
        
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
        
        String query = "SELECT * FROM `participants` WHERE id = "+id;
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join participants ON utilisateurs.id=participants.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Participants p = new Participants();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                p.setNiveauEtude(rst.getString(11));
                p.setCertificatsObtenus(rst.getInt(12));
                p.setInterssePar(rst.getString(13));
                p.setNombreDeFormation(rst.getInt(14));
                
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
           
            String queryP = "UPDATE `participants` SET `niveauEtude`=?,`certificatsObtenus`=?,`interessePar`=?,`nombreDeFormation`=? WHERE id ="+u.getId();
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
           
            psp.setString(1, ((Participants)u).getNiveauEtude());
            psp.setInt(2, ((Participants)u).getCertificatsObtenus());
            psp.setString(3, ((Participants)u).getInterssePar());
            psp.setInt(4, ((Participants)u).getNombreDeFormation());
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            
            
        
    
       
    }

}
