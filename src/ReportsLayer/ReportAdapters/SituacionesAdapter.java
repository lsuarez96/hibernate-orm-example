/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ListadoSituaciones;
import ServicesLayer.Views.ListadoSituacionesJpaController;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class SituacionesAdapter implements JRDataSource {

    private final Iterator<ListadoSituaciones> it = new ListadoSituacionesJpaController().findListadoSituacionesEntities().iterator();
    private ListadoSituaciones cursor;

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
            case "situacion":
                return cursor.getTipoSituacion();
            case "alquilado":
                return cursor.getEstaAlquilado();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
