/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Rol;
import ModelLayer.RolUsuario;
import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import com.sun.javafx.collections.ImmutableObservableList;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Luisito Suarez
 */
public class AddLoginController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Usuarios userInstance;

    @FXML
    private ComboBox<Usuarios> userCB;
    @FXML
    private CheckBox adm;
    @FXML
    private CheckBox mng;
    @FXML
    private CheckBox dep;
    private final List<Rol> roleList = new LinkedList<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Usuarios> tmList = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUsuariosEntities();
        Usuarios[] itemsUsr = new Usuarios[tmList.size()];
        tmList.toArray(itemsUsr);
        userCB.setItems(new ImmutableObservableList<>(itemsUsr));
        setLanguage();
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.onCloseRequestProperty().set(onClose());
    }

    /**
     * @param main the main to set
     */
    public void setMain(MainClass main) {
        this.main = main;
    }

    @SuppressWarnings("null")
    public void onAcceptPressed() {
        userInstance = userCB.getSelectionModel().getSelectedItem();
        if (adm.isSelected()) {
            Rol r = new Rol();
            r.setTipoRol("Administrador");
            roleList.add(r);
        }
        if (mng.isSelected()) {
            Rol r = new Rol();
            r.setTipoRol("Gerente");
            roleList.add(r);

        }
        if (dep.isSelected()) {
            Rol r = new Rol();
            r.setTipoRol("Dependiente");
            roleList.add(r);
        }
        roleList.forEach((r) -> {
            ServicesLocator.getServicesLocatorInstance().getRolServices().findRolEntities().stream().filter((r2) -> (r.getTipoRol().equals(r2.getTipoRol()))).forEachOrdered((r2) -> {
                r.setIdRol(r2.getIdRol());
            });
        });
        // Role r = rolCB.getSelectionModel().getSelectedItem();
        if (!loginExist(roleList, userInstance)) {
            if (userInstance != null && !roleList.isEmpty()) {
                for (Rol rolN : roleList) {
                    RolUsuario ru = new RolUsuario();
                    ru.setUsuarioIdUser(userInstance);
                    ru.setRolIdRol(ServicesLocator.getServicesLocatorInstance().getRolServices().findRoleByName(rolN.getTipoRol()));
                    try {
                        ServicesLocator.getServicesLocatorInstance().getRolUsuarioServices().create(ru);
                    } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, ErrorMessages.INSERTION_ERROR, ButtonType.OK).showAndWait();
                        // JOptionPane.showMessageDialog(null, ErrorMessages.INSERTION_ERROR);
                        Logger.getLogger(AddLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                main.loadRolUserView();
                stage.hide();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
                //JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El login ya existe");
        }
    }

    public void onAdminPress() {
        if (!adm.isSelected()) {
            Rol r = new Rol();
            r.setTipoRol("Administrador");
            roleList.add(r);
        } else {
            Rol r = new Rol();
            r.setTipoRol("Administrador");
            roleList.add(r);
        }
    }

    public void onMngPressed() {
        if (!adm.isSelected()) {
            Rol r = new Rol();
            r.setTipoRol("Gerente");
            roleList.add(r);
        } else {
            Rol r = new Rol();
            r.setTipoRol("Gerente");
            roleList.add(r);
        }
    }

    public void onDepPressed() {
        if (!adm.isSelected()) {
            Rol r = new Rol();
            r.setTipoRol("Dependiente");
            roleList.add(r);
        } else {
            Rol r = new Rol();
            r.setTipoRol("Dependiente");
            roleList.add(r);
        }
    }

    public boolean loginExist(List<Rol> rl, Usuarios u) {

        List<RolUsuario> lgList = ServicesLocator.getServicesLocatorInstance().getRolUsuarioServices().findRolUsuarioEntities();
        for (RolUsuario l : lgList) {
            for (Rol r : rl) {
                if (l.getRolIdRol().getTipoRol().equals(r.getTipoRol()) && l.getUsuarioIdUser().getUsuario().equals(u.getUsuario())) {
                    rl.remove(r);
                }
            }
        }

        return rl.isEmpty();
    }

    /**
     * @return the userInstance
     */
    public Usuarios getUserInstance() {
        return userInstance;
    }

    /**
     * @param userInstance the userInstance to set
     */
    public void setUserInstance(Usuarios userInstance) {
        this.userInstance = userInstance;
        if (userInstance != null) {
            userCB.getSelectionModel().select(userInstance);
            userCB.setEditable(false);
            userCB.setDisable(true);
        }
    }

    public EventHandler onClose() {
        return (EventHandler) (Event event) -> {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            stage.hide();
            main.loadRolUserView();
            stage.close();
        };
    }

    private void setLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.adm.setText(prop.getProperty("tag_admin"));
        this.dep.setText(prop.getProperty("tag_dep"));
        this.mng.setText(prop.getProperty("tag_mng"));
        this.userCB.setPromptText(prop.getProperty("tag_user"));
    }
}
