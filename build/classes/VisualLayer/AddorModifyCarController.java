/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Autos;
import ModelLayer.Modelos;
import ModelLayer.Situaciones;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import com.sun.javafx.collections.ImmutableObservableList;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddorModifyCarController implements Initializable {

    private Autos autoInstance;
    private Stage stage;
    private MainClass main;

    @FXML
    private TextField plateTF;
    @FXML
    private TextField colourTF;
    @FXML
    private TextField kmTF;

    @FXML
    private ComboBox<Modelos> modelCB;
    @FXML
    private ComboBox<Situaciones> situationCB;
    @FXML
    private Label platelb;
    @FXML
    private Label collb;
    @FXML
    private Label kmlb;
    @FXML
    private Label modlb;
    @FXML
    private Label sitlb;
    @FXML
    private Text carlb;
    @FXML
    private Button acc;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Modelos> modelList = sl.getModeloServices().findModelosEntities();
        Modelos[] marr = new Modelos[modelList.size()];
        marr = modelList.toArray(marr);
        ObservableList<Modelos> modelCBItems = new ImmutableObservableList<>(marr);
        modelCB.setItems(modelCBItems);
        List<Situaciones> situationsList = sl.getSituacionesServices().findSituacionesEntities();
        Situaciones[] srr = new Situaciones[situationsList.size()];
        situationsList.toArray(srr);
        ObservableList<Situaciones> situationCBItems = new ImmutableObservableList<>(srr);
        situationCB.setItems(situationCBItems);
    }

    @SuppressWarnings("ObjectEqualsNull")
    public void onAcceptButtonPressed() throws Exception {
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        boolean exit = false;
        if (autoInstance != null) {
            Situaciones situacion = situationCB.getSelectionModel().getSelectedItem();
            Modelos model = modelCB.getSelectionModel().getSelectedItem();
            //String brand = modelCB.getSelectionModel().getSelectedItem().getBrand();
            String color = colourTF.getText().toLowerCase();
            String plate = plateTF.getText().toLowerCase();
            float km = -1;
            try {
                km = Float.valueOf(kmTF.getText());
            } catch (NumberFormatException e) {
                kmTF.setText("0");
            }
            if (sl.getAutosServices().findAutos(autoInstance.getId()) != null && situacion != null && model != null && !color.isEmpty() && km >= 0) {

                Autos a = new Autos();
                a.setChapa(plate);
                a.setColor(color);
                a.setIdModeloAuto(model);
                a.setKilometros(km);
                a.setIdSituacionAuto(situacion);
                a.setIdAuto(autoInstance.getId());
                try {
                    sl.getAutosServices().edit(a);
                    exit = true;
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.MODIFICATION_ERROR + "\n" + e.getMessage(), ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        } else {
            Situaciones situacion = situationCB.getSelectionModel().getSelectedItem();
            Modelos model = modelCB.getSelectionModel().getSelectedItem();
            //String brand = modelCB.getSelectionModel().getSelectedItem().getBrand();
            String color = colourTF.getText().toLowerCase();
            String plate = plateTF.getText().toLowerCase();
            float km = -1;
            try {
                km = Float.valueOf(kmTF.getText());
            } catch (NumberFormatException e) {
                kmTF.setText("");
                JOptionPane.showMessageDialog(null, "La cantidad de kilometros debe ser un numero");
            }
            if (!plate.isEmpty() && situacion != null && model != null && !color.isEmpty() && km >= 0) {

                Autos a = new Autos();
                a.setChapa(plate);
                a.setColor(color);
                a.setIdModeloAuto(model);
                a.setIdSituacionAuto(situacion);
                a.setKilometros(km);
                try {
                    sl.getAutosServices().create(a);
                    exit = true;
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR + "\n" + e.getMessage(), ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        }
        if (exit) {
            main.loadCarsView();
            stage.hide();
            stage.close();
        }
    }

    /**
     * @param autoInstance the autoInstance to set
     */
    public void setAutoInstance(Autos autoInstance) {
        this.autoInstance = autoInstance;
        if (autoInstance != null) {
            plateTF.setText(autoInstance.getChapa());
            colourTF.setText(autoInstance.getColor());
            try {
                kmTF.setText(String.valueOf(autoInstance.getKilometros()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Los kilometros recorridos deben ser un numero");
            }
            Modelos m = autoInstance.getIdModeloAuto();
            modelCB.getSelectionModel().select(m);
            Situaciones s = ServicesLocator.getServicesLocatorInstance().getSituacionesServices().findSituaciones(autoInstance.getIdSituacionAuto().getId());
            situationCB.getSelectionModel().select(s);
        }
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(onClose());
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
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

    public EventHandler onClose() {
        return (EventHandler) (Event event) -> {
            main.loadCarsView();
            stage.hide();
            stage.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        };
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.acc.setText(prop.getProperty("tag_accept"));
        this.carlb.setText(prop.getProperty("tag_auto"));
        this.collb.setText(prop.getProperty("tag_color"));
        this.kmlb.setText(prop.getProperty("tag_km"));
        this.modlb.setText(prop.getProperty("tag_modelo") + "/" + prop.getProperty("tag_marca"));
        this.platelb.setText(prop.getProperty("tag_plate"));
        this.sitlb.setText(prop.getProperty("tag_situation"));
    }
}
