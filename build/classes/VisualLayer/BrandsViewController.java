/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Marcas;
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
public class BrandsViewController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TableView<Marcas> table;
    @FXML
    private TableColumn<Marcas, String> brandCol;
    @FXML
    private Button add;
    @FXML
    private Button del;
    private Marcas itemInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brandCol.setCellValueFactory((TableColumn.CellDataFeatures<Marcas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNombreMarca()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Marcas> tableItems = sl.getMarcaServices().findMarcasEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
        setLanguage(MainClass.language);

    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return the main
     */
    public MainClass getMain() {
        return main;
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
    public void setItemInstance(Marcas itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddBrand();
        stage.hide();
        stage.close();
    }

    public void onElimPressed() throws IllegalOrphanException, NonexistentEntityException {
        if (itemInstance != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getMarcaServices().destroy(itemInstance.getId());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();

                    //JOptionPane.showMessageDialog(null, ErrorMessages.ELIMINATION_ERROR);
                }
                List<Marcas> tableItems = ServicesLocator.getServicesLocatorInstance().getMarcaServices().findMarcasEntities();
                table.setItems(FXCollections.observableList(tableItems));
            }
        } else {
            new Alert(Alert.AlertType.ERROR, new LanguageSelector(MainClass.language).getProperty("tag_sel_error"), ButtonType.OK).showAndWait();
            //JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla");
        }
    }

    private void setLanguage(String lang) {
        LanguageSelector prop = new LanguageSelector(lang);
        this.brandCol.setText(prop.getProperty("tag_marca"));
        this.add.setText(prop.getProperty("tag_add_button"));
        this.del.setText(prop.getProperty("tag_del_button"));
    }

}
