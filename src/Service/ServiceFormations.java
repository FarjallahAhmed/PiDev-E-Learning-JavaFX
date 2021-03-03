/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Formations;
import Services.IServiceFormations;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import utils.MaConnection;

/**
 *
 * @author AMINE N
 */
public class ServiceFormations implements IServiceFormations{
Connection cnx;
public ServiceFormations()
{
    cnx=MaConnection.getInstance().getConnection();
}
    @Override
    public void Ajouter_Formation(Formations f) {
    try {
        //Statement stm=cnx.createStatement();
        PreparedStatement ps;
        
        //String Query="INSERT INTO formation(Objet,Type,Objectif,nb_participants,cout_hj,nb_jour,cout_fin,date_reelle,date_prevu,path) VALUES ('"+f.getObjet()+"','"+f.getType()+"','"+f.getObjectif()+"','"+f.getNb_participants()+"','"+f.getCout_hj()+"','"+f.getNb_jour()+"','"+f.getCout_fin()+"','"+f.getDate_reelle()+"','"+f.getDate_prevu()+"','"+f.getPath()+"')";
        //stm.executeUpdate(Query);
        ps=cnx.prepareStatement("INSERT INTO formation (Objet,Type,Objectif,nb_participants,cout_hj,nb_jour,cout_fin,date_reelle,date_prevu,path)VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, f.getObjet());
        ps.setString(2, f.getType());
        ps.setString(3, f.getObjectif());
        ps.setInt(4, f.getNb_participants());
        ps.setFloat(5, f.getCout_hj());
        ps.setInt(6, f.getNb_jour());
        ps.setFloat(7, f.getCout_fin());
        ps.setDate(8, (Date) f.getDate_reelle());
         ps.setDate(9, (Date) f.getDate_prevu());
        ps.setString(10, f.getPath());
        ps.executeUpdate();
       Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setHeaderText("Formation Ajouté");
            alert.setContentText("La formation a été Ajouter avec success!");
            alert.showAndWait();
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
        Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText("Look, an Error Dialog");
alert.setContentText("Ooops, there was an error!");

alert.showAndWait();
    }
         
    }
    public ObservableList<Formations> Get_Formations()
    {
        ObservableList<Formations> formationstab=FXCollections.observableArrayList();
    try {
        Statement stm=cnx.createStatement();
        String Query="select *from formation";
        ResultSet rs=stm.executeQuery(Query);
        Formations form;
        while(rs.next())
        {
            form=new Formations(rs.getInt("Id"),rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getInt("nb_participants"),rs.getFloat("cout_hj"),rs.getInt("nb_jour"),rs.getFloat("cout_fin"),rs.getDate("date_reelle"),rs.getDate("date_prevu"),rs.getString("path"));
            formationstab.add(form);
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return formationstab;
        
        
    }
    public void Supprimer_formation(int id)
    {
        try {
        Statement stm=cnx.createStatement();
        String Query=" Delete FROM formation where id='"+id+"'";
        Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Confirmation Dialog");
alert.setHeaderText("Confirmation ");
alert.setContentText("Etes vous sur de vouloir supprimer cette formation?");

Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
   stm.executeUpdate(Query);
    Alert alert2 = new Alert(AlertType.INFORMATION);
            alert2.setTitle("Suppression");
            alert2.setHeaderText("Formation Supprimé");
            alert2.setContentText("La formation a été supprimer avec success!");
            alert2.showAndWait();
} 
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    public void Modifier_formation(Formations f)
    {
        try {
        
        PreparedStatement ps;
        
        ps=cnx.prepareStatement("UPDATE  formation set `Objet`=?,`type`=?,`Objectif`=?,`nb_participants`=?,`cout_hj`=?,`nb_jour`=?,`cout_fin`=?,`date_reelle`=?,`date_prevu`=?,`path`=? where Id="+f.getId());
        ps.setString(1, f.getObjet());
        ps.setString(2, f.getType());
        ps.setString(3, f.getObjectif());
        ps.setInt(4, f.getNb_participants());
        ps.setFloat(5, f.getCout_hj());
        ps.setInt(6, f.getNb_jour());
        ps.setFloat(7, f.getCout_fin());
        ps.setDate(8, (Date) f.getDate_reelle());
        ps.setDate(9, (Date) f.getDate_prevu());
        ps.setString(10, f.getPath());
        ps.executeUpdate();
        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Update");
            alert.setHeaderText("Formation Modifié");
            alert.setContentText("La formation a été modifier avec success!");
            alert.showAndWait();

alert.showAndWait();
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
         Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setHeaderText("Formation Ajouté");
            alert.setContentText("La formation a été Ajouter avec success!");
            alert.showAndWait();
    }
    }
    
    
}
