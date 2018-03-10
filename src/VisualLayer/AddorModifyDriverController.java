/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Categorias;
import ModelLayer.Choferes;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddorModifyDriverController implements Initializable {

    private Choferes itemInstance;
    private Stage stage;
    private MainClass main;
    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField addressTF;
    @FXML
    private ComboBox<Categorias> categoryCB;

    @FXML
    private Label iddrivlb;
    @FXML
    private Label namelb;
    @FXML
    private Label lnamelb;
    @FXML
    private Label addrelb;
    @FXML
    private Label catlb;
    @FXML
    private Text drivtx;
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
        List<Categorias> catList = ServicesLocator.getServicesLocatorInstance().getCategoriaServices().findCategoriasEntities();
        Categorias[] crr = new Categorias[catList.size()];
        for (int i = 0; i < crr.length; i++) {
            crr[i] = catList.get(i);
        }
        ObservableList<Categorias> catItems = new ImmutableObservableList<>(crr);
        categoryCB.setItems(catItems);
        setLang();
    }

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Choferes itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            idTF.setText(itemInstance.getNumeroId());
            nameTF.setText(itemInstance.getNombre());
            lastNameTF.setText(itemInstance.getApellidos());
            addressTF.setText(itemInstance.getDireccion());
            categoryCB.getSelectionModel().select(itemInstance.getCategoria());
        }
    }

    public void onAcceptPressed() {
        boolean exit = false;
        if (itemInstance != null) {
            String id = idTF.getText().toLowerCase();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String address = addressTF.getText();
            Categorias category = categoryCB.getSelectionModel().getSelectedItem();
            if (!(id.isEmpty() && name.isEmpty() && lastName.isEmpty() && address.isEmpty() && category == null) && id.length() == 11) {
                try {
                    Long.valueOf(id);
                    Choferes c = new Choferes();
                    c.setIdChofer(itemInstance.getId());
                    c.setCategoria(category);
                    c.setNombre(name);
                    c.setApellidos(lastName);
                    c.setDireccion(address);
                    c.setNumeroId(id);
                    try {
                        ServicesLocator.getServicesLocatorInstance().getChoferServices().edit(c);
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, ErrorMessages.MODIFICATION_ERROR, ButtonType.OK).showAndWait();
                        Logger.getLogger(AddorModifyDriverController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    exit = true;
                } catch (NumberFormatException e) {
                    exit = false;

                }
            } else if (id.length() != 11) {
                JOptionPane.showMessageDialog(null, "El carne del chofer tener 11 caracteres numericos");
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        } else {
            String id = idTF.getText();
            String name = nameTF.getText();
            String lastName = lastNameTF.getText();
            String address = addressTF.getText();
            Categorias category = categoryCB.getSelectionModel().getSelectedItem();
            if (!(id.isEmpty() && name.isEmpty() && lastName.isEmpty() && address.isEmpty() && category == null)) {
                Choferes c = new Choferes();
                c.setCategoria(category);
                c.setNombre(name);
                c.setApellidos(lastName);
                c.setDireccion(address);
                c.setNumeroId(id);

                try {
                    ServicesLocator.getServicesLocatorInstance().getChoferServices().create(c);
                    exit = true;
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR, ButtonType.OK).showAndWait();
                    Logger.getLogger(AddorModifyDriverController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        }
        if (exit) {
            main.loadDriversView();
            stage.hide();
            stage.close();
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
        idTF.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        if (Character.isLetter(c.charAt(0))) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo se admiten numeros");
        } else if (idTF.getText().length() >= 11) {
            e.consume();
            Toolkit.getDefaultToolkit().beep();
            System.err.println("Solo puede tener 11 digitos");
            idTF.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (idTF.getText().length() == 10) {
            idTF.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.acc.setText(prop.getProperty("tag_accept"));
        this.addrelb.setText(prop.getProperty("tag_address"));
        this.catlb.setText(prop.getProperty("tag_category"));
        this.drivtx.setText(prop.getProperty("tag_driver"));
        this.iddrivlb.setText(prop.getProperty("tag_id"));
        this.lnamelb.setText(prop.getProperty("tag_lastName"));
        this.namelb.setText(prop.getProperty("tag_name"));

    }

    private EventHandler onClose() {
        return (EventHandler) (Event e) -> {
            stage.hide();
            main.loadDriversView();
            stage.close();
        };
    }

}
