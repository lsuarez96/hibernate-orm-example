/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ListadoIncumplidores;
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
public class ListadoIncumplidoresJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<ListadoIncumplidores> findListadoIncumplidoresEntities() {
        return findListadoIncumplidoresEntities(true, -1, -1);
    }

    public List<ListadoIncumplidores> findListadoIncumplidoresEntities(int maxResults, int firstResult) {
        return findListadoIncumplidoresEntities(false, maxResults, firstResult);
    }

    private List<ListadoIncumplidores> findListadoIncumplidoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListadoIncumplidores.class));
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

    public ListadoIncumplidores findListadoIncumplidores(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListadoIncumplidores.class, id);
        } finally {
            em.close();
        }
    }

    public int getListadoIncumplidoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListadoIncumplidores> rt = cq.from(ListadoIncumplidores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
