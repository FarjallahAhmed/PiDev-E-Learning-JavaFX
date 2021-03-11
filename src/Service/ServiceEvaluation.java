 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Evaluation;
import Entities.Formations;
import Entities.formeval;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import Utils.Connexion;
/**
 *
 * @author AMINE N
 */
public class ServiceEvaluation {
    Connection cnx;

    public ServiceEvaluation() {
        cnx=Connexion.getInstance().getConnexion();
    }
       public void Ajouter_Evaluation(Evaluation e) {
    try {
        //Statement stm=cnx.createStatement();
        PreparedStatement ps;
        
        //String Query="INSERT INTO formation(Objet,Type,Objectif,nb_participants,cout_hj,nb_jour,cout_fin,date_reelle,date_prevu,path) VALUES ('"+f.getObjet()+"','"+f.getType()+"','"+f.getObjectif()+"','"+f.getNb_participants()+"','"+f.getCout_hj()+"','"+f.getNb_jour()+"','"+f.getCout_fin()+"','"+f.getDate_reelle()+"','"+f.getDate_prevu()+"','"+f.getPath()+"')";
        //stm.executeUpdate(Query);
        ps=cnx.prepareStatement("INSERT INTO evaluation (Note,Rapport,id_formation)VALUES (?,?,?)");
        ps.setInt(1,e.getNote());
        ps.setString(2, e.getRapport());
        ps.setInt(3, e.getId_formation());
      
        ps.executeUpdate();
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout");
            alert.setHeaderText("Evaluation Ajouté");
            alert.setContentText("L'evaluation a été Ajouter avec success!");
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
        public ObservableList<formeval> Get_Evaluation()
    {
        ObservableList<formeval> formationstab=FXCollections.observableArrayList();
    try {
        Statement stm=cnx.createStatement();
        String Query="SELECT u.Objet, u.Type, u.Objectif, u.nb_participants,u.cout_hj,u.nb_jour,u.cout_fin, AVG(p.Note) as moy ,p.Rapport " +
"  FROM formation u " +
"  INNER JOIN evaluation p ON u.Id = p.id_formation "+
                "GROUP BY p.id_formation";

        ResultSet rs=stm.executeQuery(Query);
        formeval form;
        while(rs.next())
        {
            System.out.println(rs.getString("Objet"));
            System.out.println(rs.getString("Type"));
            System.out.println(rs.getString("Objet"));
            System.out.println(rs.getString("Moy"));
            form=new formeval(rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getInt("nb_participants"),rs.getFloat("cout_hj"),rs.getInt("nb_jour"),rs.getFloat("cout_fin"),rs.getFloat("Moy"),rs.getString("Rapport"));
            //form.setObjet(rs.getString("Objet"));
            formationstab.add(form); //public formeval(String Objet, String Type, String Objectif, int nb_participants, float cout_hj, int nb_jour, float cout_fin, int note, String rapport)
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return formationstab;
        
        
    }
    
}
