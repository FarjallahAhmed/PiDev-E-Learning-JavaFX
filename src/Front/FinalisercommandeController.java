/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Formations;
import Entities.comformation;
import Entities.panier;
import Service.ServiceCommande;
import Service.ServicePanier;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class FinalisercommandeController implements Initializable {

    @FXML
    private Button btnsupp;
    @FXML
    private Button btnajouter;
  @FXML
    private TableView<comformation> tabcommande;
    @FXML
    private TableColumn<comformation, String> coetat;
    @FXML
    private TableColumn<comformation, Float> coprix;
    
    @FXML
    private TextField txtprix;
    @FXML
    private TextField txtnbarticles;
     @FXML
    private TableColumn<comformation,String> cobjet;
    @FXML
    private TableColumn<comformation,String> cobjectif;
    @FXML
    private TableColumn<comformation,String> cotype;
     @FXML
    private TableColumn<comformation, Integer> coidform;
    @FXML
    private ComboBox<String> coboxpayment;
    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtprenom;
    @FXML
    private TextField txtadresse;
    @FXML
    private TextField txtmail;

    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
       coboxpayment.getItems().add("Carte Bancaire");
       coboxpayment.getItems().add("Visa");
       
       Afficher_Commandes();
       update_panier();
       
    }    
    public void Afficher_Commandes()
    {
        ServiceCommande sp=new ServiceCommande();
        ObservableList<comformation> list=sp.Get_formvomm(1);
        
          coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
          coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
          coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
          cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
          cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
          cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
          
           tabcommande.setItems(list);
           
        
    }
    public void update_panier()
    {
       txtnbarticles.setText(String.valueOf(tabcommande.getItems().size()));
      float prix_total=0;
       for(int i=0;i<tabcommande.getItems().size();i++)
       {
           prix_total=prix_total+tabcommande.getItems().get(i).getPrix();
          
       }
       txtprix.setText(String.valueOf(prix_total));
       panier p=new panier();
       p.setId_client(1);
       p.setNombre(tabcommande.getItems().size());
       p.setPrix_total(prix_total);
       ServicePanier sv= new ServicePanier();
       sv.Update_panier(p);
    }     

    @FXML
    private void Supprimer_btn(ActionEvent event) {
         ((Node)(event.getSource())).getScene().getWindow().hide();
    }


    @FXML
    private void presupp(MouseEvent event) {
    }

    @FXML
    private void onpasser(ActionEvent event) {
        String nom=txtnom.getText();
        String Prenom=txtprenom.getText();
        String payment=coboxpayment.getValue();
        String address=txtadresse.getText();
        String mail=txtmail.getText();
        float Prix_toal=Float.parseFloat(txtprix.getText());
        ServiceCommande sv=new ServiceCommande();
        sv.ModifierCommande2(1);
        sv.ModifierCommande3(1);
        sendmail(nom, payment, address, mail, Prix_toal, Prenom);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commander");
            alert.setHeaderText("Commande Passe");
            alert.setContentText("La Commande a été passee avec success!");
            alert.showAndWait();
    }
     public void sendmail(String nom,String payment,String adress,String mail,float prix_total,String prenom) {
		
		String to = mail;
		String from = "highriseshighrises@esprit.tn";
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
    message.setSubject("Your Order from Highrises");
 
    //set the content of the email message
    message.setText("Nom:"+nom+"\n"+"Prenom : "+prenom+"\n"+"Methode de Payment : "+payment+"\n"+"Prix total : "+prix_total+"\n "+"Adress : "+adress);

    //send the email message
    Transport.send(message);

    System.out.println("Email Message Sent Successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }

			
    }
    
}
