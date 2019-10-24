/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class MainController implements Initializable {

    @FXML
    HBox btnAcceuil;
    @FXML
    HBox btnClients;
    @FXML
    HBox btnCommandes;
    @FXML
    HBox btnCategories;
    @FXML
    HBox btnArticles;

    private Parent[] parents;
    private HBox[] btnTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            parents = new Parent[5];
            parents[0] = FXMLLoader.load(getClass().getResource("/VueController/Clients/Clients.fxml"));
            parents[1] = FXMLLoader.load(getClass().getResource("/VueController/Clients/Clients.fxml"));
            parents[2] = FXMLLoader.load(getClass().getResource("/VueController/Clients/Clients.fxml"));
            parents[3] = FXMLLoader.load(getClass().getResource("/VueController/Materiels/Materiels.fxml"));
            parents[4] = FXMLLoader.load(getClass().getResource("/VueController/Categories/Categories.fxml"));
            for (Parent parent : parents) {
                mainPane.getChildren().add(parent);
                parent.setVisible(false);
            }
            btnTab = new HBox[5];
            btnTab[0] = btnAcceuil;
            btnTab[1] = btnClients;
            btnTab[2] = btnCommandes;
            btnTab[3] = btnCategories;
            btnTab[4] = btnArticles;
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleBtnQuit(MouseEvent event) {
        System.exit(0);
    }

    public void handleTest(ActionEvent event) {
        Stage st = new Stage(StageStyle.UNDECORATED);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Confirmation.fxml"));
            Scene sc = new Scene(root);
            st.setScene(sc);
            st.showAndWait();
        } catch (IOException ex) {

        }
    }

    public void handleTabSwitch(MouseEvent event) {
        HBox n = ((HBox) event.getSource());
        for (int i = 0; i < btnTab.length; i++) {
            btnTab[i].getStyleClass().removeIf(classe -> classe.equals("tab-selected"));
            parents[i].setVisible(false);
        }
        n.getStyleClass().add("tab-selected");
        switch (n.getId().replace("btn", "")) {
            case "Clients":
                parents[1].setVisible(true);
                break;
            case "Articles":
                parents[3].setVisible(true);
                break;
            case "Categories":
                parents[4].setVisible(true);
                break;
        }
    }
    @FXML
    StackPane mainPane;
    @FXML
    AnchorPane main;
}
