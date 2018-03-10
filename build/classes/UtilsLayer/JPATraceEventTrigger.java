/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

import ModelLayer.Trazas;
import ServicesLayer.ServicesLocator;
import VisualLayer.MainClass;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

/**
 *
 * @author Luisito Suarez
 */
public class JPATraceEventTrigger {

    @PostPersist
    public void onSave(Object o) {
        System.out.println("Entro evento");
        try {
            if (o instanceof Auditable) {
                Auditable auditItem = (Auditable) o;
                int id = auditItem.getId();
                Trazas t = new Trazas();
                t.setDireccionIp(InetAddress.getLocalHost().getHostAddress());
                t.setFecha(GregorianCalendar.getInstance().getTime());
                t.setIdTuplaModificada(id);
                t.setOperacion("CREATE");
                t.setIdUsuario(MainClass.getLoggedUsser());
                t.setTablaModificada(auditItem.getClass().getSimpleName());
                Calendar c = GregorianCalendar.getInstance();
                int h = c.get(Calendar.HOUR_OF_DAY);
                int m = c.get(Calendar.MINUTE);
                int s = c.get(Calendar.SECOND);
                t.setHora(HourConverter.longToString(h, m, s));
                ServicesLocator.getServicesLocatorInstance().getTrazaServices().saveTrace(t);
                System.out.println(t);
                 new DBbackup("localhost", "5432", "postgres", "rentaCar", "0000", "9.3").performBackup();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(JPATraceEventTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostLoad
    @SuppressWarnings("UseSpecificCatch")
    public void onRead(Object o) {
        Auditable auditItem = null;
        if (o instanceof Auditable) {
            auditItem = (Auditable) o;
        }
        try {
            @SuppressWarnings("null")
            int id = auditItem.getId();
            Trazas t = new Trazas();
            t.setDireccionIp(InetAddress.getLocalHost().getHostAddress());
            t.setFecha(GregorianCalendar.getInstance().getTime());
            t.setIdTuplaModificada(id);
            t.setOperacion("READ");
            t.setIdUsuario(MainClass.getLoggedUsser());
            t.setTablaModificada(auditItem.getClass().getSimpleName());
            //ServicesLocator.getServicesLocatorInstance().getTrazaServices().saveTrace(t);
            System.out.println(t);
        } catch (Exception ex) {
            Logger.getLogger(JPATraceEventTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostUpdate
    public void onUpdate(Object o) {
        Auditable auditItem = null;
        if (o instanceof Auditable) {
            auditItem = (Auditable) o;
        }
        try {
            @SuppressWarnings("null")
            int id = auditItem.getId();
            Trazas t = new Trazas();
            t.setDireccionIp(InetAddress.getLocalHost().getHostAddress());
            t.setFecha(GregorianCalendar.getInstance().getTime());
            t.setIdTuplaModificada(id);
            t.setOperacion("UPDATE");
            t.setIdUsuario(MainClass.getLoggedUsser());
            t.setTablaModificada(auditItem.getClass().getSimpleName());
            Calendar c = GregorianCalendar.getInstance();
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);
            int s = c.get(Calendar.SECOND);
            t.setHora(HourConverter.longToString(h, m, s));
            ServicesLocator.getServicesLocatorInstance().getTrazaServices().saveTrace(t);
            System.out.println(t);
             new DBbackup("localhost", "5432", "postgres", "rentaCar", "0000", "9.3").performBackup();
        } catch (UnknownHostException ex) {
            Logger.getLogger(JPATraceEventTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostRemove
    public void onDelete(Object o) {
        Auditable auditItem = null;
        if (o instanceof Auditable) {
            auditItem = (Auditable) o;
        }
        try {
            @SuppressWarnings("null")
            int id = auditItem.getId();
            Trazas t = new Trazas();
            t.setDireccionIp(InetAddress.getLocalHost().getHostAddress());
            t.setFecha(GregorianCalendar.getInstance().getTime());
            t.setIdTuplaModificada(id);
            t.setOperacion("DELETE");
            t.setIdUsuario(MainClass.getLoggedUsser());
            t.setTablaModificada(auditItem.getClass().getSimpleName());
            Calendar c = GregorianCalendar.getInstance();
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);
            int s = c.get(Calendar.SECOND);
            t.setHora(HourConverter.longToString(h, m, s));
            ServicesLocator.getServicesLocatorInstance().getTrazaServices().saveTrace(t);
            System.out.println(t);
             new DBbackup("localhost", "5432", "postgres", "rentaCar", "0000", "9.3").performBackup();
        } catch (UnknownHostException ex) {
            Logger.getLogger(JPATraceEventTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
