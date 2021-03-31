/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Commande;
import Entities.Formations;
import Entities.comformation;
import Services.IServiceCommande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import Utils.Connexion;
import java.util.Date;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import org.controlsfx.control.Notifications;
import javafx.geometry.Pos;
//import javafx.util.Duration;

/**
 *
 * @author User
 */
public class ServiceCommande implements IServiceCommande{
    Connection cnx;

    public ServiceCommande() {
        cnx=Connexion.getInstance().getConnexion();
    }
    
    @Override
    public void AjouterCommande(Commande c) {
        try {
            Statement stm=cnx.createStatement();
       
        String query="INSERT INTO commande(prix,etat,id_client,id_formation) VALUES ('"+c.getPrix()+"','"+c.getEtat()+"','"+c.getId_client()+"','"+c.getId_formation()+"')";
        
    stm.executeUpdate(query);
    Notifications n =Notifications.create().title("SUCCESS").text("ajout avec succes!").position(Pos.TOP_CENTER).hideAfter(javafx.util.Duration.seconds(2));
        n.darkStyle();
       n.show();

         } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ObservableList<Commande> AfficherCommande(int id)  {
        ObservableList<Commande>commandes=FXCollections.observableArrayList();
       try {
            Statement stm;
        
            stm = cnx.createStatement();
       
            String query="SELECT * from `commande` where id_client="+id ;
            ResultSet rst=stm.executeQuery(query);
            
            while(rst.next()){
                Commande c=new Commande();
                c.setId(rst.getInt("id"));
                c.setDate(rst.getDate("date"));
                c.setPrix(rst.getFloat("prix"));
                c.setEtat(rst.getString("etat"));
                c.setId_formation(rst.getInt("id_formation"));
                commandes.add(c);
            }
             } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return commandes; 
    }

    @Override
    public void supprimercommande(int id) {
         try {
            Statement stm=cnx.createStatement();
       
        String query=" Delete FROM commande where id='"+id+"'";
        
    stm.executeUpdate(query);
   

         } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 

    @Override
    public void ModifierCommande(Commande c) {
        try {

        PreparedStatement ps;

        ps=cnx.prepareStatement("UPDATE  commande set `date`=?,`prix`=?,`etat`=? where id="+c.getId());
        ps.setDate(1, (java.sql.Date) c.getDate());
        ps.setFloat(2, c.getPrix());
        ps.setString(3, c.getEtat());
        ps.executeUpdate();
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("commande Modifié");
            alert.setContentText("Lacommande a été modifier avec success!");
            alert.showAndWait();

alert.showAndWait();
    } catch (SQLException ex) {
        Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
         Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setHeaderText("commande Ajouté");
            alert.setContentText("La commande a été Ajouter avec success!");
            alert.showAndWait();
    }
    }
    public ObservableList<comformation> Get_formvomm(int id_client)
    {
        String etat="non valider";
        ObservableList<comformation> formationstab=FXCollections.observableArrayList();
    try {
        Statement stm=cnx.createStatement();
        String Query="SELECT u.Objet, u.Type, u.Objectif, p.etat,p.prix,p.id_formation,p.id_client,p.id " +
"  FROM formation u " +
"  INNER JOIN commande p ON u.Id = p.id_formation where p.id_client="+id_client+" AND p.etat='"+etat+"'";

        ResultSet rs=stm.executeQuery(Query);
        comformation form;
        while(rs.next())
        {
            System.out.println(rs.getString("Objet"));
            System.out.println(rs.getString("Type"));
            System.out.println(rs.getString("Objectif"));
            System.out.println(rs.getFloat("prix"));
            System.out.println(rs.getString("etat"));
            System.out.println(rs.getInt("id_formation"));
            
            
           // form=new formeval(rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getInt("nb_participants"),rs.getFloat("cout_hj"),rs.getInt("nb_jour"),rs.getFloat("cout_fin"),rs.getInt("Note"),rs.getString("Rapport"));
            //form.setObjet(rs.getString("Objet"));
            form= new comformation(rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getFloat("prix"),rs.getString("etat"),rs.getInt("id_formation"),rs.getInt("id"));
            formationstab.add(form); //public formeval(String Objet, String Type, String Objectif, int nb_participants, float cout_hj, int nb_jour, float cout_fin, int note, String rapport)
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return formationstab;
        
        
    }
    public ObservableList<comformation> search(String input,int id_client){
         ObservableList<comformation>comformations = FXCollections.observableArrayList();
        String etat="non valider";
        try {
             Statement stm;
            stm= cnx.createStatement();
            String query="SELECT u.Objet, u.Type, u.Objectif, p.etat,p.prix,p.id_formation,p.id_client " +
"  FROM formation u " +
"  INNER JOIN commande p ON u.Id = p.id_formation where u.objet like '%"+input+"%' AND p.etat='"+etat+"' and p.id_client="+id_client;
           ResultSet rs=stm.executeQuery(query); 
           comformation form;
           while(rs.next())
        {
            System.out.println(rs.getString("Objet"));
            System.out.println(rs.getString("Type"));
            System.out.println(rs.getString("Objectif"));
            System.out.println(rs.getFloat("prix"));
            System.out.println(rs.getString("etat"));
            System.out.println(rs.getInt("id_formation"));
            form= new comformation(rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getFloat("prix"),rs.getString("etat"),rs.getInt("id_formation"));
            comformations.add(form); 
        }
           
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        return comformations;
     }
     public ObservableList<comformation> triasc(int id_client){
         ObservableList<comformation> formationstab=FXCollections.observableArrayList();
         String etat="non valider";
        try {
            
            Statement stm=cnx.createStatement();
            String query="SELECT u.Objet, u.Type, u.Objectif, p.etat,p.prix,p.id_formation,p.id_client " +
"  FROM formation u " +
"  INNER JOIN commande p ON u.Id = p.id_formation where id_client='"+id_client+"'AND p.etat='"+etat+"' ORDER by u.objet ASC";
            ResultSet rs=stm.executeQuery(query); 
           comformation form;
           while(rs.next())
        {
            System.out.println(rs.getString("Objet"));
            System.out.println(rs.getString("Type"));
            System.out.println(rs.getString("Objectif"));
            System.out.println(rs.getFloat("prix"));
            System.out.println(rs.getString("etat"));
            System.out.println(rs.getInt("id_formation"));
            form= new comformation(rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getFloat("prix"),rs.getString("etat"),rs.getInt("id_formation"));
           formationstab.add(form); 
        }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
         
       return formationstab;  
     } 
       public ObservableList<comformation> tridsc(int id_client){
           String etat="non valider";
         ObservableList<comformation> formationstab=FXCollections.observableArrayList();
        try {
            Statement stm=cnx.createStatement();
            String query="SELECT u.Objet, u.Type, u.Objectif, p.etat,p.prix,p.id_formation,p.id_client " +
"  FROM formation u " +
"  INNER JOIN commande p ON u.Id = p.id_formation where id_client='"+id_client+"' AND p.etat='"+etat+"'  ORDER by u.objet DESC";
            ResultSet rs=stm.executeQuery(query); 
           comformation form;
           while(rs.next())
        {
            System.out.println(rs.getString("Objet"));
            System.out.println(rs.getString("Type"));
            System.out.println(rs.getString("Objectif"));
            System.out.println(rs.getFloat("prix"));
            System.out.println(rs.getString("etat"));
            System.out.println(rs.getInt("id_formation"));
            form= new comformation(rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getFloat("prix"),rs.getString("etat"),rs.getInt("id_formation"));
           formationstab.add(form); 
        }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
         
       return formationstab;
       
     }
       public void ModifierCommande2(int id_client) {
        try {

        PreparedStatement ps;
        String etat="non valider";
        ps=cnx.prepareStatement("UPDATE  commande set `date`=? where id_client="+id_client+" AND etat='"+etat+"'");
        ps.setDate(1, (java.sql.Date) java.sql.Date.valueOf(LocalDate.now()));
      
        
        ps.executeUpdate();
      


    } catch (SQLException ex) {
        Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
      
    }
        
    }
       public void ModifierCommande3(int id_client) {
        try {

        PreparedStatement ps;
        String etat="valider";
        ps=cnx.prepareStatement("UPDATE  commande set `etat`=? where id_client="+id_client+" AND date is NOT NULL");
        ps.setString(1,etat);
      
        
        ps.executeUpdate();
        


    } catch (SQLException ex) {
        Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
       
    }
       }
       
          public void ViderCommande(int id_client) {
        try {

        PreparedStatement ps;
        String etat="non valider";
        ps=cnx.prepareStatement("DELETE from  commande where id_client="+id_client+" AND etat='"+etat+"'");
       
      
        
        ps.executeUpdate();
      


    } catch (SQLException ex) {
        Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
      
    }
        
    }
    
    
}
