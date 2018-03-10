/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Rol;
import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class LoginViewController implements Initializable {

    private MainClass main;
    private Stage stage;
    private int errorCount = 0;
    @FXML
    private TextField userTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private Label errorLb;
    @FXML
    private Pane pane;
    @FXML
    private Label userlb;
    @FXML
    private Label passlb;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //ServicesLocator.getServicesInstance();
        setLanguage(MainClass.language);

    }

    public void onAcceptPressed() {
        if (errorCount < 3) {
            boolean validLog = false;
            LoggedRole lr = null;
            String password = passTF.getText();
            String usuario = userTF.getText();

            List<Rol> roleList = ServicesLocator.getServicesLocatorInstance().getRolUsuarioServices().login(usuario, password);
            List<LoggedRole> logList = new ArrayList<>();
            if (roleList != null) {
                for (Rol r : roleList) {
                    switch (r.getTipoRol()) {
                        case "Administrador": {
                            logList.add(LoggedRole.ADMIN);
                        }
                        break;
                        case "Gerente": {
                            logList.add(LoggedRole.MNG);
                        }
                        break;
                        case "Dependiente": {
                            logList.add(LoggedRole.DEP);
                        }
                    }
                }

                validLog = !logList.isEmpty();
            }
            if (validLog) {
                MainClass.setLoggedRoles(logList);
                Usuarios u = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(usuario);
                MainClass.setLoggedUsser(u);
                main.loadStartPage();
                stage.hide();
            } else {
                errorCount++;
                errorLb.setText("Usuario o contraseña incorrectos..." + String.valueOf(4 - errorCount) + "!!!");
                errorLb.setTextFill(Color.RED);
                userTF.setText("");
                passTF.setText("");
                onError();
                Toolkit.getDefaultToolkit().beep();
            }

        } else {
            //JOptionPane.showMessageDialog(null, "Ha alcanzado el maximo numero de intentos");
            System.exit(0);
        }

    }

    private void onError() {
        final double xPos = pane.getLayoutX();
        Task task = new Task<Void>() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i <= 20000; i++) {
                        updateProgress(xPos + i, xPos + 20000);
                    }
                    Thread.sleep(200);
                    for (int i = 20000; i >= 0; i--) {
                        updateProgress(xPos - i, xPos - 20000);
                    }
                    Thread.sleep(200);
                    for (int i = 0; i <= 20000; i++) {
                        updateProgress(xPos + i, xPos + 20000);
                    }
                    Thread.sleep(200);
                    for (int i = 20000; i >= 0; i--) {
                        updateProgress(xPos - i, xPos - 20000);

                    }
                    Thread.sleep(200);
                    updateProgress(xPos, xPos);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected Void call() throws Exception {
                return null;
            }

        };
        pane.translateXProperty().bind(task.progressProperty());
        new Thread(task).start();

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
        this.stage.centerOnScreen();
        this.stage.getScene().getRoot().setOnKeyPressed(onEnterPressed());
    }

    public EventHandler onEnterPressed() {
        return (EventHandler) (Event event) -> {
            if (((KeyEvent) (event)).getCode() == KeyCode.ENTER) {
                onAcceptPressed();
            } else if (((KeyEvent) (event)).getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        };
    }

    private void setLanguage(String lang) {
        LanguageSelector prop = new LanguageSelector(lang);
        this.userlb.setText(prop.getProperty("tag_user"));
        this.passlb.setText(prop.getProperty("tag_pasword"));
    }
}
