/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Materiels;

import Model.Hibernate.Materiels;
import Model.Repository.CategorieRepository;
import Model.Repository.MaterielsRepository;
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
public class FormulaireMaterielController implements Initializable {

    private boolean modify = false;
    private Materiels materielModif = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (MaterielsController.getMaterielModif() != null) {
            modify = true;
            materielModif = MaterielsController.getMaterielModif();
        }
        ObservableList<String> categories = FXCollections.observableArrayList();
        CategorieRepository.getCategorie().forEach((p) -> {
            categories.add(p.getCategorie());
        });
        cbCategorie.setItems(categories);
        if (modify) {
            btnConfirmed.setText("Modifier");
            cbCategorie.getSelectionModel().select(materielModif.getCategorie().getCategorie());
            txtNumSerie.setText(materielModif.getNumSerie() + "");
            txtNom.setText(materielModif.getNomMateriels());
            txtPrix.setText(materielModif.getPrixMateriels() + "");
            txtQuantite.setText(materielModif.getQuantiteStock() + "");
        } else {
            cbCategorie.getSelectionModel().select(0);
        }

    }

    public void handleConfirmed(ActionEvent event) {
        if (modify) {
            if (materielModif.getNumSerie() == Integer.parseInt(txtNumSerie.getText())) {
                materielModif.setNomMateriels(txtNom.getText());
                materielModif.setPrixMateriels(Float.parseFloat(txtPrix.getText()));
                materielModif.setQuantiteStock(Integer.parseInt(txtQuantite.getText()));
                materielModif.setCategorie(CategorieRepository.getCategorie(cbCategorie.getSelectionModel().getSelectedItem()));
                MaterielsRepository.putMateriels(materielModif);
            }else{
                MaterielsRepository.putMateriels(materielModif, new Materiels(Integer.parseInt(txtNumSerie.getText()), CategorieRepository.getCategorie(cbCategorie.getSelectionModel().getSelectedItem()), txtNom.getText(), Float.parseFloat(txtPrix.getText()), Integer.parseInt(txtQuantite.getText())));
            }

        } else {
            Materiels n = new Materiels(Integer.parseInt(txtNumSerie.getText()), CategorieRepository.getCategorie(cbCategorie.getSelectionModel().getSelectedItem()), txtNom.getText(), Float.parseFloat(txtPrix.getText()), Integer.parseInt(txtQuantite.getText()));
            MaterielsRepository.postMateriels(n);
        }

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handleCanceled(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    @FXML
    JFXButton btnConfirmed;
    @FXML
    JFXComboBox<String> cbCategorie;
    @FXML
    TextField txtNom;
    @FXML
    TextField txtPrix;
    @FXML
    TextField txtQuantite;
    @FXML
    TextField txtNumSerie;
}
