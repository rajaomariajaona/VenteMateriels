/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Commandes;

import Model.Hibernate.Commande;
import Model.Hibernate.Materiels;
import Model.Repository.MaterielsRepository;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class FormulaireContientController implements Initializable {

    private Commande commandeCourrant = null;
    private HashMap<String, Materiels> listeMateriels;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        commandeCourrant = CommandesController.getCommandeCourrant();

        spQuantite.getEditor().textProperty().addListener((ov, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            } catch (final NumberFormatException ex) {
                spQuantite.getEditor().setText(oldValue);
            }
        });
        listeMateriels = new HashMap<>();
        ObservableList<String> liste = FXCollections.observableArrayList();
        List<Materiels> l = MaterielsRepository.getMaterielsDispo();
        l.forEach((m) -> {
            liste.add(m.getNumSerie() + " : " + m.getNomMateriels());
            listeMateriels.put(m.getNumSerie() + " : " + m.getNomMateriels(), m);
        });
        cbMeuble.setItems(liste);
        if (cbMeuble.getItems().size() != 0) {
            cbMeuble.getSelectionModel().select(0);
        }
        cbMeuble.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem()).getQuantiteStock()));
        });
        spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem()).getQuantiteStock()));
    }

    public void handlePost(ActionEvent event) {
        
    }

    public void handleCanceled(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handlePrint(Event event) {

    }

    public void handleDelete(Event event) {

    }
    @FXML
    ComboBox<String> cbMeuble;
    @FXML
    Spinner<Integer> spQuantite;
    @FXML
    TableView liste;
    @FXML
    Label lbTotal;

}
