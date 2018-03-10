/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Turista;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
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
public class TuristsViewController implements Initializable {

    @FXML
    private TableView<Turista> table;
    @FXML
    private TableColumn<Turista, String> passportCol;
    @FXML
    private TableColumn<Turista, String> nameCol;
    @FXML
    private TableColumn<Turista, String> lastNameCol;
    @FXML
    private TableColumn<Turista, Integer> ageCol;
    @FXML
    private TableColumn<Turista, String> sexCol;
    @FXML
    private TableColumn<Turista, String> phoneCol;
    @FXML
    private TableColumn<Turista, String> countryCol;
    @FXML
    private Button add;
    @FXML
    private Button del;
    @FXML
    private Button mod;

    private Stage stage;

    private MainClass mainClass;

    private Turista itemInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passportCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getPasaporte()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getNombre()));
        lastNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getApellidos()));
        ageCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, Integer> cellData) -> (ObservableValue) new SimpleIntegerProperty(cellData.getValue().getEdad()));
        sexCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getSexo()));
        phoneCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getTelefono()));
        countryCol.setCellValueFactory((TableColumn.CellDataFeatures<Turista, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getTurIdPais().getNombrePais()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        table.setItems(FXCollections.observableList(sl.getTuristaServices().findTuristaEntities()));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onItemSelected(newValue));
        setLanguage();

    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param mainClass the mainClass to set
     */
    public void setMainClass(MainClass mainClass) {
        this.mainClass = mainClass;
    }

    public void onItemSelected(Turista t) {
        itemInstance = t;
    }

    public void onAddPressed() {
        mainClass.loadAddOrModifyTurist(null);
        stage.hide();
        stage.close();
        //table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
    }

    public void onModifyPressed() {
        mainClass.loadAddOrModifyTurist(itemInstance);
        stage.hide();
        stage.close();
        // table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
    }

    public void onElimPressed() throws IllegalOrphanException, NonexistentEntityException {
        //  int op = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar este item, \n esta accion es irreversible!!!", "Eliminar turista", JOptionPane.YES_NO_OPTION);
        if (itemInstance != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getTuristaServices().destroy(itemInstance.getId());
                    table.setItems(FXCollections.observableList(ServicesLocator.getServicesLocatorInstance().getTuristaServices().findTuristaEntities()));
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();

        }
        // table.setItems(FXCollections.observableList(ServicesLocator.getTuristaServices().retriveAllTurists()));
    }

    private void setLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.add.setText(prop.getProperty("tag_add_button"));
        this.ageCol.setText(prop.getProperty("tag_age"));
        this.countryCol.setText(prop.getProperty("tag_country"));
        this.del.setText(prop.getProperty("tag_del_button"));
        this.lastNameCol.setText(prop.getProperty("tag_lastName"));
        this.mod.setText(prop.getProperty("tag_modify_button"));
        this.nameCol.setText(prop.getProperty("tag_name"));
        this.passportCol.setText(prop.getProperty("tag_passport"));
        this.phoneCol.setText(prop.getProperty("tag_phone"));
        this.sexCol.setText(prop.getProperty("tag_sex"));

    }
}
