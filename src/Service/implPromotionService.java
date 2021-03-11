/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Promotion;
import Entities.Workshop;
import Services.workshopService;
import Utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
        con = Connexion.getInstance().getConnexion();
    }
    private void dialog(Alert.AlertType alertType,String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }

    @Override
    public void ajouter(Promotion t) throws SQLException {
        float hj;
        int nb ;
        float hf;
        float resultat;
        String reqF= "SELECT * FROM formation where id = "+t;
        String req= "INSERT INTO promotion (`id_formation`,`dateDebut`, `dateFin`,`type`, `prix`) VALUES (?,?,?,?,?)";
        String sql="UPDATE formation set cout_fin=? where id = "+t;
        PreparedStatement ps = con.prepareStatement(req);
        PreparedStatement ps2 = con.prepareStatement(sql);
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery(reqF);

        while (rs.next()) {                
                    
            hj =  rs.getFloat(6);
            nb = rs.getInt(7);
            hf =  rs.getFloat(8);
            
            float total = hj*nb;

            resultat = total * (100-t.getPrix())/100;
            ps2.setFloat(1, resultat);
            ps2.executeUpdate();

            
        }
        
        ps.setInt(1, t.getId());
        ps.setDate(2, (Date)t.getDateDebut());
        ps.setDate(3, (Date)t.getDateFin());
        ps.setString(4, t.getType() );
        ps.setFloat(5, t.getPrix());
        
        //dialog(Alert.AlertType.INFORMATION, "Ajouter Avec Success Promotion");
        ps.executeUpdate();

    }
    public void affecter(Promotion t,int idd) throws SQLException {
        float hj;
        int nb ;
        float hf;
        float resultat;
        String reqF= "SELECT * FROM formation where id = "+idd;
        String req= "INSERT INTO promotion (`id`,`id_formation`,`dateDebut`, `dateFin`, `prix`) VALUES ("+idd+",?,?,?,?)";
        String sql="UPDATE formation set cout_fin=? where id = "+idd;
        PreparedStatement ps = con.prepareStatement(req);
        PreparedStatement ps2 = con.prepareStatement(sql);
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery(reqF);

        while (rs.next()) {                
                    
            hj =  rs.getFloat(6);
            nb = rs.getInt(7);
            hf =  rs.getFloat(8);
            
            float total = hj*nb;

            resultat = total * (100-t.getPrix())/100;
            ps2.setFloat(1, resultat);
            ps2.executeUpdate();

            
        }
        
        ps.setInt(1, t.getId());
        ps.setDate(2, (Date)t.getDateDebut());
        ps.setDate(3, (Date)t.getDateFin());
        
        ps.setFloat(4, t.getPrix());
        
        //dialog(Alert.AlertType.INFORMATION, "Ajouter Avec Success Promotion");
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
                // String sql='	select FROM produit p INNER JOIN promotions promo ON promo.reference = p.reference';
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
    
        
    public ObservableList< ArrayList<String>> readAllPromotion() throws SQLException {
                 ObservableList< ArrayList<String>> promotions = FXCollections.observableArrayList();
                 String sql="select f.objet as Objet, f.type, f.cout_hj, f.cout_fin, p.dateDebut, p.dateFin FROM formation f INNER JOIN promotion p ON p.id = f.id";
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery(sql);
        while (rs.next()) {                
            String objet = String.valueOf(rs.getString(1));
            String type = String.valueOf(rs.getString(2));
            String cout_h = String.valueOf(rs.getFloat(3));
            String cout_fin = String.valueOf(rs.getFloat(4));
            String dateDebut = String.valueOf(rs.getDate(5));
            String dateFin = String.valueOf(rs.getDate(6));
            
            ArrayList<String> list = new ArrayList();
            list.add(objet);
            list.add(type);
            list.add(cout_h);
            list.add(cout_fin);
            list.add(dateDebut);
            list.add(dateFin);
                    
            
            
            
            
            
            promotions.add(list);
            //Promotion p = new Promotion(id, nom, dateDebut, dateFin, prix);
           
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
