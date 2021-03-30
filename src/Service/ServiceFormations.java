/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Formations;
import Entities.formeval;
import Services.IServiceFormations;
import com.mysql.cj.Session;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javax.mail.Message;
//import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Utils.Connexion;


/**
 *
 * @author AMINE N
 */
public class ServiceFormations implements IServiceFormations{
Connection cnx;
public ServiceFormations()
{
    cnx=Connexion.getInstance().getConnexion();
}
    @Override
    public void Ajouter_Formation(Formations f) {
    try {
        //Statement stm=cnx.createStatement();
        PreparedStatement ps;
        
        //String Query="INSERT INTO formation(Objet,Type,Objectif,nb_participants,cout_hj,nb_jour,cout_fin,date_reelle,date_prevu,path) VALUES ('"+f.getObjet()+"','"+f.getType()+"','"+f.getObjectif()+"','"+f.getNb_participants()+"','"+f.getCout_hj()+"','"+f.getNb_jour()+"','"+f.getCout_fin()+"','"+f.getDate_reelle()+"','"+f.getDate_prevu()+"','"+f.getPath()+"')";
        //stm.executeUpdate(Query);
        ps=cnx.prepareStatement("INSERT INTO formation (Objet,Type,Objectif,nb_participants,cout_hj,nb_jour,cout_fin,date_reelle,date_prevu,path,categorie,id_formateur)VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
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
        ps.setString(11, f.getCategorie());
        ps.setInt(12, f.getId_formateur());
        ps.executeUpdate();
        //sendmail(f);
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
    public ObservableList<Formations> filtrer_forma(String critere )
    {
        ObservableList<Formations> formationstab=FXCollections.observableArrayList();
    try {
        Statement stm=cnx.createStatement();
        String Query="select * from formation where categorie='"+critere+"'";
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
     public ObservableList<Formations> Get_Formations2()
    {
        ObservableList<Formations> formationstab=FXCollections.observableArrayList();
    try {
        Statement stm=cnx.createStatement();
        String Query="select *from formation";
        ResultSet rs=stm.executeQuery(Query);
        Formations form;
        while(rs.next())
        {
            form=new Formations(rs.getInt("Id"),rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getInt("nb_participants"),rs.getFloat("cout_hj"),rs.getInt("nb_jour"),rs.getFloat("cout_fin"),rs.getDate("date_reelle"),rs.getDate("date_prevu"),rs.getString("path"),rs.getString("categorie"));
            formationstab.add(form);
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return formationstab;
        
        
    }
     public void sendmail(Formations r) {
		
		String to = "highriseshighrises@gmail.com";
		String from = "highriseshighrises@gmail.com";
		final String username = "highriseshighrises";
		final String password = "highrises123";
		
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");  
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		
		javax.mail.Session session = javax.mail.Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
     }
         });
		
		try {
    //create a MimeMessage object
    Message message = new MimeMessage(session);
 
    //set From email field 
    message.setFrom(new InternetAddress(from));
 
    //set To email field
    message.setRecipients(javax.mail.Message.RecipientType.TO,
               InternetAddress.parse(to));
 
    //set email subject field
    message.setSubject("Nouvelle formation ajoutée");
 
    //set the content of the email message
    message.setText(r.toString());

    //send the email message
    Transport.send(message);

    System.out.println("Email Message Sent Successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }

			
    }
     public ObservableList<Formations> search(String input)
     {
          ObservableList<Formations>comformations = FXCollections.observableArrayList();
          try {
             Statement stm;
            stm= cnx.createStatement();
            String query="Select * from formation  WHERE Objet like '%"+input+"%'";
           ResultSet rs=stm.executeQuery(query); 
           Formations form;
           while(rs.next())
        {
            
            form=new Formations(rs.getInt("Id"),rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getInt("nb_participants"),rs.getFloat("cout_hj"),rs.getInt("nb_jour"),rs.getFloat("cout_fin"),rs.getDate("date_reelle"),rs.getDate("date_prevu"),rs.getString("path"),rs.getString("categorie"));
            comformations.add(form); 
        }
           
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
          return comformations;
         
     }
      public ObservableList<Formations> Get_Formationsperformateur(int id_formateur)
    {
        ObservableList<Formations> formationstab=FXCollections.observableArrayList();
    try {
        Statement stm=cnx.createStatement();
        String Query="select *from formation where id_formateur="+id_formateur;
        ResultSet rs=stm.executeQuery(Query);
        Formations form;
        while(rs.next())
        {
            form=new Formations(rs.getInt("Id"),rs.getString("Objet"),rs.getString("Type"),rs.getString("Objectif"),rs.getInt("nb_participants"),rs.getFloat("cout_hj"),rs.getInt("nb_jour"),rs.getFloat("cout_fin"),rs.getDate("date_reelle"),rs.getDate("date_prevu"),rs.getString("path"),rs.getString("categorie"));
            formationstab.add(form);
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return formationstab;
        
        
    }
       public float Get_Reputationformateur(int id_formateur)
    {
        ObservableList<formeval> formationstab=FXCollections.observableArrayList();
        float reputation=0;
        float somme=0;
        int compteur=0;
    try {
        
        Statement stm=cnx.createStatement();
        String Query="SELECT u.Objet, u.Type, u.Objectif, u.nb_participants,u.cout_hj,u.nb_jour,u.cout_fin,u.path,u.Id, AVG(p.Note) as moy ,p.Rapport " +
"  FROM formation u " +
"  INNER JOIN evaluation p ON u.Id = p.id_formation where u.id_formateur="+id_formateur+
                " GROUP BY p.id_formation";

        ResultSet rs=stm.executeQuery(Query);
        formeval form;
        while(rs.next())
        {
            compteur++;
            somme=somme+rs.getFloat("moy");
        }
        reputation=somme/compteur;
        
    } catch (SQLException ex) {
        Logger.getLogger(ServiceFormations.class.getName()).log(Level.SEVERE, null, ex);
    }
        return reputation;
        
        
    }
    
    
}
