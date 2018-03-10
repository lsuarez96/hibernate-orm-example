/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Contratos;
import ModelLayer.FormaPago;
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
public class FormaPagoServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(FormaPago formaPago) {
        if (formaPago.getContratosList() == null) {
            formaPago.setContratosList(new ArrayList<Contratos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Contratos> attachedContratosList = new ArrayList<Contratos>();
            for (Contratos contratosListContratosToAttach : formaPago.getContratosList()) {
                contratosListContratosToAttach = em.getReference(contratosListContratosToAttach.getClass(), contratosListContratosToAttach.getIdContrato());
                attachedContratosList.add(contratosListContratosToAttach);
            }
            formaPago.setContratosList(attachedContratosList);
            em.persist(formaPago);
            for (Contratos contratosListContratos : formaPago.getContratosList()) {
                FormaPago oldContIdFormaPagoOfContratosListContratos = contratosListContratos.getContIdFormaPago();
                contratosListContratos.setContIdFormaPago(formaPago);
                contratosListContratos = em.merge(contratosListContratos);
                if (oldContIdFormaPagoOfContratosListContratos != null) {
                    oldContIdFormaPagoOfContratosListContratos.getContratosList().remove(contratosListContratos);
                    oldContIdFormaPagoOfContratosListContratos = em.merge(oldContIdFormaPagoOfContratosListContratos);
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

    public void edit(FormaPago formaPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormaPago persistentFormaPago = em.find(FormaPago.class, formaPago.getIdPago());
//            List<Contratos> contratosListOld = persistentFormaPago.getContratosList();
//            List<Contratos> contratosListNew = formaPago.getContratosList();
//            List<String> illegalOrphanMessages = null;
//            for (Contratos contratosListOldContratos : contratosListOld) {
//                if (!contratosListNew.contains(contratosListOldContratos)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Contratos " + contratosListOldContratos + " since its contIdFormaPago field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Contratos> attachedContratosListNew = new ArrayList<Contratos>();
//            for (Contratos contratosListNewContratosToAttach : contratosListNew) {
//                contratosListNewContratosToAttach = em.getReference(contratosListNewContratosToAttach.getClass(), contratosListNewContratosToAttach.getIdContrato());
//                attachedContratosListNew.add(contratosListNewContratosToAttach);
//            }
//            contratosListNew = attachedContratosListNew;
//            formaPago.setContratosList(contratosListNew);
            formaPago = em.merge(formaPago);
//            for (Contratos contratosListNewContratos : contratosListNew) {
//                if (!contratosListOld.contains(contratosListNewContratos)) {
//                    FormaPago oldContIdFormaPagoOfContratosListNewContratos = contratosListNewContratos.getContIdFormaPago();
//                    contratosListNewContratos.setContIdFormaPago(formaPago);
//                    contratosListNewContratos = em.merge(contratosListNewContratos);
//                    if (oldContIdFormaPagoOfContratosListNewContratos != null && !oldContIdFormaPagoOfContratosListNewContratos.equals(formaPago)) {
//                        oldContIdFormaPagoOfContratosListNewContratos.getContratosList().remove(contratosListNewContratos);
//                        oldContIdFormaPagoOfContratosListNewContratos = em.merge(oldContIdFormaPagoOfContratosListNewContratos);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = formaPago.getIdPago();
                if (findFormaPago(id) == null) {
                    throw new NonexistentEntityException("The formaPago with id " + id + " no longer exists.");
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
            FormaPago formaPago;
            try {
                formaPago = em.getReference(FormaPago.class, id);
                formaPago.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formaPago with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Contratos> contratosListOrphanCheck = formaPago.getContratosList();
//            for (Contratos contratosListOrphanCheckContratos : contratosListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This FormaPago (" + formaPago + ") cannot be destroyed since the Contratos " + contratosListOrphanCheckContratos + " in its contratosList field has a non-nullable contIdFormaPago field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(formaPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<FormaPago> findFormaPagoEntities() {
        return findFormaPagoEntities(true, -1, -1);
    }

    public List<FormaPago> findFormaPagoEntities(int maxResults, int firstResult) {
        return findFormaPagoEntities(false, maxResults, firstResult);
    }

    private List<FormaPago> findFormaPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormaPago.class));
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

    public FormaPago findFormaPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormaPago> rt = cq.from(FormaPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
