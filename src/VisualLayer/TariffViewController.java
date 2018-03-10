/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Tarifa;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import UtilsLayer.MoneyFormatter;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class TariffViewController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Tarifa itemInstance;

    @FXML
    private TableView<Tarifa> table;

    @FXML
    private TableColumn<Tarifa, String> normalCol;
    @FXML
    private TableColumn<Tarifa, String> speciallCol;

    @FXML
    private Button add;
    @FXML
    private Button del;
    @FXML
    private Button mod;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        normalCol.setCellValueFactory((TableColumn.CellDataFeatures<Tarifa, String> cellData) -> (ObservableValue) new SimpleStringProperty(MoneyFormatter.conversion(cellData.getValue().getTarifaNormal())));
        speciallCol.setCellValueFactory((TableColumn.CellDataFeatures<Tarifa, String> cellData) -> (ObservableValue) new SimpleStringProperty(MoneyFormatter.conversion(cellData.getValue().getTarifaEspecial())));

        List<Tarifa> tableItems = ServicesLocator.getServicesLocatorInstance().getTarifaServices().findTarifaEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
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
        if (!main.getLoggedRoles().contains(LoggedRole.MNG)) {
            add.setDisable(true);
            del.setDisable(true);
            mod.setDisable(true);
        }
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Tarifa itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onElimPressed() throws IllegalOrphanException, NonexistentEntityException {
        if (itemInstance != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getTarifaServices().destroy(itemInstance.getId());
                    List<Tarifa> tableItems = ServicesLocator.getServicesLocatorInstance().getTarifaServices().findTarifaEntities();
                    table.setItems(FXCollections.observableList(tableItems));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();
        }
    }

    public void onAddPress() {
        main.loadAddModifyTariff();
        stage.hide();
        stage.close();
//        List<Tarifas> tableItems = ServicesLocator.getTarifasServices().retriveAllTariffs();
//        table.setItems(FXCollections.observableList(tableItems));
    }

    private void setLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.add.setText(prop.getProperty("tag_add_button"));
        this.del.setText(prop.getProperty("tag_del_button"));
        this.mod.setText(prop.getProperty("tag_modify_button"));
        this.normalCol.setText(prop.getProperty("tag_normal_tar"));
        this.speciallCol.setText(prop.getProperty("tag_special_tar"));
    }
}
