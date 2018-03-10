/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.Cipher;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yordanka
 */
public class ChangePasswordViewController implements Initializable {

    @FXML
    private Text changePass;
    @FXML
    private Label userN;
    @FXML
    private Text pass;
    @FXML
    private Text newPass;
    @FXML
    private Text confPass;
    @FXML
    private PasswordField passf;
    @FXML
    private PasswordField newPassf;
    @FXML
    private PasswordField confPassF;
    @FXML
    private Button acc;
    private MainClass main;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setLang();
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.changePass.setText(prop.getProperty("tag_change_pass"));
        userN.setText(MainClass.getLoggedUsser().getUsuario());
        pass.setText(prop.getProperty("tag_pasword"));
        newPass.setText(prop.getProperty("tag_new_pass"));
        confPass.setText(prop.getProperty("tag_conf_pass"));
        acc.setText(prop.getProperty("tag_accept"));

    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(onClose());
    }

    public void accept() {
        if (pass.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(new LanguageSelector(MainClass.language).getProperty("tag_req_pass"));
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (!MainClass.getLoggedUsser().getPasswordUsuario().equals(Cipher.SHA256(passf.getText()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(new LanguageSelector(MainClass.language).getProperty("tag_incorrect_pass"));
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (!newPassf.getText().equals(confPassF.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(new LanguageSelector(MainClass.language).getProperty("tag_incorrect_pass"));
            alert.setHeaderText(null);
            alert.showAndWait();
            newPassf.setText("");
            confPassF.setText("");
        } else {
            Usuarios u = MainClass.getLoggedUsser();
            u.setPasswordUsuario(Cipher.SHA256(newPassf.getText()));
            try {
                ServicesLocator.getServicesLocatorInstance().getUsuarioServices().edit(u);
                stage.hide();
                main.loadStartPage();
                stage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(new LanguageSelector(MainClass.language).getProperty("tag_mod_error"));
                alert.setHeaderText(new LanguageSelector(MainClass.language).getProperty("tag_modif_error"));
                alert.showAndWait();
                Logger.getLogger(ChangePasswordViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private EventHandler onClose() {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                stage.hide();
                main.loadStartPage();
                stage.close();

                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
