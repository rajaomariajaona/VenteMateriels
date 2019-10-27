/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Commandes;

import Model.Hibernate.Commande;
import Model.Repository.ClientRepository;
import Model.Repository.CommandeRepository;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class FormulaireCommandeController implements Initializable {

    private boolean modify = false;
    private Commande commandeModif = null;
    private HashMap<String, Integer> nomClients;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        restrictDatePicker(datePicker, LocalDate.of(2000, Month.JANUARY, 1), LocalDate.now());
        
        if (CommandesController.getCommandeModif() != null) {
            modify = true;
            commandeModif = CommandesController.getCommandeModif();
        }
        nomClients = new HashMap<>();
        ObservableList<String> clients = FXCollections.observableArrayList();
        
        ClientRepository.getClient().forEach((p) -> {
            nomClients.put(p.getNomClient() + " " + p.getPrenomClient(), p.getNumClient());
            clients.add(p.getNomClient() + " " + p.getPrenomClient());
        });
        cbClient.setItems(clients);
        if (modify) {
            cbClient.getSelectionModel().select(commandeModif.getClient().getNomClient() + " " + commandeModif.getClient().getPrenomClient());
            datePicker.setValue(new java.sql.Date(commandeModif.getDateCommande().getTime()).toLocalDate());
            btnConfirmed.setText("Modifier");
        } else {
            cbClient.getSelectionModel().select(0);
            datePicker.setValue(LocalDate.now());
        }
    }

    private void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #95a5a6;");
                        } else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #95a5a6;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    public void handleConfirmed(ActionEvent event) {
        if (modify) {
            commandeModif.setClient(ClientRepository.getClient(nomClients.get(cbClient.getSelectionModel().getSelectedItem()).intValue()));
            commandeModif.setDateCommande(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            CommandeRepository.putCommande(commandeModif);
        } else {
            Commande n;
            n = new Commande(ClientRepository.getClient(nomClients.get(cbClient.getSelectionModel().getSelectedItem()).intValue()), Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), false);
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
    ComboBox<String> cbClient;
    @FXML
    DatePicker datePicker;
}
