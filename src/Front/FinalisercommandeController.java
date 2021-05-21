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
import UserSession.UserSession;
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
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

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
    private TextField txtadresse;
    @FXML
    private TextField txtmail;
    @FXML
    private TextField txtcarte;
    @FXML
    private TextField txtcvc;
    @FXML
    private DatePicker datexp;

    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
       //coboxpayment.getItems().add("Carte Bancaire");
       coboxpayment.getItems().add("Visa");
     //  txtnbarticles.setText(String.valueOf(tabcommande.getItems().size()));
       Afficher_Commandes();
       update_panier();
       
    }    
    public void Afficher_Commandes()
    {
        ServiceCommande sp=new ServiceCommande();
        ObservableList<comformation> list=sp.Get_formvomm(UserSession.getInstace("", 0, "").getId());
        
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
       p.setId_client(UserSession.getInstace("", 0, "").getId());
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
       // String Prenom=txtprenom.getText();
        String payment=coboxpayment.getValue();
        String id=txtadresse.getText();
        String mail=txtmail.getText();
        float Prix_total=Float.parseFloat(txtprix.getText());
        
        Stripe.apiKey="sk_test_UyGOFESdBdhmN6TtUwAY3Knn00fbOUdEE4";
//        Customer c=new Customer();
        
        Map<String,Object> chargeparam=new HashMap<String,Object>();
            try {
//                 a=Customer.retrieve("cus_JBPAuy5vZXINOr");
//                 chargeparam.put("amount", "5000");
//                 chargeparam.put("currency","usd");
//                 chargeparam.put("customer",a.getId());
//                 Charge.create(chargeparam);
//                 Card c=(Card)a.getSources().retrieve("card_1IZ2PiGmAyVmHtCPoFTFBJpj");
                //System.out.println(c.toString());
                
                Map<String, Object> retrieveParams =
                    new HashMap<>();
                    List<String> expandList = new ArrayList<>();
                    expandList.add("sources");
                    retrieveParams.put("expand", expandList);
                        Customer customer =
                        Customer.retrieve(
                        id,
                        retrieveParams,
                         null
                            );
                            Card card =
                    (Card) customer.getSources().retrieve(customer.getDefaultSource());
                            if( card.getLast4().equals(txtcarte.getText().substring(txtcarte.getText().length()-4))&& datexp.getValue().getYear()==card.getExpYear()&& datexp.getValue().getMonthValue()==card.getExpMonth()&& card.getName().equals(nom)&&customer.getEmail().equals(mail)){
                            PanierController p =new PanierController();
                            chargeparam.put("amount",String.valueOf((int)Math.round(PanierController.prixtotal*100*0.36)));
                 chargeparam.put("currency","usd");
                 chargeparam.put("customer",customer.getId());
                 Charge.create(chargeparam);
                                
                 }else{
                            Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText("Look, an Error Dialog");
alert.setContentText("Ooops, there was an error!");

alert.showAndWait();}
//                            System.out.println( Math.round(PanierController.prixtotal*0.36));
                           
            } catch (StripeException ex) {
                Logger.getLogger(FinalisercommandeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        ServiceCommande sv=new ServiceCommande();
        sv.ModifierCommande2(UserSession.getInstace("", 0, "").getId());
        sv.ModifierCommande3(UserSession.getInstace("", 0, "").getId());
        sendmail(nom, payment, id, mail, Prix_total, "");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commander");
            alert.setHeaderText("Commande Passe");
            alert.setContentText("La Commande a été passee avec success!");
            alert.showAndWait();
    }
     public void sendmail(String nom,String payment,String adress,String mail,float Prix_total,String prenom) {
		
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
    message.setText("Merci pour votre confiance! "+"\n"+"Nom:"+nom+"\n"+"Methode de Payment : "+payment+"\n"+"Prix total : "+Prix_total+"TND"+"\n"+"Bonne journée.");

    //send the email message
    Transport.send(message);

    System.out.println("Email Message Sent Successfully");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }

			
    }
    
}
