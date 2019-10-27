/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Commandes;

import Model.Hibernate.Commande;
import Model.Hibernate.Contient;
import Model.Hibernate.Materiels;
import Model.Repository.ContientRepository;
import Model.Repository.MaterielsRepository;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
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
        
        colNumSerie.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getMateriels().getNumSerie()).asObject());
        colQuantite.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantiteCommande()).asObject());
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMateriels().getNomMateriels()));
        colPrixUnitaire.setCellValueFactory(c -> new SimpleFloatProperty(c.getValue().getMateriels().getPrixMateriels()).asObject());
        colPrixTotal.setCellValueFactory(c -> new SimpleFloatProperty(c.getValue().getMateriels().getPrixMateriels() * c.getValue().getQuantiteCommande()).asObject());
        
        

        spQuantite.getEditor().textProperty().addListener((ov, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            } catch (final NumberFormatException ex) {
                spQuantite.getEditor().setText(oldValue);
            }
        });
        
        loadContients();
        
        
    }

    public void handlePost(Event event) {
        ContientRepository.postContient(new Contient(commandeCourrant, listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem()), spQuantite.getValue()));
        this.loadContients();
    }

    public void handleCanceled(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handlePrint(Event event) {

    }

    public void handleDelete(Event event) {
        if(!liste.getSelectionModel().isEmpty()){
            ContientRepository.deleteContient(liste.getSelectionModel().getSelectedItem());
            this.loadContients();
        }
    }
    private void loadContients(){
        listeMateriels = new HashMap<>();
        ObservableList<String> lstCmbMateriels = FXCollections.observableArrayList();
        List<Materiels> l = MaterielsRepository.getMaterielsDispo();
        l.forEach((m) -> {
            lstCmbMateriels.add(m.getNumSerie() + " : " + m.getNomMateriels());
            listeMateriels.put(m.getNumSerie() + " : " + m.getNomMateriels(), m);
        });
        cbMeuble.setItems(lstCmbMateriels);
        
        ObservableList<Contient> lis = FXCollections.observableArrayList();
        lis.clear();
        ContientRepository.getContient(commandeCourrant).forEach((t) -> {
            lis.add(t);
        });
        
        liste.setItems(lis);
        
        
        if (cbMeuble.getItems().size() != 0) {
            cbMeuble.getSelectionModel().select(0);
            spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem()).getQuantiteStock()));
        }
        cbMeuble.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem()).getQuantiteStock()));
        });
    }
    @FXML
    ComboBox<String> cbMeuble;
    @FXML
    Spinner<Integer> spQuantite;
    @FXML
    TableView<Contient> liste;
    @FXML
    TableColumn<Contient, String> colNom;
    @FXML
    TableColumn<Contient, Integer> colNumSerie;
    @FXML
    TableColumn<Contient, Integer> colQuantite;
    @FXML
    TableColumn<Contient, Float> colPrixUnitaire;
    @FXML
    TableColumn<Contient, Float> colPrixTotal;
    @FXML
    Label lbTotal;

}
