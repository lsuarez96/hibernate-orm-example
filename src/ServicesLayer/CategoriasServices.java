/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Categorias;
import ModelLayer.Choferes;
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
public class CategoriasServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Categorias categorias) throws Exception {
        if (categorias.getChoferesList() == null) {
            categorias.setChoferesList(new ArrayList<Choferes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Choferes> attachedChoferesList = new ArrayList<Choferes>();
            for (Choferes choferesListChoferesToAttach : categorias.getChoferesList()) {
                choferesListChoferesToAttach = em.getReference(choferesListChoferesToAttach.getClass(), choferesListChoferesToAttach.getIdChofer());
                attachedChoferesList.add(choferesListChoferesToAttach);
            }
            categorias.setChoferesList(attachedChoferesList);
            em.persist(categorias);
            for (Choferes choferesListChoferes : categorias.getChoferesList()) {
                Categorias oldCategoriaOfChoferesListChoferes = choferesListChoferes.getCategoria();
                choferesListChoferes.setCategoria(categorias);
                choferesListChoferes = em.merge(choferesListChoferes);
                if (oldCategoriaOfChoferesListChoferes != null) {
                    oldCategoriaOfChoferesListChoferes.getChoferesList().remove(choferesListChoferes);
                    oldCategoriaOfChoferesListChoferes = em.merge(oldCategoriaOfChoferesListChoferes);
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

    public void edit(Categorias categorias) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias persistentCategorias = em.find(Categorias.class, categorias.getIdCat());
//            List<Choferes> choferesListOld = persistentCategorias.getChoferesList();
//            List<Choferes> choferesListNew = categorias.getChoferesList();
//            List<String> illegalOrphanMessages = null;
//            for (Choferes choferesListOldChoferes : choferesListOld) {
//                if (!choferesListNew.contains(choferesListOldChoferes)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Choferes " + choferesListOldChoferes + " since its categoria field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Choferes> attachedChoferesListNew = new ArrayList<Choferes>();
//            for (Choferes choferesListNewChoferesToAttach : choferesListNew) {
//                choferesListNewChoferesToAttach = em.getReference(choferesListNewChoferesToAttach.getClass(), choferesListNewChoferesToAttach.getIdChofer());
//                attachedChoferesListNew.add(choferesListNewChoferesToAttach);
//            }
//            choferesListNew = attachedChoferesListNew;
//            categorias.setChoferesList(choferesListNew);
            categorias = em.merge(categorias);
//            for (Choferes choferesListNewChoferes : choferesListNew) {
//                if (!choferesListOld.contains(choferesListNewChoferes)) {
//                    Categorias oldCategoriaOfChoferesListNewChoferes = choferesListNewChoferes.getCategoria();
//                    choferesListNewChoferes.setCategoria(categorias);
//                    choferesListNewChoferes = em.merge(choferesListNewChoferes);
//                    if (oldCategoriaOfChoferesListNewChoferes != null && !oldCategoriaOfChoferesListNewChoferes.equals(categorias)) {
//                        oldCategoriaOfChoferesListNewChoferes.getChoferesList().remove(choferesListNewChoferes);
//                        oldCategoriaOfChoferesListNewChoferes = em.merge(oldCategoriaOfChoferesListNewChoferes);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorias.getIdCat();
                if (findCategorias(id) == null) {
                    throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.");
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
            Categorias categorias;
            try {
                categorias = em.getReference(Categorias.class, id);
                categorias.getIdCat();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
//            List<Choferes> choferesListOrphanCheck = categorias.getChoferesList();
//            for (Choferes choferesListOrphanCheckChoferes : choferesListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Categorias (" + categorias + ") cannot be destroyed since the Choferes " + choferesListOrphanCheckChoferes + " in its choferesList field has a non-nullable categoria field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Categorias> findCategoriasEntities() {
        return findCategoriasEntities(true, -1, -1);
    }

    public List<Categorias> findCategoriasEntities(int maxResults, int firstResult) {

        return findCategoriasEntities(false, maxResults, firstResult);
    }

    private List<Categorias> findCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        List<Categorias> result = null;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categorias.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }

            result = q.getResultList();
        } finally {
            em.close();
        }
        //  ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
        return result;
    }

    public Categorias findCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            em.close();
        }

    }

    public int getCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> rt = cq.from(Categorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
