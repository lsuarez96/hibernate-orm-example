/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ListadoIngresos;
import ServicesLayer.Views.ListadoIngresosJpaController;
import UtilsLayer.MoneyFormatter;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class ListadoIngresosAdapter implements JRDataSource {

    Iterator<ListadoIngresos> ingIt = new ListadoIngresosJpaController().findListadoIngresosEntities().iterator();
    ListadoIngresos cursor;

    @Override
    public boolean next() throws JRException {
        if (ingIt.hasNext()) {
            cursor = ingIt.next();
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        switch (jrf.getName()) {
            case "ingreso_anno":
                return MoneyFormatter.conversion(Double.valueOf(cursor.getIngresoAnno()));
            case "anno":
                return cursor.getAnno();
            case "mes":
                return cursor.getMes();
            case "ingreso_mes":
                return MoneyFormatter.conversion(Double.valueOf(cursor.getIngresoMes()));
        }
        return null;
    }

}
