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
            
            
            String queryU = "INSERT INTO `utilisateurs`(`nom`, `prenom`, `dateNaissance`, `cin`, `email`, `login`, `pwd`, `num`,`idp`)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(queryU,Statement.RETURN_GENERATED_KEYS);
            
            
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setDate(3, u.getDateNaissance());
            ps.setString(4, u.getCin());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getLogin());
            ps.setString(7, u.getPassword());
            ps.setString(8, u.getNum()); 
            ps.setString(9, "participants"); 
            
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
                p.setNiveauEtude(rst.getString(13));
                p.setCertificatsObtenus(rst.getInt(14));
                p.setInterssePar(rst.getString(15));
                p.setNombreDeFormation(rst.getInt(16));
                
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
        
        
        
        String m = "'<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
"	<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
"	<head>\n" +
"		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"		<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
"		<meta name=\"format-detection\" content=\"telephone=no\" /> <!-- disable auto telephone linking in iOS -->\n" +
"		<title>Respmail is a response HTML email designed to work on all major email platforms and smartphones</title>\n" +
"		<style type=\"text/css\">\n" +
"			/* RESET STYLES */\n" +
"			html { background-color:#E1E1E1; margin:0; padding:0; }\n" +
"			body, #bodyTable, #bodyCell, #bodyCell{height:100% !important; margin:0; padding:0; width:100% !important;font-family:Helvetica, Arial, \"Lucida Grande\", sans-serif;}\n" +
"			table{border-collapse:collapse;}\n" +
"			table[id=bodyTable] {width:100%!important;margin:auto;max-width:500px!important;color:#7A7A7A;font-weight:normal;}\n" +
"			img, a img{border:0; outline:none; text-decoration:none;height:auto; line-height:100%;}\n" +
"			a {text-decoration:none !important;border-bottom: 1px solid;}\n" +
"			h1, h2, h3, h4, h5, h6{color:#5F5F5F; font-weight:normal; font-family:Helvetica; font-size:20px; line-height:125%; text-align:Left; letter-spacing:normal;margin-top:0;margin-right:0;margin-bottom:10px;margin-left:0;padding-top:0;padding-bottom:0;padding-left:0;padding-right:0;}\n" +
"\n" +
"			/* CLIENT-SPECIFIC STYLES */\n" +
"			.ReadMsgBody{width:100%;} .ExternalClass{width:100%;} /* Force Hotmail/Outlook.com to display emails at full width. */\n" +
"			.ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div{line-height:100%;} /* Force Hotmail/Outlook.com to display line heights normally. */\n" +
"			table, td{mso-table-lspace:0pt; mso-table-rspace:0pt;} /* Remove spacing between tables in Outlook 2007 and up. */\n" +
"			#outlook a{padding:0;} /* Force Outlook 2007 and up to provide a \"view in browser\" message. */\n" +
"			img{-ms-interpolation-mode: bicubic;display:block;outline:none; text-decoration:none;} /* Force IE to smoothly render resized images. */\n" +
"			body, table, td, p, a, li, blockquote{-ms-text-size-adjust:100%; -webkit-text-size-adjust:100%; font-weight:normal!important;} /* Prevent Windows- and Webkit-based mobile platforms from changing declared text sizes. */\n" +
"			.ExternalClass td[class=\"ecxflexibleContainerBox\"] h3 {padding-top: 10px !important;} /* Force hotmail to push 2-grid sub headers down */\n" +
"\n" +
"			/* /\\/\\/\\/\\/\\/\\/\\/\\/ TEMPLATE STYLES /\\/\\/\\/\\/\\/\\/\\/\\/ */\n" +
"\n" +
"			/* ========== Page Styles ========== */\n" +
"			h1{display:block;font-size:26px;font-style:normal;font-weight:normal;line-height:100%;}\n" +
"			h2{display:block;font-size:20px;font-style:normal;font-weight:normal;line-height:120%;}\n" +
"			h3{display:block;font-size:17px;font-style:normal;font-weight:normal;line-height:110%;}\n" +
"			h4{display:block;font-size:18px;font-style:italic;font-weight:normal;line-height:100%;}\n" +
"			.flexibleImage{height:auto;}\n" +
"			.linkRemoveBorder{border-bottom:0 !important;}\n" +
"			table[class=flexibleContainerCellDivider] {padding-bottom:0 !important;padding-top:0 !important;}\n" +
"\n" +
"			body, #bodyTable{background-color:#E1E1E1;}\n" +
"			#emailHeader{background-color:#E1E1E1;}\n" +
"			#emailBody{background-color:#FFFFFF;}\n" +
"			#emailFooter{background-color:#E1E1E1;}\n" +
"			.nestedContainer{background-color:#F8F8F8; border:1px solid #CCCCCC;}\n" +
"			.emailButton{background-color:#205478; border-collapse:separate;}\n" +
"			.buttonContent{color:#FFFFFF; font-family:Helvetica; font-size:18px; font-weight:bold; line-height:100%; padding:15px; text-align:center;}\n" +
"			.buttonContent a{color:#FFFFFF; display:block; text-decoration:none!important; border:0!important;}\n" +
"			.emailCalendar{background-color:#FFFFFF; border:1px solid #CCCCCC;}\n" +
"			.emailCalendarMonth{background-color:#205478; color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; padding-top:10px; padding-bottom:10px; text-align:center;}\n" +
"			.emailCalendarDay{color:#205478; font-family:Helvetica, Arial, sans-serif; font-size:60px; font-weight:bold; line-height:100%; padding-top:20px; padding-bottom:20px; text-align:center;}\n" +
"			.imageContentText {margin-top: 10px;line-height:0;}\n" +
"			.imageContentText a {line-height:0;}\n" +
"			#invisibleIntroduction {display:none !important;} /* Removing the introduction text from the view */\n" +
"\n" +
"			/*FRAMEWORK HACKS & OVERRIDES */\n" +
"			span[class=ios-color-hack] a {color:#275100!important;text-decoration:none!important;} /* Remove all link colors in IOS (below are duplicates based on the color preference) */\n" +
"			span[class=ios-color-hack2] a {color:#205478!important;text-decoration:none!important;}\n" +
"			span[class=ios-color-hack3] a {color:#8B8B8B!important;text-decoration:none!important;}\n" +
"			/* A nice and clean way to target phone numbers you want clickable and avoid a mobile phone from linking other numbers that look like, but are not phone numbers.  Use these two blocks of code to \"unstyle\" any numbers that may be linked.  The second block gives you a class to apply with a span tag to the numbers you would like linked and styled.\n" +
"			Inspired by Campaign Monitors article on using phone numbers in email: http://www.campaignmonitor.com/blog/post/3571/using-phone-numbers-in-html-email/.\n" +
"			*/\n" +
"			.a[href^=\"tel\"], a[href^=\"sms\"] {text-decoration:none!important;color:#606060!important;pointer-events:none!important;cursor:default!important;}\n" +
"			.mobile_link a[href^=\"tel\"], .mobile_link a[href^=\"sms\"] {text-decoration:none!important;color:#606060!important;pointer-events:auto!important;cursor:default!important;}\n" +
"\n" +
"\n" +
"			/* MOBILE STYLES */\n" +
"			@media only screen and (max-width: 480px){\n" +
"				/*////// CLIENT-SPECIFIC STYLES //////*/\n" +
"				body{width:100% !important; min-width:100% !important;} /* Force iOS Mail to render the email at full width. */\n" +
"\n" +
"				/* FRAMEWORK STYLES */\n" +
"				/*\n" +
"				CSS selectors are written in attribute\n" +
"				selector format to prevent Yahoo Mail\n" +
"				from rendering media query styles on\n" +
"				desktop.\n" +
"				*/\n" +
"				/*td[class=\"textContent\"], td[class=\"flexibleContainerCell\"] { width: 100%; padding-left: 10px !important; padding-right: 10px !important; }*/\n" +
"				table[id=\"emailHeader\"],\n" +
"				table[id=\"emailBody\"],\n" +
"				table[id=\"emailFooter\"],\n" +
"				table[class=\"flexibleContainer\"],\n" +
"				td[class=\"flexibleContainerCell\"] {width:100% !important;}\n" +
"				td[class=\"flexibleContainerBox\"], td[class=\"flexibleContainerBox\"] table {display: block;width: 100%;text-align: left;}\n" +
"				/*\n" +
"				The following style rule makes any\n" +
"				image classed with flexibleImage\n" +
"				fluid when the query activates.\n" +
"				Make sure you add an inline max-width\n" +
"				to those images to prevent them\n" +
"				from blowing out.\n" +
"				*/\n" +
"				td[class=\"imageContent\"] img {height:auto !important; width:100% !important; max-width:100% !important; }\n" +
"				img[class=\"flexibleImage\"]{height:auto !important; width:100% !important;max-width:100% !important;}\n" +
"				img[class=\"flexibleImageSmall\"]{height:auto !important; width:auto !important;}\n" +
"\n" +
"\n" +
"				/*\n" +
"				Create top space for every second element in a block\n" +
"				*/\n" +
"				table[class=\"flexibleContainerBoxNext\"]{padding-top: 10px !important;}\n" +
"\n" +
"				/*\n" +
"				Make buttons in the email span the\n" +
"				full width of their container, allowing\n" +
"				for left- or right-handed ease of use.\n" +
"				*/\n" +
"				table[class=\"emailButton\"]{width:100% !important;}\n" +
"				td[class=\"buttonContent\"]{padding:0 !important;}\n" +
"				td[class=\"buttonContent\"] a{padding:15px !important;}\n" +
"\n" +
"			}\n" +
"\n" +
"			/*  CONDITIONS FOR ANDROID DEVICES ONLY\n" +
"			*   http://developer.android.com/guide/webapps/targeting.html\n" +
"			*   http://pugetworks.com/2011/04/css-media-queries-for-targeting-different-mobile-devices/ ;\n" +
"			=====================================================*/\n" +
"\n" +
"			@media only screen and (-webkit-device-pixel-ratio:.75){\n" +
"				/* Put CSS for low density (ldpi) Android layouts in here */\n" +
"			}\n" +
"\n" +
"			@media only screen and (-webkit-device-pixel-ratio:1){\n" +
"				/* Put CSS for medium density (mdpi) Android layouts in here */\n" +
"			}\n" +
"\n" +
"			@media only screen and (-webkit-device-pixel-ratio:1.5){\n" +
"				/* Put CSS for high density (hdpi) Android layouts in here */\n" +
"			}\n" +
"			/* end Android targeting */\n" +
"\n" +
"			/* CONDITIONS FOR IOS DEVICES ONLY\n" +
"			=====================================================*/\n" +
"			@media only screen and (min-device-width : 320px) and (max-device-width:568px) {\n" +
"\n" +
"			}\n" +
"			/* end IOS targeting */\n" +
"		</style>\n" +
"		<!--\n" +
"			Outlook Conditional CSS\n" +
"			These two style blocks target Outlook 2007 & 2010 specifically, forcing\n" +
"			columns into a single vertical stack as on mobile clients. This is\n" +
"			primarily done to avoid the page break bug and is optional.\n" +
"			More information here:\n" +
"			http://templates.mailchimp.com/development/css/outlook-conditional-css\n" +
"		-->\n" +
"		<!--[if mso 12]>\n" +
"			<style type=\"text/css\">\n" +
"				.flexibleContainer{display:block !important; width:100% !important;}\n" +
"			</style>\n" +
"		<![endif]-->\n" +
"		<!--[if mso 14]>\n" +
"			<style type=\"text/css\">\n" +
"				.flexibleContainer{display:block !important; width:100% !important;}\n" +
"			</style>\n" +
"		<![endif]-->\n" +
"	</head>\n" +
"	<body bgcolor=\"#E1E1E1\" leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\">\n" +
"\n" +
"		<!-- CENTER THE EMAIL // -->\n" +
"		<!--\n" +
"		1.  The center tag should normally put all the\n" +
"			content in the middle of the email page.\n" +
"			I added \"table-layout: fixed;\" style to force\n" +
"			yahoomail which by default put the content left.\n" +
"		2.  For hotmail and yahoomail, the contents of\n" +
"			the email starts from this center, so we try to\n" +
"			apply necessary styling e.g. background-color.\n" +
"		-->\n" +
"		<center style=\"background-color:#E1E1E1;\">\n" +
"			<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\" style=\"table-layout: fixed;max-width:100% !important;width: 100% !important;min-width: 100% !important;\">\n" +
"				<tr>\n" +
"					<td align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
"\n" +
"						<!-- EMAIL HEADER // -->\n" +
"						<!--\n" +
"							The table \"emailBody\" is the emails container.\n" +
"							Its width can be set to 100% for a color band\n" +
"							that spans the width of the page.\n" +
"						-->\n" +
"						<table bgcolor=\"#E1E1E1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" id=\"emailHeader\">\n" +
"\n" +
"							<!-- HEADER ROW // -->\n" +
"							<tr>\n" +
"								<td align=\"center\" valign=\"top\">\n" +
"									<!-- CENTERING TABLE // -->\n" +
"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"										<tr>\n" +
"											<td align=\"center\" valign=\"top\">\n" +
"												<!-- FLEXIBLE CONTAINER // -->\n" +
"												<table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
"													<tr>\n" +
"														<td valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
"\n" +
"															<!-- CONTENT TABLE // -->\n" +
"															<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"																<tr>\n" +
"																	<!--\n" +
"																		The \"invisibleIntroduction\" is the text used for short preview\n" +
"																		of the email before the user opens it (50 characters max). Sometimes,\n" +
"																		you do not want to show this message depending on your design but this\n" +
"																		text is highly recommended.\n" +
"																		You do not have to worry if it is hidden, the next <td> will automatically\n" +
"																		center and apply to the width 100% and also shrink to 50% if the first <td>\n" +
"																		is visible.\n" +
"																	-->\n" +
"																	<td align=\"left\" valign=\"middle\" id=\"invisibleIntroduction\" class=\"flexibleContainerBox\" style=\"display:none !important; mso-hide:all;\">\n" +
"																		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:100%;\">\n" +
"																			<tr>\n" +
"																				<td align=\"left\" class=\"textContent\">\n" +
"																					<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:13px;color:#828282;text-align:center;line-height:120%;\">\n" +
"																						The introduction of your message preview goes here. Try to make it short.\n" +
"																					</div>\n" +
"																				</td>\n" +
"																			</tr>\n" +
"																		</table>\n" +
"																	</td>\n" +
"																	<td align=\"right\" valign=\"middle\" class=\"flexibleContainerBox\">\n" +
"																		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:100%;\">\n" +
"																			<tr>\n" +
"																				<td align=\"left\" class=\"textContent\">\n" +
"																					<!-- CONTENT // -->\n" +
"																					<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:13px;color:#828282;text-align:center;line-height:120%;\">\n" +
"																						If you cant see this message, <a href=\"#\" target=\"_blank\" style=\"text-decoration:none;border-bottom:1px solid #828282;color:#828282;\"><span style=\"color:#828282;\">view&nbsp;it&nbsp;in&nbsp;your&nbsp;browser</span></a>.\n" +
"																					</div>\n" +
"																				</td>\n" +
"																			</tr>\n" +
"																		</table>\n" +
"																	</td>\n" +
"																</tr>\n" +
"															</table>\n" +
"														</td>\n" +
"													</tr>\n" +
"												</table>\n" +
"												<!-- // FLEXIBLE CONTAINER -->\n" +
"											</td>\n" +
"										</tr>\n" +
"									</table>\n" +
"									<!-- // CENTERING TABLE -->\n" +
"								</td>\n" +
"							</tr>\n" +
"							<!-- // END -->\n" +
"\n" +
"						</table>\n" +
"						<!-- // END -->\n" +
"\n" +
"						<!-- EMAIL BODY // -->\n" +
"						<!--\n" +
"							The table \"emailBody\" is the emails container.\n" +
"							Its width can be set to 100% for a color band\n" +
"							that spans the width of the page.\n" +
"						-->\n" +
"						<table bgcolor=\"#FFFFFF\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" id=\"emailBody\">\n" +
"\n" +
"							<!-- MODULE ROW // -->\n" +
"							<!--\n" +
"								To move or duplicate any of the design patterns\n" +
"								in this email, simply move or copy the entire\n" +
"								MODULE ROW section for each content block.\n" +
"							-->\n" +
"							<tr>\n" +
"								<td align=\"center\" valign=\"top\">\n" +
"									<!-- CENTERING TABLE // -->\n" +
"									<!--\n" +
"										The centering table keeps the content\n" +
"										tables centered in the emailBody table,\n" +
"										in case its width is set to 100%.\n" +
"									-->\n" +
"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"color:#FFFFFF;\" bgcolor=\"#3498db\">\n" +
"										<tr>\n" +
"											<td align=\"center\" valign=\"top\">\n" +
"												<!-- FLEXIBLE CONTAINER // -->\n" +
"												<!--\n" +
"													The flexible container has a set width\n" +
"													that gets overridden by the media query.\n" +
"													Most content tables within can then be\n" +
"													given 100% widths.\n" +
"												-->\n" +
"												<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
"													<tr>\n" +
"														<td align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
"\n" +
"															<!-- CONTENT TABLE // -->\n" +
"															<!--\n" +
"															The content table is the first element\n" +
"																thats entirely separate from the structural\n" +
"																framework of the email.\n" +
"															-->\n" +
"															<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"100%\">\n" +
"																<tr>\n" +
"																	<td align=\"center\" valign=\"top\" class=\"textContent\">\n" +
"																		<h1 style=\"color:#FFFFFF;line-height:100%;font-family:Helvetica,Arial,sans-serif;font-size:35px;font-weight:normal;margin-bottom:5px;text-align:center;\">Welcome !</h1>\n" +
"																		<h2 style=\"text-align:center;font-weight:normal;font-family:Helvetica,Arial,sans-serif;font-size:23px;margin-bottom:10px;color:#205478;line-height:135%;\">HighRises</h2>\n" +
"																		<div style=\"text-align:center;font-family:Helvetica,Arial,sans-serif;font-size:15px;margin-bottom:0;color:#FFFFFF;line-height:135%;\">Veuillez Verifier Votre Compte</div>\n" +
"																	</td>\n" +
"																</tr>\n" +
"															</table>\n" +
"															<!-- // CONTENT TABLE -->\n" +
"\n" +
"														</td>\n" +
"													</tr>\n" +
"												</table>\n" +
"												<!-- // FLEXIBLE CONTAINER -->\n" +
"											</td>\n" +
"										</tr>\n" +
"									</table>\n" +
"									<!-- // CENTERING TABLE -->\n" +
"								</td>\n" +
"							</tr>\n" +
"							<!-- // MODULE ROW -->\n" +
"\n" +
"\n" +
"							<!-- MODULE ROW // -->\n" +
"							<!--  The \"mc:hideable\" is a feature for MailChimp which allows\n" +
"								you to disable certain row. It works perfectly for our row structure.\n" +
"								http://kb.mailchimp.com/article/template-language-creating-editable-content-areas/\n" +
"							-->\n" +
"							<tr mc:hideable>\n" +
"								<td align=\"center\" valign=\"top\">\n" +
"									<!-- CENTERING TABLE // -->\n" +
"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"										<tr>\n" +
"											<td align=\"center\" valign=\"top\">\n" +
"												<!-- FLEXIBLE CONTAINER // -->\n" +
"												<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
"													<tr>\n" +
"														<td valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
"\n" +
"															<!-- CONTENT TABLE // -->\n" +
"															<table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"																<tr>\n" +
"																	<td align=\"left\" valign=\"top\" class=\"flexibleContainerBox\">\n" +
"																		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"210\" style=\"max-width: 100%;\">\n" +
"																			<tr>\n" +
"																				<td align=\"left\" class=\"textContent\">\n" +
"																					<h3 style=\"color:#5F5F5F;line-height:125%;font-family:Helvetica,Arial,sans-serif;font-size:20px;font-weight:normal;margin-top:0;margin-bottom:3px;text-align:left;\"></h3>\n" +
"																					<div style=\"text-align:left;font-family:Helvetica,Arial,sans-serif;font-size:15px;margin-bottom:0;color:#5F5F5F;line-height:135%;\"></div>\n" +
"																				</td>\n" +
"																			</tr>\n" +
"																		</table>\n" +
"																	</td>\n" +
"																	<td align=\"right\" valign=\"middle\" class=\"flexibleContainerBox\">\n" +
"																		<table class=\"flexibleContainerBoxNext\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"210\" style=\"max-width: 100%;\">\n" +
"																			<tr>\n" +
"																				<td align=\"left\" class=\"textContent\">\n" +
"																					<h3 style=\"color:#5F5F5F;line-height:125%;font-family:Helvetica,Arial,sans-serif;font-size:20px;font-weight:normal;margin-top:0;margin-bottom:3px;text-align:left;\"></h3>\n" +
"																					<div style=\"text-align:left;font-family:Helvetica,Arial,sans-serif;font-size:15px;margin-bottom:0;color:#5F5F5F;line-height:135%;\"></div>\n" +
"																				</td>\n" +
"																			</tr>\n" +
"																		</table>\n" +
"																	</td>\n" +
"																</tr>\n" +
"															</table>\n" +
"															<!-- // CONTENT TABLE -->\n" +
"\n" +
"														</td>\n" +
"													</tr>\n" +
"												</table>\n" +
"												<!-- // FLEXIBLE CONTAINER -->\n" +
"											</td>\n" +
"										</tr>\n" +
"									</table>\n" +
"									<!-- // CENTERING TABLE -->\n" +
"								</td>\n" +
"							</tr>\n" +
"							<!-- // MODULE ROW -->\n" +
"\n" +
"\n" +
"							<!-- MODULE ROW // -->\n" +
"							<tr>\n" +
"								<td align=\"center\" valign=\"top\">\n" +
"									<!-- CENTERING TABLE // -->\n" +
"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"										<tr style=\"padding-top:0;\">\n" +
"											<td align=\"center\" valign=\"top\">\n" +
"												<!-- FLEXIBLE CONTAINER // -->\n" +
"												<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" width=\"500\" class=\"flexibleContainer\">\n" +
"													<tr>\n" +
"														<td style=\"padding-top:0;\" align=\"center\" valign=\"top\" width=\"500\" class=\"flexibleContainerCell\">\n" +
"\n" +
"															<!-- CONTENT TABLE // -->\n" +
"															<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"50%\" class=\"emailButton\" style=\"background-color: #3498DB;\">\n" +
"																<tr>\n" +
"																	<td align=\"center\" valign=\"middle\" class=\"buttonContent\" style=\"padding-top:15px;padding-bottom:15px;padding-right:15px;padding-left:15px;\">\n" +
"																		<a style=\"color:#FFFFFF;text-decoration:none;font-family:Helvetica,Arial,sans-serif;font-size:20px;line-height:135%;\" href=\"http://127.0.0.1/integration/Front_Office/Views/auth.html?email='.$_POST['email'].'&amp;message=9\" target=\"_blank\">"+contenu+"</a>\n" +
"																	</td>\n" +
"																</tr>\n" +
"															</table>\n" +
"															<!-- // CONTENT TABLE -->\n" +
"\n" +
"														</td>\n" +
"													</tr>\n" +
"												</table>\n" +
"												<!-- // FLEXIBLE CONTAINER -->\n" +
"											</td>\n" +
"										</tr>\n" +
"									</table>\n" +
"									<!-- // CENTERING TABLE -->\n" +
"								</td>\n" +
"							</tr>\n" +
"							<!-- // MODULE ROW -->\n" +
"\n" +
"\n" +
"							'";
        
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
