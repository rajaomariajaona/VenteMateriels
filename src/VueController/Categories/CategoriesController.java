/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Categories;

import Model.Hibernate.Categorie;
import Model.Repository.CategorieRepository;
import VueController.ConfirmationController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class CategoriesController implements Initializable {

    private ObservableList<Categorie> categories;
    private static Categorie categorieModif = null;

    public static Categorie getCategorieModif() {
        return categorieModif;
    }

    public static void setCategorieModif(Categorie categorieModif) {
        CategoriesController.categorieModif = categorieModif;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.visibleProperty().addListener((o,oldValue, newValue) -> {
           if(newValue){
           this.loadCategories();
           }
        });
        liste.setPlaceholder(new Label(""));
        categories = FXCollections.observableArrayList();
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        this.loadCategories();
    }

    public void handlePost(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FormulaireCategorie.fxml"));
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
                this.loadCategories();
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
                        this.deleteCategorie();
                        this.loadCategories();
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
            CategoriesController.setCategorieModif(liste.getSelectionModel().getSelectedItem());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FormulaireCategorie.fxml"));
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
                    CategoriesController.setCategorieModif(null);
                    this.loadCategories();
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

    private void loadCategories() {
        categories.clear();
        for (Categorie c : CategorieRepository.getCategorie()) {
            categories.add(c);
        }
        FilteredList<Categorie> filteredData = new FilteredList<>(categories, c -> true);
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(categorie -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (categorie.getCategorie().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                return false;
            });
        });
        SortedList<Categorie> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(liste.comparatorProperty());
        liste.setItems(sortedData);
    }

    private void deleteCategorie() {
        CategorieRepository.deleteCategorie(liste.getSelectionModel().getSelectedItem());
    }
    @FXML
    AnchorPane root;
    @FXML
    TextField txtSearch;
    @FXML
    ImageView imgSearch;
    @FXML
    TableView<Categorie> liste;
    @FXML
    TableColumn<Categorie, Integer> colCategorie;
}
