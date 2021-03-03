/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjava;

import Entity.Promotion;
import Entity.Workshop;
import implService.implPromotionService;
import implService.implWorkshopService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.util.Callback;

/**
 *
 * @author dell
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField nbPart;
    @FXML
    private TextField prix;
    @FXML
    private TextField name;
    @FXML
    private TextField lieu;
    @FXML
    private TextField type;
    @FXML
    private TextArea desc;
    @FXML
    private DatePicker dateF;
    @FXML
    private DatePicker dateD;
    @FXML
    private Spinner<?> hDebut;
    @FXML
    private Spinner<?> hFin;
    @FXML
    private Button add;
    @FXML
    private Button cancel;
    @FXML
    private TableView<Workshop> tableView;
    @FXML
    private Button Display;
    
    ObservableList<Workshop> listWorkshop;
    @FXML
    private Button delete;
    private TextField idDel;
    @FXML
    private TextField idup;
    @FXML
    private Button btnUpdate;
    @FXML
    private DatePicker dateDP;
    @FXML
    private DatePicker dateDF;
    @FXML
    private Button addP;
    @FXML
    private Button updateP;
    @FXML
    private TableView<?> tableViewP;
    @FXML
    private TextField pourc;
    @FXML
    private TextField idupP;
    @FXML
    private Button updatePbtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        implWorkshopService wService = new implWorkshopService();
        
        TableColumn<Workshop, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Workshop, Void>, TableCell<Workshop, Void>> cellFactory = new Callback<TableColumn<Workshop, Void>, TableCell<Workshop, Void>>() {
            @Override
            public TableCell<Workshop, Void> call(final TableColumn<Workshop, Void> param) {
                final TableCell<Workshop, Void> cell = new TableCell<Workshop, Void>() {

                    private final Button btn = new Button("Delete");
                   

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Workshop data = getTableView().getItems().get(getIndex());
                            implWorkshopService wService = new implWorkshopService();
        
                            try {
                                wService.delete(data.getId());
                                listWorkshop = wService.readAll();
                                tableView.setItems(listWorkshop);
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
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

        
        colBtn.setCellFactory(cellFactory);

        

        TableColumn<Workshop, Integer> idCol = 
                new TableColumn<Workshop, Integer>("id");
        TableColumn<Workshop, String> nameCol = 
                new TableColumn<Workshop, String>("nomEvent");
        TableColumn<Workshop, Date> dateDCol = 
                new TableColumn<Workshop, Date>("dateDebut");
        TableColumn<Workshop, Date> dateFCol = 
                new TableColumn<Workshop, Date>("dateFin");
        TableColumn<Workshop, Time> hDebut = 
                new TableColumn<Workshop, Time>("hDebut");
        TableColumn<Workshop, Time> hFin = 
                new TableColumn<Workshop, Time>("hFin");
        TableColumn<Workshop, String> lieu = 
                new TableColumn<Workshop, String>("lieu");
        TableColumn<Workshop, Integer> nbParticipant = 
                new TableColumn<Workshop, Integer>("nbParticipant");
        TableColumn<Workshop, String> type = 
                new TableColumn<Workshop, String>("type");
        TableColumn<Workshop, String> description = 
                new TableColumn<Workshop, String>("description");
        TableColumn<Workshop, Float> prix = 
                new TableColumn<Workshop, Float>("prix");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        dateDCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        hDebut.setCellValueFactory(new PropertyValueFactory<>("hDebut"));
        hFin.setCellValueFactory(new PropertyValueFactory<>("hFin"));
        lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        nbParticipant.setCellValueFactory(new PropertyValueFactory<>("nbParticipant"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        
        try {
            listWorkshop = wService.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.setItems(listWorkshop);
        tableView.getColumns().addAll(idCol,nameCol,dateDCol,dateFCol,hDebut,hFin,lieu,nbParticipant,type,description,prix);
        tableView.getColumns().add(colBtn);
        listWorkshop = FXCollections.observableArrayList();
    }    

    @FXML
    private void addWorkshop(ActionEvent event) throws SQLException {
        
        Workshop w = new Workshop();
        implWorkshopService wService = new implWorkshopService();
        
        
        w.setNomEvent(name.getText());
        w.setDateDebut(Date.valueOf(dateD.getValue()));
        w.setDateFin(Date.valueOf(dateF.getValue()));
        w.sethDebut(0);
        w.sethFin(15);
        w.setType(type.getText());
        w.setDescription(desc.getText());
        w.setLieu(lieu.getText());
        w.setNbParticipant(Integer.parseInt(nbPart.getText()));
        w.setPrix(Float.parseFloat(prix.getText()));
        
        
        
        
        wService.ajouter(w);
        listWorkshop = wService.readAll();
        tableView.setItems(listWorkshop);
        System.out.println("ajouter avec success");
    }

    @FXML
    private void displayWorkshop(ActionEvent event) throws SQLException {
        
        
        implWorkshopService wService = new implWorkshopService();
        
        
        listWorkshop = wService.readAll();
        tableView.setItems(listWorkshop);
        System.out.println(listWorkshop);
        
        
        
    }



    private void deleteWorkshop(ActionEvent event) throws SQLException {
        
        implWorkshopService wService = new implWorkshopService();
        
        wService.delete(Integer.parseInt(idDel.getText()));
      
        this.displayWorkshop(event);
    }

    @FXML
    private void cancelWorkshop(ActionEvent event) {
        
        name.clear();

       
        type.clear();
        desc.clear();
        lieu.clear();
        nbPart.clear();
        prix.clear();
    }

    @FXML
    private void updateWorkshop(ActionEvent event) throws SQLException {
        
        implWorkshopService wService = new implWorkshopService();
        Workshop w = new Workshop();
        
        w = wService.find(Integer.parseInt(idup.getText()));
        name.setText(w.getNomEvent());
        dateD.setValue(LocalDate.parse(String.valueOf(w.getDateDebut())));
        dateF.setValue(LocalDate.parse(String.valueOf(w.getDateFin())));
        //w.sethDebut(0);
        //w.sethFin(15);
        type.setText(w.getType());
        desc.setText(w.getDescription());
        lieu.setText(w.getLieu());
        nbPart.setText(String.valueOf( w.getNbParticipant()));
        prix.setText(String.valueOf(w.getPrix()));
        
    }

    @FXML
    private void confirmUpdate(ActionEvent event)  {
        Workshop w = new Workshop();
        implWorkshopService wService = new implWorkshopService();
        
        w.setId(Integer.parseInt(idup.getText()));
        w.setNomEvent(name.getText());
        w.setDateDebut(Date.valueOf(dateD.getValue()));
        w.setDateFin(Date.valueOf(dateF.getValue()));
        w.sethDebut(0);
        w.sethFin(15);
        w.setType(type.getText());
        w.setDescription(desc.getText());
        w.setLieu(lieu.getText());
        w.setNbParticipant(Integer.parseInt(nbPart.getText()));
        w.setPrix(Float.parseFloat(prix.getText()));
        
        
        
        
        try {
            wService.update(w);
            this.displayWorkshop(event);
            System.out.println("modifier");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void addPromotion(ActionEvent event) throws SQLException {
        
        Promotion p = new Promotion();
        implPromotionService pService = new implPromotionService();
        
        p.setType(type.getText());
        p.setPrix(Float.parseFloat(pourc.getText()));
        p.setDateDebut(Date.valueOf(dateDP.getValue()));
        p.setDateFin(Date.valueOf(dateDF.getValue()));
        
        pService.ajouter(p);
        
    }


   
    
}
