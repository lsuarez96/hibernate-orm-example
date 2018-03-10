/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class UsersViewController implements Initializable {

    private Stage stage;
    private MainClass main;

    private Usuarios itemInstance;
    @FXML
    private TableView<Usuarios> table;
    @FXML
    private TableColumn<Usuarios, String> userCol;
    @FXML
    private TableColumn<Usuarios, String> nameCol;
    @FXML
    private TableColumn<Usuarios, String> lnameCol;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userCol.setCellValueFactory((TableColumn.CellDataFeatures<Usuarios, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getUsuario()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Usuarios, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNombre()));
        lnameCol.setCellValueFactory((TableColumn.CellDataFeatures<Usuarios, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getApellidos()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Usuarios> tableItems = sl.getUsuarioServices().findUsuariosEntities();
        table.setItems(FXCollections.observableList(tableItems));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setItemInstance(newValue));
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.onCloseRequestProperty().set(closeAction());
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
    public void setItemInstance(Usuarios itemInstance) {
        this.itemInstance = itemInstance;
    }

    public void onAddPressed() {
        main.loadAddUser(null);
        stage.hide();
        stage.close();
    }

    public void modifyUser() {
        if (itemInstance != null) {
            main.loadAddUser(itemInstance);
            stage.hide();
            stage.close();
        }
    }

    public void onElimPressed() throws IllegalOrphanException, NonexistentEntityException, NonexistentEntityException, NonexistentEntityException, NonexistentEntityException {
        if (itemInstance != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getUsuarioServices().destroy(itemInstance.getId());
                    List<Usuarios> tableItems = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUsuariosEntities();
                    table.setItems(FXCollections.observableList(tableItems));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();

                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();

        }
    }

    public EventHandler closeAction() {

        EventHandler eh = (EventHandler) (Event event) -> {
            main.loadRolUserView();
            stage.hide();
            stage.close();
        };
        return eh;
    }
}
