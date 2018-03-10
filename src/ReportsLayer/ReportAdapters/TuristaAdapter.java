/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.TuristasPais;
import ServicesLayer.Views.TuristasPaisJpaController;
import UtilsLayer.MoneyFormatter;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class TuristaAdapter implements JRDataSource {

    private final Iterator<TuristasPais> it = new TuristasPaisJpaController().findTuristasPaisEntities().iterator();
    private TuristasPais cursor;

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
            case "pais":
                return cursor.getNombrePais();
            case "pasaporte":
                return cursor.getPasaporte();
            case "nombre":
                return cursor.getNombre();
            case "apellidos":
                return cursor.getApellidos();
            case "autos_alquilados":
                return cursor.getAutosAlquilados();
            case "importe_total": {
                try {
                    return (MoneyFormatter.conversion(Double.valueOf(cursor.getImporteTotal())));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
