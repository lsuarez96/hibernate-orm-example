/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Autos;
import ModelLayer.Choferes;
import ModelLayer.Contratos;
import ModelLayer.FormaPago;
import ModelLayer.Turista;
import ModelLayer.Usuarios;
import ServicesLayer.ServicesLocator;
import UtilsLayer.ErrorMessages;
import UtilsLayer.LanguageSelector;
import com.sun.javafx.collections.ImmutableObservableList;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddModifyContractController implements Initializable {

    private Stage stage;
    private MainClass main;
    private Contratos itemInstance;

    @FXML
    private ComboBox<Autos> carCB;
    @FXML
    private ComboBox<Turista> turistCB;
    @FXML
    private ComboBox<Choferes> driverCB;
    @FXML
    private DatePicker startDP;
    @FXML
    private DatePicker endDP;
    @FXML
    private DatePicker deliveryDP;
    @FXML
    private ComboBox<FormaPago> payCB;
    @FXML
    private TextField completTF;
    @FXML
    private ContextMenu completCM;
    @FXML
    private Label idturlb;
    @FXML
    private Label filtlb;
    @FXML
    private Label carlb;
    @FXML
    private Label startlb;
    @FXML
    private Label endlb;
    @FXML
    private Label drivlb;
    @FXML
    private Label deliverlb;
    @FXML
    private Label fplb;
    @FXML
    private Button accept;
    @FXML
    private Text txt;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Autos> listaAutos = getAvailableCars();
        Autos[] carList = new Autos[listaAutos.size()];
        listaAutos.toArray(carList);
        List<Turista> listaTur = ServicesLocator.getServicesLocatorInstance().getTuristaServices().findTuristaEntities();
        Turista[] turList = new Turista[listaTur.size()];
        listaTur.toArray(turList);
        List<Choferes> chofList = ServicesLocator.getServicesLocatorInstance().getChoferServices().findChoferesEntities();
        Choferes[] drivList = new Choferes[chofList.size()];
        chofList.toArray(drivList);
        List<FormaPago> fpList = ServicesLocator.getServicesLocatorInstance().getFormaPagoServices().findFormaPagoEntities();
        FormaPago[] payList = new FormaPago[fpList.size()];
        fpList.toArray(payList);
        ObservableList<Autos> carItems = new ImmutableObservableList<>(carList);
        ObservableList<Turista> turItems = new ImmutableObservableList<>(turList);
        ObservableList<Choferes> drivItems = new ImmutableObservableList<>(drivList);
        ObservableList<FormaPago> payItems = new ImmutableObservableList<>(payList);
        carCB.setItems(carItems);
        turistCB.setItems(turItems);
        driverCB.setItems(drivItems);
        payCB.setItems(payItems);
        setLang();

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

    /**
     * @param itemInstance the itemInstance to set
     */
    public void setItemInstance(Contratos itemInstance) {
        this.itemInstance = itemInstance;
        if (itemInstance != null) {
            carCB.getSelectionModel().select(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutos(itemInstance.getContIdAuto().getId()));
            turistCB.getSelectionModel().select(ServicesLocator.getServicesLocatorInstance().getTuristaServices().findTurista(itemInstance.getContIdTur().getId()));
            carCB.setEditable(false);
            turistCB.setEditable(false);
            try {
                driverCB.getSelectionModel().select(ServicesLocator.getServicesLocatorInstance().getChoferServices().findChoferes(itemInstance.getContIdChof().getId()));
            } catch (NullPointerException e) {

            }
            payCB.getSelectionModel().select(ServicesLocator.getServicesLocatorInstance().getFormaPagoServices().findFormaPago(itemInstance.getContIdFormaPago().getId()));
            LocalDate l = LocalDate.parse(itemInstance.getFechaI().toString());
            startDP.setValue(LocalDate.parse(itemInstance.getFechaI().toString()));
            endDP.setValue(LocalDate.parse(itemInstance.getFechaF().toString()));
            try {
                deliveryDP.setValue(LocalDate.parse(itemInstance.getFechaEntrega().toString()));
            } catch (NullPointerException e) {

            }

        } else {
            deliverlb.setDisable(true);
            deliveryDP.setEditable(false);
            deliveryDP.setDisable(true);

        }
    }

    public void onAcceptPressed() throws Exception {
        boolean exit = false;
        if (itemInstance != null) {
            Turista idTur = itemInstance.getContIdTur();
            Autos plate = itemInstance.getContIdAuto();
            Choferes idChof;
            try {
                idChof = driverCB.getSelectionModel().getSelectedItem();
            } catch (NullPointerException e) {
                idChof = null;
            }
            FormaPago pay = payCB.getSelectionModel().getSelectedItem();

            LocalDate startD = startDP.getValue();
            LocalDate endD = endDP.getValue();
            LocalDate deliveryD = deliveryDP.getValue();
            Date sDate = null;
            Date eDate = null;
            Date dDate = null;
            try {
                sDate = Date.valueOf(startD);
                eDate = Date.valueOf(endD);
                dDate = Date.valueOf(deliveryD);
            } catch (NullPointerException e) {

            }
            if (!(idTur == null && plate == null && pay == null && sDate == null && eDate == null)) {
                Contratos c = new Contratos();

                Usuarios id_usuario = MainClass.getLoggedUsser();
                c.setIdUsuario(id_usuario);
                c.setContIdAuto(plate);
                c.setContIdChof(idChof);
                c.setContIdFormaPago(pay);
                c.setContIdTur(idTur);
                c.setFechaF(eDate);
                c.setFechaI(sDate);
                c.setIdContrato(itemInstance.getIdContrato());
                try {
                    ServicesLocator.getServicesLocatorInstance().getContratoServices().edit(c);
                    if (dDate != null) {
                        c.setFechaEntrega(dDate);
                        ServicesLocator.getServicesLocatorInstance().getContratoServices().edit(c);
                    }
                    exit = true;
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                    alert.setHeaderText(ErrorMessages.MODIFICATION_ERROR);
                    alert.showAndWait();
                    // JOptionPane.showMessageDialog(null, ErrorMessages.MODIFICATION_ERROR);
                    Logger.getLogger(AddModifyContractController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
                // JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
            }
        } else {
            Turista idTur = turistCB.getSelectionModel().getSelectedItem();
            Autos plate = carCB.getSelectionModel().getSelectedItem();
            Choferes idChof;
            try {
                idChof = driverCB.getSelectionModel().getSelectedItem();
            } catch (NullPointerException e) {
                idChof = null;
            }
            FormaPago pay = payCB.getSelectionModel().getSelectedItem();
            LocalDate startD = startDP.getValue();
            LocalDate endD = endDP.getValue();
            // LocalDate deliveryD = deliveryDP.getValue();
            Date sDate = null;
            Date eDate = null;

            try {
                sDate = Date.valueOf(startD);
                eDate = Date.valueOf(endD);
                // dDate = new Date(deliveryD.getYear(), deliveryD.getMonthValue(), deliveryD.getDayOfMonth());
            } catch (NullPointerException e) {

            }
            if (!(idTur == null && plate == null && pay == null && sDate == null && eDate == null)) {
                Contratos c = new Contratos();
                c.setContIdAuto(plate);
                c.setContIdChof(idChof);
                c.setContIdFormaPago(pay);
                c.setContIdTur(idTur);
                c.setFechaI(sDate);
                c.setFechaF(eDate);
                c.setIdUsuario(MainClass.getLoggedUsser());
                try {
                    ServicesLocator.getServicesLocatorInstance().getContratoServices().create(c);
                    exit = true;
                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.setHeaderText(ErrorMessages.INSERTION_ERROR);
                    alert.showAndWait();
                    // JOptionPane.showMessageDialog(null, ErrorMessages.INSERTION_ERROR);
                }

            } else {
                new Alert(Alert.AlertType.ERROR, ErrorMessages.EMPTY_FIELD_ERROR, ButtonType.OK).showAndWait();
            }
        }
        if (exit) {
            main.loadContractsView();
            stage.hide();
            stage.close();
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

    public List<Autos> getAvailableCars() {
        List<Autos> list = ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities();
        Iterator<Autos> it = list.iterator();
        while (it.hasNext()) {
            if (!new LanguageSelector(MainClass.language).getProperty("tag_free").equalsIgnoreCase(it.next().getIdSituacionAuto().getTipoSituacion())) {
                it.remove();
            }
        }
        return list;
    }

    public void onKeyPressedForFilter() {
        List<Autos> listaAutos = getAvailableCars();
        String[] completeResult = completTF.getText().split(" ");
        for (String completeResult1 : completeResult) {
            Iterator<Autos> it = listaAutos.iterator();
            while (it.hasNext()) {
                Autos tempA = it.next();
                String[] a = tempA.toString().split(" ");
                boolean exist = false;
                for (String res2 : a) {
                    if (res2.toLowerCase().matches(completeResult1) || res2.toLowerCase().startsWith(completeResult1)) {
                        exist = true;

                    }
                }
                if (!exist) {
                    it.remove();
                }
            }
        }
        Autos[] carList = new Autos[listaAutos.size()];
        listaAutos.toArray(carList);
        completCM.getItems().clear();
        Iterator<Autos> it = listaAutos.iterator();
        while (it.hasNext()) {
            MenuItem item = new MenuItem(it.next().toString());
            item.onActionProperty().set(onMenuItemSelected());
            completCM.getItems().add(item);
        }
        completCM.show(completTF, Side.BOTTOM, 0, 0);
        Autos[] carList2 = new Autos[getAvailableCars().size()];
        getAvailableCars().toArray(carList2);
        carCB.setItems(new ImmutableObservableList<>(carList2));

    }

    public EventHandler onMenuItemSelected() {
        return (EventHandler) (Event event) -> {
            //To change body of generated methods, choose Tools | Templates.
            completTF.setText(((MenuItem) event.getSource()).getText());
            List<Autos> list = getAvailableCars();
            Autos a = null;
            Iterator<Autos> it = list.iterator();
            boolean found = false;
            while (it.hasNext() && !found) {
                Autos temp = it.next();
                if (temp.toString().toLowerCase().equals(completTF.getText().toLowerCase())) {
                    a = temp;
                    found = true;
                }
            }
            carCB.getSelectionModel().select(a);
        };
    }

    public Contratos findContract(Contratos c) {
        List<Contratos> list = ServicesLocator.getServicesLocatorInstance().getContratoServices().findContratosEntities();
        for (Contratos c1 : list) {
            if (c == null) {
                return c;
            } else if (c.getContIdAuto().getChapa().equals(c1.getContIdAuto().getChapa())
                    && c.getContIdTur().getPasaporte().equals(c1.getContIdTur().getPasaporte())
                    && c.getFechaI() == c1.getFechaI()) {
                return c;
            }
        }
        return null;
    }

    private void setLang() {
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.idturlb.setText(prop.getProperty("tag_passport"));
        this.filtlb.setText(prop.getProperty("tag_filt"));
        this.carlb.setText(prop.getProperty("tag_auto"));
        this.deliverlb.setText(prop.getProperty("tag_delivery"));
        this.drivlb.setText(prop.getProperty("tag_driver"));
        this.endlb.setText(prop.getProperty("tag_end_date"));
        this.startlb.setText(prop.getProperty("tag_start_date"));
        this.fplb.setText(prop.getProperty("tag_pay_form"));
        this.accept.setText(prop.getProperty("tag_accept"));
        txt.setText(prop.getProperty("tag_contract"));
    }

    public EventHandler onClose() {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                stage.hide();
                main.loadContractsView();
                stage.close();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
