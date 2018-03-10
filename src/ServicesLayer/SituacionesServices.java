/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Autos;
import ModelLayer.Situaciones;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
public class SituacionesServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Situaciones situaciones) {
        if (situaciones.getAutosList() == null) {
            situaciones.setAutosList(new ArrayList<Autos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Autos> attachedAutosList = new ArrayList<Autos>();
            for (Autos autosListAutosToAttach : situaciones.getAutosList()) {
                autosListAutosToAttach = em.getReference(autosListAutosToAttach.getClass(), autosListAutosToAttach.getIdAuto());
                attachedAutosList.add(autosListAutosToAttach);
            }
            situaciones.setAutosList(attachedAutosList);
            em.persist(situaciones);
            for (Autos autosListAutos : situaciones.getAutosList()) {
                Situaciones oldIdSituacionAutoOfAutosListAutos = autosListAutos.getIdSituacionAuto();
                autosListAutos.setIdSituacionAuto(situaciones);
                autosListAutos = em.merge(autosListAutos);
                if (oldIdSituacionAutoOfAutosListAutos != null) {
                    oldIdSituacionAutoOfAutosListAutos.getAutosList().remove(autosListAutos);
                    oldIdSituacionAutoOfAutosListAutos = em.merge(oldIdSituacionAutoOfAutosListAutos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public void edit(Situaciones situaciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Situaciones persistentSituaciones = em.find(Situaciones.class, situaciones.getIdSit());
            List<Autos> autosListOld = persistentSituaciones.getAutosList();
            List<Autos> autosListNew = situaciones.getAutosList();
            List<String> illegalOrphanMessages = null;
            for (Autos autosListOldAutos : autosListOld) {
                if (!autosListNew.contains(autosListOldAutos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Autos " + autosListOldAutos + " since its idSituacionAuto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Autos> attachedAutosListNew = new ArrayList<Autos>();
            for (Autos autosListNewAutosToAttach : autosListNew) {
                autosListNewAutosToAttach = em.getReference(autosListNewAutosToAttach.getClass(), autosListNewAutosToAttach.getIdAuto());
                attachedAutosListNew.add(autosListNewAutosToAttach);
            }
            autosListNew = attachedAutosListNew;
            situaciones.setAutosList(autosListNew);
            situaciones = em.merge(situaciones);
            for (Autos autosListNewAutos : autosListNew) {
                if (!autosListOld.contains(autosListNewAutos)) {
                    Situaciones oldIdSituacionAutoOfAutosListNewAutos = autosListNewAutos.getIdSituacionAuto();
                    autosListNewAutos.setIdSituacionAuto(situaciones);
                    autosListNewAutos = em.merge(autosListNewAutos);
                    if (oldIdSituacionAutoOfAutosListNewAutos != null && !oldIdSituacionAutoOfAutosListNewAutos.equals(situaciones)) {
                        oldIdSituacionAutoOfAutosListNewAutos.getAutosList().remove(autosListNewAutos);
                        oldIdSituacionAutoOfAutosListNewAutos = em.merge(oldIdSituacionAutoOfAutosListNewAutos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = situaciones.getIdSit();
                if (findSituaciones(id) == null) {
                    throw new NonexistentEntityException("The situaciones with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Situaciones situaciones;
            try {
                situaciones = em.getReference(Situaciones.class, id);
                situaciones.getIdSit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The situaciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Autos> autosListOrphanCheck = situaciones.getAutosList();
            for (Autos autosListOrphanCheckAutos : autosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Situaciones (" + situaciones + ") cannot be destroyed since the Autos " + autosListOrphanCheckAutos + " in its autosList field has a non-nullable idSituacionAuto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(situaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Situaciones> findSituacionesEntities() {
        return findSituacionesEntities(true, -1, -1);
    }

    public List<Situaciones> findSituacionesEntities(int maxResults, int firstResult) {
        return findSituacionesEntities(false, maxResults, firstResult);
    }

    private List<Situaciones> findSituacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Situaciones.class));
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

    public Situaciones findSituaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Situaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSituacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Situaciones> rt = cq.from(Situaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
