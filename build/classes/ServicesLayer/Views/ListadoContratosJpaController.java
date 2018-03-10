/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ListadoContratos;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yordanka
 */
public class ListadoContratosJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<ListadoContratos> findListadoContratosEntities() {
        return findListadoContratosEntities(true, -1, -1);
    }

    public List<ListadoContratos> findListadoContratosEntities(int maxResults, int firstResult) {
        return findListadoContratosEntities(false, maxResults, firstResult);
    }

    private List<ListadoContratos> findListadoContratosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListadoContratos.class));
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

    public ListadoContratos findListadoContratos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListadoContratos.class, id);
        } finally {
            em.close();
        }
    }

    public int getListadoContratosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListadoContratos> rt = cq.from(ListadoContratos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
