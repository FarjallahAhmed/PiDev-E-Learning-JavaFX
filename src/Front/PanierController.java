/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Commande;
import Entities.comformation;
import Entities.panier;
import Service.ServiceCommande;
import Service.ServicePanier;
import UserSession.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Pagination;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class PanierController implements Initializable {
    private int  vartri=0;
    @FXML
    private TableView<comformation> tabcommande;
    @FXML
    private TableColumn<comformation, String> coetat;
    @FXML
    private TableColumn<comformation, Float> coprix;
    @FXML
    public TextField txtprix;
    @FXML
    public TextField txtnbarticles;
       @FXML
    private TableColumn<comformation,String> cobjet;
    @FXML
    private TableColumn<comformation,String> cobjectif;
    @FXML
    private TableColumn<comformation,String> cotype;
    @FXML
    private Button btnsupp;
    @FXML
    private TextField suppanier;
    @FXML
    private TableColumn<comformation, Integer> coidform;
    @FXML
    private TextField tfsearch;
    @FXML
    private Button btnpasser;
    public static float prixtotal;
    @FXML
    private VBox boxaff;
    ObservableList<comformation>list2;
    @FXML
    private Button btnvider;
     public static HashSet<Integer> hsetid;
    @FXML
    private Button btndelete;
    @FXML
    private Label totalprixx;
    @FXML
    private Label nbarticleeeee;

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {
       hsetid=new HashSet<Integer>();
        Afficher_Commandes();
       update_panier();
        ServiceCommande pService = new ServiceCommande();
       list2 = pService.Get_formvomm(UserSession.getInstace("", 0, "").getId());
       if(list2.size()!=0){
       try {
            boxaff.getChildren().add(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));

        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
       
       
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
       prixtotal=prix_total;
       txtprix.setText(String.valueOf(prix_total));
       totalprixx.setText(txtprix.getText());
       nbarticleeeee.setText(txtnbarticles.getText());
       panier p=new panier();
       p.setId_client(UserSession.getInstace("", 0, "").getId());
       p.setNombre(tabcommande.getItems().size());
       p.setPrix_total(prix_total);
       ServicePanier sv= new ServicePanier();
       sv.Update_panier(p);
    }  

    @FXML
    private void Supprimer_btn(ActionEvent event) {
      ServiceCommande sc=new ServiceCommande();
         
         Commande c=new Commande();
         
         //suppanier.setText(String.valueOf(c.getId_formation()));
        sc.supprimercommande(Integer.parseInt(suppanier.getText()));
        this.Afficher_Commandes();
        update_panier();
    }

    @FXML
    private void presupp(MouseEvent event) {
        comformation c =tabcommande.getSelectionModel().getSelectedItem();
        suppanier.setText(String.valueOf(c.getId_formation()));
    }

    @FXML
    private void SearchKey(KeyEvent event) {
               ServiceCommande sc=new ServiceCommande();
        ObservableList<comformation> comformations =sc.search(tfsearch.getText(), UserSession.getInstace("", 0, "").getId());
         coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
          coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
          coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
          cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
          cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
          cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
        
           tabcommande.setItems(comformations);
           update_panier();
           list2=sc.search(tfsearch.getText(), UserSession.getInstace("", 0, "").getId());
           try {
            boxaff.getChildren().set(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));
            
        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void tribtn(MouseEvent event) {
           ServiceCommande sc=new ServiceCommande();
        ObservableList<comformation> formationstab;
        if(vartri==1){
            vartri=0;
            formationstab=sc.triasc(UserSession.getInstace("", 0, "").getId());
            coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
            coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
            coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
            cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
            cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
            cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
            tabcommande.setItems(formationstab);
            list2=sc.triasc(UserSession.getInstace("", 0, "").getId());
              update_panier();
        }
        else{
            vartri=1;
            formationstab=sc.tridsc(UserSession.getInstace("", 0, "").getId());
            coidform.setCellValueFactory(new PropertyValueFactory<comformation,Integer>("id_formation"));
          coetat.setCellValueFactory(new PropertyValueFactory<comformation,String>("Etat"));
          coprix.setCellValueFactory(new PropertyValueFactory<comformation,Float>("Prix"));
          cobjet.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objet"));
          cobjectif.setCellValueFactory(new PropertyValueFactory<comformation,String>("Objectif"));
          cotype.setCellValueFactory(new PropertyValueFactory<comformation,String>("Type"));
        
           tabcommande.setItems(formationstab);
           list2=sc.tridsc(UserSession.getInstace("", 0, "").getId());
             update_panier();
        }
        try {
            boxaff.getChildren().set(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));
            
        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void onfinalisercommande(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/finalisercommande.fxml"));
            Parent root =(Parent) loader.load();
            //EvaluerController f=loader.getController();
            
            //afficher_eval();
           // f.setval(String.valueOf(data.getId()));
            //System.out.println(idupdate.getText());
             Stage stage = new Stage();
           stage.setScene(new Scene(root));
           stage.show();
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }
  
     public static AnchorPane createPromotion(comformation j,comformation b) {
        
       
        AnchorPane card2 = new AnchorPane();
        
        //JFXButton btnBack = new JFXButton("Back");
        //Pane promo = new Pane();
        //Label t = new Label("Promotion");
        
        String promolb =  "-fx-underline : true;"
                +"-fx-font-weight : bold;"
                +"-fx-text-fill: red;"
                +"-fx-font-size : 18;";
        
        String stylePromo = "-fx-text-fill: white;"
                + "-fx-font-size : 48;";
        //promo.setPrefSize(1250, 139);
        
//        t.setStyle(stylePromo);
//        t.setLayoutX(100);
//        t.setLayoutY(50);
        //promo.setStyle("-fx-background-color :  #2D75E8;");
       // promo.getChildren().addAll(t);
        card2.setPrefSize(1250, 650);
        card2.setStyle("-fx-background-color :  white;");
        //card2.getChildren().add(promo);
        int xcard= 0;
        String styleCard = "-fx-background-raduis: 3;"
                + "-fx-border-raduis : 5;"
                + "-fx-border-color: #2D75E8"; 
        String styleLabel = "-fx-text-fill: #738796;"
                + "-fx-font-weight : bold;"
                + "-fx-font-size : 15;"; 
        String styleTitre = "-fx-text-fill: #738796;"
                + "-fx-font-weight : italic;"
                + "-fx-font-size : 18;";
        String styleButton = "-fx-text-fill: white;"
                + "-fx-background-color : #337AB7;"
                + "-fx-font-size : 14;";
        String stylefooter = "-fx-border-raduis : 0 0 3 3;"
                + "-fx-background-color: #E5E8EB; "
                + "-fx-border-width : 1 0 0 0;"
                + "-fx-border-color: #C3C3C3"; 
        String styleBack = "-fx-text-fill: white;"
                + "-fx-font-weight : bold;"
                + "-fx-font-size : 18;"; 
        //btnBack.setStyle(styleBack);
//        btnBack.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                Parent root; 
//                try {
//                    //if(UserSession.UserSession.getType().equals("Formateur"))
//                      //  root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml"));
//                    //else
//                        root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml"));
//                    Scene scene = new Scene(root);
//                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
//                } catch (IOException ex) {
//                    Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                 
//            }
//        });
            
        for(int i=0;i<2;i++){
            
            AnchorPane card = new AnchorPane();
            
            VBox rootlb = new VBox();
            VBox root = new VBox();
            HBox rootf = new HBox();
            HBox test = new HBox();
            ImageView pour  = new ImageView();
            
            
            
            Pane footer = new Pane();
//            JFXButton buy = new JFXButton("BUY");
//            JFXButton download = new JFXButton("Evaluer");
//            JFXButton download2 = new JFXButton("Download");
//            Rating rating = new Rating(5);
              JFXCheckBox check =new JFXCheckBox();

            Label l1 = new Label(),l2 = new Label(),l3 = new Label(),l4 = new Label(),l5 = new Label(),l6 = new Label(),l7 = new Label();

            l1.setStyle(styleTitre);
            l2.setStyle(styleLabel);
            l3.setStyle(styleLabel);
            l4.setStyle(styleLabel);
            l5.setStyle(styleLabel);
            l6.setStyle(styleLabel);
            l7.setStyle(promolb);
            GlyphsDude.setIcon(l4, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(l3, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(l6, FontAwesomeIcon.MONEY);
             GlyphsDude.setIcon(l7, FontAwesomeIcon.MONEY);
//            buy.setStyle(styleButton);
//            download.setStyle(styleButton);
//            download2.setStyle(styleButton);
              check.setStyle(
    "-fx-border-color: lightblue; "
    + "-fx-font-size: 20;"
    + "-fx-border-insets: -5; "
    + "-fx-border-radius: 5;"
    + "-fx-border-style: dotted;"
    + "-fx-border-width: 2;"
);
              check.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(check.isSelected()){
                        System.out.println(j.getId());
//                        if(!hsetid.contains(j.getId()))
                        hsetid.add(j.getId());
                    }else
                        hsetid.remove(j.getId());
                }
            });
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
         
      
      
            
            footer.setPrefSize(504, 50);        
            test.setSpacing(35); 
            
            rootlb.setSpacing(18);
            
            card.setPrefSize(504, 250);
            card.setLayoutX(30+xcard);
            card.setLayoutY(40);        
       
                
                try {
                 //InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(j.getPrix())+".png");
                 //Image image = new Image(stream);
                 //pour.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        
                l1.setText("Objet : "+j.getObjet());
                l2.setText("Type  :  "+j.getType());
                l3.setText("Objectif: "+j.getObjectif());
                l4.setText("Etat :  "+j.getEtat());
                //l5.setText("Pourcentage :  "+b.getPrix());
                //float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
          
            l6.setText(" "+j.getPrix()+" ");
           
           

           

                
                
            
            if(i==1){
                
                l1.setText("Objet : "+b.getObjet());
                l2.setText("Type  :  "+b.getType());
                l3.setText("Objectif: "+b.getObjectif());
                l4.setText("Etat :  "+b.getEtat());
                //l5.setText("Pourcentage :  "+b.getPrix());
                //float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
          
            l6.setText(" "+b.getPrix()+" ");
                    //rootf.getChildren().addAll(l6,l7);
                check.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(check.isSelected()){
                        System.out.println(b.getId());
                        
                        hsetid.add(b.getId());
                    }else
                        hsetid.remove(b.getId());
                }
            });
                try {
                 //InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(b.getPrix())+".png");
                 //Image image = new Image(stream);
                 //pour.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
          
              
        
            }
         rootf.getChildren().addAll(l6,l7);
            
        
        

            

//            test.getChildren().addAll(rating,buy,download,download2);
                
              test.getChildren().addAll(check);
            
            footer.getChildren().add(test);
            test.setLayoutY(7);
            
            rootlb.getChildren().addAll(l1,l2,l3,l4,rootf); 
            //rootlb.setLayoutX(40);
            //root.getChildren().add(rootlb);
            
           
            
            card.getChildren().addAll(rootlb,pour,footer);
            rootlb.setLayoutX(40);            
            footer.setLayoutY(200);
            footer.setLayoutX(2);
            pour.setLayoutX(339);
            pour.setLayoutY(44);
            
            
           

            card.setLayoutY(200);
            
            card2.getChildren().add(card);
            xcard+= 609;
      
      
        }
                     
        return card2;
    }
     public static AnchorPane createPromotion2(comformation j) {
        
       
        AnchorPane card2 = new AnchorPane();
        
        //JFXButton btnBack = new JFXButton("Back");
        //Pane promo = new Pane();
        //Label t = new Label("Promotion");
        
        String promolb =  "-fx-underline : true;"
                +"-fx-font-weight : bold;"
                +"-fx-text-fill: red;"
                +"-fx-font-size : 18;";
        
        String stylePromo = "-fx-text-fill: white;"
                + "-fx-font-size : 48;";
        //promo.setPrefSize(1250, 139);
        
//        t.setStyle(stylePromo);
//        t.setLayoutX(100);
//        t.setLayoutY(50);
        //promo.setStyle("-fx-background-color :  #2D75E8;");
       // promo.getChildren().addAll(t);
        card2.setPrefSize(1250, 650);
        card2.setStyle("-fx-background-color :  white;");
        //card2.getChildren().add(promo);
        int xcard= 0;
        String styleCard = "-fx-background-raduis: 3;"
                + "-fx-border-raduis : 5;"
                + "-fx-border-color: #2D75E8"; 
        String styleLabel = "-fx-text-fill: #738796;"
                + "-fx-font-weight : bold;"
                + "-fx-font-size : 15;"; 
        String styleTitre = "-fx-text-fill: #738796;"
                + "-fx-font-weight : italic;"
                + "-fx-font-size : 18;";
        String styleButton = "-fx-text-fill: white;"
                + "-fx-background-color : #337AB7;"
                + "-fx-font-size : 14;";
        String stylefooter = "-fx-border-raduis : 0 0 3 3;"
                + "-fx-background-color: #E5E8EB; "
                + "-fx-border-width : 1 0 0 0;"
                + "-fx-border-color: #C3C3C3"; 
        String styleBack = "-fx-text-fill: white;"
                + "-fx-font-weight : bold;"
                + "-fx-font-size : 18;"; 
        //btnBack.setStyle(styleBack);
//        btnBack.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                Parent root; 
//                try {
//                    //if(UserSession.UserSession.getType().equals("Formateur"))
//                      //  root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml"));
//                    //else
//                        root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml"));
//                    Scene scene = new Scene(root);
//                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
//                } catch (IOException ex) {
//                    Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                 
//            }
//        });
            
        
            
            AnchorPane card = new AnchorPane();
            
            VBox rootlb = new VBox();
            VBox root = new VBox();
            HBox rootf = new HBox();
            HBox test = new HBox();
            ImageView pour  = new ImageView();
            
            
            
            Pane footer = new Pane();
//            JFXButton buy = new JFXButton("BUY");
//            JFXButton download = new JFXButton("Evaluer");
//            JFXButton download2 = new JFXButton("Download");
//            Rating rating = new Rating(5);
              JFXCheckBox check =new JFXCheckBox();

            Label l1 = new Label(),l2 = new Label(),l3 = new Label(),l4 = new Label(),l5 = new Label(),l6 = new Label(),l7 = new Label();

            l1.setStyle(styleTitre);
            l2.setStyle(styleLabel);
            l3.setStyle(styleLabel);
            l4.setStyle(styleLabel);
            l5.setStyle(styleLabel);
            l6.setStyle(styleLabel);
            l7.setStyle(promolb);
            GlyphsDude.setIcon(l4, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(l3, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(l6, FontAwesomeIcon.MONEY);
             GlyphsDude.setIcon(l7, FontAwesomeIcon.MONEY);
//            buy.setStyle(styleButton);
//            download.setStyle(styleButton);
//            download2.setStyle(styleButton);
 check.setStyle(
    "-fx-border-color: lightblue; "
    + "-fx-font-size: 20;"
    + "-fx-border-insets: -5; "
    + "-fx-border-radius: 5;"
    + "-fx-border-style: dotted;"
    + "-fx-border-width: 2;"
);
                check.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(check.isSelected()){
                        System.out.println(j.getId());
                        //if(!hsetid.contains(j.getId()))
                        hsetid.add(j.getId());
                    }else
                        hsetid.remove(j.getId());
                }
            });
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
      
            
     
            
            footer.setPrefSize(504, 50);        
            test.setSpacing(35); 
            
            rootlb.setSpacing(18);
            
            card.setPrefSize(504, 250);
            card.setLayoutX(30+xcard);
            card.setLayoutY(40);        
       
                
                try {
                 //InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(j.getPrix())+".png");
                 //Image image = new Image(stream);
                 //pour.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        
                l1.setText("Objet : "+j.getObjet());
                l2.setText("Type  :  "+j.getType());
               l3.setText("Objectif: "+j.getObjectif());
                l4.setText("Etat :  "+j.getEtat());
                //l5.setText("Pourcentage :  "+b.getPrix());
                //float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
          
            l6.setText(" "+j.getPrix()+" ");
           

           

                
                
            
        
         rootf.getChildren().addAll(l6,l7);
            
        
        

            

//            test.getChildren().addAll(rating,buy,download,download2);
              test.getChildren().addAll(check);
            
            footer.getChildren().add(test);
            test.setLayoutY(7);
            
            rootlb.getChildren().addAll(l1,l2,l3,l4,rootf); 
            //rootlb.setLayoutX(40);
            //root.getChildren().add(rootlb);
            
           
            
            card.getChildren().addAll(rootlb,pour,footer);
            rootlb.setLayoutX(40);            
            footer.setLayoutY(200);
            footer.setLayoutX(2);
            pour.setLayoutX(339);
            pour.setLayoutY(44);
            
            
           

            card.setLayoutY(200);
            
            card2.getChildren().add(card);
            xcard+= 609;
      
      
        
                     
        return card2;
    }
     public  VBox page() throws SQLException{

       
        Pagination pagination = new Pagination();
        pagination.setPageCount(list2.size()-1);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(3);

        pagination.setPageFactory((pageIndex) -> {

                System.out.println(pageIndex);
                if(pageIndex+1 <list2.size())
            return new VBox(createPromotion(list2.get(pageIndex),list2.get(pageIndex+1)));
                else return new VBox(createPromotion2(list2.get(pageIndex)));
        });

//        promotionpage.getChildren().add(pagination);
        VBox vBox = new VBox(pagination);


       return vBox;
    }

    @FXML
    private void viderpanier(ActionEvent event) {
        ServiceCommande sc=new ServiceCommande();
        sc.ViderCommande(UserSession.getInstace("", 0, "").getId());
        Afficher_Commandes();
        update_panier();
       list2 = sc.Get_formvomm(UserSession.getInstace("", 0, "").getId()); 
       try {
            boxaff.getChildren().set(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));

        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        ServiceCommande sc=new ServiceCommande();
        for(int a:hsetid){
            sc.supprimercommande(a);
            
        }
        Afficher_Commandes();
        update_panier();
       list2 = sc.Get_formvomm(UserSession.getInstace("", 0, "").getId()); 
       try {
            boxaff.getChildren().set(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));

        } catch (SQLException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
