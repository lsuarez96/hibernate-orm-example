/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ListadoIncumplidores;
import ServicesLayer.Views.ListadoIncumplidoresJpaController;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class IncumplidoresAdapter implements JRDataSource {

    private final Iterator<ListadoIncumplidores> it = new ListadoIncumplidoresJpaController().findListadoIncumplidoresEntities().iterator();
    private ListadoIncumplidores cursor;

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
            case "nombre":
                return cursor.getNombre();
            case "apellidos":
                return cursor.getApellidos();
            case "fecha_fin":
                return cursor.getFechaF();
            case "fecha_entrega":
                return cursor.getFechaEntrega();
        }

        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
