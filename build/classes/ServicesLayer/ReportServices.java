/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ReportsLayer.ReportAdapters.CarAdapter;
import ReportsLayer.ReportAdapters.ContratoAdapter;
import ReportsLayer.ReportAdapters.ContratoPaisAdapter;
import ReportsLayer.ReportAdapters.ContratosMarcaModeloAdapter;
import ReportsLayer.ReportAdapters.DriverAdapter;
import ReportsLayer.ReportAdapters.IncumplidoresAdapter;
import ReportsLayer.ReportAdapters.ListadoIngresosAdapter;
import ReportsLayer.ReportAdapters.SituacionesAdapter;
import ReportsLayer.ReportAdapters.TuristaAdapter;
import VisualLayer.MainClass;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Luisito Suarez
 */
public class ReportServices {

    public void loadTuristsByCountryReport() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/listado_turistas" + cod + ".jasper";
        JRDataSource ds = new TuristaAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadCarsReport() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/listado_autos" + cod + ".jasper";
        JRDataSource ds = new CarAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadListadoContratos() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/listado_contratos" + cod + ".jasper";
        JRDataSource ds = new ContratoAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadListadoChoferes() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/listado_choferes" + cod + ".jasper";
        JRDataSource ds = new DriverAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadListadoSituaciones() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/listado_situaciones" + cod + ".jasper";
        JRDataSource ds = new SituacionesAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadListadoIncumplidores() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/turistas_incumplidores" + cod + ".jasper";
        JRDataSource ds = new IncumplidoresAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadContratPorMarcaModelo() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/contratos_marca_modelo" + cod + ".jasper";
        JRDataSource ds = new ContratosMarcaModeloAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadContratoPorPais() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/contratos_por_pais" + cod + ".jasper";
        JRDataSource ds = new ContratoPaisAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadIncomingIngress() {
        String cod = MainClass.getLangCode();
        String path = "src/ReportsLayer/ingresos_anuales2" + cod + ".jasper";
        JRDataSource ds = new ListadoIngresosAdapter();
        try {
            JasperPrint print = JasperFillManager.fillReport(path, null, ds);
            // System.out.println(ServicesLocator.getServicesLocatorInstance().getAutosServices().findAutosEntities());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(JasperManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
