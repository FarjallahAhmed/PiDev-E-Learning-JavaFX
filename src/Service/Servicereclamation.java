/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.reclamation;
import Services.IServicereclamation;
import Utiles.Maconnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author rania
 */
public class Servicereclamation implements IServicereclamation{
    Connection cnx;

    public Servicereclamation() {
        cnx=Maconnexion.getInstance().getConnection();
    }
    

    @Override
    public void ajouter_reclamation(reclamation r) {
        try {
            Statement stm=cnx.createStatement();
            String query="INSERT INTO reclamation(id_user,objet,message) VALUES ('"+r.getId_user()+"','"+r.getObjet()+"','"+r.getMessage()+"')";
             Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Confirmation Dialog");
alert.setHeaderText("Confirmation ");
alert.setContentText("Etes vous sur de vouloir ajouter cette reclamation?");

Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    stm.executeUpdate(query);
    Alert alert2 = new Alert(AlertType.INFORMATION);
            alert2.setTitle("Ajout");
            alert2.setHeaderText("Reclamation ajoutée");
            alert2.setContentText("La reclamation a été ajouter avec success!");
            alert2.showAndWait();}
            
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    @Override
    public ObservableList<reclamation> Afficher_reclamation() {
      ObservableList<reclamation>reclamations=FXCollections.observableArrayList();
 
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `reclamation`";
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(rst.getInt("id_user"));
                 r.setObjet(rst.getString("objet"));
                 r.setMessage(rst.getString("message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
}

    @Override
    public void supprimer_reclamation(int id_reclamation) {
        try {
            Statement stm=cnx.createStatement();
            String query=" Delete FROM reclamation where id_reclamation='"+id_reclamation+"'";
            Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Confirmation Dialog");
alert.setHeaderText("Confirmation ");
alert.setContentText("Etes vous sur de vouloir supprimer cette reclamation?");

Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    stm.executeUpdate(query);
    Alert alert2 = new Alert(AlertType.INFORMATION);
            alert2.setTitle("Suppression");
            alert2.setHeaderText("Reclamation Supprimé");
            alert2.setContentText("La reclamation a été supprimer avec success!");
            alert2.showAndWait();}
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier_reclamation(reclamation r) {
      try {

        PreparedStatement ps;

        ps=cnx.prepareStatement("UPDATE  reclamation set `id_user`=?,`objet`=?,`message`=? where id_reclamation="+r.getId_reclamation());
        ps.setInt(1, r.getId_user());
        ps.setString(2,r.getObjet());
        ps.setString(3,r.getMessage());
        ps.executeUpdate();
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("Reclamation Modifié");
            alert.setContentText("La reclamation a été modifier avec success!");
            alert.showAndWait();

//alert.showAndWait();
    } catch (SQLException ex) {
        Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
//         Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Ajout");
//            alert.setHeaderText("Reclamation Ajouté");
//            alert.setContentText("La reclamation a été Ajouter avec success!");
//            alert.showAndWait();
    }
    }
    }

