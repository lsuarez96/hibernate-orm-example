/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportsLayer.ReportAdapters;

import ModelLayer.Views.ListadoChofer;
import ServicesLayer.Views.ListadoChoferJpaController;
import java.util.Iterator;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Yordanka
 */
public class DriverAdapter implements JRDataSource {

    private final Iterator<ListadoChofer> it = new ListadoChoferJpaController().findListadoChoferEntities().iterator();
    private ListadoChofer cursor;

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
            case "numero_id":
                return cursor.getNumeroId();
            case "nombre":
                return cursor.getNombre();
            case "apellidos":
                return cursor.getApellidos();
            case "direccion":
                return cursor.getDireccion();
            case "categoria":
                return cursor.getCategoria();
            case "cant_carros_manejados_chofer":
                return cursor.getCantCarrosManejadosChofer();
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
