/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ListadoSituaciones;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yordanka
 */
public class ListadoSituacionesJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<ListadoSituaciones> findListadoSituacionesEntities() {
        return findListadoSituacionesEntities(true, -1, -1);
    }

    public List<ListadoSituaciones> findListadoSituacionesEntities(int maxResults, int firstResult) {
        return findListadoSituacionesEntities(false, maxResults, firstResult);
    }

    private List<ListadoSituaciones> findListadoSituacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListadoSituaciones.class));
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

    public ListadoSituaciones findListadoSituaciones(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListadoSituaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getListadoSituacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListadoSituaciones> rt = cq.from(ListadoSituaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
