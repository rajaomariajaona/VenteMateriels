/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Clients;

import VueController.Clients.ClientsController;
import Model.Hibernate.Client;
import Model.Repository.ProvinceRepository;
import Model.Repository.ClientRepository;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class FormulaireClientController implements Initializable {

    private boolean modify = false;
    private Client clientModif = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (ClientsController.getClientModif() != null) {
            modify = true;
            clientModif = ClientsController.getClientModif();
        }
        ObservableList<String> provinces = FXCollections.observableArrayList();
        ProvinceRepository.getProvince().forEach((p) -> {
            provinces.add(p.getProvince());
        });
        cbProvince.setItems(provinces);
        if (modify) {
            btnConfirmed.setText("Modifier");
            cbProvince.getSelectionModel().select(clientModif.getProvince().getProvince());
            txtNom.setText(clientModif.getNomClient());
            txtPrenom.setText(clientModif.getPrenomClient());
            txtTel.setText(clientModif.getTelClient());
            txtAdresse.setText(clientModif.getAdresseClient());
        } else {
            cbProvince.getSelectionModel().select(0);
        }

    }

    public void handleConfirmed(ActionEvent event) {
        if (modify) {
            clientModif.setNomClient(txtNom.getText());
            clientModif.setPrenomClient(txtPrenom.getText());
            clientModif.setTelClient(txtTel.getText());
            clientModif.setAdresseClient(txtAdresse.getText());
            clientModif.setProvince(ProvinceRepository.getProvince(cbProvince.getSelectionModel().getSelectedItem()));
            ClientRepository.putClient(clientModif);
        } else {
            Client n = new Client(ProvinceRepository.getProvince(cbProvince.getSelectionModel().getSelectedItem()), txtNom.getText(), txtPrenom.getText(), txtTel.getText(), txtAdresse.getText());
            ClientRepository.postClient(n);
        }

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handleCanceled(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    @FXML
    JFXButton btnConfirmed;
    @FXML
    JFXComboBox<String> cbProvince;
    @FXML
    TextField txtNom;
    @FXML
    TextField txtPrenom;
    @FXML
    TextField txtAdresse;
    @FXML
    TextField txtTel;
}
