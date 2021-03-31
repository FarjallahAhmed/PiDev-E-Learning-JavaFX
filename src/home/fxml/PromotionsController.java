/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.fxml;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import Entities.Promotion;
import Service.implPromotionService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;
import org.controlsfx.glyphfont.FontAwesome;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class PromotionsController implements Initializable {

    public static ObservableList<Promotion> list ;

    public static JFXComboBox<Integer> filtrePourc = new JFXComboBox<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        implPromotionService pService = new implPromotionService();

        try {
            list = pService.readAllPromotion();
   
        } catch (SQLException ex) {
            Logger.getLogger(PromotionsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
    public static AnchorPane createPromotion(Promotion j,Promotion b) {
        
       
        AnchorPane card2 = new AnchorPane();
        
        JFXButton btnBack = new JFXButton("Back");
        Pane promo = new Pane();
        Label t = new Label("Promotion");
        Label tfiltre = new Label("Filtrer :");
        
        for (int i = 0; i<9;i++)
            filtrePourc.getItems().add(i*10);
        
        String promolb =  "-fx-underline : true;"
                +"-fx-font-weight : bold;"
                +"-fx-text-fill: red;"
                +"-fx-font-size : 18;";
        
        String stylePromo = "-fx-text-fill: white;"
                + "-fx-font-size : 48;";
        promo.setPrefSize(1250, 139);
        
        t.setStyle(stylePromo);
        t.setLayoutX(100);
        t.setLayoutY(50);
        tfiltre.setLayoutX(1000);
        tfiltre.setLayoutY(150);
        filtrePourc.setLayoutX(1100);
        filtrePourc.setLayoutY(150);
        promo.setStyle("-fx-background-color :  #2D75E8;");
        promo.getChildren().addAll(btnBack,t,tfiltre,filtrePourc);
        card2.setPrefSize(1250, 650);
        card2.setStyle("-fx-background-color :  white;");
        card2.getChildren().add(promo);
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
        btnBack.setStyle(styleBack);
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Parent root; 
                try {
                    if(UserSession.UserSession.getType().equals("Formateur"))
                        root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml"));
                    else
                        root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml"));
                    Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
                } catch (IOException ex) {
                    Logger.getLogger(PromotionsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }
        });
        for(int i=0;i<2;i++){
            
            AnchorPane card = new AnchorPane();
            
            VBox rootlb = new VBox();
            VBox root = new VBox();
            HBox rootf = new HBox();
            HBox test = new HBox();
            ImageView pour  = new ImageView();
            
            
            
            Pane footer = new Pane();
            JFXButton buy = new JFXButton("BUY");
            Rating rating = new Rating(5);

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
            buy.setStyle(styleButton);
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
            
            
            footer.setPrefSize(504, 50);        
            test.setSpacing(270); 
            
            rootlb.setSpacing(18);
            
            card.setPrefSize(504, 250);
            card.setLayoutX(30+xcard);
            card.setLayoutY(40);        
       
                
                try {
                 InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(j.getPrix())+".png");
                 Image image = new Image(stream);
                 pour.setImage(image);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PromotionsController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        
                l1.setText("Objet : "+j.getObjet());
                l2.setText("Type  :  "+j.getType());
                l3.setText("Date Debut : "+j.getDateDebut());
                l4.setText("Date Fin :  "+j.getDateFin());
                //l5.setText("Pourcentage :  "+b.getPrix());
                float resultat = j.getCout_f() * (100-j.getPrix())/100;
          
            l6.setText(" "+j.getCout_f()+" ");
            l7.setText(" "+resultat);
           

           

                
                
            
            if(i==1){
                
                l1.setText("Objet : "+b.getObjet());
                l2.setText("Type  :  "+b.getType());
                l3.setText("Date Debut : "+b.getDateDebut());
                l4.setText("Date Fin :  "+b.getDateFin());
               
                    float resultat2 = b.getCout_f() * (100-b.getPrix())/100;
                    l6.setText(" "+b.getCout_f()+" ");
                    l7.setText(" " +resultat2);
                    //rootf.getChildren().addAll(l6,l7);
               
                try {
                 InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(b.getPrix())+".png");
                 Image image = new Image(stream);
                 pour.setImage(image);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PromotionsController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
                
            }
            rootf.getChildren().addAll(l6,l7);
            
        
        

            

            test.getChildren().addAll(rating,buy);
            
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
    
    public  VBox page() throws SQLException{
        
        implPromotionService pService = new implPromotionService();
        Pagination pagination = new Pagination();
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    if(filtrePourc.getValue()==0){
                        list = pService.readAllPromotion();
                        pagination.setPageCount(list.size());
                        pagination.setCurrentPageIndex(0);
                        pagination.setMaxPageIndicatorCount(3);
   
                    }else{
                        list = pService.filrePromotionPourcentage(filtrePourc.getValue());
                        pagination.setPageCount(list.size());
                        pagination.setCurrentPageIndex(0);
                        pagination.setMaxPageIndicatorCount(3);
                        System.out.println("list: "+list);
                        pagination.setPageFactory((pageIndex) -> {
                        if(list.size() == 1){
                            return new VBox(createPromotion(list.get(pageIndex),list.get(pageIndex)));

                        }
                        return new VBox(createPromotion(list.get(pageIndex),list.get(pageIndex+1)));
                    });
                        
                    }
                    
                   
                } catch (SQLException ex) {
                    Logger.getLogger(PromotionsController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(filtrePourc.getValue());
                
            }
            
        };
        
        System.out.println("list: "+list);
        list = pService.readAllPromotion();
        filtrePourc.setOnAction(event);
        pagination.setPageFactory((pageIndex) -> {

            return new VBox(createPromotion(list.get(pageIndex),list.get(pageIndex+1)));
        });
        VBox vBox = new VBox(pagination);
        
       
       return vBox;
    }
    
    
 
    
}
