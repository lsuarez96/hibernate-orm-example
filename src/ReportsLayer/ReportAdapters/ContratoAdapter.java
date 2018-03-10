/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ListadoContratos;
import ServicesLayer.Views.ListadoContratosJpaController;
import UtilsLayer.MoneyFormatter;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class ContratoAdapter implements JRDataSource {

    private final Iterator<ListadoContratos> it = new ListadoContratosJpaController().findListadoContratosEntities().iterator();
    private ListadoContratos cursor;

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
            case "modelo":
                return cursor.getNombreModelo();
            case "marca":
                return cursor.getNombreMarca();
            case "listado_contratos_chapa":
                return cursor.getChapa();
            case "forma_pago":
                return cursor.getTipoPago();
            case "fecha_i":
                return cursor.getFechaI();
            case "fecha_f":
                return cursor.getFechaF();
            case "dias_prorroga_contrato":
                return cursor.getCalcularDiasProrrogaContrato();
            case "chofer":
                return cursor.getHayChofer();
            case "importe_total": {
                try {
                    return MoneyFormatter.conversion(Double.valueOf(cursor.getImporteTotal()));
                } catch (Exception e) {
                    return (MoneyFormatter.conversion(0d));
                }
            }
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
