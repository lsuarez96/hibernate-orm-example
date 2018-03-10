/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Autos;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class CarsViewController implements Initializable {

    @FXML
    private TableView<Autos> table;
    @FXML
    private TableColumn<Autos, String> plateCol;
    @FXML
    private TableColumn<Autos, String> situationCol;
    @FXML
    private TableColumn<Autos, String> modelCol;
    @FXML
    private TableColumn<Autos, String> brandCol;
    @FXML
    private TableColumn<Autos, Float> kmCol;
    @FXML
    private TableColumn<Autos, String> colourCol;

    private Stage stage;
    private MainClass main;
    private Autos selectedItem;
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
    @SuppressWarnings("All")
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        plateCol.setCellValueFactory((TableColumn.CellDataFeatures<Autos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getChapa()));
        situationCol.setCellValueFactory((TableColumn.CellDataFeatures<Autos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdSituacionAuto().getTipoSituacion()));
        modelCol.setCellValueFactory((TableColumn.CellDataFeatures<Autos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdModeloAuto().getNombreModelo()));
        brandCol.setCellValueFactory((TableColumn.CellDataFeatures<Autos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdModeloAuto().getModeloIdMarca().getNombreMarca()));
        kmCol.setCellValueFactory((TableColumn.CellDataFeatures<Autos, Float> cellData) -> (ObservableValue) new SimpleFloatProperty(cellData.getValue().getKilometros()));
        colourCol.setCellValueFactory((TableColumn.CellDataFeatures<Autos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getColor()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Autos> tableItems = sl.getAutosServices().findAutosEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onItemSelected(newValue));
        setLang();

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

    public void onItemSelected(Autos a) {
        this.selectedItem = a;
    }

    public void onAddButtonPressed() {
        main.loadAddorModifyCar(null);
        stage.hide();
        stage.close();
    }

    public void onModifyButtonPressed() {
        if (selectedItem != null) {
            main.loadAddorModifyCar(selectedItem);
            stage.hide();
            stage.close();
            List<Autos> tableItems = ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities();
            table.setItems(FXCollections.observableList(tableItems));
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el auto que desea modificar");
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public void onElimButtonPressed() throws NonexistentEntityException {
        if (selectedItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getAutosServices().destroy(selectedItem.getId());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, new LanguageSelector(MainClass.language).getProperty("tag_sel_error"), ButtonType.OK).showAndWait();
            //  JOptionPane.showMessageDialog(null, "Debe seleccionar el auto que desea eliminar");
        }
        List<Autos> tableItems = ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities();
        table.setItems(FXCollections.observableList(tableItems));
    }

    public void onMenuItem1Pressed() {
        main.loadAddorModifyModel(null);

    }

    public void onMenuItem2Pressed() {
        main.loadAddBrand();
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        add.setText(prop.getProperty("tag_add_button"));
        mod.setText(prop.getProperty("tag_modify_button"));
        del.setText(prop.getProperty("tag_del_button"));
        this.brandCol.setText(prop.getProperty("tag_marca"));
        this.colourCol.setText(prop.getProperty("tag_color"));
        this.kmCol.setText(prop.getProperty("tag_km"));
        this.modelCol.setText(prop.getProperty("tag_modelo"));
        this.plateCol.setText(prop.getProperty("tag_plate"));
        this.situationCol.setText(prop.getProperty("tag_situation"));
    }
}
