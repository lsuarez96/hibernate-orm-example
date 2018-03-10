/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Marcas;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddBrandController implements Initializable {

    @FXML
    private TextField brandTF;
    @FXML
    private Label brandlb;
    @FXML
    private Button accept;

    private Stage stage;
    private MainClass main;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        selectLanguage();
    }

    public void onAcceptPressed() {
        String b = brandTF.getText().toLowerCase();
        // Marcas temp = ServicesLocator.getServicesLocatorInstance().getMarcaServices().findMarcaByName(b);
        if (!b.isEmpty()) {
            Marcas m = new Marcas();
            m.setNombreMarca(b);
            try {
                ServicesLocator.getServicesLocatorInstance().getMarcaServices().create(m);
            } catch (Exception ex) {
                // JOptionPane.showMessageDialog(null, ErrorMessages.INSERTION_ERROR);
                new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR, ButtonType.OK).showAndWait();
                Logger.getLogger(AddBrandController.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.loadBrandsView();
            stage.hide();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            //JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
        }
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    private void selectLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.brandlb.setText(prop.getProperty("tag_marca"));
        this.accept.setText(prop.getProperty("tag_accept"));

    }
}
