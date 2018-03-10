/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer.Views;

import ModelLayer.Views.ListadoChofer;
import Exceptions.NonexistentEntityException;
import Exceptions.PreexistingEntityException;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yordanka
 */
public class ListadoChoferJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(ListadoChofer listadoChofer) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(listadoChofer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findListadoChofer(listadoChofer.getIdView()) != null) {
                throw new PreexistingEntityException("ListadoChofer " + listadoChofer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListadoChofer listadoChofer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            listadoChofer = em.merge(listadoChofer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listadoChofer.getIdView();
                if (findListadoChofer(id) == null) {
                    throw new NonexistentEntityException("The listadoChofer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListadoChofer listadoChofer;
            try {
                listadoChofer = em.getReference(ListadoChofer.class, id);
                listadoChofer.getIdView();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listadoChofer with id " + id + " no longer exists.", enfe);
            }
            em.remove(listadoChofer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListadoChofer> findListadoChoferEntities() {
        return findListadoChoferEntities(true, -1, -1);
    }

    public List<ListadoChofer> findListadoChoferEntities(int maxResults, int firstResult) {
        return findListadoChoferEntities(false, maxResults, firstResult);
    }

    private List<ListadoChofer> findListadoChoferEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListadoChofer.class));
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

    public ListadoChofer findListadoChofer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListadoChofer.class, id);
        } finally {
            em.close();
        }
    }

    public int getListadoChoferCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListadoChofer> rt = cq.from(ListadoChofer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
