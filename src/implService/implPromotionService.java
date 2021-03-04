/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implService;

import Entity.Promotion;
import Entity.Workshop;
import Service.workshopService;
import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author dell
 */
public class implPromotionService implements workshopService<Promotion>{
    
        private Connection con;
    

    public implPromotionService() {
        con = Connexion.getIstance().getConnection();
    }
    private void dialog(Alert.AlertType alertType,String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }

    @Override
    public void ajouter(Promotion t) throws SQLException {
        String req= "INSERT INTO promotion (`dateDebut`, `dateFin`,`type`, `prix`) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        
        
        ps.setDate(1, (Date)t.getDateDebut());
        ps.setDate(2, (Date)t.getDateFin());
        ps.setString(3, t.getType() );
        ps.setFloat(4, t.getPrix());
        dialog(Alert.AlertType.INFORMATION, "Ajouter Avec Success Promotion");
        ps.executeUpdate();

    }

    @Override
    public void delete(int t) throws SQLException {
            PreparedStatement ps;
 
            ps = con.prepareStatement("delete from promotion where id = ?");
            ps.setInt(1, t);
            dialog(Alert.AlertType.INFORMATION, "Delete avec sucee");
            ps.executeUpdate();
    }

    @Override
    public void update(Promotion t) throws SQLException {
            PreparedStatement ps;
        
            ps = con.prepareStatement("UPDATE `promotion` SET "
                    + "`dateDebut`=?,"
                    + "`dateFin`=?,"
                    + "`type`=?,"
                    + "`prix`=? WHERE id=?");
            
            ps.setDate(1, (Date)t.getDateDebut());
            ps.setDate(2, (Date)t.getDateFin());
            ps.setString(3, t.getType() );
            ps.setFloat(4, t.getPrix());
            ps.setInt(5, t.getId() );
            dialog(Alert.AlertType.INFORMATION, "Ajouter Avec Success Promotion");
            ps.executeUpdate();
            
    }

    @Override
    public ObservableList<Promotion> readAll() throws SQLException {
                 ObservableList<Promotion> promotions = FXCollections.observableArrayList();
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery("select * from promotion");
        while (rs.next()) {                
            int id = rs.getInt(1);
            String nom = rs.getString(4);
            Date dateDebut = rs.getDate(2);
            Date dateFin = rs.getDate(3);
            float prix =  rs.getFloat(5);


            Promotion p = new Promotion(id, nom, dateDebut, dateFin, prix);
            promotions.add(p);
        }
    return promotions;
    }

    @Override
    public Promotion find(int id) throws SQLException {
        Statement ste=con.createStatement();
        Promotion p = new Promotion();
        ResultSet rs = ste.executeQuery("select * from promotion where id = "+id);
        
     while (rs.next()) {                
            int idu = rs.getInt(1);
            String nom = rs.getString(4);
            Date dateDebut = rs.getDate(2);
            Date dateFin = rs.getDate(3);
            float prix =  rs.getFloat(5);
            
            p.setId(idu);
            p.setPrix(prix);
            p.setType(nom);
            p.setDateFin(dateFin);
            p.setDateDebut(dateDebut);
          
     }
            return p;
    }

        
}
