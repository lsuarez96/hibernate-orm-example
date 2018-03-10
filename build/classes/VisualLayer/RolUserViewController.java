/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.RolUsuario;
import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luis
 */
public class RolUserViewController implements Initializable {

    @FXML
    private TreeView<String> treeView;
    @FXML
    private Label userNlb;
    @FXML
    private Label namelb;
    @FXML
    private Label lnamelb;

    private MainClass main;
    private Stage stage;

    @FXML
    private Label userNlb2;
    @FXML
    private Label namelb2;
    @FXML
    private Label lnamelb2;

    @FXML
    private Button addU;
    @FXML
    private Button delU;
    @FXML
    private Button modU;
    @FXML
    private Button changPassU;
    @FXML
    private Button addR;
    @FXML
    private Button delR;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fullTableData();
        userNlb.setText("-");
        namelb.setText("-");
        lnamelb.setText("-");
        setLanguage();
    }

    public void fullTableData() {

        List<RolUsuario> loginList = ServicesLocator.getServicesLocatorInstance().getRolUsuarioServices().findRolUsuarioEntities();
        List<AuxLoginRepresentation> auxLogList = new ArrayList<>();
        for (RolUsuario l1 : loginList) {
            AuxLoginRepresentation temp = new AuxLoginRepresentation();
            temp.setRol(l1.getRolIdRol().getTipoRol());
            for (RolUsuario l2 : loginList) {
                String user = l2.getUsuarioIdUser().getUsuario();
                if (l2.getRolIdRol().getTipoRol().equalsIgnoreCase(l1.getRolIdRol().getTipoRol()) && !temp.getUsersList().contains(user)) {
                    temp.addUser(user);
                }
            }

            auxLogList.add(temp);
        }
        String usuario = new LanguageSelector(MainClass.language).getProperty("tag_user");
        TreeItem<String> root = new TreeItem<>("Rol/" + usuario);
        root.setExpanded(true);
        TreeItem<String> adminItem = new TreeItem<>(new LanguageSelector(MainClass.language).getProperty("tag_admin"));
        adminItem.setExpanded(true);
        TreeItem<String> mngItem = new TreeItem<>(new LanguageSelector(MainClass.language).getProperty("tag_mng"));
        mngItem.setExpanded(true);
        TreeItem<String> depItem = new TreeItem<>(new LanguageSelector(MainClass.language).getProperty("tag_dep"));
        depItem.setExpanded(true);

        for (AuxLoginRepresentation al : auxLogList) {
            switch (al.getRol()) {
                case "Administrador": {
                    for (String usr : al.getUsersList()) {
                        TreeItem<String> itm = new TreeItem<>(usr);
                        boolean exist = false;
                        for (TreeItem<String> i : adminItem.getChildren()) {
                            if (i.getValue().equals(itm.getValue())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            adminItem.getChildren().add(itm);
                        }
                    }
                }
                break;
                case "Gerente": {
                    for (String usr : al.getUsersList()) {
                        TreeItem<String> itm = new TreeItem<>(usr);
                        boolean exist = false;
                        for (TreeItem<String> i : mngItem.getChildren()) {
                            if (i.getValue().equals(itm.getValue())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            mngItem.getChildren().add(itm);
                        }

                    }
                }
                break;
                case "Dependiente": {
                    for (String usr : al.getUsersList()) {
                        TreeItem<String> itm = new TreeItem<>(usr);
                        boolean exist = false;
                        for (TreeItem<String> i : depItem.getChildren()) {
                            if (i.getValue().equals(itm.getValue())) {
                                exist = true;
                            }
                        }
                        if (!exist) {
                            depItem.getChildren().add(itm);
                        }
                    }
                }
            }
        }

        root.getChildren().add(adminItem);
        root.getChildren().add(mngItem);
        root.getChildren().add(depItem);
        treeView.setRoot(root);

    }

    public class AuxLoginRepresentation {

        private String rol;
        private final List<String> usersNameList;

        public AuxLoginRepresentation() {
            usersNameList = new ArrayList<>();
        }

        /**
         * @return the rol
         */
        public String getRol() {
            return rol;
        }

        /**
         * @param rol the rol to set
         */
        public void setRol(String rol) {
            this.rol = rol;
        }

        public void addUser(String user) {
            usersNameList.add(user);
        }

        public List<String> getUsersList() {
            return usersNameList;
        }
    }

    public void onItemSelected() {
        try {
            String selected = treeView.getSelectionModel().getSelectedItem().getValue();
            Usuarios user = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(selected);
            if (user != null) {
                userNlb.setText(user.getUsuario());
                namelb.setText(user.getNombre());
                lnamelb.setText(user.getApellidos());
            }
        } catch (Exception e) {

        }
    }

    public void elimPrivilege() throws NonexistentEntityException {
        try {
            String selected = treeView.getSelectionModel().getSelectedItem().getValue();
            Usuarios user = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(selected);
            if (user != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> bt = alert.showAndWait();

                // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
                if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                    TreeItem<String> rolItem = treeView.getSelectionModel().getSelectedItem().getParent();
                    String rol = rolItem.getValue();
                    try {
                        ServicesLocator.getServicesLocatorInstance().getRolUsuarioServices().destroy(findRolUsuario(selected, rol));
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();
            }
            fullTableData();
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();
        }
    }

    public void addLogin() {
        main.loadAddLogin();
        stage.hide();
        stage.close();
    }

    public void elimUsuario() throws IllegalOrphanException, Exceptions.NonexistentEntityException {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        Usuarios user = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(selected);

        if (user != null) {
            if (!selected.equals(MainClass.getLoggedUsser().getUsuario())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, new LanguageSelector(MainClass.language).getProperty("tag_confirm_elim"), ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> bt = alert.showAndWait();

                // int op = JOptionPane.showConfirmDialog(null, "Esta sequro que desea eliminar este item, ESTA ACCION ES IRREVERSIBLE!!!!", "Eliminar modelo", JOptionPane.YES_NO_OPTION);
                if (bt.isPresent() && bt.get().equals(ButtonType.YES)) {
                    try {
                        ServicesLocator.getServicesLocatorInstance().getUsuarioServices().destroy(user.getId());
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, ErrorMessages.ELIMINATION_ERROR, ButtonType.OK).showAndWait();

                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.LOGGED_USER_ERROR, ButtonType.OK).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();

        }
        fullTableData();
    }

    public void modifyUser() {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        Usuarios user = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(selected);
        if (user != null) {
            main.loadAddUser(user);
            stage.hide();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();
        }
    }

    public void addUser() {
        main.loadAddUser(null);
        stage.hide();
        stage.close();
    }

    public void manageUsers() {
        // main.loadUserView();
        stage.hide();
        stage.close();
    }

    public void changePassword() {
        String selected = treeView.getSelectionModel().getSelectedItem().getValue();
        Usuarios user = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(selected);
        if (user != null) {
            main.loadChangePassword(user);
        } else {
            new Alert(Alert.AlertType.ERROR, ErrorMessages.NO_SELECTION_ERROR, ButtonType.OK).showAndWait();

        }
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

    public EventHandler onClose() {
        return (EventHandler) (Event event) -> {
            stage.hide();
            main.loadStartPage();
            stage.close();
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        };
    }

    public int findRolUsuario(String user, String rol) {
        List<RolUsuario> list = ServicesLocator.getServicesLocatorInstance().getRolUsuarioServices().findRolUsuarioEntities();
        for (RolUsuario ru : list) {
            if (ru.getRolIdRol().getTipoRol().equals(rol) && ru.getUsuarioIdUser().getUsuario().equals(user)) {
                return ru.getId();
            }
        }
        return -1;
    }

    private void setLanguage() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.addR.setText(prop.getProperty("tag_add_button"));
        this.addU.setText(prop.getProperty("tag_add_button"));
        this.changPassU.setText(prop.getProperty("tag_change_pass"));
        this.delR.setText(prop.getProperty("tag_del_button"));
        this.delU.setText(prop.getProperty("tag_del_button"));
        this.lnamelb2.setText(prop.getProperty("tag_lastName"));
        this.modU.setText(prop.getProperty("tag_modify_button"));
        this.namelb2.setText(prop.getProperty("tag_name"));
        this.userNlb2.setText(prop.getProperty("tag_user"));
    }
}
