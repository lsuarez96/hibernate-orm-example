/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Contratos;
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
public class TuristaServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Turista turista) throws Exception {
        if (turista.getContratosList() == null) {
            turista.setContratosList(new ArrayList<Contratos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais turIdPais = turista.getTurIdPais();
            if (turIdPais != null) {
                turIdPais = em.getReference(turIdPais.getClass(), turIdPais.getIdPais());
                turista.setTurIdPais(turIdPais);
            }
            List<Contratos> attachedContratosList = new ArrayList<Contratos>();
            for (Contratos contratosListContratosToAttach : turista.getContratosList()) {
                contratosListContratosToAttach = em.getReference(contratosListContratosToAttach.getClass(), contratosListContratosToAttach.getIdContrato());
                attachedContratosList.add(contratosListContratosToAttach);
            }
            turista.setContratosList(attachedContratosList);
            em.persist(turista);
            if (turIdPais != null) {
                turIdPais.getTuristaList().add(turista);
                turIdPais = em.merge(turIdPais);
            }
            for (Contratos contratosListContratos : turista.getContratosList()) {
                Turista oldContIdTurOfContratosListContratos = contratosListContratos.getContIdTur();
                contratosListContratos.setContIdTur(turista);
                contratosListContratos = em.merge(contratosListContratos);
                if (oldContIdTurOfContratosListContratos != null) {
                    oldContIdTurOfContratosListContratos.getContratosList().remove(contratosListContratos);
                    oldContIdTurOfContratosListContratos = em.merge(oldContIdTurOfContratosListContratos);
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

    public void edit(Turista turista) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turista persistentTurista = em.find(Turista.class, turista.getIdTur());
            Pais turIdPaisOld = persistentTurista.getTurIdPais();
            Pais turIdPaisNew = turista.getTurIdPais();
//            List<Contratos> contratosListOld = persistentTurista.getContratosList();
//            List<Contratos> contratosListNew = turista.getContratosList();
//            List<String> illegalOrphanMessages = null;
//            for (Contratos contratosListOldContratos : contratosListOld) {
//                if (!contratosListNew.contains(contratosListOldContratos)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Contratos " + contratosListOldContratos + " since its contIdTur field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            if (turIdPaisNew != null) {
                turIdPaisNew = em.getReference(turIdPaisNew.getClass(), turIdPaisNew.getIdPais());
                turista.setTurIdPais(turIdPaisNew);
            }
//            List<Contratos> attachedContratosListNew = new ArrayList<Contratos>();
//            for (Contratos contratosListNewContratosToAttach : contratosListNew) {
//                contratosListNewContratosToAttach = em.getReference(contratosListNewContratosToAttach.getClass(), contratosListNewContratosToAttach.getIdContrato());
//                attachedContratosListNew.add(contratosListNewContratosToAttach);
//            }
//            contratosListNew = attachedContratosListNew;
//            turista.setContratosList(contratosListNew);
            turista = em.merge(turista);
//            if (turIdPaisOld != null && !turIdPaisOld.equals(turIdPaisNew)) {
//                turIdPaisOld.getTuristaList().remove(turista);
//                turIdPaisOld = em.merge(turIdPaisOld);
//            }
//            if (turIdPaisNew != null && !turIdPaisNew.equals(turIdPaisOld)) {
//                turIdPaisNew.getTuristaList().add(turista);
//                turIdPaisNew = em.merge(turIdPaisNew);
//            }
//            for (Contratos contratosListNewContratos : contratosListNew) {
//                if (!contratosListOld.contains(contratosListNewContratos)) {
//                    Turista oldContIdTurOfContratosListNewContratos = contratosListNewContratos.getContIdTur();
//                    contratosListNewContratos.setContIdTur(turista);
//                    contratosListNewContratos = em.merge(contratosListNewContratos);
//                    if (oldContIdTurOfContratosListNewContratos != null && !oldContIdTurOfContratosListNewContratos.equals(turista)) {
//                        oldContIdTurOfContratosListNewContratos.getContratosList().remove(contratosListNewContratos);
//                        oldContIdTurOfContratosListNewContratos = em.merge(oldContIdTurOfContratosListNewContratos);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = turista.getIdTur();
                if (findTurista(id) == null) {
                    throw new NonexistentEntityException("The turista with id " + id + " no longer exists.");
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
            Turista turista;
            try {
                turista = em.getReference(Turista.class, id);
                turista.getIdTur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turista with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Contratos> contratosListOrphanCheck = turista.getContratosList();
//            for (Contratos contratosListOrphanCheckContratos : contratosListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Turista (" + turista + ") cannot be destroyed since the Contratos " + contratosListOrphanCheckContratos + " in its contratosList field has a non-nullable contIdTur field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Pais turIdPais = turista.getTurIdPais();
//            if (turIdPais != null) {
//                turIdPais.getTuristaList().remove(turista);
//                turIdPais = em.merge(turIdPais);
//            }
            em.remove(turista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Turista> findTuristaEntities() {
        return findTuristaEntities(true, -1, -1);
    }

    public List<Turista> findTuristaEntities(int maxResults, int firstResult) {
        return findTuristaEntities(false, maxResults, firstResult);
    }

    private List<Turista> findTuristaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turista.class));
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

    public Turista findTurista(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turista.class, id);
        } finally {
            em.close();
        }
    }

    public int getTuristaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turista> rt = cq.from(Turista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
