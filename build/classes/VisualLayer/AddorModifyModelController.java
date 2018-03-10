/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Marcas;
import ModelLayer.Modelos;
import ModelLayer.Tarifa;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import com.sun.javafx.collections.ImmutableObservableList;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddorModifyModelController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Modelos modelInstance;
    @FXML
    private TextField modelTF;
    @FXML
    private ComboBox<Marcas> brandCB;
    @FXML
    private ComboBox<Tarifa> tarCB;
    @FXML
    private Label modlb;
    @FXML
    private Label marclb;
    @FXML
    private Label tarlb;
    @FXML
    private Button acc;
    @FXML
    private Text modtxt;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Marcas> brandsList = sl.getMarcaServices().findMarcasEntities();
        Marcas[] auxArr = new Marcas[brandsList.size()];
        brandsList.toArray(auxArr);
        ObservableList<Marcas> brandCbItems = new ImmutableObservableList<>(auxArr);
        brandCB.setItems(brandCbItems);
        List<Tarifa> tarList = sl.getTarifaServices().findTarifaEntities();
        Tarifa[] tarItems = new Tarifa[tarList.size()];
        tarList.toArray(tarItems);
        tarCB.setItems(new ImmutableObservableList<>(tarItems));
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
    }

    public void onAcceptPressed() {
        boolean exit = false;
        if (modelInstance == null) {
            String model = modelTF.getText().toLowerCase();
            Marcas brand = brandCB.getSelectionModel().getSelectedItem();
            Tarifa tar = tarCB.getSelectionModel().getSelectedItem();

            if (!(model.isEmpty() && brand == null && tar == null)) {

                Modelos m = new Modelos();
                m.setNombreModelo(model);
                m.setModeloIdMarca(brand);
                m.setModeloIdTar(tar);
                ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
                try {
                    sl.getModeloServices().create(m);
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR, ButtonType.OK).showAndWait();
                    Logger.getLogger(AddorModifyModelController.class.getName()).log(Level.SEVERE, null, ex);
                }
                exit = true;

            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
                exit = false;
            }
            if (exit) {
                main.loadModelsView();
                stage.hide();
                stage.close();

            }
        } else {
            String model = modelTF.getText().toLowerCase();
            Marcas brand = brandCB.getSelectionModel().getSelectedItem();
            Tarifa tarn = tarCB.getSelectionModel().getSelectedItem();

            if (!(model.isEmpty() && brand == null && tarn == null)) {
                Modelos m = new Modelos();
                m.setIdModelo(modelInstance.getId());
                m.setNombreModelo(model);
                m.setModeloIdMarca(brand);
                m.setModeloIdTar(tarn);
                try {
                    ServicesLocator.getServicesLocatorInstance().getModeloServices().edit(m);
                    exit = true;
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.MODIFICATION_ERROR, ButtonType.OK).showAndWait();
                    Logger.getLogger(AddorModifyModelController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
                exit = false;
            }
            if (exit) {
                main.loadModelsView();
                stage.hide();
                stage.close();
            }
        }

    }

    /**
     * @param modelInstance the modelInstance to set
     */
    public void setModelInstance(Modelos modelInstance) {
        this.modelInstance = modelInstance;
        if (modelInstance != null) {

            modelTF.setText(modelInstance.getNombreModelo());
            brandCB.getSelectionModel().select(ServicesLocator.getServicesLocatorInstance().getMarcaServices().findMarcaByName(modelInstance.getModeloIdMarca().getNombreMarca()));
            tarCB.getSelectionModel().select(ServicesLocator.getServicesLocatorInstance().getTarifaServices().findTarifa(modelInstance.getModeloIdTar().getId()));
        }
    }

    public void keyPressedForLetters(KeyEvent e) {
        String c = e.getCharacter();
        if (Character.isDigit(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten letras");
        }
    }

    public void keyPressedForDigits(KeyEvent e) {
        String c = e.getCharacter();
        if (Character.isLetter(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten numeros");
        }
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.acc.setText(prop.getProperty("tag_accept"));
        this.marclb.setText(prop.getProperty("tag_marca"));
        this.modlb.setText(prop.getProperty("tag_modelo"));
        this.modtxt.setText(prop.getProperty("tag_modelo"));
        this.tarlb.setText(prop.getProperty("tag_tarif"));

    }

}
