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
import org.hibernate.exception.ConstraintViolationException;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class FormulaireContientController implements Initializable {

    private Commande commandeCourrant = null;
    private HashMap<String, Materiels> listeMateriels;
    private ObservableList<String> lstCmbMateriels;
    private ObservableList<Contient> lis;
    private float total = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listeMateriels = new HashMap<>();
        commandeCourrant = CommandesController.getCommandeCourrant();
        lstCmbMateriels = FXCollections.observableArrayList();
        lis = FXCollections.observableArrayList();
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

        cbMeuble.getSelectionModel().selectedItemProperty().addListener((ov, t, t1) -> {
            if (!listeMateriels.isEmpty() && !cbMeuble.getItems().isEmpty()) {
                spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem()).getQuantiteStock()));
            }
        });
        cbMeuble.setItems(lstCmbMateriels);
        liste.setItems(lis);
        loadContients();
    }

    public void handlePost(Event event) {
        if (!cbMeuble.getItems().isEmpty()) {
            Materiels m = listeMateriels.get(cbMeuble.getSelectionModel().getSelectedItem());
            try {
                ContientRepository.postContient(new Contient(commandeCourrant, m, spQuantite.getValue()));
                int oldStock = m.getQuantiteStock();
                m.setQuantiteStock(oldStock - spQuantite.getValue());
                MaterielsRepository.putMateriels(m);
            } catch (ConstraintViolationException e) {
                Contient c = ContientRepository.getContient(commandeCourrant, m);
                int qte = c.getQuantiteCommande();
                qte += spQuantite.getValue();
                c.setQuantiteCommande(qte);
                ContientRepository.putContient(c);
                int oldStock = m.getQuantiteStock();
                m.setQuantiteStock(oldStock - spQuantite.getValue());
                MaterielsRepository.putMateriels(m);
            }
            this.loadContients();
        }
    }

    public void handleCanceled(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handlePrint(Event event) {

    }

    public void handleDelete(Event event) {
        if (!liste.getSelectionModel().isEmpty()) {
            Contient c = liste.getSelectionModel().getSelectedItem();
            Materiels m = c.getMateriels();
            int oldStock = m.getQuantiteStock();
            m.setQuantiteStock(oldStock + c.getQuantiteCommande());
            MaterielsRepository.putMateriels(m);
            ContientRepository.deleteContient(c);
            this.loadContients();
        }
    }

    private void loadContients() {
        
        listeMateriels.clear();
        lstCmbMateriels.clear();
        MaterielsRepository.getMaterielsDispo().forEach((m) -> {
            lstCmbMateriels.add(m.getNumSerie() + " : " + m.getNomMateriels());
            listeMateriels.put(m.getNumSerie() + " : " + m.getNomMateriels(), m);
        });
        lis.clear();
        this.total = 0;
        ContientRepository.getContient(commandeCourrant).forEach((t) -> {
            lis.add(t);
            this.total += t.getMateriels().getPrixMateriels() * t.getQuantiteCommande();
        });
        lbTotal.setText(String.valueOf(total));
        if (!lstCmbMateriels.isEmpty()) {
            cbMeuble.getSelectionModel().select(0);
            spQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, listeMateriels.get(cbMeuble.getItems().get(0)).getQuantiteStock()));
        }
        
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
