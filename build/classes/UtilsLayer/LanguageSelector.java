/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Yordanka
 */
public class LanguageSelector extends Properties {

    private static final long serialVersionUID = 1L;

    public LanguageSelector(String idioma) {
        switch (idioma) {
            case "Español":
                getProperties("ResourceEs_es.properties");
                break;
            case "Inglés":
                getProperties("ResourceEn_en.properties");
                break;
            default:
                getProperties("ResourceEs_es.properties");
                break;
        }
    }

    public LanguageSelector() {
        // TODO Auto-generated constructor stub
    }

    /* se leen las propiedades */
    private void getProperties(String idioma) {
        try {
            this.load(getClass().getResourceAsStream(idioma));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    Locale idioma;

    public Locale getIdioma() {
        if (idioma == null) {
            idioma = new Locale("es", "ES");
        }
        return idioma;
    }

    public void cambiarIngles() {
        idioma = new Locale("en", "EN");
    }

    public void cambiarSpanish() {
        idioma = new Locale("es", "ES");
    }

}
