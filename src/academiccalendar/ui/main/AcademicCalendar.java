/*
 * @Academic Calendar Version 1.0 3/7/2017
 * @owner and @author: FrumbSoftware
 * @Team Members: Paul Meyer, Karis Druckenmiller , Darick Cayton, Rodolfo Madriz
 */
package academiccalendar.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Paul, Karis, Darick and Rodolfo #Frumbug 4 life
 */
public class AcademicCalendar extends Application {
        
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        // Set main window icon
        stage.getIcons().add(
        new Image( "academiccalendar/ui/icons/app_icon.png" ));
        stage.setTitle("LUAC (Lindenwood University Academic Calendar) by Frumbug Software");
        
        // Maximize window at launch
        stage.setMaximized(true);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
