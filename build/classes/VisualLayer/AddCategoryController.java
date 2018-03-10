/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualLayer;

import UtilsLayer.LanguageSelector;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luisito
 */
public class AddCategoryController implements Initializable {

    private Stage stage;
    private MainClass main;

    @FXML
    private TextField cat;
    @FXML
    private Label catlb;
    @FXML
    private Button accept;
    @FXML
    private Text cattxt;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LanguageSelector prop = new LanguageSelector(MainClass.language);
        this.accept.setText(prop.getProperty("tag_accept"));
        this.catlb.setText(prop.getProperty("tag_cat"));
        this.cattxt.setText(prop.getProperty("tag_cat"));
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

    public void onAdd() {
        /*
        String c = cat.getText().toLowerCase();
        Categorias temp = ServicesLocator.getCategoriasServices().retriveCategory(c);
        if (!c.isEmpty() && temp == null) {
            try {
                ServicesLocator.getCategoriasServices().insertCategory(new Categorias(c));
            } catch (InsertionModificationException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            stage.hide();
            stage.close();
        } else if (temp != null) {
            JOptionPane.showMessageDialog(null, "La categoria ya existe");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios");
        }*/
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

}
