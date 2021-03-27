/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Services.workshopService;
import Entities.Workshop;
import Utils.Connexion;
import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
/**
 *
 * @author dell
 */
public class implWorkshopService implements workshopService<Workshop>{

    private Connection con;
    

    public implWorkshopService() {
        con = Connexion.getInstance().getConnexion();
    }
    private void dialog(Alert.AlertType alertType,String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }
    
    @Override
    public void ajouter(Workshop t) throws SQLException {
        String req= "INSERT INTO workshop (`nameCalendar`,`nomEvent`, `dateDebut`, `dateFin`, `hDebut`, `hFin`, `lieu`, `nbParticipant`, `type`, `description`, `prix`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        
        
        ps.setString(1, t.getNomCalendar() );
        ps.setString(2, t.getNomEvent() );
        ps.setDate(3, (Date)t.getDateDebut());
        ps.setDate(4, (Date)t.getDateFin());
        ps.setTime(5, t.gethDebut());
        ps.setTime(6, t.gethFin());
        ps.setString(7, t.getLieu() );
        ps.setInt(8, t.getNbParticipant());
        ps.setString(9, t.getType() );
        ps.setString(10, t.getDescription() );
        ps.setFloat(11, t.getPrix());
        ps.executeUpdate();
        
    }

    @Override
    public void delete(int t) throws SQLException {
            
            PreparedStatement ps;
 
            ps = con.prepareStatement("delete from workshop where id = ?");
            ps.setInt(1, t);
            ps.executeUpdate();
    }
    
    public void delete(String cl) throws SQLException {
            
            PreparedStatement ps;
 
            ps = con.prepareStatement("delete from workshop where nameCalendar = ?");
            ps.setString(1, cl);
            ps.executeUpdate();
    }
    
    

    @Override
    public void update(Workshop t) throws SQLException {
        PreparedStatement ps;
        
            ps = con.prepareStatement("update workshop SET `nomEvent`=?,`dateDebut`=?,"
                    + "`dateFin`=?,`hDebut`=?,`hFin`=?,"
                    + "`lieu`=?,`nbParticipant`=?,`type`=?,"
                    + "`description`=?,`prix`=? where id =?");
            ps.setString(1, t.getNomEvent() );
            ps.setDate(2, t.getDateDebut());
            ps.setDate(3, t.getDateFin());
            ps.setTime(4, t.gethDebut());
            ps.setTime(5, t.gethFin());
            ps.setString(6, t.getLieu() );
            ps.setInt(7, t.getNbParticipant());
            ps.setString(8, t.getType() );
            ps.setString(9, t.getDescription() );
            ps.setFloat(10, t.getPrix());
            ps.setInt(11, t.getId() );
            
            ps.executeUpdate();

    }

    @Override
    public ObservableList<Workshop> readAll() throws SQLException {
         ObservableList<Workshop> workshops = FXCollections.observableArrayList();
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from workshop");
     while (rs.next()) {                
               int id = rs.getInt(1);
               String nom = rs.getString(3);
               Date dateDebut = rs.getDate(4);
               Date dateFin = rs.getDate(5);
               Time hdebut = rs.getTime(6);
               Time hfin = rs.getTime(7);
               String lieu = rs.getString(8);
               int nbParticipant = rs.getInt(9);
               String type = rs.getString(10);
               String description = rs.getString(11);
               float prix =  rs.getFloat(12);
               
               
               Workshop w = new Workshop(id, nom, dateDebut, dateFin, hdebut, hfin, lieu, nbParticipant, type, description, prix);
              workshops.add(w);
     }
    return workshops;
    }

    @Override
    public Workshop find(int id) throws SQLException {
        Statement ste=con.createStatement();
        Workshop w = new Workshop();
        ResultSet rs = ste.executeQuery("select * from workshop where id = "+id);
        
     while (rs.next()) {                
               int idu = rs.getInt(1);
               String nom = rs.getString(3);
               Date dateDebut = rs.getDate(4);
               Date dateFin = rs.getDate(5);
               Time hdebut = rs.getTime(6);
               Time hfin = rs.getTime(7);
               String lieu = rs.getString(8);
               int nbParticipant = rs.getInt(9);
               String type = rs.getString(10);
               String description = rs.getString(11);
               float prix =  rs.getFloat(12);
               
            w.setNomEvent(nom);
            w.setDateDebut(dateDebut);
            w.setDateFin(dateFin);
            w.sethDebut(hdebut);
            w.sethFin(hfin);
            w.setType(type);
            w.setDescription(description);
            w.setLieu(lieu);
            w.setNbParticipant(nbParticipant);
            w.setPrix(prix);
               
              
              
     }
            return w;
    }

    public ObservableList<Workshop> readAllW(String calendar) throws SQLException {
        
        ObservableList<Workshop> workshops = FXCollections.observableArrayList();
        //List<Workshop> workshops = new ArrayList<>();
        Statement ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select * from workshop where `nameCalendar` = '"+calendar+"'");
     while (rs.next()) {                
               int id = rs.getInt(1);
               String nom = rs.getString(3);
               Date dateDebut = rs.getDate(4);
               Date dateFin = rs.getDate(5);
               Time hdebut = rs.getTime(6);
               Time hfin = rs.getTime(7);
               String lieu = rs.getString(8);
               int nbParticipant = rs.getInt(9);
               String type = rs.getString(10);
               String description = rs.getString(11);
               float prix =  rs.getFloat(12);
               
               
               Workshop w = new Workshop(id, nom, dateDebut, dateFin, hdebut, hfin, lieu, nbParticipant, type, description, prix);
              workshops.add(w);
     }
    return workshops;

    }
    
    public boolean sendMail(Workshop t) throws WriterException, IOException {
        
        qrCode(t);
        // Recipient's email ID needs to be mentioned.
      
      String to = "highriseshighrises@gmail.com";

      // Sender's email ID needs to be mentioned
      String from = "highriseshighrises@gmail.com";
      final String username = "highriseshighrises@gmail.com";//change accordingly
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
           String messageSend = new String();
           MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
           BodyPart messageBodyPart1 = new MimeBodyPart();     
                    

	
	   // Set From: header field of the header.
	   message.setFrom(new InternetAddress(from));
	
	   // Set To: header field of the header.
	   message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
	
	   // Set Subject: header field
	   message.setSubject(t.getNomEvent());
	
	   // Now set the actual message
            messageSend ="<h1>"+t.getNomEvent()+ "</h1>"+"Hello,between "+t.getDateDebut()+" and"+t.getDateFin()+" there will be an event of "+t.getDescription()
            +" you can find us in "+ t.getLieu()+" the begin at "+t.gethDebut()+" and end at "+t.gethFin()
            +" So your are welcome to join us.  "+"</br> you find below a qr code for your participation ";
            
            messageBodyPart1.setContent(messageSend,"text/html");  
            String filename = "C:\\Users\\dell\\Desktop\\Game\\"+t.getNomEvent()+".png";  
            DataSource source = new FileDataSource(filename);    
            messageBodyPart2.setDataHandler(new DataHandler(source)); 
            messageBodyPart2.setFileName(filename); 
            Multipart multipart = new MimeMultipart();    
            multipart.addBodyPart(messageBodyPart1);    
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);
	   // Send message
            Transport.send(message);

	   System.out.println("Sent message successfully....");
           return true;
      } catch (MessagingException e) {
         throw new RuntimeException(e);
         
         
      }
        
        
        
        //return false;

    }
          public static void createQR(String data, String path,
                                String charset, Map hashMap,
                                int height, int width)
        throws WriterException, IOException
    {
 
        BitMatrix matrix = new MultiFormatWriter().encode(
            new String(data.getBytes(charset), charset),
            BarcodeFormat.QR_CODE, width, height);
 
        MatrixToImageWriter.writeToFile(
            matrix,
            path.substring(path.lastIndexOf('.') + 1),
            new File(path));
    }
          public static void qrCode(Workshop w) throws WriterException, IOException {
        // TODO code application logic here
        
        // The data that the QR code will contain
        String data =  w.getNomEvent();
 
        // The path where the image will get saved
        String path = "C:\\Users\\dell\\Desktop\\Game\\"+data+".png";
 
        // Encoding charset
        String charset = "UTF-8";
 
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
            = new HashMap<EncodeHintType,
                          ErrorCorrectionLevel>();
 
        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                    ErrorCorrectionLevel.L);
 
        // Create the QR code and save
        // in the specified folder
        // as a jpg file
        createQR(data, path, charset, hashMap, 300, 300);
        System.out.println("QR Code Generated!!! ");
    }
    
}
