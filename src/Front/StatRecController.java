/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Front;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import Entities.reclamation;
import Service.Servicereclamation;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author rania
 */
public class StatRecController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private BarChart<String, Integer> bc;
    @FXML
    private NumberAxis na;
    @FXML
    private CategoryAxis ca;
    @FXML
    private Button btnPDF;
    @FXML
    private ComboBox<Integer> cbYear;
    
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    private List<reclamation> listRec = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));
        ca.setCategories(monthNames);
        Servicereclamation sR = new Servicereclamation();
        listRec = sR.getAllRec();
        
        for(int i=2025; i > 2010; i--) {
            cbYear.getItems().add(i);
        }
        
        
    }    
    
    public void saveAsPng(BarChart lineChart, String path) {
            WritableImage image = lineChart.snapshot(new SnapshotParameters(), null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @FXML
    private void btnPDFClicked(MouseEvent event) throws FileNotFoundException, DocumentException, IOException{
                saveAsPng(bc, "D:\\chart.png");

        OutputStream file = new FileOutputStream(new File("D:\\TestPDF.pdf"));


            Document document = new Document();

            PdfWriter.getInstance(document, file);


            document.open();

            document.add(new Paragraph("Reclamation: Stats"));

            document.add(new Paragraph(new Date().toString()));
            
            Image img = Image.getInstance("D:\\chart.png");
            document.add(img);


            document.close();

            file.close();
            
            Notifications n = Notifications.create()
                              .title("PDF Chart")
                              .text("  file path: "+file)
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();

    }

    @FXML
    private void cbYearAction(ActionEvent event) {
        System.out.println(""+cbYear.getValue());
        System.out.println(""+listRec);
        int[] monthCounter = new int[12];
        for (reclamation p : listRec) {
            System.out.println(""+p.getDate());
            int month = p.getDate().toLocalDate().getMonthValue() - 1;
            int year = p.getDate().toLocalDate().getYear();
            if ( year == cbYear.getValue()) {
                monthCounter[month]++;
            }
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }
        
        bc.getData().clear();
        bc.getData().add(series);

    }
    
}
