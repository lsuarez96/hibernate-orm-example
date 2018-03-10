/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Yordanka
 */
public class MoneyFormatter {

    public static String conversion(Double valor) {
        Locale.setDefault(Locale.US);
        if (valor == null) {
            valor = 0d;
        }
        System.out.println(DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        return NumberFormat.getCurrencyInstance().format(valor);
    }
    public static void main(String[] args) {
        conversion(0d);
    }
}
