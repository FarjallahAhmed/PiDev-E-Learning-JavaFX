/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Message;
import Entities.Utilisateurs;
import Entities.reclamation;
import Services.IServicereclamation;
import Utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author rania
 */
public class Servicereclamation implements IServicereclamation{
    Connection cnx;
    String idUser = ""+UserSession.UserSession.getInstace("", 0, "").getId();
    String TypeUser = UserSession.UserSession.getInstace("", 0, "").getType();


    public Servicereclamation() {
        cnx=Connexion.getInstance().getConnexion();
        /*if (UserSession.UserSession.getInstace("", 0, "").getType().equals("Admin")) {
            idUser = "%";
        }
        System.out.println(idUser+" ----------------------");*/
    }
    
    
    
    
    public void addMessage(Message m) {
        try {
            Statement stm=cnx.createStatement();
            String query="INSERT INTO message(contenu) VALUES ('"+m.getContenu()+"')";

            stm.executeUpdate(query);
 
            
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    @Override
    public void ajouter_reclamation(reclamation r) {
        try {
            Statement stm=cnx.createStatement();
            
             Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Confirmation Dialog");
alert.setHeaderText("Confirmation ");
alert.setContentText("Etes vous sur de vouloir ajouter cette reclamation?");

Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
    addMessage(r.getMessage());
    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    String query="INSERT INTO reclamation(id_user,objet,id_message,date) VALUES ('"+idUser+"','"+r.getObjet()+"','"+getMessageByContenu(r.getMessage().getContenu()).getId()+"','"+sqlDate+"')";
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
    
    public Message getMessageByContenu(String con) {
        Message m = new Message();
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `message` where contenu = '"+con+"'";
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 m.setId(rst.getInt("id_message"));
                 m.setContenu(rst.getString("contenu"));
                 
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return m;
}
    
    public List<Utilisateurs> getAllUsers() {
        List<Utilisateurs> l = new ArrayList<Utilisateurs>();
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `utilisateurs`";
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 Utilisateurs m = new Utilisateurs();
                 m.setId(rst.getInt("id"));
                 m.setNom(rst.getString("nom"));
                 m.setPrenom(rst.getString("prenom"));
                 l.add(m);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return l;
}
    
    public Message getMessageById(int id) {
        Message m = new Message();
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `message` where id_message = "+id;
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 m.setId(rst.getInt("id_message"));
                 m.setContenu(rst.getString("contenu"));
                 
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return m;
}
        
    public Utilisateurs getUseerById(int id) {
        Utilisateurs m = new Utilisateurs();
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `utilisateurs` where id = "+id;
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 m.setId(rst.getInt("id"));
                 m.setNom(rst.getString("nom"));
                 m.setPrenom(rst.getString("prenom"));
      
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return m;
}
    

    @Override
        public ObservableList<reclamation> Afficher_reclamation() {
      ObservableList<reclamation>reclamations=FXCollections.observableArrayList();
 
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `reclamation` where id_user = '"+idUser+"'";
             if(TypeUser.equals("Admin")) {
             query="Select * from `reclamation`";
        }
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 r.setDate(rst.getDate("date"));
                 
                 System.out.println(rst.getInt("id_message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
}
public ObservableList<reclamation> search(String input) {
      ObservableList<reclamation>reclamations=FXCollections.observableArrayList();
      
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="SELECT reclamation.id_reclamation, reclamation.id_user,"
                     + " reclamation.objet, reclamation.id_message "
                     + "FROM reclamation LEFT JOIN utilisateurs ON reclamation.id_user = utilisateurs.id"
                     + " where reclamation.id_user = '"+idUser+"' AND ( utilisateurs.prenom like '%"+input+"%' "
                     + "or utilisateurs.nom like '%"+input+"%' "
                     + "or reclamation.objet like '%"+input+"%' )";
             if(TypeUser.equals("Admin")) {
             query="SELECT reclamation.id_reclamation, reclamation.id_user,"
                     + " reclamation.objet, reclamation.id_message "
                     + "FROM reclamation LEFT JOIN utilisateurs ON reclamation.id_user = utilisateurs.id"
                     + " where utilisateurs.prenom like '%"+input+"%' "
                     + "or utilisateurs.nom like '%"+input+"%' "
                     + "or reclamation.objet like '%"+input+"%'";
        }
             System.out.println(""+query);
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 
                 System.out.println(rst.getInt("id_message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
}
    
    
    public void deleteMessage(int id_message) {
        try {
            Statement stm=cnx.createStatement();
            String query="Delete FROM message where id_message='"+id_message+"'";
           
    stm.executeUpdate(query);
    
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void updateMessage (Message m) {
        try {
            PreparedStatement ps;
            ps=cnx.prepareStatement("UPDATE  message set `contenu`=? where id_message=?");
        ps.setString(1, m.getContenu());
        ps.setInt(2, m.getId());
        ps.executeUpdate();
 
            
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    @Override
    public void modifier_reclamation(reclamation r) {
      try {

        PreparedStatement ps;

        ps=cnx.prepareStatement("UPDATE  reclamation set `id_user`=?,`objet`=? where id_reclamation="+r.getId_reclamation());
        ps.setInt(1, r.getId_user().getId());
        ps.setString(2,r.getObjet());
        ps.executeUpdate();
        updateMessage(r.getMessage());
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

public ObservableList<reclamation> triASC() {
      ObservableList<reclamation>reclamations=FXCollections.observableArrayList();
 
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `reclamation` where id_user = '"+idUser+"' ORDER BY objet ASC";
             if (TypeUser.equals("Admin")){
                query="Select * from `reclamation` ORDER BY objet ASC";
             }
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 
                 System.out.println(rst.getInt("id_message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
}

public ObservableList<reclamation> triDSC() {
      ObservableList<reclamation>reclamations=FXCollections.observableArrayList();
 
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `reclamation` where id_user = '"+idUser+"' ORDER BY objet DESC";
             if (TypeUser.equals("Admin")){
                query="Select * from `reclamation` ORDER BY objet DESC";
             }
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 
                 System.out.println(rst.getInt("id_message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
}

    public void sendmail(reclamation r) {
		
		String to = "Admin@Gmail.com";
		String from = "de035e2f63-9bcb24@inbox.mailtrap.io";
		final String username = "8398fa6d71985e";
		final String password = "d36e07e0134e7b";
		
		String host = "smtp.mailtrap.io";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");  
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
    }
         });
		
		try {
    //create a MimeMessage object
    javax.mail.Message message = new MimeMessage(session);
 
    //set From email field 
    message.setFrom(new InternetAddress(from));
 
    //set To email field
    message.setRecipients(javax.mail.Message.RecipientType.TO,
               InternetAddress.parse(to));
 
    //set email subject field
    message.setSubject("Reclamation !");
 
    //set the content of the email message
    message.setText(r.toString());

    //send the email message
    Transport.send(message);

    System.out.println("Email Message Sent Successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
    }
    public List<reclamation> getAllRec() {
      List<reclamation>reclamations= new ArrayList<>();
 
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `reclamation`";
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 r.setDate(rst.getDate("date"));
                 
                 System.out.println(rst.getInt("id_message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
}

    public void archiveRec(int id) {
       reclamation r=this.getReclamation(id);
        try {
            Statement stm=cnx.createStatement();
            
             
    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    String query="INSERT INTO archive (id_reclamation,id_user,objet,id_message,date) VALUES ('"+r.getId_reclamation()+"','"+idUser+"','"+r.getObjet()+"','"+getMessageByContenu(r.getMessage().getContenu()).getId()+"','"+sqlDate+"')";
    stm.executeUpdate(query);
    
            
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    

    }
    
    public reclamation getReclamation(int id) {
        reclamation r=new reclamation();
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `reclamation` where id_reclamation = '"+id+"'";
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 r.setDate(rst.getDate("date"));
                 
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return r;
    }

    public ObservableList<reclamation> archiiiiiiive() {
        ObservableList<reclamation>reclamations=FXCollections.observableArrayList();
 
        try {
             Statement  stm;
             stm= cnx.createStatement();
             String query="Select * from `archive` where id_user = '"+idUser+"'";
             if (TypeUser.equals("Admin")){
                query="Select * from `archive`";
             }
             ResultSet rst=stm.executeQuery(query);
             while (rst.next()){
                 reclamation r=new reclamation();
                 r.setId_reclamation(rst.getInt("id_reclamation"));
                 r.setId_user(getUseerById(rst.getInt("id_user")));
                 r.setObjet(rst.getString("objet"));
                 
                 r.setMessage(getMessageById(rst.getInt("id_message")));
                 r.setDate(rst.getDate("date"));
                 
                 System.out.println(rst.getInt("id_message"));
                 reclamations.add(r);
                     
             }
                  
        } catch (SQLException ex) {
            Logger.getLogger(Servicereclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
         return reclamations;
    }
    
}

