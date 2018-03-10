/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Pais;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
public class CountryViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Pais itemInstance;

    @FXML
    private TableView<Pais> table;
    @FXML
    private TableColumn<Pais, String> countryCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryCol.setCellValueFactory((TableColumn.CellDataFeatures<Pais, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNombrePais()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Pais> tableItems = sl.getPaisServices().findPaisEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        countryCol.setText(prop.getProperty("tag_country"));
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
    public void setItemInstance(Pais itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddCountry();
        stage.hide();
        stage.close();
//        List<Pais> tableItems = ServicesLocator.getPaisServices().retriveAllCountries();
//        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onElimPressed() throws IllegalOrphanException, NonexistentEntityException {
        if (itemInstance != null) {
            int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getPaisServices().destroy(itemInstance.getId());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, ErrorMessages.ELIMINATION_ERROR);
                }
                List<Pais> tableItems = ServicesLocator.getServicesLocatorInstance().getPaisServices().findPaisEntities();
                table.setItems(FXCollections.observableList(tableItems));
            }
        }
    }
}
