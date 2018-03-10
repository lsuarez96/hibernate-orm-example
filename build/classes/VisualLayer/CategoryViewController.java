/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Categorias;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class CategoryViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Categorias itemInstance;
    @FXML
    private TableView<Categorias> table;
    @FXML
    private TableColumn<Categorias, String> catCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catCol.setCellValueFactory((TableColumn.CellDataFeatures<Categorias, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getTipoCategoria()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Categorias> tableItems = sl.getCategoriaServices().findCategoriasEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
        setlanguage(MainClass.language);
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

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Categorias itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddCat();
    }

    public void onElimPressed() {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getCategoriaServices().destroy(itemInstance.getId());
                } catch (IllegalOrphanException | NonexistentEntityException ex) {
                    JOptionPane.showMessageDialog(null, ErrorMessages.ELIMINATION_ERROR);
                    Logger.getLogger(CategoryViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<Categorias> tableItems = ServicesLocator.getServicesLocatorInstance().getCategoriaServices().findCategoriasEntities();
                table.setItems(FXCollections.observableList(tableItems));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla");
        }
    }

    private void setlanguage(String lang) {
        LanguageSelector prop = new LanguageSelector(lang);
        this.catCol.setText(prop.getProperty("tag_category"));

    }

}
