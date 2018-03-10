/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ListadoAutos;
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
public class ListadoAutosJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<ListadoAutos> findListadoAutosEntities() {
        return findListadoAutosEntities(true, -1, -1);
    }

    public List<ListadoAutos> findListadoAutosEntities(int maxResults, int firstResult) {
        return findListadoAutosEntities(false, maxResults, firstResult);
    }

    private List<ListadoAutos> findListadoAutosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListadoAutos.class));
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

    public ListadoAutos findListadoAutos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListadoAutos.class, id);
        } finally {
            em.close();
        }
    }

    public int getListadoAutosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListadoAutos> rt = cq.from(ListadoAutos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
