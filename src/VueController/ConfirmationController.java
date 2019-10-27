/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class ConfirmationController implements Initializable {
    private static int result;

    public static int getResult() {
        return result;
    }
    @FXML
    private Text text;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        result = 0;
    }
    public void handleBtnQuit(ActionEvent event){
        result = 0;
        ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }
    public void handleBtnConfirmed(ActionEvent event){
        result = 1;
        ((Stage)((Node) event.getSource()).getScene().getWindow()).close();
    }  
    
}
