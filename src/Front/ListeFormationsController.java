/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import Entities.Commande;
import Entities.Formations;
import Entities.Promotion;
import Entities.categorie;
import Entities.formeval;
import Service.Servicecategorie;
import Service.ServiceCommande;
import Service.ServiceEvaluation;
import Service.ServiceFormations;
import UserSession.UserSession;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import static java.util.Collections.list;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author AMINE N
 */
public class ListeFormationsController implements Initializable {
     ObservableList<Formations> list ;
     ObservableList<formeval> listeval ;
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
FileChooser fileChooser=new FileChooser();
    @FXML
    private ComboBox<String> filtrercombo;
    @FXML
    private TableColumn<Formations, String> cocat;
    @FXML
    private TextField chercher;
    
    private static String idTxt;
    @FXML
    private VBox vboxaffichage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        fileChooser.setInitialDirectory(new File("C:\\Users\\AMINE N\\Desktop\\downloads"));
          Servicecategorie sv=new Servicecategorie();
          ServiceFormations sv2= new ServiceFormations();
          ServiceEvaluation serviceeval=new ServiceEvaluation();
          listeval=serviceeval.Get_Evaluation();
          list=sv2.Get_Formations2();
        ObservableList<categorie> list3=sv.Get_Categories();
        Afficher_formations();
        for(categorie c:list3)
        filtrercombo.getItems().add(c.getNom());
         filtrercombo.getItems().add("All");
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
        TableColumn<Formations, Void> colBtn = new TableColumn("Action");
          Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>> cellFactory = new Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>>() {
            @Override
            public TableCell<Formations, Void> call(final TableColumn<Formations, Void> param) {
                final TableCell<Formations, Void> cell = new TableCell<Formations, Void>() {

                    private final Button btn = new Button("Evaluer");
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Evaluer.fxml"));
                                    Parent root =(Parent) loader.load();
                                    EvaluerController f=loader.getController();
            
                                     //afficher_eval();
                                    f.setval(String.valueOf(data.getId()));
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
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                        }
                    }
                };
                return cell;
            }
        
           
        };
          
          
          TableColumn<Formations, Void> colBtn2 = new TableColumn("Action2");
          Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>> cellFactory2 = new Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>>() {
            @Override
            public TableCell<Formations, Void> call(final TableColumn<Formations, Void> param) {
                final TableCell<Formations, Void> cell = new TableCell<Formations, Void>() {

                    private final Button btn = new Button("Commander");
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                                 Commande c=new Commande();
        c.setId_client(UserSession.getInstace("", 0, "").getId());
        c.setId_formation(data.getId());
        c.setEtat("non valider");
        c.setPrix(data.getCout_fin());
        ServiceCommande s=new ServiceCommande();
        s.AjouterCommande(c);
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            
                        }
                    }
                };
                return cell;
            }
        
           
        };
          TableColumn<Formations, Void> colBtn3 = new TableColumn("Action3");
          Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>> cellFactory3 = new Callback<TableColumn<Formations, Void>, TableCell<Formations, Void>>() {
            @Override
            public TableCell<Formations, Void> call(final TableColumn<Formations, Void> param) {
                final TableCell<Formations, Void> cell = new TableCell<Formations, Void>() {

                    private final Button btn3 = new Button("Download");
                   

                    {
                        btn3.setOnAction((ActionEvent event) -> {
                            Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
            //new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",data.getPath()).start();
                               donwload_ftp(data.getPath());
            
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn3);
                            
                        }
                    }
                };
                return cell;
            }
        
           
        };
          
        
        colBtn.setCellFactory(cellFactory);
        colBtn2.setCellFactory(cellFactory2);
        colBtn3.setCellFactory(cellFactory3);
        ServiceFormations sp=new ServiceFormations();
        ObservableList<Formations> list=sp.Get_Formations2();
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
           cocat.setCellValueFactory(new PropertyValueFactory<Formations,String>("categorie"));
           colBtn.setCellFactory(cellFactory);
           tabform.setItems(list);
           tabform.getColumns().add(colBtn);
           tabform.getColumns().add(colBtn2);
           tabform.getColumns().add(colBtn3);
           
        
    }

    @FXML
    private void SearchKey(KeyEvent event) {
        //ServiceEvaluation sv=new ServiceEvaluation();
        ServiceFormations sv=new ServiceFormations();
         ObservableList <Formations> comformations =  FXCollections.observableArrayList();
         
          if (chercher.getText().isEmpty())
          {
              list = sv.Get_Formations2();
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

    @FXML
    private void onfiltrer(ActionEvent event) {
         ServiceFormations sv=new ServiceFormations();
         ObservableList <Formations> comformations =  FXCollections.observableArrayList();
         if(filtrercombo.getValue().equals("All"))
         {
             list = sv.Get_Formations();
             tabform.setItems(comformations);
         }
         
         else
         {
             list=sv.filtrer_forma(filtrercombo.getValue());
             tabform.setItems(comformations);
         }
         try {
            vboxaffichage.getChildren().set(0,page());
            //System.out.println(vboxaffichage.getChildren().indexOf(page()));
           
        } catch (SQLException ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void backDashboard(ActionEvent event) throws IOException {
        
                 Parent root = FXMLLoader.load(getClass().getResource("/participant/Home.fxml")); 
                 Scene scene = new Scene(root);
                 pidevfinal.PidevFinal.parentWindow.setScene(scene);
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
            JFXButton buy = new JFXButton("BUY");
            JFXButton download = new JFXButton("Evaluer");
            JFXButton download2 = new JFXButton("Download");
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
            download.setStyle(styleButton);
            download2.setStyle(styleButton);
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
            download.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Evaluer.fxml"));
                                    Parent root =(Parent) loader.load();
                                    EvaluerController f=loader.getController();
            
                                     //afficher_eval();
                                    f.setval(String.valueOf(j.getId()));
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
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
            });
            buy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  
                            //Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                                 Commande c=new Commande();
        c.setId_client(UserSession.getInstace("", 0, "").getId());
        c.setId_formation(j.getId());
        c.setEtat("non valider");
        c.setPrix(j.getCout_fin());
        ServiceCommande s=new ServiceCommande();
        s.AjouterCommande(c);
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
            });
            download2.setOnAction((ActionEvent event) -> {
                            //Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
            //new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",data.getPath()).start();
                               donwload_ftp(j.getPath());
            
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
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
               
                
                rating.setPartialRating(true);
                rating.setDisable(true);
                //rating.set
                
                
                rating.setRating(s.AffecterRatetogui(j));
                
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
                rating.setPartialRating(true);
                rating.setRating(s.AffecterRatetogui(b));
                System.out.println("evalll"+s.AffecterRatetogui(b)+"ID"+b.getId());
                    float resultat2 = b.getCout_fin() * (100-b.getCout_hj())/100;
                    l6.setText(" "+b.getCout_fin()+" ");
                    l7.setText(" " +resultat2);
                    //rootf.getChildren().addAll(l6,l7);
               
                try {
                 //InputStream stream = new FileInputStream("C:\\Users\\dell\\Desktop\\Game\\pourcentage\\"+Math.round(b.getPrix())+".png");
                 //Image image = new Image(stream);
                 //pour.setImage(image);
            } catch (Exception ex) {
                Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
                download.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ServiceFormations wService = new ServiceFormations();
        
                           try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Evaluer.fxml"));
                                    Parent root =(Parent) loader.load();
                                    EvaluerController f=loader.getController();
            
                                     //afficher_eval();
                                    f.setval(String.valueOf(b.getId()));
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
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    }
                });
                buy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  
                            //Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                                 Commande c=new Commande();
        c.setId_client(UserSession.getInstace("", 0, "").getId());
        c.setId_formation(b.getId());
        c.setEtat("non valider");
        c.setPrix(b.getCout_fin());
        ServiceCommande s=new ServiceCommande();
        s.AjouterCommande(c);
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
            });
              
                   download2.setOnAction((ActionEvent event) -> {
                            //Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
            //new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",data.getPath()).start();
                               donwload_ftp(b.getPath());
            
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        });
            }
         rootf.getChildren().addAll(l6,l7);
             
                
        
        

            

            test.getChildren().addAll(rating,buy,download,download2);
            
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
            JFXButton buy = new JFXButton("BUY");
            JFXButton download = new JFXButton("Evaluer");
            JFXButton download2 = new JFXButton("Download");
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
            download.setStyle(styleButton);
            download2.setStyle(styleButton);
            footer.setStyle(stylefooter);
            card.setStyle(styleCard);
            ServiceEvaluation s=new ServiceEvaluation();
            rating.setRating(s.AffecterRatetogui(j));
            download.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                           
                               
                               
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
       
        
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Evaluer.fxml"));
                                    Parent root =(Parent) loader.load();
                                    EvaluerController f=loader.getController();
            
                                     //afficher_eval();
                                    f.setval(String.valueOf(j.getId()));
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
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
            });
            buy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                  
                            //Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
                                 Commande c=new Commande();
        c.setId_client(UserSession.getInstace("", 0, "").getId());
        c.setId_formation(j.getId());
        c.setEtat("non valider");
        c.setPrix(j.getCout_fin());
        ServiceCommande s=new ServiceCommande();
        s.AjouterCommande(c);
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
            });
            download2.setOnAction((ActionEvent event) -> {
                            //Formations data = getTableView().getItems().get(getIndex());
                            ServiceFormations wService = new ServiceFormations();
        
                           try {
            //new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",data.getPath()).start();
                               donwload_ftp(j.getPath());
            
        } catch (Exception ex) {
            Logger.getLogger(ListeFormationsController.class.getName()).log(Level.SEVERE, null, ex);
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
                //l5.setText("Pourcentage :  "+b.getPrix());
                float resultat = j.getCout_fin() * (100-j.getCout_hj())/100;
          
            l6.setText(" "+j.getCout_fin()+" ");
            l7.setText(" "+resultat);
           

           

                
                
            
        
         rootf.getChildren().addAll(l6,l7);
            
        
        

            

            test.getChildren().addAll(rating,buy,download,download2);
            
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
   
    }

