/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Evaluation;
import Entities.Formations;
import Entities.panier;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import Utils.Connexion;

/**
 *
 * @author AMINE N
 */
public class ServicePanier {
    Connection cnx;
    public ServicePanier()
{
    cnx=Connexion.getInstance().getConnexion();
}
         public void Ajouter_Panier(panier e) {
    try {
        //Statement stm=cnx.createStatement();
        PreparedStatement ps;
        
        //String Query="INSERT INTO formation(Objet,Type,Objectif,nb_participants,cout_hj,nb_jour,cout_fin,date_reelle,date_prevu,path) VALUES ('"+f.getObjet()+"','"+f.getType()+"','"+f.getObjectif()+"','"+f.getNb_participants()+"','"+f.getCout_hj()+"','"+f.getNb_jour()+"','"+f.getCout_fin()+"','"+f.getDate_reelle()+"','"+f.getDate_prevu()+"','"+f.getPath()+"')";
        //stm.executeUpdate(Query);
        ps=cnx.prepareStatement("INSERT INTO panier (id_client) VALUES (?) ");
        ps.setInt(1,e.getId_client());
       
      
        ps.executeUpdate();
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setHeaderText("Panier cree");
            alert.setContentText("Panier a été Ajouter avec success");
            alert.showAndWait();
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
        Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText("Look, an Error Dialog");
alert.setContentText("Ooops, there was an error!");

alert.showAndWait();
    }
         
    }
         public boolean chercher_panier(int id_client)
         {
             boolean test=false;
             try {
                 
        Statement stm=cnx.createStatement();
        String Query="select *from panier where id_client="+id_client;
        ResultSet rs=stm.executeQuery(Query);
        
       if(rs.next())
       {
           test=true;
       }
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return test;
         }
         public void Update_panier(panier f)
         {
             try {
        
        PreparedStatement ps;
        
        ps=cnx.prepareStatement("UPDATE  panier set `prix_total`=?,`nombre`=? where id_client="+f.getId_client());
        ps.setFloat(1, f.getPrix_total());
        ps.setInt(2, f.getNombre());
            
        ps.executeUpdate();
        


    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
         
    }
         }
         
}
         


