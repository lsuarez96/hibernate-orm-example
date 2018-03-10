/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ContratoPorModeloMarca;
import ServicesLayer.Views.ContratoPorModeloMarcaJpaController;
import UtilsLayer.MoneyFormatter;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class ContratosMarcaModeloAdapter implements JRDataSource {

    private final Iterator<ContratoPorModeloMarca> it = new ContratoPorModeloMarcaJpaController().findContratoPorModeloMarcaEntities().iterator();
    private ContratoPorModeloMarca cursor;

    @Override
    public boolean next() throws JRException {
        if (it.hasNext()) {
            cursor = it.next();
            return true;

        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        switch (jrf.getName()) {
            case "importe_total":
                return cursor.getImporteTotal();
            case "marca":
                return cursor.getNombreMarca();
            case "importe_marca": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getCalcularImporteMarca()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
            case "modelo":
                return cursor.getNombreModelo();
            case "total_carros":
                return cursor.getTotalCarrosMarMod();
            case "total_dias_alquilado":
                return cursor.getTotalDiasAlquilado();
            case "importe_tarjeta": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getImporteTarjeta()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
            case "importe_cheque": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getImporteCheque()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
            case "importe_efectivo": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getImporteEfectivo()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
        }
        return null;
    }

}
