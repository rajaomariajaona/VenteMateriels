/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Acceuil;

import Model.Repository.CategorieRepository;
import Model.Repository.ClientRepository;
import Model.Repository.MaterielsRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class AcceuilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadStatistiques();
    }

    private void loadStatistiques() {
        lbClient.setText(String.valueOf(ClientRepository.getClient().size()));
        lbMateriel.setText(String.valueOf(MaterielsRepository.getMateriels().size()));
        lbCategorie.setText(String.valueOf(CategorieRepository.getCategorie().size()));
    }

    @FXML
    private AnchorPane root;
    @FXML
    private Label lbClient;
    @FXML
    private Label lbMateriel;
    @FXML
    private Label lbCategorie;
    @FXML
    private Tab matStockTab;
    @FXML
    private BarChart<?, ?> materiels;
    @FXML
    private PieChart categorie;

}
