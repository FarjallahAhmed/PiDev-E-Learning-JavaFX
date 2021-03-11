/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Activite;
import Entities.Participants;
import Entities.Utilisateurs;
import Entities.projet;
import Services.ActiviteService;
import Utils.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Asus
 */
public class impProjet implements ActiviteService<projet>{
    
    
    private Connection con;
    private int id_activite;
    public static int insertedId;
    
    public impProjet() {
        con =  Connexion.getInstance().getConnexion();
    }

    @Override
    public void ajouter(projet t) throws SQLException {
        
        
          String req= "INSERT INTO `projet`(`nom`, `sujet`, `description`, `dateC`) VALUES (?,?,?,?)";
          PreparedStatement ps = con.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
          
                
        ps.setString(1, t.getNom() );
        ps.setString(2, t.getSujet() );
        ps.setString(3, t.getDescription() );
        ps.setDate(4, (java.sql.Date)(Date)t.getDate_creation() );
        
        ps.executeUpdate();
        try (ResultSet generatedKeys = ps.getGeneratedKeys()){
            generatedKeys.next();               
            insertedId = generatedKeys.getInt(1);
            
        } catch (Exception e) {
            System.out.println("non");
        }
        
       
            
                
            
        
        
         
    
        /*String reqP= "INSERT INTO projet (`id_projet`,`nbmax`,`h_rencontre`) VALUES (?,?,?)";
        PreparedStatement psP = con.prepareStatement(reqP);
        
        psP.setInt(1,insertedId);
        psP.setInt(2,t.getNombreMax());
        psP.setString(3,t.getHeureRencontre());
        System.out.println(t);
        psP.executeUpdate();*/
        
        
        
        
        
    }
        
        
    
        

    @Override
    public void delete(int t) throws SQLException {
        PreparedStatement ps;
        
        ps = con.prepareStatement("delete from projet where id_projet= ?");
        ps.setInt(1, t);
        
        ps.executeUpdate();

    }
        public projet get(String id) throws SQLException {
        
         
        projet a = new projet();
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery("select * from projet where `nom` = '"+id+"'");
        while(rs.next()){
           
          int idu = rs.getInt("id_projet");
          
          String nom = rs.getString("nom");
          String sujet = rs.getString("sujet");
          String description = rs.getString("description");
          Date date_creation = rs.getDate("dateC");
          
          a.setId(idu);
          a.setNom(nom);
          a.setSujet(sujet);
          a.setDescription(description);
          a.setDate_creation(date_creation);
            
        }
            System.out.println(a);
      return a;
    }

    @Override
    public void update(projet t) throws SQLException {
        
         PreparedStatement ps;
        
            ps = con.prepareStatement("update projet SET `nom`=?,"
            + "`sujet`=?,`description`=?,"
            + "`dateC`=? where id_projet ="+t.getId());
            
        
        ps.setString(1, t.getNom());
        ps.setString(2, t.getSujet());
        ps.setString(3, t.getDescription());
        ps.setDate(4, (java.sql.Date)(java.util.Date)t.getDate_creation());
        ps.executeUpdate();
    }

    @Override
    public ObservableList<projet> readAll() throws SQLException {
        ObservableList<projet> activites = FXCollections.observableArrayList();
        
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from projet");
      while (rs.next()) {
          int id = rs.getInt(1);
          String nom = rs.getString(2);
          String sujet = rs.getString(3);
          String description = rs.getString(4);
          java.util.Date date_creation = rs.getDate(5);
          
          projet a = new projet(id, nom, sujet, description, date_creation);
          activites.add(a);
      }
      return activites;

    }
    
    
//    public ObservableList<Activite> readProjetAndActivite() throws SQLException {
//        
//        ObservableList<Activite> activites = FXCollections.observableArrayList();
//        
//        Statement ste=con.createStatement();
//    ResultSet rs = ste.executeQuery("select * from projet INNER JOIN activite WHERE projet.id_projet = activite.id_activite");
//    Activite a = new Activite();
//      while (rs.next()) {
//          
//          int id = rs.getInt(1);
//          String nom = rs.getString(2);
//          String sujet = rs.getString(3);
//          String description = rs.getString(4);
//          java.util.Date date_creation = rs.getDate(5);
//          
//          
//          
//          
//          
//          
//          
//          
//          
//        //  projet a = new projet(id, nom, sujet, description, date_creation);
//          
//      //    activites.add(a);
//          
//      }
//      return activites;
//
//    }
//    
    
    
    public ObservableList< ArrayList<String>> readAllActivite() throws SQLException {
                 ObservableList< ArrayList<String>> promotions = FXCollections.observableArrayList();
                 String sql="select * from projet INNER JOIN activite WHERE projet.id_projet = activite.id_activite";
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
        ResultSet rs = ste.executeQuery(sql);
        while (rs.next()) {                
           
            
            
          
          String nom = rs.getString(2);
          String sujet = rs.getString(3);
          String description = rs.getString(4);
          String date_creation = String.valueOf(rs.getDate(5));
          String nomActivite = rs.getString(10);
          
            
            ArrayList<String> list = new ArrayList();
            list.add(nom);
            list.add(sujet);
            list.add(description);
            list.add(date_creation);
            list.add(nomActivite);
   

            
            promotions.add(list);
            //Promotion p = new Promotion(id, nom, dateDebut, dateFin, prix);
           
        }
        return promotions;
    }
    
    
    
     public ObservableList<projet> chercherProjet(String input) throws SQLException
    {
        
            Statement stm;
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery("Select * from projet WHERE nom like '%"+input+"%'");
            projet p = new projet();
            ObservableList<projet>comformations = FXCollections.observableArrayList();
            
            
             while (rs.next())
            {
                
                int id = rs.getInt(1);
                String nom = rs.getString(2);
                String sujet = rs.getString(3);
                String description = rs.getString(4);
                java.util.Date date_creation = rs.getDate(5);
                
                
                   projet a = new projet(id, nom, sujet, description, date_creation);
                 comformations.add(a);
               
            }
     
                     
        return comformations;    
        
    }
     
     
     public boolean sendMail(String dest,String obj,String contenu) {
        
        // Recipient's email ID needs to be mentioned.
      String to = dest;

      // Sender's email ID needs to be mentioned
      String from = "highriseshighrises@gmail.com";
      final String username = "highriseshighrises";//change accordingly
      final String password = "highrises123";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");

      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
	   }
         });

      try {
	   // Create a default MimeMessage object.
	   Message message = new MimeMessage(session);
	
	   // Set From: header field of the header.
	   message.setFrom(new InternetAddress(from));
	
	   // Set To: header field of the header.
	   message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
	
	   // Set Subject: header field
	   message.setSubject(obj);
	
	   // Now set the actual message
	   message.setText(contenu);

	   // Send message
	   Transport.send(message);

	   System.out.println("Sent message successfully....");
           return true;
      } catch (MessagingException e) {
         throw new RuntimeException(e);

      }
      
    }
    
    
    
    
    
    
    



    }

   

    