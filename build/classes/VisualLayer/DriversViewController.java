/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Choferes;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import java.net.URL;
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
public class DriversViewController implements Initializable {

    @FXML
    private TableView<Choferes> table;
    @FXML
    private TableColumn<Choferes, String> idCol;
    @FXML
    private TableColumn<Choferes, String> nameCol;
    @FXML
    private TableColumn<Choferes, String> lastNameCol;
    @FXML
    private TableColumn<Choferes, String> categoryCol;
    @FXML
    private TableColumn<Choferes, String> addressCol;

    private Stage stage;
    private MainClass main;
    private Choferes itemInstance;

    @FXML
    private Button add;
    @FXML
    private Button mod;
    @FXML
    private Button del;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idCol.setCellValueFactory((TableColumn.CellDataFeatures<Choferes, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNumeroId()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Choferes, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNombre()));
        lastNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Choferes, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getApellidos()));
        categoryCol.setCellValueFactory((TableColumn.CellDataFeatures<Choferes, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getCategoria().getTipoCategoria()));
        addressCol.setCellValueFactory((TableColumn.CellDataFeatures<Choferes, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getDireccion()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        table.setItems(FXCollections.observableList(sl.getChoferServices().findChoferesEntities()));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onItemSelected(newValue));
        setLanguage(MainClass.language);
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
//        try {
//            Task task = new Task<Void>() {
//                @Override
//                @SuppressWarnings("SleepWhileInLoop")
//                public void run() {
//                    table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
//                }
//
//                @Override
//                protected Void call() throws Exception {
//                    return null;
//                }
//            };
//
//            ScheduledService<Void> se = new ScheduledService() {
//                @Override
//                protected Task<Void> createTask() {
//                    return task;
//                }
//            };
//            se.setPeriod(Duration.INDEFINITE);
//            se.setDelay(Duration.ONE);
//            se.setRestartOnFailure(true);
//            se.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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

    public void onItemSelected(Choferes c) {
        itemInstance = c;
    }

    public void onAddPressed() {
        main.loadAddorModifyDriver(null);
        stage.hide();
        stage.close();
        //table.setItems(FXCollections.observableList(ServicesLocator.getChoferServices().retriveAllDrivers()));
    }

    public void onModifyPressed() {
        if (itemInstance != null) {
            main.loadAddorModifyDriver(itemInstance);
            stage.hide();
            stage.close();
            //table.setItems(FXCollections.observableList(ServicesLocator.getServicesLocatorInstance().getChoferServices().findChoferesEntities()));
        }
    }

    public void onElimPressed() throws NonexistentEntityException {
        if (itemInstance != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getChoferServices().destroy(itemInstance.getId());
                    table.setItems(FXCollections.observableList(ServicesLocator.getServicesLocatorInstance().getChoferServices().findChoferesEntities()));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();
        }
    }

    private void setLanguage(String lang) {
        LanguageSelector prop = new LanguageSelector(lang);
        this.add.setText(prop.getProperty("tag_add_button"));
        this.addressCol.setText(prop.getProperty("tag_address"));
        this.categoryCol.setText(prop.getProperty("tag_category"));
        this.del.setText(prop.getProperty("tag_del_button"));
        this.idCol.setText(prop.getProperty("tag_id"));
        this.lastNameCol.setText(prop.getProperty("tag_lastName"));
        this.mod.setText(prop.getProperty("tag_modify_button"));
        this.nameCol.setText(prop.getProperty("tag_name"));

    }

}
