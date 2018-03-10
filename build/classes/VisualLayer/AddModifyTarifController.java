/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Tarifa;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class AddModifyTarifController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TextField normalTF;
    @FXML
    private TextField speciallTF;
    @FXML
    private Label normlb;
    @FXML
    private Label speclb;
    @FXML
    private Button accept;
    @FXML
    private Text tar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        tar.setText(prop.getProperty("title_tariff_view"));
        this.normlb.setText(prop.getProperty("tag_normal_tar"));
        speclb.setText(prop.getProperty("tag_special_tar"));
        accept.setText(prop.getProperty("tag_accept"));
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

    /**
     *
     */
    public void onAcceptPressed() {

        String normal = normalTF.getText();
        String speccial = speciallTF.getText();
        Tarifa t = new Tarifa();

        if (!(normal.isEmpty() && speccial.isEmpty())) {
            t.setTarifaNormal(Double.valueOf(normal));
            t.setTarifaEspecial(Double.valueOf(speccial));

            try {
                ServicesLocator.getServicesLocatorInstance().getTarifaServices().create(t);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR, ButtonType.OK).showAndWait();
                Logger.getLogger(AddModifyTarifController.class.getName()).log(Level.SEVERE, null, ex);
            }
            main.loadTariffView();
            stage.hide();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
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
}
