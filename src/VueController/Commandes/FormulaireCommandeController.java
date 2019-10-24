/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Commandes;

import VueController.Commandes.CommandesController;
import Model.Hibernate.Commande;
import Model.Repository.ProvinceRepository;
import Model.Repository.CommandeRepository;
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
public class FormulaireCommandeController implements Initializable {

    private boolean modify = false;
    private Commande commandeModif = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (CommandesController.getCommandeModif() != null) {
            modify = true;
            commandeModif = CommandesController.getCommandeModif();
        }
        ObservableList<String> provinces = FXCollections.observableArrayList();
        ProvinceRepository.getProvince().forEach((p) -> {
            provinces.add(p.getProvince());
        });
        cbProvince.setItems(provinces);
        if (modify) {
            btnConfirmed.setText("Modifier");
            cbProvince.getSelectionModel().select(commandeModif.getProvince().getProvince());
            txtNom.setText(commandeModif.getNomCommande());
            txtPrenom.setText(commandeModif.getPrenomCommande());
            txtTel.setText(commandeModif.getTelCommande());
            txtAdresse.setText(commandeModif.getAdresseCommande());
        } else {
            cbProvince.getSelectionModel().select(0);
        }

    }

    public void handleConfirmed(ActionEvent event) {
        if (modify) {
            commandeModif.setNomCommande(txtNom.getText());
            commandeModif.setPrenomCommande(txtPrenom.getText());
            commandeModif.setTelCommande(txtTel.getText());
            commandeModif.setAdresseCommande(txtAdresse.getText());
            commandeModif.setProvince(ProvinceRepository.getProvince(cbProvince.getSelectionModel().getSelectedItem()));
            CommandeRepository.putCommande(commandeModif);
        } else {
            Commande n = new Commande(ProvinceRepository.getProvince(cbProvince.getSelectionModel().getSelectedItem()), txtNom.getText(), txtPrenom.getText(), txtTel.getText(), txtAdresse.getText());
            CommandeRepository.postCommande(n);
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
