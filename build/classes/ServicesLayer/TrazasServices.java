/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import ModelLayer.Trazas;
import ModelLayer.Usuarios;
import UtilsLayer.JPAUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yordanka
 */
public class TrazasServices implements Serializable {

    private static List<Trazas> tracesToPersist;

    public TrazasServices() {
        tracesToPersist = new LinkedList<>();
    }

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Trazas trazas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuario = trazas.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUser());
                trazas.setIdUsuario(idUsuario);
            }
            em.persist(trazas);
            if (idUsuario != null) {
                idUsuario.getTrazasList().add(trazas);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trazas> findTrazasEntities() {
        return findTrazasEntities(true, -1, -1);
    }

    public List<Trazas> findTrazasEntities(int maxResults, int firstResult) {
        return findTrazasEntities(false, maxResults, firstResult);
    }

    private List<Trazas> findTrazasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trazas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Trazas findTrazas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trazas.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrazasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trazas> rt = cq.from(Trazas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void saveTrace(Trazas t) {
//        System.out.println(t.toString());
        tracesToPersist.add(t);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void persistTraces() {
        try {
            Iterator<Trazas> tit = tracesToPersist.iterator();
            while (tit.hasNext()) {
                Trazas cursor = tit.next();
                if (cursor.getIdUsuario() != null && cursor.getIdTuplaModificada() > 0) {
                    create(cursor);
                }
            }
            tracesToPersist.clear();
            persistTracesToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void persistTracesToFile() {
        File file = new File("Log.csv");
        try (PrintWriter writer = new PrintWriter(file)) {
            String header = "Tabla/ Accion/ Id fila modificada/ Direccion ip/ Fecha/ Usuario/ Nombre del Usuario";
            writer.println(header);
            List<Trazas> tracesList = ServicesLocator.getServicesLocatorInstance().getTrazaServices().findTrazasEntities();
            for (Trazas t : tracesList) {
                String tabla = t.getTablaModificada();
                String accion = t.getOperacion();
                String id = String.valueOf(t.getIdTuplaModificada());
                String ip = t.getDireccionIp();
                String fecha = t.getFecha().toString();
                String usuario = t.getIdUsuario().getUsuario();
                String nombre = t.getIdUsuario().getNombre() + " " + t.getIdUsuario().getApellidos();
                writer.println(tabla + "/ " + accion + "/ " + id + "/ " + ip + "/ " + fecha + "/ " + usuario + "/ " + nombre);
            }
            writer.close();
        } catch (IOException e) {

        }

    }
}
