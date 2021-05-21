/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidevfinal;

import static Utils.validate_code_source.isAddressValid;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mehdi
 * 
 */
    
 
   
public class PidevFinal extends Application {
    
    public static Stage parentWindow;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        parentWindow = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/login/Main.fxml"));
// Parent root = FXMLLoader.load(getClass().getResource("/home/UtilisateursCard.fxml"));
        /*
            if( isAddressValid("wassim.ridene@esprit.tn"))
                 System.out.println("true");
            else     
                 System.out.println("false");
        */
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
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
