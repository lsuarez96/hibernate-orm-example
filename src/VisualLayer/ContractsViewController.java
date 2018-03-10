/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Autos;
import ModelLayer.Choferes;
import ModelLayer.Contratos;
import ModelLayer.Turista;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import UtilsLayer.MoneyFormatter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class ContractsViewController implements Initializable {

    @FXML
    private TableView<Contratos> table;
    @FXML
    private TableColumn<Contratos, String> idTurCol;
    @FXML
    private TableColumn<Contratos, String> plateCarCol;
    @FXML
    private TableColumn<Contratos, String> startDateCol;
    @FXML
    private TableColumn<Contratos, String> endDateCol;
    @FXML
    private TableColumn<Contratos, String> idDriverCol;
    @FXML
    private TableColumn<Contratos, String> deliveryDateCol;
    @FXML
    private TableColumn<Contratos, String> payFormCol;
    @FXML
    private TableColumn<Contratos, String> importCol;

    @FXML
    private Button del;
    @FXML
    private Button add;
    @FXML
    private Button modify;
//Detalles turista
    @FXML
    private Text passportlb;
    @FXML
    private Text passportdet;
    @FXML
    private Text namelb;
    @FXML
    private Text namedet;
    @FXML
    private Text lnamelb;
    @FXML
    private Text lnamedet;
    @FXML
    private Text agelb;
    @FXML
    private Text agedet;
    @FXML
    private Text countrylb;
    @FXML
    private Text countrydet;
    @FXML
    private Text sexlb;
    @FXML
    private Text sexdet;
//Detalles del auto
    @FXML
    private Text platlb;
    @FXML
    private Text paltedet;
    @FXML
    private Text modellb;
    @FXML
    private Text modeldet;
    @FXML
    private Text brandlb;
    @FXML
    private Text branddet;
    @FXML
    private Text sitlb;
    @FXML
    private Text sitdet;
    @FXML
    private Text tarnlb;
    @FXML
    private Text tarndet;
    @FXML
    private Text tarelb;
    @FXML
    private Text taredet;
    @FXML
    private Text colorlb;
    @FXML
    private Text colordet;
    @FXML
    private Text kmlb;
    @FXML
    private Text kmdet;

    //choferes
    @FXML
    private Text idlb;
    @FXML
    private Text iddet;
    @FXML
    private Text namechoflb;
    @FXML
    private Text nameChofdet;
    @FXML
    private Text lnamechoflb;
    @FXML
    private Text lnamechofdet;
    @FXML
    private Text catlb;
    @FXML
    private Text catdet;
    @FXML
    private Text addresslb;
    @FXML
    private Text addressdet;

    @FXML
    private TitledPane turpane;
    @FXML
    private TitledPane carPane;
    @FXML
    private TitledPane drivPane;
    @FXML
    private Accordion acord;

    @FXML
    private Text detalle;
    private Stage stage;
    private MainClass main;
    private Contratos c;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTurCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getContIdTur().getPasaporte()));
        plateCarCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getContIdAuto().getChapa()));
        startDateCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getFechaI().toString()));
        endDateCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getFechaF().toString()));
        idDriverCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getChofer().getNumeroId()));
        deliveryDateCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getFechaEntregaAsString()));
        payFormCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(cellData.getValue().getContIdFormaPago().toString()));
        importCol.setCellValueFactory((TableColumn.CellDataFeatures<Contratos, String> cellData) -> (ObservableValue) new SimpleStringProperty(MoneyFormatter.conversion(cellData.getValue().getImporteTotal())));
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        table.setItems(FXCollections.observableList(sl.getContratoServices().findContratosEntities()));
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setC(newValue));
        setLanguage(MainClass.language);
        this.agedet.setText(String.valueOf("-"));
        this.countrydet.setText("-");
        this.lnamedet.setText("-");
        this.namedet.setText("-");
        this.passportdet.setText("-");
        this.sexdet.setText("-");
        this.paltedet.setText("-");
        this.modeldet.setText("-");
        this.branddet.setText("-");
        this.sitdet.setText("-");
        this.tarndet.setText("-");
        this.taredet.setText("-");
        this.colordet.setText("-");
        this.kmdet.setText("-");
        this.iddet.setText("-");
        this.nameChofdet.setText("-");
        this.lnamechofdet.setText("-");
        this.catdet.setText("-");
        this.addressdet.setText("-");
        this.acord.setExpandedPane(turpane);

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
        if (!MainClass.getLoggedRoles().contains(LoggedRole.MNG)) {
            del.setDisable(true);
        }
    }

    /**
     * @param c the c to set
     */
    public void setC(Contratos c) {
        if (c != null) {
            this.c = c;
            setTuristData(c.getContIdTur());
            setCarData(c.getContIdAuto());
            try {
                setDriverData(c.getChofer());
            } catch (NullPointerException e) {
                setDriverData(null);
            }
        }
    }

    public void setTuristData(Turista t) {
        if (t != null) {
            this.agedet.setText(String.valueOf(t.getEdad()));
            this.countrydet.setText(t.getTurIdPais().getNombrePais());
            this.lnamedet.setText(t.getApellidos());
            this.namedet.setText(t.getNombre());
            this.passportdet.setText(t.getPasaporte());
            this.sexdet.setText(t.getSexo());
        } else {
            this.agedet.setText(String.valueOf("-"));
            this.countrydet.setText("-");
            this.lnamedet.setText("-");
            this.namedet.setText("-");
            this.passportdet.setText("-");
            this.sexdet.setText("-");
        }
    }

    public void setCarData(Autos a) {
        if (a != null) {
            this.paltedet.setText(a.getChapa());
            this.modeldet.setText(a.getIdModeloAuto().getNombreModelo());
            this.branddet.setText(a.getIdModeloAuto().getModeloIdMarca().getNombreMarca());
            this.sitdet.setText(a.getIdSituacionAuto().getTipoSituacion());
            this.tarndet.setText(MoneyFormatter.conversion(a.getIdModeloAuto().getModeloIdTar().getTarifaNormal()));
            this.taredet.setText(MoneyFormatter.conversion(a.getIdModeloAuto().getModeloIdTar().getTarifaEspecial()));
            this.colordet.setText(a.getColor());
            this.kmdet.setText(String.valueOf(a.getKilometros()));
        } else {
            this.paltedet.setText("-");
            this.modeldet.setText("-");
            this.branddet.setText("-");
            this.sitdet.setText("-");
            this.tarndet.setText("-");
            this.taredet.setText("-");
            this.colordet.setText("-");
            this.kmdet.setText("-");
        }
    }

    public void setDriverData(Choferes chof) {
        if (chof != null) {
            this.iddet.setText(chof.getNumeroId());
            this.nameChofdet.setText(chof.getNombre());
            this.lnamechofdet.setText(chof.getApellidos());
            this.catdet.setText(chof.getCategoria().getTipoCategoria());
            this.addressdet.setText(chof.getDireccion());
        } else {
            this.iddet.setText("-");
            this.nameChofdet.setText("-");
            this.lnamechofdet.setText("-");
            this.catdet.setText("-");
            this.addressdet.setText("-");
        }
    }

    public void onAddPressed() {
        main.loadAddModifyContract(null);
        stage.hide();
        stage.close();
        //table.setItems(FXCollections.observableList(ServicesLocator.getContratoServices().retriveAllContracts()));
    }

    public void onModifyPressed() {
        if (c != null) {
            main.loadAddModifyContract(c);
            //table.setItems(FXCollections.observableList(ServicesLocator.getContratoServices().retriveAllContracts()));
            stage.hide();
            stage.close();
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public void onElimPressed() throws Exception {
        if (c != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> bt = alert.showAndWait();

            // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
            if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                try {
                    ServicesLocator.getServicesLocatorInstance().getContratoServices().destroy(c.getId());
                    table.setItems(FXCollections.observableList(ServicesLocator.getServicesLocatorInstance().getContratoServices().findContratosEntities()));
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
        this.del.setText(prop.getProperty("tag_del_button"));
        this.modify.setText(prop.getProperty("tag_modify_button"));
        this.deliveryDateCol.setText(prop.getProperty("tag_delivery"));
        this.endDateCol.setText(prop.getProperty("tag_end_date"));
        this.idDriverCol.setText(prop.getProperty("tag_driver"));
        this.idTurCol.setText(prop.getProperty("tag_passport"));
        this.importCol.setText(prop.getProperty("tag_import"));
        this.payFormCol.setText(prop.getProperty("tag_pay_form"));
        this.plateCarCol.setText(prop.getProperty("tag_plate"));
        this.startDateCol.setText(prop.getProperty("tag_start_date"));
        //turista
        this.agelb.setText(prop.getProperty("tag_age"));
        this.countrylb.setText(prop.getProperty("tag_country"));
        this.lnamelb.setText(prop.getProperty("tag_lastName"));
        this.namelb.setText(prop.getProperty("tag_name"));
        this.passportlb.setText(prop.getProperty("tag_passport"));
        this.sexlb.setText(prop.getProperty("tag_sex"));
        //carro
        this.platlb.setText(prop.getProperty("tag_plate"));
        this.modellb.setText(prop.getProperty("tag_modelo"));
        this.brandlb.setText(prop.getProperty("tag_marca"));
        this.sitlb.setText(prop.getProperty("tag_situation"));
        this.colorlb.setText(prop.getProperty("tag_color"));
        this.kmlb.setText(prop.getProperty("tag_km"));
        this.tarelb.setText(prop.getProperty("tag_special_tar"));
        this.tarnlb.setText(prop.getProperty("tag_normal_tar"));
        //chofer
        this.idlb.setText(prop.getProperty("tag_id"));
        this.namechoflb.setText(prop.getProperty("tag_name"));
        this.lnamechoflb.setText(prop.getProperty("tag_lastName"));
        this.addresslb.setText(prop.getProperty("tag_address"));
        this.catlb.setText(prop.getProperty("tag_category"));
        //panels label
        turpane.setText(prop.getProperty("tag_turist"));
        carPane.setText(prop.getProperty("tag_auto"));
        drivPane.setText(prop.getProperty("tag_driver"));
        detalle.setText(prop.getProperty("tag_detail"));

    }
}
