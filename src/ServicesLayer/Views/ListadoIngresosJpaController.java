/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ListadoIngresos;
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
public class ListadoIngresosJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<ListadoIngresos> findListadoIngresosEntities() {
        return findListadoIngresosEntities(true, -1, -1);
    }

    public List<ListadoIngresos> findListadoIngresosEntities(int maxResults, int firstResult) {
        return findListadoIngresosEntities(false, maxResults, firstResult);
    }

    private List<ListadoIngresos> findListadoIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListadoIngresos.class));
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

    public ListadoIngresos findListadoIngresos(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListadoIngresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getListadoIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListadoIngresos> rt = cq.from(ListadoIngresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
