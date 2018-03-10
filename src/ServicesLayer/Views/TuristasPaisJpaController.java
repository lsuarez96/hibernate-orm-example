/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.TuristasPais;
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
public class TuristasPaisJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<TuristasPais> findTuristasPaisEntities() {
        return findTuristasPaisEntities(true, -1, -1);
    }

    public List<TuristasPais> findTuristasPaisEntities(int maxResults, int firstResult) {
        return findTuristasPaisEntities(false, maxResults, firstResult);
    }

    private List<TuristasPais> findTuristasPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TuristasPais.class));
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

    public TuristasPais findTuristasPais(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TuristasPais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTuristasPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TuristasPais> rt = cq.from(TuristasPais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
