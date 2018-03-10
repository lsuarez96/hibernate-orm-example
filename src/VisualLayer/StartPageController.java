/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ServicesLayer.ServicesLocator;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class StartPageController implements Initializable {

    private Stage stage;
    private MainClass main;
    @FXML
    private MenuItem manageUser;
    @FXML
    private Label userlb;
    @FXML
    private Menu auxtab;
    @FXML
    private Menu userMenu;
    @FXML
    private Menu rep;
    @FXML
    private Label contlb;
    @FXML
    private Label carlb;
    @FXML
    private Label drivlb;
    @FXML
    private Label turlb;
    @FXML
    private Circle cont;
    @FXML
    private Circle tur;
    @FXML
    private Circle car;
    @FXML
    private Circle driv;
    @FXML
    private MenuBar barraMenu;
    @FXML
    private CheckMenuItem spanish;
    @FXML
    private CheckMenuItem english;
    @FXML
    private Menu help;
    @FXML
    private MenuItem helpTopic;

    @FXML
    private MenuItem turByCount;
    @FXML
    private MenuItem carList;
    @FXML
    private MenuItem drivList;
    @FXML
    private MenuItem situation;
    @FXML
    private MenuItem incump;
    @FXML
    private MenuItem contByBrand;
    @FXML
    private MenuItem contByCount;
    @FXML
    private MenuItem incomings;
    @FXML
    private MenuItem contList;
    @FXML
    private MenuItem modelsMI;
    @FXML
    private MenuItem brandsMI;
    @FXML
    private MenuItem countriesMI;
    @FXML
    private MenuItem tariffMI;
    @FXML
    private MenuItem categoriesMI;
    @FXML
    private MenuItem payFormMI;
    @FXML
    private MenuItem lang;
    @FXML
    private MenuItem traceslb;

    @FXML
    private Hyperlink userhp;
    @FXML
    private ImageView backImage;

    @FXML
    MenuItem changepass;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        language(MainClass.language);
        switch (MainClass.language) {
            case "Español": {
                onSpanishSelected();
                spanish.setSelected(true);
            }
            break;
            case "Inglés": {
                onEnglishSelected();
                english.setSelected(true);
            }
            break;
            default:
                onSpanishSelected();
                break;
        }
        userhp.setText(MainClass.getLoggedUsser().getUsuario());
        // backImage.autosize();
        backImage.smoothProperty().set(false);

    }

    public void onContratoSelect() {
        main.loadContractsView();
    }

    public void onAutoSelect() {
        main.loadCarsView();
    }

    public void onChoferSelect() {
        main.loadDriversView();
    }

    public void onTuristaSelect() {
        main.loadTuristsView();
    }

    public void onModelsSelected() {
        main.loadModelsView();
    }

    public void onBrandsSelected() {
        main.loadBrandsView();
    }

    public void onTariffSelected() {
        main.loadTariffView();
    }

    public void onCategorySelected() {
        main.loadCatView();
    }

    public void onFormaPago() {
        main.loadPayFormView();
    }

    public void onCoutriesSelected() {
        main.loadCountriesView();
    }

    public void loadTuristByCountryReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadTuristsByCountryReport();
    }

    public void loadCarsReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadCarsReport();
    }

    public void loadContractsByBrandModelReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadContratPorMarcaModelo();
    }

    public void loadDriversReports() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadListadoChoferes();
    }

    public void loadSituationReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadListadoSituaciones();
    }

    public void loadContractListReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadListadoContratos();
    }

    public void loadIncumpReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadListadoIncumplidores();
    }

    public void loadContractByCountryReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadContratoPorPais();
    }

    public void loadIncomingsReport() {

        ServicesLocator.getServicesLocatorInstance().getReportServices().loadIncomingIngress();
    }

    public void close() {
        stage.close();
    }

    /**
     * @param stage the stage to set
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.onCloseRequestProperty().set(closeAction());

    }

    /**
     * @param main the main to set
     */
    @SuppressWarnings("element-type-mismatch")
    public void setMain(MainClass main) {
        this.main = main;
        if (!MainClass.getLoggedRoles().contains(LoggedRole.ADMIN)) {
            manageUser.setDisable(true);
            traceslb.setDisable(true);
            barraMenu.getMenus().remove(traceslb);
            barraMenu.getMenus().remove(manageUser);
        }

        if (!MainClass.getLoggedRoles().contains(LoggedRole.DEP) && !MainClass.getLoggedRoles().contains(LoggedRole.MNG)) {
            this.auxtab.setDisable(true);
            this.car.setDisable(true);
            this.car.setVisible(false);
            this.carlb.setDisable(true);
            carlb.setVisible(false);
            this.cont.setDisable(true);
            cont.setVisible(false);
            this.contlb.setDisable(true);
            contlb.setVisible(false);
            this.driv.setDisable(true);
            driv.setVisible(false);
            this.drivlb.setDisable(true);
            drivlb.setVisible(false);
            this.rep.setDisable(true);

            this.tur.setDisable(true);
            tur.setVisible(false);
            this.turlb.setDisable(true);
            turlb.setVisible(false);
            barraMenu.getMenus().remove(auxtab);
            barraMenu.getMenus().remove(rep);
        }
        if (!MainClass.getLoggedRoles().contains(LoggedRole.ADMIN)) {
            barraMenu.getMenus().remove(userMenu);
        }

    }

    public EventHandler closeAction() {

        EventHandler eh = (EventHandler) (Event event) -> {
            stage.hide();
            stage.close();
        };
        return eh;
    }

    public void onAboutPressed() {
        main.loadAboutView();
    }

    public void closeSession() {
        main.loadUserLoginView();
        stage.hide();
        stage.close();
    }

    public void changePass() {
        stage.hide();
        main.loadChangePasswordUser();
        stage.close();
    }

    public void manageUsers() {
        main.loadRolUserView();
        stage.hide();
        stage.close();
    }

    public void showTraces() {
        main.loadTracesView();
    }

    public void onSpanishSelected() {
        english.setSelected(false);
        MainClass.setLanguage("Español");
        language("Español");
    }

    public void onEnglishSelected() {
        spanish.setSelected(false);
        MainClass.setLanguage("Inglés");
        language("Inglés");
    }

    public void onMouseEntered() {
        userhp.setUnderline(true);
        userhp.effectProperty().set(new Glow());
    }

    public void onMouseExited() {
        userhp.setUnderline(false);
        userhp.effectProperty().set(null);
    }

    public void language(String lang) {
        LanguageSelector prop = new LanguageSelector(lang);
        this.auxtab.setText(prop.getProperty("tag_tablas_aux"));
        this.carList.setText(prop.getProperty("tag_list_car"));
        this.carlb.setText(prop.getProperty("tag_auto"));
        this.contByBrand.setText(prop.getProperty("tag_cont_brand_model"));
        this.contByCount.setText(prop.getProperty("tag_con_count"));
        this.contList.setText(prop.getProperty("tag_list_cont"));
        this.contlb.setText(prop.getProperty("tag_contract"));
        this.drivList.setText(prop.getProperty("tag_list_driv"));
        this.drivlb.setText(prop.getProperty("tag_driver"));
        this.english.setText("English");
        this.spanish.setText("Español");
        this.help.setText(prop.getProperty("tag_help"));
        this.helpTopic.setText(prop.getProperty("tag_help_topic"));
        this.incomings.setText(prop.getProperty("tag_import_sum"));
        this.incump.setText(prop.getProperty("tag_incump"));
        this.manageUser.setText(prop.getProperty("tag_manag_user"));
        this.rep.setText(prop.getProperty("tag_report"));
        this.situation.setText(prop.getProperty("tag_sit_car"));
        this.turByCount.setText(prop.getProperty("tag_tur_x_count"));
        this.turlb.setText(prop.getProperty("tag_turist"));
        this.userMenu.setText(prop.getProperty("tag_user"));
        this.modelsMI.setText(prop.getProperty("tag_modelo"));
        this.categoriesMI.setText(prop.getProperty("tag_category"));
        this.countriesMI.setText(prop.getProperty("tag_country"));
        this.brandsMI.setText(prop.getProperty("tag_marca"));
        this.payFormMI.setText(prop.getProperty("tag_pay_form"));
        this.tariffMI.setText(prop.getProperty("tag_tarif"));
        this.lang.setText(prop.getProperty("tag_lang"));
        changepass.setText(prop.getProperty("tag_change_pass"));
        this.traceslb.setText(prop.getProperty("title_traces"));
        try {
            stage.setTitle(prop.getProperty("title_start_page"));
        } catch (NullPointerException e) {

        }
    }
}
