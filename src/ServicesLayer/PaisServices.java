/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Pais;
import ModelLayer.Turista;
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
public class PaisServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Pais pais) throws Exception {
        if (pais.getTuristaList() == null) {
            pais.setTuristaList(new ArrayList<Turista>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Turista> attachedTuristaList = new ArrayList<Turista>();
            for (Turista turistaListTuristaToAttach : pais.getTuristaList()) {
                turistaListTuristaToAttach = em.getReference(turistaListTuristaToAttach.getClass(), turistaListTuristaToAttach.getIdTur());
                attachedTuristaList.add(turistaListTuristaToAttach);
            }
            pais.setTuristaList(attachedTuristaList);
            em.persist(pais);
            for (Turista turistaListTurista : pais.getTuristaList()) {
                Pais oldTurIdPaisOfTuristaListTurista = turistaListTurista.getTurIdPais();
                turistaListTurista.setTurIdPais(pais);
                turistaListTurista = em.merge(turistaListTurista);
                if (oldTurIdPaisOfTuristaListTurista != null) {
                    oldTurIdPaisOfTuristaListTurista.getTuristaList().remove(turistaListTurista);
                    oldTurIdPaisOfTuristaListTurista = em.merge(oldTurIdPaisOfTuristaListTurista);
                }
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

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdPais());
//            List<Turista> turistaListOld = persistentPais.getTuristaList();
//            List<Turista> turistaListNew = pais.getTuristaList();
//            List<String> illegalOrphanMessages = null;
//            for (Turista turistaListOldTurista : turistaListOld) {
//                if (!turistaListNew.contains(turistaListOldTurista)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Turista " + turistaListOldTurista + " since its turIdPais field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Turista> attachedTuristaListNew = new ArrayList<Turista>();
//            for (Turista turistaListNewTuristaToAttach : turistaListNew) {
//                turistaListNewTuristaToAttach = em.getReference(turistaListNewTuristaToAttach.getClass(), turistaListNewTuristaToAttach.getIdTur());
//                attachedTuristaListNew.add(turistaListNewTuristaToAttach);
//            }
//            turistaListNew = attachedTuristaListNew;
//            pais.setTuristaList(turistaListNew);
            pais = em.merge(pais);
//            for (Turista turistaListNewTurista : turistaListNew) {
//                if (!turistaListOld.contains(turistaListNewTurista)) {
//                    Pais oldTurIdPaisOfTuristaListNewTurista = turistaListNewTurista.getTurIdPais();
//                    turistaListNewTurista.setTurIdPais(pais);
//                    turistaListNewTurista = em.merge(turistaListNewTurista);
//                    if (oldTurIdPaisOfTuristaListNewTurista != null && !oldTurIdPaisOfTuristaListNewTurista.equals(pais)) {
//                        oldTurIdPaisOfTuristaListNewTurista.getTuristaList().remove(turistaListNewTurista);
//                        oldTurIdPaisOfTuristaListNewTurista = em.merge(oldTurIdPaisOfTuristaListNewTurista);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getIdPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Turista> turistaListOrphanCheck = pais.getTuristaList();
//            for (Turista turistaListOrphanCheckTurista : turistaListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Turista " + turistaListOrphanCheckTurista + " in its turistaList field has a non-nullable turIdPais field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Pais findCountryByName(String country) {
        EntityManager em = getEntityManager();
        Pais u = null;
        try {
            Query q = em.createNamedQuery("Pais.findByNombrePais");
            q.setParameter("nombrePais", country);
            List l = q.getResultList();
            if (!l.isEmpty()) {
                u = (Pais) l.get(0);
            }
        } finally {
            em.close();
        }
        return u;// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
