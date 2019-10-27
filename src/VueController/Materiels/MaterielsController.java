/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Materiels;

import Model.Hibernate.Materiels;
import Model.Repository.MaterielsRepository;
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
public class MaterielsController implements Initializable {

    private ObservableList<Materiels> materiels;
    private static Materiels materielModif = null;

    public static Materiels getMaterielModif() {
        return materielModif;
    }

    public static void setMaterielModif(Materiels materielModif) {
        MaterielsController.materielModif = materielModif;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.visibleProperty().addListener((o, oldValue, newValue) -> {
            if (newValue) {
                this.loadMateriels();
            }
        });
        liste.setPlaceholder(new Label(""));
        materiels = FXCollections.observableArrayList();
        colNumSerie.setCellValueFactory(new PropertyValueFactory<>("numSerie"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomMateriels"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixMateriels"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));
        colCategorie.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getCategorie().getCategorie()));
        this.loadMateriels();
    }

    public void handlePost(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FormulaireMateriel.fxml"));
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
                this.loadMateriels();
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
                        this.deleteMateriel();
                        this.loadMateriels();
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
            MaterielsController.setMaterielModif(liste.getSelectionModel().getSelectedItem());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FormulaireMateriel.fxml"));
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
                    MaterielsController.setMaterielModif(null);
                    this.loadMateriels();
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

    private void loadMateriels() {
        materiels.clear();
        for (Materiels c : MaterielsRepository.getMateriels()) {
            materiels.add(c);
        }
        FilteredList<Materiels> filteredData = new FilteredList<>(materiels, c -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(materiel -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if ((materiel.getNumSerie() + "").toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (materiel.getNomMateriels().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if ((materiel.getPrixMateriels() + "").toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if ((materiel.getQuantiteStock() + "").toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (materiel.getCategorie().getCategorie().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Materiels> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(liste.comparatorProperty());
        liste.setItems(sortedData);
    }

    private void deleteMateriel() {
        MaterielsRepository.deleteMateriels(liste.getSelectionModel().getSelectedItem());
    }
    @FXML
    AnchorPane root;
    @FXML
    TextField txtSearch;
    @FXML
    ImageView imgSearch;
    @FXML
    TableView<Materiels> liste;
    @FXML
    TableColumn<Materiels, Integer> colNumSerie;
    @FXML
    TableColumn<Materiels, String> colNom;
    @FXML
    TableColumn<Materiels, String> colPrix;
    @FXML
    TableColumn<Materiels, String> colQuantite;
    @FXML
    TableColumn<Materiels, String> colCategorie;
}
