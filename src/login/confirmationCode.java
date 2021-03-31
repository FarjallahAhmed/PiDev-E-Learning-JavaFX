/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Mehdi
 */
public class confirmationCode implements Initializable {

    @FXML
    private TextField code;
    private String testCode;
    @FXML
    private HBox time;
    
    public static boolean timeIsUp = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        testCode = getRandomNumberString();
        System.out.println(testCode);
        countDownTime count = new countDownTime();
        time.getChildren().add(count.setCountdown());
    }    

    @FXML
    private void AddAccount(ActionEvent event) {
        
       // ((Node)(event.getSource())).getScene().getWindow().hide();
       
       if ((code.getText().equals(testCode))&&(timeIsUp==false))
       {
            SignUpController.confirmationCodeParticipant = true;
           ((Node)(event.getSource())).getScene().getWindow().hide();
       }
       else 
       {
           SignUpController.confirmationCodeParticipant = false;
           ((Node)(event.getSource())).getScene().getWindow().hide();
       }
        
        
       
       
        
    }
    
    public static String getRandomNumberString() {
    // It will generate 6 digit random Number.
    // from 0 to 999999
    Random rnd = new Random();
    int number = rnd.nextInt(999999);

    // this will convert any number sequence into 6 character.
    return String.format("%06d", number);
}
    
}
