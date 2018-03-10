/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Trazas;
import ServicesLayer.ServicesLocator;
import UtilsLayer.LanguageSelector;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yordanka
 */
public class TracesViewController implements Initializable {

    private MainClass main;
    private Stage stage;

    @FXML
    private TableColumn<Trazas, String> opCol;
    @FXML
    private TableColumn<Trazas, String> tableCol;

    @FXML
    private TableColumn<Trazas, Integer> regCol;
    @FXML
    private TableColumn<Trazas, String> ipCol;
    @FXML
    private TableColumn<Trazas, String> dateCol;
    @FXML
    private TableColumn<Trazas, String> hourCol;
    @FXML
    private TableColumn<Trazas, String> userCol;
    @FXML
    private Menu fileMN;
    @FXML
    private MenuItem txtmi;
    @FXML
    private MenuItem pdfmi;
    @FXML
    private MenuItem xlsmi;
    @FXML
    private TableView<Trazas> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        opCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getOperacion()));
        tableCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getTablaModificada()));
        ipCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getDireccionIp()));
        regCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, Integer> cellData) -> (ObservableValue) new SimpleIntegerProperty(cellData.getValue().getIdTuplaModificada()));
        dateCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        hourCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getHora()));
        userCol.setCellValueFactory((TableColumn.CellDataFeatures<Trazas, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getIdUsuario().getUsuario()));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Trazas> tableItems = sl.getTrazaServices().findTrazasEntities();
        table.setItems(FXCollections.observableList(tableItems));
        setLanguage();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    void setMain(MainClass main) {
        this.main = main;
    }

    private void setLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.dateCol.setText(prop.getProperty("tag_date"));
        this.hourCol.setText(prop.getProperty("tag_hour"));
        this.ipCol.setText(prop.getProperty("tag_ip"));
        this.opCol.setText(prop.getProperty("tag_operation"));
        this.regCol.setText(prop.getProperty("tag_cod"));
        this.userCol.setText(prop.getProperty("tag_user"));
        this.fileMN.setText(prop.getProperty("tag_file"));
        this.txtmi.setText(prop.getProperty("tag_txt"));
        // this.pdfmi.setText(prop.getProperty("tag_pdf"));
        // this.xlsmi.setText(prop.getProperty("tag_xls"));
        tableCol.setText(prop.getProperty("tag_table"));

    }

    public void exportCSV() {
        try {
            Desktop.getDesktop().open(new File("Log.csv"));
        } catch (IOException ex) {
            Logger.getLogger(TracesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
