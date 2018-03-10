/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ContratoPais;
import ServicesLayer.Views.ContratoPaisJpaController;
import UtilsLayer.MoneyFormatter;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class ContratoPaisAdapter implements JRDataSource {

    private final Iterator<ContratoPais> it = new ContratoPaisJpaController().findContratoPaisEntities().iterator();
    private ContratoPais cursor;

    @Override
    public boolean next() throws JRException {
        //To change body of generated methods, choose Tools | Templates.
        if (it.hasNext()) {
            cursor = it.next();
            return true;
        }
        return false;

    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        switch (jrf.getName()) {
            case "pais":
                return cursor.getNombrePais();
            case "marca":
                return cursor.getNombreMarca();
            case "modelo":
                return cursor.getNombreModelo();
            case "dias_alquilado":
                return cursor.getDiasAlquilado();
            case "dias_prorroga":
                return cursor.getDiasProrroga();
            case "importe_efectivo": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getImporteEfectivo()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
            case "importe_marca": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getCalcularImporteMarca()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
        }

        return null;
    }

}
