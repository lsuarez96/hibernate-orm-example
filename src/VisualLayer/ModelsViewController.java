/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Modelos;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
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
public class ModelsViewController implements Initializable {

    @FXML
    private TableView<Modelos> table;
    @FXML
    private TableColumn<Modelos, String> modelsCol;
    @FXML
    private TableColumn<Modelos, String> brandsCol;

    @FXML
    private TableColumn<Modelos, String> tarNCol;
    @FXML
    private TableColumn<Modelos, String> tarECol;

    private Stage stage;
    private MainClass main;
    private Modelos itemInstance;
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
        modelsCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNombreModelo()));
        brandsCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getModeloIdMarca().getNombreMarca()));
        tarNCol.setCellValueFactory((TableColumn.CellDataFeatures<Modelos, String> cellData) -> (ObservableValue) new SimpleStringProperty("$" + cellData.getValue().getModeloIdTar().getTarifaNormal()));
        tarECol.setCellValueFactory((TableColumn.CellDataFeatures<Modelos, String> cellData) -> (ObservableValue) new SimpleStringProperty("$" + cellData.getValue().getModeloIdTar().getTarifaEspecial()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Modelos> tableItems = ServicesLocator.getServicesLocatorInstance().getModeloServices().findModelosEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
        selectLanguage();

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

        }
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    private void setItemInstance(Modelos itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddorModifyModel(null);
        stage.hide();
        stage.close();
        List<Modelos> tableItems = ServicesLocator.getServicesLocatorInstance().getModeloServices().findModelosEntities();
        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onElimPressed() throws IllegalOrphanException, NonexistentEntityException {
        if (itemInstance != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getModeloServices().destroy(itemInstance.getId());
                    List<Modelos> tableItems = ServicesLocator.getServicesLocatorInstance().getModeloServices().findModelosEntities();
                    table.setItems(FXCollections.observableList(tableItems));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();
        }
    }

    public void onAddBrand() {
        main.loadAddBrand();
        List<Modelos> tableItems = ServicesLocator.getServicesLocatorInstance().getModeloServices().findModelosEntities();
        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onModifyPressed() {
        if (itemInstance != null) {
            main.loadAddorModifyModel(itemInstance);
            stage.hide();
            stage.close();
        }
    }

    public void onAddTarif() {
        main.loadAddModifyTariff();
    }

    private void selectLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.add.setText(prop.getProperty("tag_add_button"));
        this.brandsCol.setText(prop.getProperty("tag_marca"));
        this.del.setText(prop.getProperty("tag_del_button"));
        this.mod.setText(prop.getProperty("tag_modify_button"));
        this.modelsCol.setText(prop.getProperty("tag_modelo"));
        this.tarECol.setText(prop.getProperty("tag_special_tar"));
        this.tarNCol.setText(prop.getProperty("tag_normal_tar"));
    }
}
