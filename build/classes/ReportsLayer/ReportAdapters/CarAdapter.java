/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ListadoAutos;
import ServicesLayer.Views.ListadoAutosJpaController;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class CarAdapter implements JRDataSource {

    private final Iterator<ListadoAutos> it = new ListadoAutosJpaController().findListadoAutosEntities().iterator();
    private ListadoAutos cursor;

    @Override
    public boolean next() throws JRException {
        if (it.hasNext()) {
            cursor = it.next();
            return true;

        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        switch (jrf.getName()) {
            case "chapa":
                return cursor.getChapa();
            case "marca":
                return cursor.getNombreMarca();
            case "modelo":
                return cursor.getNombreModelo();
            case "color":
                return cursor.getColor();
            case "kilometros":
                return cursor.getKilometros();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
