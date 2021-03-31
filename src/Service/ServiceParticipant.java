/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Participants;
import Entities.Utilisateurs;
import Services.IServiceUtilisateurs;
import Utils.Connexion;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Mehdi
 */
public class ServiceParticipant implements IServiceUtilisateurs {
    
    Connection cnx;

    public ServiceParticipant(Connection cnx) {
        this.cnx = Connexion.getInstance().getConnexion();
    }

    public ServiceParticipant() {
       this.cnx = Connexion.getInstance().getConnexion();
    }
    
    

    
    public int AjouterUtilisateur(Utilisateurs u) {
        try {
            
            
            //Statement stm = cnx.createStatement();
            
            
            String queryU = "INSERT INTO `utilisateurs`(`nom`, `prenom`, `dateNaissance`, `cin`, `email`, `login`, `pwd`, `num`)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(queryU,Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setDate(3, u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            
           ps.executeUpdate();
            
            int insertedID ;
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                insertedID = (generatedKeys.getInt(1));
                System.out.println(insertedID);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
           
        }
            
            
            
            
            
            
            
            String queryP = "INSERT INTO `participants`(`id`,`niveauEtude`, `certificatsObtenus`, `interessePar`, `nombreDeFormation`)"
                    + "VALUES (?,?,?,?,?)"; 
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
            psp.setInt(1, insertedID);
            psp.setString(2, ((Participants)u).getNiveauEtude());
            psp.setInt(3, ((Participants)u).getCertificatsObtenus());
            psp.setString(4, ((Participants)u).getInterssePar());
            psp.setInt(5, ((Participants)u).getNombreDeFormation());
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            return(insertedID);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceParticipant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return(0);
    
    
    }

    @Override
    public ObservableList<Utilisateurs> AfficherUtlisaterus() throws SQLException  {
        
       
            String query = "SELECT * FROM `participants`";
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join participants ON utilisateurs.id=participants.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Participants p = new Participants();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                p.setImage(rst.getString(10));
                p.setNiveauEtude(rst.getString(12));
                p.setCertificatsObtenus(rst.getInt(13));
                p.setInterssePar(rst.getString(14));
                p.setNombreDeFormation(rst.getInt(15));
                
                oblist.add(p);
                utilisateurs.add(p);
                
               
            }
     
                     
        return oblist;    
       
        }
    
    
    
    public void SupprimerUtilisateur(int id) {
        
        
        String queryU = "delete  from utilisateurs where id="+id;
        String queryP = "DELETE FROM `participants` WHERE id = "+id;
        
        System.out.println(queryP);
        Statement stm;
        
        try {
            stm = cnx.createStatement();
            stm.executeUpdate(queryP);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
         try {
            stm = cnx.createStatement();
            stm.executeUpdate(queryU);
            System.out.println("Done");
        } catch (SQLException ex) {
            System.out.println("Failed");
        }
        
        
        
        }

    public List<Utilisateurs> AfficherUtlisaterus(Participants p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Utilisateurs> getSelectedUser(int id) throws SQLException {
        
        String query = "SELECT * FROM `participants` WHERE id = "+id;
            
            List <Utilisateurs> utilisateurs = new ArrayList <> ();
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from utilisateurs Inner Join participants ON utilisateurs.id=participants.id");
            
            ObservableList <Utilisateurs> oblist =  FXCollections.observableArrayList();
            
             while (rst.next())
            {
                
                Participants p = new Participants();
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                p.setNiveauEtude(rst.getString(11));
                p.setCertificatsObtenus(rst.getInt(12));
                p.setInterssePar(rst.getString(13));
                p.setNombreDeFormation(rst.getInt(14));
                
                oblist.add(p);
                utilisateurs.add(p);
                
               
            }
     
                     
        return oblist;    
      
    }

    @Override
    public void ModifierUtilisateur(Utilisateurs u) throws SQLException {
        
       
            
            
            //Statement stm = cnx.createStatement();
            
            
            String queryU = "UPDATE `utilisateurs` SET `nom`=?,`prenom`=?,`dateNaissance`=?,`cin`=?,`email`=?,`login`=?,`pwd`=?,`num`=? WHERE id = "+u.getId();
            PreparedStatement ps = cnx.prepareStatement(queryU);
            
            
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setDate(3, u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            
           ps.executeUpdate();
           
            String queryP = "UPDATE `participants` SET `niveauEtude`=?,`certificatsObtenus`=?,`interessePar`=?,`nombreDeFormation`=? WHERE id ="+u.getId();
            PreparedStatement psp = cnx.prepareStatement(queryP);
            
           
            psp.setString(1, ((Participants)u).getNiveauEtude());
            psp.setInt(2, ((Participants)u).getCertificatsObtenus());
            psp.setString(3, ((Participants)u).getInterssePar());
            psp.setInt(4, ((Participants)u).getNombreDeFormation());
            psp.executeUpdate();
            
            //stm.executeUpdate(queryU);
            //stm.executeUpdate(queryP);
            
            
        
    
       
    }
    
    
    
    public Participants getParticipants(int id) throws SQLException
    {
        
         Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from participants Inner Join utilisateurs ON utilisateurs.id=participants.id WHERE utilisateurs.id ="+id);
            Participants p = new Participants();
            
            
             while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(7));
                p.setPrenom(rst.getString(8));
                p.setDateNaissance(rst.getDate(9));
                p.setCin(rst.getString(10));
                p.setEmail(rst.getString(11));
                p.setLogin(rst.getString(12));
                p.setPassword(rst.getString(13));
                p.setNum(rst.getString(14));
                p.setNiveauEtude(rst.getString(2));
                p.setCertificatsObtenus(rst.getInt(3));
                p.setInterssePar(rst.getString(4));
                p.setNombreDeFormation(rst.getInt(5));
                p.setImage(rst.getString(15));
                
              
                
                
               
            }
     
                     
        return p;    
        
    }
    
    public int CountParticipant() throws SQLException
    {
        
       
               int total = 0;
               
               Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select count(*) from participants Inner Join utilisateurs ON utilisateurs.id=participants.id");
            
             while (rst.next())
            {
               total  = rst.getInt("count(*)");
            }
            
            return total;
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

        //return false;
    
    
    
     public Participants getSelectedUserByEmail(String id) throws SQLException  {
        
            Statement stm;
            stm = cnx.createStatement();
            
            ResultSet rst = stm.executeQuery("Select * from participants Inner Join utilisateurs ON utilisateurs.id=participants.id WHERE utilisateurs.email = '"+id+"'");
            Participants p = new Participants();
            
            
        try {
            while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(7));
                p.setPrenom(rst.getString(8));
                p.setDateNaissance(rst.getDate(9));
                p.setCin(rst.getString(10));
                p.setEmail(rst.getString(11));
                p.setLogin(rst.getString(12));
                p.setPassword(rst.getString(13));
                p.setNum(rst.getString(14));
                p.setNiveauEtude(rst.getString(2));
                p.setCertificatsObtenus(rst.getInt(3));
                p.setInterssePar(rst.getString(4));
                p.setNombreDeFormation(rst.getInt(5));
                
            }
        } catch (SQLException ex) {
           return null;
        }
               
        return p;  
  
     }  
     
     public Utilisateurs getSelectedUsersByEmail(String id) throws SQLException  {
        
            Statement stm;
            stm = cnx.createStatement();
            
            ResultSet rst = stm.executeQuery("Select * from utilisateurs WHERE email = '"+id+"'");
            Utilisateurs p = new Utilisateurs();
            
            
        try {
            while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(2));
                p.setPrenom(rst.getString(3));
                p.setDateNaissance(rst.getDate(4));
                p.setCin(rst.getString(5));
                p.setEmail(rst.getString(6));
                p.setLogin(rst.getString(7));
                p.setPassword(rst.getString(8));
                p.setNum(rst.getString(9));
                
                
            }
        } catch (SQLException ex) {
           return null;
        }
               
        return p;  
        
        
     }  
     
     
      public void ModifierUtilisateurOnly(Utilisateurs u) throws SQLException {
        
       
            
            
            //Statement stm = cnx.createStatement();
            
            
            String queryU = "UPDATE `utilisateurs` SET `nom`=?,`prenom`=?,`dateNaissance`=?,`cin`=?,`email`=?,`login`=?,`pwd`=?,`num`=? WHERE email = '"+u.getEmail()+"'";
            PreparedStatement ps = cnx.prepareStatement(queryU);
            
            
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setDate(3, u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            
           ps.executeUpdate();
          
  
    }
      
      
      public ObservableList<Utilisateurs> chercherParticipants(String input,int id) throws SQLException
    {
        
            Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from participants Inner Join utilisateurs ON utilisateurs.id=participants.id WHERE utilisateurs.nom like '%"+input+"%'");
            Participants p = new Participants();
            ObservableList<Utilisateurs>comformations = FXCollections.observableArrayList();
            
            
             while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(7));
                p.setPrenom(rst.getString(8));
                p.setDateNaissance(rst.getDate(9));
                p.setCin(rst.getString(10));
                p.setEmail(rst.getString(11));
                p.setLogin(rst.getString(12));
                p.setPassword(rst.getString(13));
                p.setNum(rst.getString(14));
                p.setNiveauEtude(rst.getString(2));
                p.setCertificatsObtenus(rst.getInt(3));
                p.setInterssePar(rst.getString(4));
                p.setNombreDeFormation(rst.getInt(5));
                comformations.add(p);
                   
               
            }
     
                     
        return comformations;    
        
    }
      
      
      
      
      
    public Utilisateurs getParticipantsUtilisateurs(int id) throws SQLException
    {
        
         Statement stm;
            stm = cnx.createStatement();
            ResultSet rst = stm.executeQuery("Select * from participants Inner Join utilisateurs ON utilisateurs.id=participants.id WHERE utilisateurs.id ="+id);
            Utilisateurs p = new Utilisateurs();
            
            
             while (rst.next())
            {
                
                
                
                p.setId(rst.getInt(1));
                p.setNom(rst.getString(7));
                p.setPrenom(rst.getString(8));
                p.setDateNaissance(rst.getDate(9));
                p.setCin(rst.getString(10));
                p.setEmail(rst.getString(11));
                p.setLogin(rst.getString(12));
                p.setPassword(rst.getString(13));
                p.setNum(rst.getString(14));
             
                
              
                
                
               
            }
     
                     
        return p;    
        
    }
    
    public void setImageUser(String image , int id) throws SQLException
    {
        
                
            String queryU = "UPDATE `utilisateurs` SET `image`= ? WHERE id = '"+id+"'";
            PreparedStatement ps = cnx.prepareStatement(queryU);
            
            
            ps.setString(1,image);
            
           
            
           ps.executeUpdate();
    }
    
    
    public void  uploadtp(String path,String fullpath)
    {
        String server = "127.0.0.1";
        int port = 21;
        String user = "mehdi";
        String pass = "123456789";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(fullpath);
 
            String firstRemoteFile = path;
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
 
            // APPROACH #2: uploads second file using an OutputStream
            
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    
    
      public static void donwload_ftp(String path)
    {
         String server = "127.0.0.1";
        int port = 21;
        String user = "mehdi";
        String pass = "123456789";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            // APPROACH #1: using retrieveFile(String, OutputStream)
            String remoteFile1 = "/"+path;
            File downloadFile1 = new File("C:\\Users\\mehdi\\Downloads\\"+path);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
                               
               
            
            outputStream1.close();
 
            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
            }
 
            // APPROACH #2: using InputStream retrieveFileStream(String)
            
          
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
        
     
}
