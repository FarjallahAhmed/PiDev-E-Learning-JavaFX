/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Workshop;
import static Service.implWorkshopService.qrCode;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author dell
 */
public class threadMail extends Thread {

    public Workshop w; 

    public threadMail(Workshop w) {
        this.w = w;
    }

    public Workshop getW() {
        return w;
    }
    
        public void run(){
            try {
                sendMail(this.getW());
            } catch (WriterException ex) {
                Logger.getLogger(threadMail.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(threadMail.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    
    }
}
        
