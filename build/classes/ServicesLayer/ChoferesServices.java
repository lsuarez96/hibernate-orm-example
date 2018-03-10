/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Categorias;
import ModelLayer.Choferes;
import ModelLayer.Contratos;
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
public class ChoferesServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Choferes choferes) throws Exception {
        if (choferes.getContratosList() == null) {
            choferes.setContratosList(new ArrayList<Contratos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias categoria = choferes.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getIdCat());
                choferes.setCategoria(categoria);
            }
            List<Contratos> attachedContratosList = new ArrayList<Contratos>();
            for (Contratos contratosListContratosToAttach : choferes.getContratosList()) {
                contratosListContratosToAttach = em.getReference(contratosListContratosToAttach.getClass(), contratosListContratosToAttach.getIdContrato());
                attachedContratosList.add(contratosListContratosToAttach);
            }
            choferes.setContratosList(attachedContratosList);
            em.persist(choferes);
            if (categoria != null) {
                categoria.getChoferesList().add(choferes);
                categoria = em.merge(categoria);
            }
            for (Contratos contratosListContratos : choferes.getContratosList()) {
                Choferes oldContIdChofOfContratosListContratos = contratosListContratos.getContIdChof();
                contratosListContratos.setContIdChof(choferes);
                contratosListContratos = em.merge(contratosListContratos);
                if (oldContIdChofOfContratosListContratos != null) {
                    oldContIdChofOfContratosListContratos.getContratosList().remove(contratosListContratos);
                    oldContIdChofOfContratosListContratos = em.merge(oldContIdChofOfContratosListContratos);
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

    public void edit(Choferes choferes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Choferes persistentChoferes = em.find(Choferes.class, choferes.getIdChofer());
//            Categorias categoriaOld = persistentChoferes.getCategoria();
//            Categorias categoriaNew = choferes.getCategoria();
//            List<Contratos> contratosListOld = persistentChoferes.getContratosList();
//            List<Contratos> contratosListNew = choferes.getContratosList();
//            if (categoriaNew != null) {
//                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getIdCat());
//                choferes.setCategoria(categoriaNew);
//            }
//            List<Contratos> attachedContratosListNew = new ArrayList<Contratos>();
//            for (Contratos contratosListNewContratosToAttach : contratosListNew) {
//                contratosListNewContratosToAttach = em.getReference(contratosListNewContratosToAttach.getClass(), contratosListNewContratosToAttach.getIdContrato());
//                attachedContratosListNew.add(contratosListNewContratosToAttach);
//            }
//            contratosListNew = attachedContratosListNew;
//            choferes.setContratosList(contratosListNew);
            choferes = em.merge(choferes);
//            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
//                categoriaOld.getChoferesList().remove(choferes);
//                categoriaOld = em.merge(categoriaOld);
//            }
//            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
//                categoriaNew.getChoferesList().add(choferes);
//                categoriaNew = em.merge(categoriaNew);
//            }
//            for (Contratos contratosListOldContratos : contratosListOld) {
//                if (!contratosListNew.contains(contratosListOldContratos)) {
//                    contratosListOldContratos.setContIdChof(null);
//                    contratosListOldContratos = em.merge(contratosListOldContratos);
//                }
//            }
//            for (Contratos contratosListNewContratos : contratosListNew) {
//                if (!contratosListOld.contains(contratosListNewContratos)) {
//                    Choferes oldContIdChofOfContratosListNewContratos = contratosListNewContratos.getContIdChof();
//                    contratosListNewContratos.setContIdChof(choferes);
//                    contratosListNewContratos = em.merge(contratosListNewContratos);
//                    if (oldContIdChofOfContratosListNewContratos != null && !oldContIdChofOfContratosListNewContratos.equals(choferes)) {
//                        oldContIdChofOfContratosListNewContratos.getContratosList().remove(contratosListNewContratos);
//                        oldContIdChofOfContratosListNewContratos = em.merge(oldContIdChofOfContratosListNewContratos);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = choferes.getIdChofer();
                if (findChoferes(id) == null) {
                    throw new NonexistentEntityException("The choferes with id " + id + " no longer exists.");
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
            Choferes choferes;
            try {
                choferes = em.getReference(Choferes.class, id);
                choferes.getIdChofer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The choferes with id " + id + " no longer exists.", enfe);
            }
//            Categorias categoria = choferes.getCategoria();
//            if (categoria != null) {
//                categoria.getChoferesList().remove(choferes);
//                categoria = em.merge(categoria);
//            }
//            List<Contratos> contratosList = choferes.getContratosList();
//            for (Contratos contratosListContratos : contratosList) {
//                contratosListContratos.setContIdChof(null);
//                contratosListContratos = em.merge(contratosListContratos);
//            }
            em.remove(choferes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Choferes> findChoferesEntities() {
        return findChoferesEntities(true, -1, -1);
    }

    public List<Choferes> findChoferesEntities(int maxResults, int firstResult) {
        return findChoferesEntities(false, maxResults, firstResult);
    }

    private List<Choferes> findChoferesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Choferes.class));
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

    public Choferes findChoferes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Choferes.class, id);
        } finally {
            em.close();
        }
    }

    public int getChoferesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Choferes> rt = cq.from(Choferes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
