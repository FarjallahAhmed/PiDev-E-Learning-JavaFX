/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implService;

import Entity.Promotion;
import Service.workshopService;
import connection.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Promotion t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Promotion> readAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Promotion find(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
