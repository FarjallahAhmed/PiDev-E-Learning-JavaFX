/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Message;
import Entities.Utilisateurs;
import Entities.reclamation;
import Service.Servicereclamation;
import UserSession.UserSession;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author rania
 */
public class AfficherRecController implements Initializable {

    @FXML
    private VBox vboxafficher;

    
    ObservableList<reclamation> list2;
    @FXML
    private Button trj;
    @FXML
    private TextField search;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
      // enti eda hot mena juste bech nfasarlek:
        Servicereclamation pService = new Servicereclamation();
       list2 = pService.Afficher_reclamation();
       if(list2.size()!=0){
       try {
            vboxafficher.getChildren().add(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));

        } catch (SQLException ex) {
            Logger.getLogger(AfficherRecController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    }
    
    public static AnchorPane createPromotion(reclamation j,reclamation b) {
       
       
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
         JFXButton supprimer = new JFXButton("Supprimer");
//            Rating rating = new Rating(5);
              JFXCheckBox check =new JFXCheckBox();

            Label l1 = new Label(),l2 = new Label(),l3 = new Label(),l4 = new Label(),l5 = new Label();

            l1.setStyle(styleTitre);
            l2.setStyle(styleLabel);
            l3.setStyle(styleLabel);
            l4.setStyle(styleLabel);
            l5.setStyle(styleLabel);
            GlyphsDude.setIcon(l4, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(l3, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(supprimer,FontAwesomeIcon.TRASH);
//            buy.setStyle(styleButton);
//            download.setStyle(styleButton);
             supprimer.setStyle(styleButton);
              check.setStyle(
    "-fx-border-color: lightblue; "
    + "-fx-font-size: 20;"
    + "-fx-border-insets: -5; "
    + "-fx-border-radius: 5;"
    + "-fx-border-style: dotted;"
    + "-fx-border-width: 2;"
);//checkbox css
              
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
         
     
     
           
            footer.setPrefSize(504, 50);        
            test.setSpacing(35);
           
            rootlb.setSpacing(18);
           
            card.setPrefSize(504, 250);
            card.setLayoutX(30+xcard);
            card.setLayoutY(40);        
             supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                 Servicereclamation sv= new Servicereclamation();
                 sv.archiveRec(j.getId_reclamation());
                 sv.supprimer_reclamation(j.getId_reclamation());
                 
                }
            });
               
                try {
                 //InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(j.getPrix())+".png");
                 //Image image = new Image(stream);
                 //pour.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(AfficherRecController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       
                l1.setText("User : "+j.getId_user().getNom()+" "+j.getId_user().getPrenom());
                l2.setText("Objet  :  "+j.getObjet());
                l3.setText("Date: "+j.getDate());
                l4.setText("Message :  "+j.getMessage().getContenu());
                //l5.setText("Pourcentage :  "+b.getPrix());
                //float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
         
//            l6.setText(" "+j.getPrix()+" ");
           
           

           

               
               
           
            if(i==1){
               
                l1.setText("User : "+b.getId_user().getNom()+" "+b.getId_user().getPrenom());
                l2.setText("Objet  :  "+b.getObjet());
                l3.setText("Date: "+b.getDate());
                l4.setText("Message :  "+b.getMessage().getContenu());
                //l5.setText("Pourcentage :  "+b.getPrix());
                //float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
         
         //   l6.setText(" "+b.getPrix()+" ");
                    //rootf.getChildren().addAll(l6,l7);
                supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                 Servicereclamation sv= new Servicereclamation();
                 sv.archiveRec(b.getId_reclamation());
                 sv.supprimer_reclamation(b.getId_reclamation());
                 
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
           
       
       

           

//            test.getChildren().addAll(rating,buy,download,download2);
               if (UserSession.getInstace("", 0, "").getType().equals("Admin"))
                       supprimer.setVisible(false);
              test.getChildren().addAll(supprimer);
           
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

    public static AnchorPane createPromotion2(reclamation j) {
          
       
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
             JFXButton supprimer = new JFXButton("Supprimer");
//            Rating rating = new Rating(5);
              JFXCheckBox check =new JFXCheckBox();

            Label l1 = new Label(),l2 = new Label(),l3 = new Label(),l4 = new Label(),l5 = new Label();

            l1.setStyle(styleTitre);
            l2.setStyle(styleLabel);
            l3.setStyle(styleLabel);
            l4.setStyle(styleLabel);
            l5.setStyle(styleLabel);
         
            GlyphsDude.setIcon(l4, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(l3, FontAwesomeIcon.CALENDAR_ALT);
            GlyphsDude.setIcon(supprimer,FontAwesomeIcon.TRASH);
           
//            buy.setStyle(styleButton);
//            download.setStyle(styleButton);
            supprimer.setStyle(styleButton);
 check.setStyle(
    "-fx-border-color: lightblue; "
    + "-fx-font-size: 20;"
    + "-fx-border-insets: -5; "
    + "-fx-border-radius: 5;"
    + "-fx-border-style: dotted;"
    + "-fx-border-width: 2;"
);// checkbox css
                
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
     
           
     
           
            footer.setPrefSize(504, 50);        
            test.setSpacing(35);
           
            rootlb.setSpacing(18);
           
            card.setPrefSize(504, 250);
            card.setLayoutX(30+xcard);
            card.setLayoutY(40);        
       supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                 Servicereclamation sv= new Servicereclamation();
                 sv.archiveRec(j.getId_reclamation());
                 sv.supprimer_reclamation(j.getId_reclamation());
                }
            });
               
                try {
                 //InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(j.getPrix())+".png");
                 //Image image = new Image(stream);
                 //pour.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       
                l1.setText("User : "+j.getId_user().getNom()+" "+j.getId_user().getPrenom());
                l2.setText("Objet  :  "+j.getObjet());
                l3.setText("Date: "+j.getDate());
                l4.setText("Message :  "+j.getMessage().getContenu());
           
       
       

           

//            test.getChildren().addAll(rating,buy,download,download2); 
            if (UserSession.getInstace("", 0, "").getType().equals("Admin"))
             supprimer.setVisible(false);
             test.getChildren().addAll(supprimer);
           
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
    private void searchKeyReleased(KeyEvent event) {
        vboxafficher.getChildren().clear();
        Servicereclamation sr= new Servicereclamation();
        list2 =sr.search(search.getText());
        if(list2.size()!=0){
       try {
            vboxafficher.getChildren().add(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));

        } catch (SQLException ex) {
            Logger.getLogger(AfficherRecController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    }

    private int varTri = 0;
            
    @FXML
    void triiiiiii(MouseEvent event) {
        vboxafficher.getChildren().clear();
        Servicereclamation sr= new Servicereclamation();
        ObservableList<reclamation> reclamations;
        if (varTri == 1) {
            varTri = 0;
            list2 =sr.triASC();
        }
        else {
            varTri = 1;
            list2 =sr.triDSC();
        }
        if(list2.size()!=0){
        try {
            vboxafficher.getChildren().add(0,page());
        } catch (SQLException ex) {
            Logger.getLogger(AfficherRecController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
          
    }
}
