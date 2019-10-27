/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Clients;

import Model.Hibernate.Client;
import Model.Repository.ClientRepository;
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
public class ClientsController implements Initializable {

    private ObservableList<Client> clients;
    private static Client clientModif = null;

    public static Client getClientModif() {
        return clientModif;
    }

    public static void setClientModif(Client clientModif) {
        ClientsController.clientModif = clientModif;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.visibleProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                this.loadClients();
            }
        });
        liste.setPlaceholder(new Label(""));
        clients = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<>("numClient"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telClient"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresseClient"));
        colProvince.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProvince().getProvince()));
        this.loadClients();
    }

    public void handlePost(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FormulaireClient.fxml"));
            BoxBlur blur = new BoxBlur(3, 3, 3);
            Stage dialog = new Stage(StageStyle.TRANSPARENT);
            dialog.initOwner(Main.Main.getPrimaryStage());
            dialog.initModality(Modality.WINDOW_MODAL);

            double centerXPosition = Main.Main.getPrimaryStage().getX() + Main.Main.getPrimaryStage().getWidth() / 2d;
            double centerYPosition = Main.Main.getPrimaryStage().getY() + Main.Main.getPrimaryStage().getHeight() / 2d;
            dialog.setOnShowing(ev -> dialog.hide());

            Node n = ((Node) event.getSource());
            dialog.setOnShown((t) -> {
                n.getScene().getRoot().setEffect(blur);
                dialog.setX(centerXPosition - dialog.getWidth() / 2d);
                dialog.setY(centerYPosition - dialog.getHeight() / 2d);
                dialog.show();
            });
            dialog.setOnHidden((t) -> {
                n.getScene().getRoot().setEffect(null);
                this.loadClients();
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

                double centerXPosition = Main.Main.getPrimaryStage().getX() + Main.Main.getPrimaryStage().getWidth() / 2d;
                double centerYPosition = Main.Main.getPrimaryStage().getY() + Main.Main.getPrimaryStage().getHeight() / 2d;
                dialog.setOnShowing(ev -> dialog.hide());

                Node n = ((Node) event.getSource());
                dialog.setOnShown((t) -> {
                    n.getScene().getRoot().setEffect(blur);
                    dialog.setX(centerXPosition - dialog.getWidth() / 2d);
                    dialog.setY(centerYPosition - dialog.getHeight() / 2d);
                    dialog.show();
                });
                dialog.setOnHidden((t) -> {
                    n.getScene().getRoot().setEffect(null);
                    if (ConfirmationController.getResult() == 1) {
                        this.deleteClient();
                        this.loadClients();
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
            ClientsController.setClientModif(liste.getSelectionModel().getSelectedItem());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FormulaireClient.fxml"));
                BoxBlur blur = new BoxBlur(3, 3, 3);
                Stage dialog = new Stage(StageStyle.TRANSPARENT);
                dialog.initOwner(Main.Main.getPrimaryStage());
                dialog.initModality(Modality.WINDOW_MODAL);

                double centerXPosition = Main.Main.getPrimaryStage().getX() + Main.Main.getPrimaryStage().getWidth() / 2d;
                double centerYPosition = Main.Main.getPrimaryStage().getY() + Main.Main.getPrimaryStage().getHeight() / 2d;
                dialog.setOnShowing(ev -> dialog.hide());

                Node n = ((Node) event.getSource());
                dialog.setOnShown((t) -> {
                    n.getScene().getRoot().setEffect(blur);
                    dialog.setX(centerXPosition - dialog.getWidth() / 2d);
                    dialog.setY(centerYPosition - dialog.getHeight() / 2d);
                    dialog.show();
                });
                dialog.setOnHidden((t) -> {
                    n.getScene().getRoot().setEffect(null);
                    ClientsController.setClientModif(null);
                    this.loadClients();
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

    private void loadClients() {
        clients.clear();
        for (Client c : ClientRepository.getClient()) {
            clients.add(c);
        }
        FilteredList<Client> filteredData = new FilteredList<>(clients, c -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (client.getNumClient().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (client.getNomClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (client.getPrenomClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (client.getAdresseClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (client.getTelClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (client.getProvince().getProvince().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(liste.comparatorProperty());
        liste.setItems(sortedData);
    }

    private void deleteClient() {
        ClientRepository.deleteClient(liste.getSelectionModel().getSelectedItem());
    }
    @FXML
    AnchorPane root;
    @FXML
    TextField txtSearch;

    @FXML
    ImageView imgSearch;
    @FXML
    TableView<Client> liste;
    @FXML
    TableColumn<Client, Integer> colId;
    @FXML
    TableColumn<Client, String> colNom;
    @FXML
    TableColumn<Client, String> colPrenom;
    @FXML
    TableColumn<Client, String> colTel;
    @FXML
    TableColumn<Client, String> colAdresse;
    @FXML
    TableColumn<Client, String> colProvince;
}
