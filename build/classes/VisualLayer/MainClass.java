/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import ModelLayer.Autos;
import ModelLayer.Choferes;
import ModelLayer.Contratos;
import ModelLayer.Modelos;
import ModelLayer.Turista;
import ModelLayer.Usuarios;
import UtilsLayer.LanguageSelector;
import UtilsLayer.LoggedRole;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Luisito
 */
@SuppressWarnings("UseSpecificCatch")
public class MainClass extends Application {

    private static Usuarios loggedUsser;
    private static List<LoggedRole> loggedRoles;
    public static String language = "Español";
    private LanguageSelector prop;

    @Override
    @SuppressWarnings("empty-statement")
    public void start(Stage primaryStage) {
        loadLanguageFromConfigFile();
        prop = new LanguageSelector(language);
        loadProgressIndicator(primaryStage);
        // loadUserLoginView();
    }

    public void loadCarsView() {
        prop = new LanguageSelector(language);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CarsView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_cars_view"));
            CarsViewController controller = (CarsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadTuristsView() {
        prop = new LanguageSelector(language);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TuristsView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_turist_view"));
            stage.setScene(scene);
            TuristsViewController controller = (TuristsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMainClass(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadProgressIndicator(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Loading.fxml"));

            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.initStyle(StageStyle.UNDECORATED);
            ((LoadingController) (loader.getController())).setStage(stage);
            ((LoadingController) (loader.getController())).setMain(this);
            //stage.setTitle("Gestor de turistas...");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadDriversView() {
        prop = new LanguageSelector(language);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DriversView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle(prop.getProperty("title_drivers_view"));
            stage.setResizable(false);
            stage.setScene(scene);
            DriversViewController controller = (DriversViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadContractsView() {
        prop = new LanguageSelector(language);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ContractsView.fxml"));
            Stage stage = new Stage();
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle(prop.getProperty("title_contracts_view"));
            ContractsViewController controller = (ContractsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);

            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void loadAddorModifyCar(Autos a) {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyCar.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_car"));

            AddorModifyCarController controller = (AddorModifyCarController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setAutoInstance(a);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadAddOrModifyTurist(Turista t) {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyTurist.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_turist"));

            AddorModifyTuristController controller = (AddorModifyTuristController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setItemInstance(t);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadAddorModifyDriver(Choferes c) {
        prop = new LanguageSelector(language);
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyDriver.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_driver"));
            stage.setResizable(false);
            AddorModifyDriverController controller = (AddorModifyDriverController) loader.getController();
            controller.setItemInstance(c);
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadAddorModifyModel(Modelos m) {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddorModifyModel.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_model"));
            stage.setResizable(false);
            AddorModifyModelController controller = (AddorModifyModelController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setModelInstance(m);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddBrand() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBrand.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_brand"));
            stage.setScene(scene);
            AddBrandController controller = (AddBrandController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadModelsView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModelsView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_model_view"));
            ModelsViewController controller = (ModelsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadBrandsView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BrandsView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_brand_view"));
            BrandsViewController controller = (BrandsViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadAddModifyTariff() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyTarif.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_tariff"));
            stage.setScene(scene);
            AddModifyTarifController controller = (AddModifyTarifController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);

            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTariffView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TariffView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle(prop.getProperty("title_tariff_view"));
            stage.setScene(scene);
            stage.setResizable(false);
            TariffViewController controller = (TariffViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddCat() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCategory.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Insertar Categoria...");
            AddCategoryController controller = (AddCategoryController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            // Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCatView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CategoryView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_category_view"));
            CategoryViewController controller = (CategoryViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);

            stage.show();
        } catch (IOException ex) {
            // Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadPayFormView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PayFormView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_pay_form"));
            PayFormViewController controller = (PayFormViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadStartPage() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartPage.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setTitle(prop.getProperty("title_start_page"));
            stage.setScene(scene);
            //stage.setResizable(false);
            StartPageController controller = (StartPageController) loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("static-access")
    public void loadAddModifyContract(Contratos c) {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyContract.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_contract"));
            AddModifyContractController controller = (AddModifyContractController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setItemInstance(c);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void loadAddCountry() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyCountry.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            AddModifyCountryController controller = (AddModifyCountryController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadCountriesView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CountryView.fxml"));
        Stage stage = new Stage();
        try {
            //loader.load(getClass().getResource("AddModifyContract.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            Scene scene = new Scene(pane);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle(prop.getProperty("title_country_view"));
            CountryViewController controller = (CountryViewController) loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddLogin() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLogin.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_login"));
            AddLoginController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddLogin(Usuarios u) {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLogin.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_login"));
            AddLoginController controller = loader.getController();
            controller.setMain(this);
            controller.setStage(stage);
            controller.setUserInstance(u);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAddUser(Usuarios u) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        Stage stage = new Stage();
        prop = new LanguageSelector(language);
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_add_user"));
            AddUserController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setItemInstance(u);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUserView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(prop.getProperty("title_user_view"));
            UsersViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAdminView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            AdminViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUserLoginView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        // Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            // stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            ProgressIndicator pi = new ProgressIndicator();
            pi.setPrefSize(100, 100);
            pane.getChildren().add(0, pi);
            stage.setAlwaysOnTop(true);
            LoginViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            // stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUserLoginView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            LoginViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAboutView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("About.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            //stage.setAlwaysOnTop(true);
            stage.setTitle(prop.getProperty("title_about"));
            AboutController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadRolUserView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RolUserView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle(prop.getProperty("title_user_view"));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            RolUserViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadChangePassword(Usuarios u) {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Password.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle(prop.getProperty("title_change_password"));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            PasswordController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            controller.setUserInstance(u);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadChangePasswordUser() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePasswordView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(false);
            //stage.setAlwaysOnTop(true);
            stage.setTitle(prop.getProperty("title_change_password"));
            ChangePasswordViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the loggedRoles
     */
    public static List<LoggedRole> getLoggedRoles() {
        return loggedRoles;
    }

    /**
     * @param loggedRoles the loggedRoles to set
     */
    public static void setLoggedRoles(List<LoggedRole> loggedRoles) {
        MainClass.loggedRoles = loggedRoles;
    }

    public static void setLoggedUsser(Usuarios u) {
        loggedUsser = u;
    }

    /**
     * @return the loggedUsser
     */
    public static Usuarios getLoggedUsser() {
        return loggedUsser;
    }

    public void loadLanguageFromConfigFile() {
        File file = new File("app.conf");
        try (RandomAccessFile f = new RandomAccessFile(file, "rw")) {
            String lang = "";
            if (file.exists() && f.getFilePointer() < f.length()) {
                lang = f.readUTF();
            }
            if ("Español".contains(lang) || "Inglés".contains(lang)) {
                language = lang;
            } else {
                language = "Español";
            }
        } catch (IOException e) {

        }
    }

    public static void setLanguage(String lang) {
        MainClass.language = lang;
        File file = new File("app.conf");
        if (file.exists()) {
            file.delete();
            file = new File("app.conf");
        }
        try (RandomAccessFile f = new RandomAccessFile(file, "rw")) {
            f.writeUTF(lang);
        } catch (IOException e) {

        }
    }

    public static String getLangCode() {
        if (language == "Español") {
            return "";
        }
        return "En";
    }

    public void loadTracesView() {
        prop = new LanguageSelector(language);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TracesView.fxml"));
        Stage stage = new Stage();
        try {
            AnchorPane pane = loader.load();
            //stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle(prop.getProperty("title_traces"));
            //stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            TracesViewController controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
