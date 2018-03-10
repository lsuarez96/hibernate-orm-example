/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ContratoPorModeloMarca;
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
public class ContratoPorModeloMarcaJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public List<ContratoPorModeloMarca> findContratoPorModeloMarcaEntities() {
        return findContratoPorModeloMarcaEntities(true, -1, -1);
    }

    public List<ContratoPorModeloMarca> findContratoPorModeloMarcaEntities(int maxResults, int firstResult) {
        return findContratoPorModeloMarcaEntities(false, maxResults, firstResult);
    }

    private List<ContratoPorModeloMarca> findContratoPorModeloMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContratoPorModeloMarca.class));
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

    public ContratoPorModeloMarca findContratoPorModeloMarca(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContratoPorModeloMarca.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratoPorModeloMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContratoPorModeloMarca> rt = cq.from(ContratoPorModeloMarca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
