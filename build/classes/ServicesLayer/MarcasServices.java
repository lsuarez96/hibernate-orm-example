/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Marcas;
import ModelLayer.Modelos;
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
public class MarcasServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Marcas marcas) throws Exception {
        if (marcas.getModelosList() == null) {
            marcas.setModelosList(new ArrayList<Modelos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Modelos> attachedModelosList = new ArrayList<Modelos>();
            for (Modelos modelosListModelosToAttach : marcas.getModelosList()) {
                modelosListModelosToAttach = em.getReference(modelosListModelosToAttach.getClass(), modelosListModelosToAttach.getIdModelo());
                attachedModelosList.add(modelosListModelosToAttach);
            }
            marcas.setModelosList(attachedModelosList);
            em.persist(marcas);
            for (Modelos modelosListModelos : marcas.getModelosList()) {
                Marcas oldModeloIdMarcaOfModelosListModelos = modelosListModelos.getModeloIdMarca();
                modelosListModelos.setModeloIdMarca(marcas);
                modelosListModelos = em.merge(modelosListModelos);
                if (oldModeloIdMarcaOfModelosListModelos != null) {
                    oldModeloIdMarcaOfModelosListModelos.getModelosList().remove(modelosListModelos);
                    oldModeloIdMarcaOfModelosListModelos = em.merge(oldModeloIdMarcaOfModelosListModelos);
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

    public void edit(Marcas marcas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcas persistentMarcas = em.find(Marcas.class, marcas.getIdMarca());
//            List<Modelos> modelosListOld = persistentMarcas.getModelosList();
//            List<Modelos> modelosListNew = marcas.getModelosList();
//            List<String> illegalOrphanMessages = null;
//            for (Modelos modelosListOldModelos : modelosListOld) {
//                if (!modelosListNew.contains(modelosListOldModelos)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Modelos " + modelosListOldModelos + " since its modeloIdMarca field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Modelos> attachedModelosListNew = new ArrayList<Modelos>();
//            for (Modelos modelosListNewModelosToAttach : modelosListNew) {
//                modelosListNewModelosToAttach = em.getReference(modelosListNewModelosToAttach.getClass(), modelosListNewModelosToAttach.getIdModelo());
//                attachedModelosListNew.add(modelosListNewModelosToAttach);
//            }
//            modelosListNew = attachedModelosListNew;
//            marcas.setModelosList(modelosListNew);
            marcas = em.merge(marcas);
//            for (Modelos modelosListNewModelos : modelosListNew) {
//                if (!modelosListOld.contains(modelosListNewModelos)) {
//                    Marcas oldModeloIdMarcaOfModelosListNewModelos = modelosListNewModelos.getModeloIdMarca();
//                    modelosListNewModelos.setModeloIdMarca(marcas);
//                    modelosListNewModelos = em.merge(modelosListNewModelos);
//                    if (oldModeloIdMarcaOfModelosListNewModelos != null && !oldModeloIdMarcaOfModelosListNewModelos.equals(marcas)) {
//                        oldModeloIdMarcaOfModelosListNewModelos.getModelosList().remove(modelosListNewModelos);
//                        oldModeloIdMarcaOfModelosListNewModelos = em.merge(oldModeloIdMarcaOfModelosListNewModelos);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marcas.getIdMarca();
                if (findMarcas(id) == null) {
                    throw new NonexistentEntityException("The marcas with id " + id + " no longer exists.");
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
            Marcas marcas;
            try {
                marcas = em.getReference(Marcas.class, id);
                marcas.getIdMarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcas with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Modelos> modelosListOrphanCheck = marcas.getModelosList();
//            for (Modelos modelosListOrphanCheckModelos : modelosListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Marcas (" + marcas + ") cannot be destroyed since the Modelos " + modelosListOrphanCheckModelos + " in its modelosList field has a non-nullable modeloIdMarca field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(marcas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Marcas> findMarcasEntities() {
        return findMarcasEntities(true, -1, -1);
    }

    public List<Marcas> findMarcasEntities(int maxResults, int firstResult) {
        return findMarcasEntities(false, maxResults, firstResult);
    }

    private List<Marcas> findMarcasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marcas.class));
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

    public Marcas findMarcas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marcas.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marcas> rt = cq.from(Marcas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Marcas findMarcaByName(String brandName) {
        EntityManager em = getEntityManager();
        Marcas u = null;
        try {
            Query q = em.createNamedQuery("Marcas.findByNombreMarca");
            q.setParameter("nombreMarca", brandName);
            List l = q.getResultList();
            if (!l.isEmpty()) {
                u = (Marcas) l.get(0);
            }
        } finally {
            em.close();
        }
        return u;
    }
}
