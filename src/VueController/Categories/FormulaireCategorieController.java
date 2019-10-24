/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueController.Categories;

import Model.Hibernate.Categorie;
import Model.Repository.CategorieRepository;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
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
public class FormulaireCategorieController implements Initializable {

    private boolean modify = false;
    private Categorie categorieModif = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (CategoriesController.getCategorieModif() != null) {
            modify = true;
            categorieModif = CategoriesController.getCategorieModif();
            txtCategorie.setText(categorieModif.getCategorie());
            btnConfirmed.setText("Modifier");
        }
    }

    public void handleConfirmed(ActionEvent event) {
        if (modify) {
            CategorieRepository.putCategorie(categorieModif, new Categorie(txtCategorie.getText()));
        }else{
            CategorieRepository.postCategorie(new Categorie(txtCategorie.getText()));
        }

        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void handleCanceled(Event event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    @FXML
    JFXButton btnConfirmed;
    @FXML
    TextField txtCategorie;
}
