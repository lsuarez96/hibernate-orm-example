/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import UtilsLayer.JPAUtil;
import UtilsLayer.LanguageSelector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yordanka
 */
public class LoadingController implements Initializable {

    @FXML
    private Label infoLab;
    @FXML
    private HBox hbox;
    @FXML
    private ProgressIndicator progInd;

    private Task progressTask;

    private Stage stage;
    private MainClass main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        infoLab.setText(new LanguageSelector(MainClass.language).getProperty("tag_conecting"));
        progressTask = getStartWorkerThread();
        progInd.setVisible(true);

        progInd.progressProperty().bind(progressTask.progressProperty());
        Thread h = new Thread(progressTask);
        h.start();
        progressTask.setOnCancelled(onCanceled(h));
    }

    private EventHandler onCanceled(Thread t) {
        return (EventHandler) (Event event) -> {
            t.interrupt();
            stage.hide();
            main.loadUserLoginView();
            stage.close();
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        };
    }

    private Task getStartWorkerThread() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                boolean end = true;
                while (end) {
                    progInd.progressProperty().unbind();
                    try {
                        JPAUtil.getEntityManager();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK)
                                .setHeaderText("FATAL: Database conection error");
                    }
                    progressTask.cancel(end);
                    end = false;
                }
//                progInd.setVisible(end);
//                hbox.setVisible(end);
                return !end;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
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
}
