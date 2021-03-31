/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.fxml;

import Entities.Commande;
import Entities.Formations;
import Entities.categorie;
import Entities.formeval;
import Front.EvaluerController;
import Front.ListeFormationsController;
import static Front.ListeFormationsController.donwload_ftp;
import Service.ServiceCommande;
import Service.ServiceEvaluation;
import Service.ServiceFormations;
import Service.Servicecategorie;
import UserSession.UserSession;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class Ajout_formationController implements Initializable {
    private  static FormulaireformController contrl;
   @FXML
    private TableView<Formations> tabform;
    @FXML
    private TableColumn<Formations, Integer> coid;
    
    @FXML
    private TableColumn<Formations, String> cotype;
    @FXML
    private TableColumn<Formations, String> coobjectif;
    @FXML
    private TableColumn<Formations, Integer> conbparticp;
    @FXML
    private TableColumn<Formations, Float> cocouthj;
    @FXML
    private TableColumn<Formations, Integer> conbjour;
    @FXML
    private TableColumn<Formations, Float> cocoutfin;
    @FXML
    private TableColumn<Formations, Date> codaterelle;
    @FXML
    private TableColumn<Formations, Date> codateprevu;
    @FXML
    private TableColumn<Formations, String> copath;
    @FXML
    private TableColumn<Formations, String> coobject;
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnexporter;
    @FXML
    private Button affecttserPromotion;
    
    public static Formations affecterP;
    @FXML
    private VBox vboxaffichage;
     ObservableList<Formations> list ;
     ObservableList<formeval> listeval ;
    private Label totalFormateurs;
    @FXML
    private Label totalFormations;
    @FXML
    private TextField chercher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           Afficher_formations();
           
               Servicecategorie sv=new Servicecategorie();
          ServiceFormations sv2= new ServiceFormations();
          System.out.println("REPUTATION DE CE FORMATEUR="+sv2.Get_Reputationformateur(UserSession.getInstace("", 0, "").getId()));
          ServiceEvaluation serviceeval=new ServiceEvaluation();
          listeval=serviceeval.Get_Evaluation();
          list=sv2.Get_Formationsperformateur(UserSession.getInstace("", 0, "").getId());
        ObservableList<categorie> list3=sv.Get_Categories();
        Afficher_formations();
        totalFormations.setText(String.valueOf(list.size()));
         if(list.size()!=0){
        try {
            vboxaffichage.getChildren().add(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));
           
        } catch (SQLException ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
    }    
    public void Afficher_formations()
    {
        ServiceFormations sp=new ServiceFormations();
        ObservableList<Formations> list=sp.Get_Formations();
        coid.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("Id"));
         coobject.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
          cotype.setCellValueFactory(new PropertyValueFactory<Formations,String>("Type"));
           coobjectif.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objectif"));
           conbparticp.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
           cocouthj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
           conbjour.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_jour"));
           cocoutfin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
           codaterelle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_reelle"));
           codateprevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
           copath.setCellValueFactory(new PropertyValueFactory<Formations,String>("path"));
           tabform.setItems(list);
           
        
    }


    @FXML
    private void oncreerformation(ActionEvent event) throws IOException {
        if (event.getSource() == btnajouter) {
            
            
            
                 Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/formulaireform.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        }
        
        
        
        
    
    }

    @FXML
    private void on_exporter(ActionEvent event) {
        TableView<Formations> table = new TableView<Formations>();
         ServiceFormations Service = new ServiceFormations();
         ObservableList<Formations> data =FXCollections.observableArrayList();  
   
        
        double w = 500.00;
        // set width of table view
        table.setPrefWidth(w);
        // set resize policy
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // intialize columns
        TableColumn<Formations,String> Objet  = new TableColumn<Formations,String>("Objet");
        TableColumn<Formations,String> Type  = new TableColumn<Formations,String>("Type");
        TableColumn<Formations,Integer> nb_participants = new TableColumn<Formations,Integer>("Nb_participants");
        TableColumn<Formations,Float> couht_hj  = new TableColumn<Formations,Float>("Cout_hj");
        TableColumn<Formations,Integer> nb_jour  = new TableColumn<Formations,Integer>("nb_jour");
        TableColumn<Formations,Float> cout_fin = new TableColumn<Formations,Float>("cout_fin");
        TableColumn<Formations,Date> date_relle = new TableColumn<Formations,Date>("date_relle");
        TableColumn<Formations,Date> date_prevu  = new TableColumn<Formations,Date>("date_prevu");
        
        
            
        // set width of columns
        Objet.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        Type.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        nb_participants.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        couht_hj.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        nb_jour.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        cout_fin.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        date_relle.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width       
        date_prevu.setMaxWidth( 1f * Integer.MAX_VALUE * 40 ); // 50% width
        
        // 
        Objet.setCellValueFactory(new PropertyValueFactory<Formations,String>("Objet"));
        Type.setCellValueFactory( new PropertyValueFactory<Formations,String>("Type"));
        nb_participants.setCellValueFactory(new PropertyValueFactory<Formations,Integer>("nb_participants"));
        couht_hj.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_hj"));
        nb_jour.setCellValueFactory( new PropertyValueFactory<Formations,Integer>("nb_jour"));
        cout_fin.setCellValueFactory(new PropertyValueFactory<Formations,Float>("cout_fin"));
        date_relle.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_relle")); 
        date_prevu.setCellValueFactory(new PropertyValueFactory<Formations,Date>("date_prevu"));
        
        
        // Add columns to the table
        table.getColumns().addAll(Objet,Type,nb_participants,couht_hj,nb_jour,cout_fin,date_relle,date_prevu);
        
        
        // Query to get ALL Events from the selected calendar!!
        //String getMonthEventsQuery = "SELECT * From EVENTS WHERE CalendarName='" + calendarName + "' ORDER BY EventDate";
        
        // Store the results here
        //ResultSet result = databaseHandler.executeQuery(getMonthEventsQuery);
        data = Service.Get_Formations();
        
        /*
        try {
        
        while(result.next()){
        //initalize temporarily strings
        String tempTerm="";
        
        
        //***** Get term, Event Description and Date ***
        
        //Get Event Description
        String eventDescript = result.getString("EventDescription");
        //Get Term ID for an event
        int termID = result.getInt("TermID");
        
        //Get Event Date and format it as day-month-year
        Date dDate=result.getDate("EventDate");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String eventDate = df.format(dDate);
        
        //Query that will get the term name based on a term ID
        String getTermQuery = "SELECT TermName FROM TERMS WHERE TermID=" + termID + "";
        //Execute query to get TermName and store it in a ResultSet variable
        ResultSet termResult = databaseHandler.executeQuery(getTermQuery);
        
        
        while(termResult.next())
        {
        tempTerm=termResult.getString(1);
        /*
        while(programResult.next())
        {
        tempProgram = programResult.getString(1);
        }
        tempTerm+=" "+tempProgram;
        
        }
        
        
        //Get Term Name for an event
        //tempTerm = termResult.getString(1);
        
        
        //Add event information in a row
        data.add(new Event(tempTerm,eventDescript,eventDate));
        
        }
        } catch (SQLException ex) {
        Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
         
       
        table.getItems().setAll(data);
        // open dialog window and export table as pdf
        PrinterJob job = PrinterJob.createPrinterJob();
        System.out.println(table);
        if(job != null){
          
          job.printPage(table);
          job.endJob();
        }
       }

    @FXML
    private void backDashboard(ActionEvent event) throws IOException {
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/formateur/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
    }

    @FXML
    private void getFormationInfo(MouseEvent event) {
        affecterP = tabform.getSelectionModel().getSelectedItem();
        
        
    }

    @FXML
    private void affPromotions(ActionEvent event) throws IOException {
       /* Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
        
        System.out.println(affecterP);*/
       
       
       
       Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml"));
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
          stage.show();
    }
     public static AnchorPane createPromotion(Formations j,Formations b) {
        
       
        AnchorPane card2 = new AnchorPane();
         ServiceEvaluation s=new ServiceEvaluation();
        
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
        String styleButton2 = "-fx-text-fill: white;"
                + "-fx-background-color : #DC143C;"
                + "-fx-font-size : 14;";
        String styleButton3 = "-fx-text-fill: white;"
                + "-fx-background-color : #3CB371;"
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
            JFXButton Modifier = new JFXButton("Modifier");
            JFXButton Supprimer = new JFXButton("Supprimer");
            JFXButton promotion = new JFXButton("Affecter Promotion");
            Rating rating = new Rating(5);
            rating.setPrefSize(5, 5);
            rating.setLayoutY(0.5);
            
            //rating.setLayoutY(i);

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
             GlyphsDude.setIcon(Supprimer, FontAwesomeIcon.TRASH);
             GlyphsDude.setIcon(Modifier, FontAwesomeIcon.PENCIL);
            Modifier.setStyle(styleButton3);
            Supprimer.setStyle(styleButton2);
            promotion.setStyle(styleButton);
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
            promotion.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                      Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(Ajout_formationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
        affecterP=j;
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
          stage.show();
                }
            });
            Supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ServiceFormations sv=new ServiceFormations();
                    sv.Supprimer_formation(j.getId());
                    
                     
                }
            });
            Modifier.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                         try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/fxml/formulaireform.fxml"));
                                    Parent root =(Parent) loader.load();
                                    FormulaireformController f=loader.getController();
            
                                     //afficher_eval();
                                   f.set_alllabes(j);
                                    //idTxt = String.valueOf(data.getId());
                                    
                                    
                                    //System.out.println(idupdate.getText());
                                      Stage stage = new Stage();
                                      Scene scene = new Scene(root);
                                      scene.setFill(Color.TRANSPARENT);
                                        stage.setScene(scene);
                                        stage.initStyle(StageStyle.TRANSPARENT);
                                         stage.setScene(scene);
                                      //stage.setScene(new Scene(root));
                                      stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Ajout_formationController.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
                }
            });
            
         
          
          
            
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
                l3.setText("Date Debut : "+j.getDate_prevu());
                l4.setText("Date Fin :  "+j.getDate_reelle());
               
                
                
                
                //System.out.println("EVALLL"+s.AffecterRatetogui(j)+"IDD"+j.getId());
                
                
                //l5.setText("Pourcentage :  "+b.getPrix());
                float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
          
            l6.setText(" "+j.getCout_fin()+" ");
            l7.setText(" "+resultat);
           

           

                
                
            
            if(i==1){
                
                l1.setText("Objet : "+b.getObjet());
                l2.setText("Type  :  "+b.getType());
                l3.setText("Date Debut : "+b.getDate_prevu());
                l4.setText("Date Fin :  "+b.getDate_reelle());
               
                System.out.println("evalll"+s.AffecterRatetogui(b)+"ID"+b.getId());
                    float resultat2 = b.getCout_fin() * (100-b.getCout_hj())/100;
                    l6.setText(" "+b.getCout_fin()+" ");
                    l7.setText(" " +resultat2);
                    //rootf.getChildren().addAll(l6,l7);
                       promotion.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                      Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(Ajout_formationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                           affecterP=b;
                             Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            stage.setScene(scene);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(scene);
        
                            stage.show();
                }
            });
                            Supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ServiceFormations sv=new ServiceFormations();
                    sv.Supprimer_formation(b.getId());
                    
                     
                }
            });
                                 Modifier.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                         try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/fxml/formulaireform.fxml"));
                                    Parent root =(Parent) loader.load();
                                    FormulaireformController f=loader.getController();
            
                                     //afficher_eval();
                                   f.set_alllabes(b);
                                    //idTxt = String.valueOf(data.getId());
                                    
                                    
                                    //System.out.println(idupdate.getText());
                                      Stage stage = new Stage();
                                      Scene scene = new Scene(root);
                                      scene.setFill(Color.TRANSPARENT);
                                        stage.setScene(scene);
                                        stage.initStyle(StageStyle.TRANSPARENT);
                                         stage.setScene(scene);
                                      //stage.setScene(new Scene(root));
                                      stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Ajout_formationController.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
                }
            });
      
            }
         rootf.getChildren().addAll(l6,l7);
           rating.setVisible(false);
                
        
        

            

            test.getChildren().addAll(Modifier,Supprimer,promotion);
            
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
    public static AnchorPane createPromotion2(Formations j) {
        
       
        AnchorPane card2 = new AnchorPane();

        
        String promolb =  "-fx-underline : true;"
                +"-fx-font-weight : bold;"
                +"-fx-text-fill: red;"
                +"-fx-font-size : 18;";
        
        String stylePromo = "-fx-text-fill: white;"
                + "-fx-font-size : 48;";

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
         String styleButton2 = "-fx-text-fill: white;"
                + "-fx-background-color : #DC143C;"
                + "-fx-font-size : 14;";
        String styleButton3 = "-fx-text-fill: white;"
                + "-fx-background-color : #3CB371;"
                + "-fx-font-size : 14;";

            
        
            
            AnchorPane card = new AnchorPane();
            
            VBox rootlb = new VBox();
            VBox root = new VBox();
            HBox rootf = new HBox();
            HBox test = new HBox();
            ImageView pour  = new ImageView();
            
            
            
    Pane footer = new Pane();
            JFXButton Modifier = new JFXButton("Modifier");
            JFXButton Supprimer = new JFXButton("Supprimer");
            JFXButton promotion = new JFXButton("Affecter Promotion");
            Rating rating = new Rating(5);
            rating.setPrefSize(5, 5);
            rating.setLayoutY(0.5);
            

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
             GlyphsDude.setIcon(Supprimer, FontAwesomeIcon.TRASH);
             GlyphsDude.setIcon(Modifier, FontAwesomeIcon.PENCIL);
            Modifier.setStyle(styleButton3);
            Supprimer.setStyle(styleButton2);
            promotion.setStyle(styleButton);
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
            ServiceEvaluation s=new ServiceEvaluation();

            footer.setPrefSize(504, 50);        
            test.setSpacing(35); 
            
            rootlb.setSpacing(18);
            
            card.setPrefSize(504, 250);
            card.setLayoutX(30+xcard);
            card.setLayoutY(40);        
  promotion.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                      Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/home/fxml/affecterPromotion.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(Ajout_formationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

        affecterP=j;
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        
          stage.show();
                }
            });
            Supprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ServiceFormations sv=new ServiceFormations();
                    sv.Supprimer_formation(j.getId());
                    
                     
                }
            });
                 Modifier.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                         try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/fxml/formulaireform.fxml"));
                                    Parent root =(Parent) loader.load();
                                    FormulaireformController f=loader.getController();
            
                                     //afficher_eval();
                                   f.set_alllabes(j);
                                    //idTxt = String.valueOf(data.getId());
                                    
                                    
                                    //System.out.println(idupdate.getText());
                                      Stage stage = new Stage();
                                      Scene scene = new Scene(root);
                                      scene.setFill(Color.TRANSPARENT);
                                        stage.setScene(scene);
                                        stage.initStyle(StageStyle.TRANSPARENT);
                                         stage.setScene(scene);
                                      //stage.setScene(new Scene(root));
                                      stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Ajout_formationController.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
                }
            });
       
        
                l1.setText("Objet : "+j.getObjet());
                l2.setText("Type  :  "+j.getType());
                l3.setText("Date Debut : "+j.getDate_prevu());
                l4.setText("Date Fin :  "+j.getDate_reelle());
                //l5.setText("Pourcentage :  "+b.getPrix());
                float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
          
            l6.setText(" "+j.getCout_fin()+" ");
            l7.setText(" "+resultat);
           

           

                
                
            
        
         rootf.getChildren().addAll(l6,l7);
            
        
        

            

            test.getChildren().addAll(Modifier,Supprimer,promotion);
            
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
        
        ServiceFormations pService = new ServiceFormations();
         ServiceEvaluation serviceeval=new ServiceEvaluation();
       // list = pService.Get_Formations();
        Pagination pagination = new Pagination();
        pagination.setPageCount(list.size()-1);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(3);
        
        pagination.setPageFactory((pageIndex) -> {
                    
                System.out.println(pageIndex);
                if(pageIndex+1 <list.size())
            return new VBox(createPromotion(list.get(pageIndex),list.get(pageIndex+1)));
                else return new VBox(createPromotion2(list.get(pageIndex)));
        });

//        promotionpage.getChildren().add(pagination);
        VBox vBox = new VBox(pagination);
        
       
       return vBox;
    }

    @FXML
    private void onrechercher(KeyEvent event) {
          ServiceFormations sv=new ServiceFormations();
         ObservableList <Formations> comformations =  FXCollections.observableArrayList();
         
          if (chercher.getText().isEmpty())
          {
              list = sv.Get_Formationsperformateur(UserSession.getInstace("", 0, "").getId());
              //tabform.setItems(comformations);
          }
          else
          {
              list=sv.search(chercher.getText());
                //tabform.setItems(comformations);      
          }
         try {
            vboxaffichage.getChildren().set(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));
           
        } catch (SQLException ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


}

    

