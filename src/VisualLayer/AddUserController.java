/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.Cipher;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class AddUserController implements Initializable {

    @FXML
    private TextField userTF;
    @FXML
    private PasswordField passF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField lnameTF;
    @FXML
    private Label usrlb;
    @FXML
    private Label passlb;
    @FXML
    private Label namlb;
    @FXML
    private Label lnamelb;
    @FXML
    private Text usrtx;
    @FXML
    private Button acc;

    private Stage stage;
    private MainClass main;

    private Usuarios itemInstance;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLang();
    }

    public void onAcceptPressed() throws NonexistentEntityException, Exception {
        ServicesLocator sl = ServicesLocator.getServicesLocatorInstance();
        if (itemInstance == null) {
            String user = userTF.getText();
            String passw = passF.getText();
            String name = nameTF.getText();
            String lname = lnameTF.getText();
            Usuarios u = new Usuarios();
            u.setNombre(name);
            u.setApellidos(lname);
            u.setUsuario(user);
            Usuarios temp = sl.getUsuarioServices().findUserByName(user);
            if (temp == null && !(passw.isEmpty() && user.isEmpty() && name.isEmpty() && lname.isEmpty())) {
                u.setPasswordUsuario(Cipher.SHA256(passw));
                try {
                    sl.getUsuarioServices().create(u);
                    main.loadAddLogin(sl.getUsuarioServices().findUserByName(user));
                    stage.hide();
                    stage.close();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR + "\n" + e.getMessage(), ButtonType.OK).showAndWait();
                }
            } else if (temp != null) {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.RECORD_EXIST_ERROR, ButtonType.OK).showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        } else {

            String user = userTF.getText();
            String passw = itemInstance.getPasswordUsuario();
            String name = nameTF.getText();
            String lname = lnameTF.getText();
            Usuarios u = new Usuarios(itemInstance.getId(), user, passw, name, lname);

            if (!(user.isEmpty() && passw.isEmpty() && name.isEmpty() && lname.isEmpty())) {
                try {
                    sl.getUsuarioServices().edit(u);
                    main.loadRolUserView();
                    stage.hide();
                    stage.close();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, ErrorMessages.MODIFICATION_ERROR + "\n" + ex.getMessage(), ButtonType.OK).showAndWait();
                    Logger.getLogger(AddUserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();

            }
        }
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
        if (itemInstance != null) {
            nameTF.setText(itemInstance.getNombre());
            lnameTF.setText(itemInstance.getApellidos());
            userTF.setText(itemInstance.getUsuario());
            passF.setText(itemInstance.getPasswordUsuario());
            passF.setEditable(false);
            passF.setDisable(true);
            passF.setVisible(false);
            passlb.setVisible(false);

        }
    }

    public EventHandler closeAction() {
        return (EventHandler) (Event event) -> {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            stage.hide();
            main.loadRolUserView();
            stage.close();
        };
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.lnamelb.setText(prop.getProperty("tag_lastName"));
        this.namlb.setText(prop.getProperty("tag_name"));
        this.passlb.setText(prop.getProperty("tag_pasword"));
        this.usrlb.setText(prop.getProperty("tag_user"));
        this.usrtx.setText(prop.getProperty("tag_user"));
        acc.setText(prop.getProperty("tag_accept"));
    }
}
