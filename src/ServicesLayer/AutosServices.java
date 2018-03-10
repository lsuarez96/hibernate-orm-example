/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Autos;
import ModelLayer.Modelos;
import ModelLayer.Situaciones;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yordanka
 */
public class AutosServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Autos autos) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelos idModeloAuto = autos.getIdModeloAuto();
            if (idModeloAuto != null) {
                idModeloAuto = em.getReference(idModeloAuto.getClass(), idModeloAuto.getIdModelo());
                autos.setIdModeloAuto(idModeloAuto);
            }
            Situaciones idSituacionAuto = autos.getIdSituacionAuto();
            if (idSituacionAuto != null) {
                idSituacionAuto = em.getReference(idSituacionAuto.getClass(), idSituacionAuto.getIdSit());
                autos.setIdSituacionAuto(idSituacionAuto);
            }
            em.persist(autos);
            if (idModeloAuto != null) {
                idModeloAuto.getAutosList().add(autos);
                idModeloAuto = em.merge(idModeloAuto);
            }
            if (idSituacionAuto != null) {
                idSituacionAuto.getAutosList().add(autos);
                idSituacionAuto = em.merge(idSituacionAuto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public void edit(Autos autos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autos persistentAutos = em.find(Autos.class, autos.getIdAuto());
            Modelos idModeloAutoOld = persistentAutos.getIdModeloAuto();
            Modelos idModeloAutoNew = autos.getIdModeloAuto();
            Situaciones idSituacionAutoOld = persistentAutos.getIdSituacionAuto();
            Situaciones idSituacionAutoNew = autos.getIdSituacionAuto();
//            if (idModeloAutoNew != null) {
//                idModeloAutoNew = em.getReference(idModeloAutoNew.getClass(), idModeloAutoNew.getIdModelo());
//                autos.setIdModeloAuto(idModeloAutoNew);
//            }
//            if (idSituacionAutoNew != null) {
//                idSituacionAutoNew = em.getReference(idSituacionAutoNew.getClass(), idSituacionAutoNew.getIdSit());
//                autos.setIdSituacionAuto(idSituacionAutoNew);
//            }
            autos = em.merge(autos);
//            if (idModeloAutoOld != null && !idModeloAutoOld.equals(idModeloAutoNew)) {
//                idModeloAutoOld.getAutosList().remove(autos);
//                idModeloAutoOld = em.merge(idModeloAutoOld);
//            }
//            if (idModeloAutoNew != null && !idModeloAutoNew.equals(idModeloAutoOld)) {
//                idModeloAutoNew.getAutosList().add(autos);
//                idModeloAutoNew = em.merge(idModeloAutoNew);
//            }
//            if (idSituacionAutoOld != null && !idSituacionAutoOld.equals(idSituacionAutoNew)) {
//                idSituacionAutoOld.getAutosList().remove(autos);
//                idSituacionAutoOld = em.merge(idSituacionAutoOld);
//            }
//            if (idSituacionAutoNew != null && !idSituacionAutoNew.equals(idSituacionAutoOld)) {
//                idSituacionAutoNew.getAutosList().add(autos);
//                idSituacionAutoNew = em.merge(idSituacionAutoNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = autos.getIdAuto();
                if (findAutos(id) == null) {
                    throw new NonexistentEntityException("The autos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autos autos;
            try {
                autos = em.getReference(Autos.class, id);
                autos.getIdAuto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autos with id " + id + " no longer exists.", enfe);
            }
//            Modelos idModeloAuto = autos.getIdModeloAuto();
//            if (idModeloAuto != null) {
//                idModeloAuto.getAutosList().remove(autos);
//                idModeloAuto = em.merge(idModeloAuto);
//            }
//            Situaciones idSituacionAuto = autos.getIdSituacionAuto();
//            if (idSituacionAuto != null) {
//                idSituacionAuto.getAutosList().remove(autos);
//                idSituacionAuto = em.merge(idSituacionAuto);
//            }
            em.remove(autos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Autos> findAutosEntities() {
        return findAutosEntities(true, -1, -1);
    }

    public List<Autos> findAutosEntities(int maxResults, int firstResult) {
        return findAutosEntities(false, maxResults, firstResult);
    }

    private List<Autos> findAutosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        List<Autos> result = null;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            result = q.getResultList();
        } finally {
            em.close();
        }
        // ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
        return result;
    }

    public Autos findAutos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autos> rt = cq.from(Autos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Autos findCarByPlate(String userName) {
        EntityManager em = getEntityManager();
        Autos u = null;
        try {
            Query q = em.createNamedQuery("Autos.findByChapa");
            q.setParameter("chapa", userName);
            List l = q.getResultList();
            if (!l.isEmpty()) {
                u = (Autos) l.get(0);
            }
        } finally {
            em.close();
        }
        return u;
    }

}
