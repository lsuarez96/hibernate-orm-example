/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Pais;
import ModelLayer.Turista;
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
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddorModifyTuristController implements Initializable {

    private Turista itemInstance;
    private Stage stage;
    private MainClass main;

    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField ageTF;
    @FXML
    private ComboBox<String> sexCB;
    @FXML
    private ComboBox<Pais> countryCB;
    @FXML
    private Label passlb;
    @FXML
    private Label namelb;
    @FXML
    private Label lnamelb;
    @FXML
    private Label phonelb;
    @FXML
    private Label agelb;
    @FXML
    private Label countrylb;
    @FXML
    private Text turlb;
    @FXML
    private Button acc;
    @FXML
    private Label sexlb;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String sex = "";
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        ObservableList<String> sexItems = new ImmutableObservableList<>(new String[]{prop.getProperty("tag_male"), prop.getProperty("tag_female")});
        sexCB.setItems(sexItems);
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        List<Pais> countriesList = sl.getPaisServices().findPaisEntities();
        Pais[] parr = new Pais[countriesList.size()];
        countriesList.toArray(parr);
        ObservableList<Pais> countryCBItems = new ImmutableObservableList<>(parr);
        countryCB.setItems(countryCBItems);
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Turista itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            idTF.setText(itemInstance.getPasaporte());
            nameTF.setText(itemInstance.getNombre());
            lastNameTF.setText(itemInstance.getApellidos());
            phoneTF.setText(itemInstance.getTelefono());
            ageTF.setText(String.valueOf(itemInstance.getEdad()));
            sexCB.getSelectionModel().select(itemInstance.getSexo());
            Pais p = itemInstance.getTurIdPais();
            countryCB.getSelectionModel().select(p);
        }
    }

    @SuppressWarnings("ObjectEqualsNull")
    public void onAcceptPressed() {
        boolean exit = false;
        if (itemInstance != null) {
            String id = idTF.getText().toLowerCase();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String phone = phoneTF.getText();
            String sex = sexCB.getSelectionModel().getSelectedItem().toLowerCase();
            int age = -1;
            try {
                age = Integer.valueOf(ageTF.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            }
            Pais country = countryCB.getSelectionModel().getSelectedItem();
            if (!id.isEmpty() && !name.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !sex.isEmpty() && age > 17 && country != null) {
                Turista t = new Turista(itemInstance.getId());
                t.setApellidos(lastName);
                t.setNombre(name);
                t.setEdad(age);
                t.setTurIdPais(country);
                t.setPasaporte(id);
                t.setTelefono(phone);
                t.setSexo(sex);
                try {
                    ServicesLocator.getServicesLocatorInstance().getTuristaServices().edit(t);
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.MODIFICATION_ERROR, ButtonType.OK).showAndWait();
                    Logger.getLogger(AddorModifyTuristController.class.getName()).log(Level.SEVERE, null, ex);
                }
                exit = true;
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }

        } else {
            String id = idTF.getText().toLowerCase();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String phone = phoneTF.getText();
            String sex = sexCB.getSelectionModel().getSelectedItem().toLowerCase();
            int age = -1;
            try {
                age = Integer.valueOf(ageTF.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                ageTF.setText(String.valueOf(itemInstance.getEdad()));
            }
            Pais country = countryCB.getSelectionModel().getSelectedItem();
            if (!id.isEmpty() && !name.isEmpty() && !lastName.isEmpty() && !phone.isEmpty() && !sex.isEmpty() && age > 17 && country != null) {
                Turista t = new Turista();
                t.setPasaporte(id);
                t.setNombre(name);
                t.setApellidos(lastName);
                t.setTelefono(phone);
                t.setTurIdPais(country);
                t.setEdad(age);
                t.setSexo(sex);

                try {
                    ServicesLocator.getServicesLocatorInstance().getTuristaServices().create(t);
                    exit = true;
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR, ButtonType.OK).showAndWait();
                    Logger.getLogger(AddorModifyTuristController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        }
        if (exit) {
            main.loadTuristsView();
            stage.hide();
            stage.close();

        }
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

    public void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.acc.setText(prop.getProperty("tag_accept"));
        this.agelb.setText(prop.getProperty("tag_age"));
        this.countrylb.setText(prop.getProperty("tag_country"));
        this.lnamelb.setText(prop.getProperty("tag_lastName"));
        this.namelb.setText(prop.getProperty("tag_name"));
        this.phonelb.setText(prop.getProperty("tag_phone"));
        this.turlb.setText(prop.getProperty("tag_turist"));
        this.passlb.setText(prop.getProperty("tag_passport"));
        this.sexlb.setText(prop.getProperty("tag_sex"));
    }
}
