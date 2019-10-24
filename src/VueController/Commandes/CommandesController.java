/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Commandes;

import Model.Hibernate.Commande;
import Model.Repository.CommandeRepository;
import VueController.ConfirmationController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author snowden
 */
public class CommandesController implements Initializable {

    private ObservableList<Commande> commandes;
    private static Commande commandeModif = null;

    public static Commande getCommandeModif() {
        return commandeModif;
    }

    public static void setCommandeModif(Commande commandeModif) {
        CommandesController.commandeModif = commandeModif;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.visibleProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                this.loadCommandes();
            }
        });
        liste.setPlaceholder(new Label(""));
        commandes = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<>("numCommande"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomCommande"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomCommande"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telCommande"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresseCommande"));
        colProvince.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProvince().getProvince()));
        this.loadCommandes();
    }

    public void handlePost(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FormulaireCommande.fxml"));
            BoxBlur blur = new BoxBlur(3, 3, 3);
            Stage dialog = new Stage(StageStyle.TRANSPARENT);
            dialog.initOwner(Main.Main.getPrimaryStage());
            dialog.initModality(Modality.WINDOW_MODAL);

            Node n = ((Node) event.getSource());
            dialog.setOnShown((t) -> {
                n.getScene().getRoot().setEffect(blur);
            });
            dialog.setOnHidden((t) -> {
                n.getScene().getRoot().setEffect(null);
                this.loadCommandes();
            });

            Scene sc = new Scene(root);
            sc.setFill(Color.TRANSPARENT);
            dialog.setScene(sc);
            dialog.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleDelete(MouseEvent event) {
        if (!liste.getSelectionModel().isEmpty()) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/VueController/Confirmation.fxml"));
                BoxBlur blur = new BoxBlur(3, 3, 3);
                Stage dialog = new Stage(StageStyle.TRANSPARENT);
                dialog.initOwner(Main.Main.getPrimaryStage());
                dialog.initModality(Modality.WINDOW_MODAL);

                Node n = ((Node) event.getSource());
                dialog.setOnShown((t) -> {
                    n.getScene().getRoot().setEffect(blur);
                });
                dialog.setOnHidden((t) -> {
                    n.getScene().getRoot().setEffect(null);
                    if (ConfirmationController.getResult() == 1) {
                        this.deleteCommande();
                        this.loadCommandes();
                    }
                });

                Scene sc = new Scene(root);
                sc.setFill(Color.TRANSPARENT);
                dialog.setScene(sc);
                dialog.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void handlePatch(MouseEvent event) {
        if (!liste.getSelectionModel().isEmpty()) {
            CommandesController.setCommandeModif(liste.getSelectionModel().getSelectedItem());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FormulaireCommande.fxml"));
                BoxBlur blur = new BoxBlur(3, 3, 3);
                Stage dialog = new Stage(StageStyle.TRANSPARENT);
                dialog.initOwner(Main.Main.getPrimaryStage());
                dialog.initModality(Modality.WINDOW_MODAL);

                Node n = ((Node) event.getSource());
                dialog.setOnShown((t) -> {
                    n.getScene().getRoot().setEffect(blur);
                });
                dialog.setOnHidden((t) -> {
                    n.getScene().getRoot().setEffect(null);
                    CommandesController.setCommandeModif(null);
                    this.loadCommandes();
                });

                Scene sc = new Scene(root);
                sc.setFill(Color.TRANSPARENT);
                dialog.setScene(sc);
                dialog.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadCommandes() {
        commandes.clear();
        for (Commande c : CommandeRepository.getCommande()) {
            commandes.add(c);
        }
        FilteredList<Commande> filteredData = new FilteredList<>(commandes, c -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(commande -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (commande.getNumCommande().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (commande.getNomCommande().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (commande.getPrenomCommande().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (commande.getAdresseCommande().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (commande.getTelCommande().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (commande.getProvince().getProvince().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Commande> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(liste.comparatorProperty());
        liste.setItems(sortedData);
    }

    private void deleteCommande() {
        CommandeRepository.deleteCommande(liste.getSelectionModel().getSelectedItem());
    }
    @FXML
    AnchorPane root;
    @FXML
    TextField txtSearch;

    @FXML
    ImageView imgSearch;
    @FXML
    TableView<Commande> liste;
    @FXML
    TableColumn<Commande, Integer> colId;
    @FXML
    TableColumn<Commande, String> colNom;
    @FXML
    TableColumn<Commande, String> colPrenom;
    @FXML
    TableColumn<Commande, String> colTel;
    @FXML
    TableColumn<Commande, String> colAdresse;
    @FXML
    TableColumn<Commande, String> colProvince;
}
