/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Autos;
import ModelLayer.Marcas;
import ModelLayer.Modelos;
import ModelLayer.Tarifa;
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
public class ModelosServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Modelos modelos) throws Exception {
        if (modelos.getAutosList() == null) {
            modelos.setAutosList(new ArrayList<Autos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcas modeloIdMarca = modelos.getModeloIdMarca();
            if (modeloIdMarca != null) {
                modeloIdMarca = em.getReference(modeloIdMarca.getClass(), modeloIdMarca.getIdMarca());
                modelos.setModeloIdMarca(modeloIdMarca);
            }
            Tarifa modeloIdTar = modelos.getModeloIdTar();
            if (modeloIdTar != null) {
                modeloIdTar = em.getReference(modeloIdTar.getClass(), modeloIdTar.getIdTarifa());
                modelos.setModeloIdTar(modeloIdTar);
            }
            List<Autos> attachedAutosList = new ArrayList<Autos>();
            for (Autos autosListAutosToAttach : modelos.getAutosList()) {
                autosListAutosToAttach = em.getReference(autosListAutosToAttach.getClass(), autosListAutosToAttach.getIdAuto());
                attachedAutosList.add(autosListAutosToAttach);
            }
            modelos.setAutosList(attachedAutosList);
            em.persist(modelos);
            if (modeloIdMarca != null) {
                modeloIdMarca.getModelosList().add(modelos);
                modeloIdMarca = em.merge(modeloIdMarca);
            }
            if (modeloIdTar != null) {
                modeloIdTar.getModelosList().add(modelos);
                modeloIdTar = em.merge(modeloIdTar);
            }
            for (Autos autosListAutos : modelos.getAutosList()) {
                Modelos oldIdModeloAutoOfAutosListAutos = autosListAutos.getIdModeloAuto();
                autosListAutos.setIdModeloAuto(modelos);
                autosListAutos = em.merge(autosListAutos);
                if (oldIdModeloAutoOfAutosListAutos != null) {
                    oldIdModeloAutoOfAutosListAutos.getAutosList().remove(autosListAutos);
                    oldIdModeloAutoOfAutosListAutos = em.merge(oldIdModeloAutoOfAutosListAutos);
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

    public void edit(Modelos modelos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modelos persistentModelos = em.find(Modelos.class, modelos.getIdModelo());
            Marcas modeloIdMarcaOld = persistentModelos.getModeloIdMarca();
            Marcas modeloIdMarcaNew = modelos.getModeloIdMarca();
            Tarifa modeloIdTarOld = persistentModelos.getModeloIdTar();
            Tarifa modeloIdTarNew = modelos.getModeloIdTar();
//            List<Autos> autosListOld = persistentModelos.getAutosList();
//            List<Autos> autosListNew = modelos.getAutosList();
//            List<String> illegalOrphanMessages = null;
//            for (Autos autosListOldAutos : autosListOld) {
//                if (!autosListNew.contains(autosListOldAutos)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Autos " + autosListOldAutos + " since its idModeloAuto field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (modeloIdMarcaNew != null) {
//                modeloIdMarcaNew = em.getReference(modeloIdMarcaNew.getClass(), modeloIdMarcaNew.getIdMarca());
//                modelos.setModeloIdMarca(modeloIdMarcaNew);
//            }
//            if (modeloIdTarNew != null) {
//                modeloIdTarNew = em.getReference(modeloIdTarNew.getClass(), modeloIdTarNew.getIdTarifa());
//                modelos.setModeloIdTar(modeloIdTarNew);
//            }
//            List<Autos> attachedAutosListNew = new ArrayList<Autos>();
//            for (Autos autosListNewAutosToAttach : autosListNew) {
//                autosListNewAutosToAttach = em.getReference(autosListNewAutosToAttach.getClass(), autosListNewAutosToAttach.getIdAuto());
//                attachedAutosListNew.add(autosListNewAutosToAttach);
//            }
//            autosListNew = attachedAutosListNew;
//            modelos.setAutosList(autosListNew);
            modelos = em.merge(modelos);
//            if (modeloIdMarcaOld != null && !modeloIdMarcaOld.equals(modeloIdMarcaNew)) {
//                modeloIdMarcaOld.getModelosList().remove(modelos);
//                modeloIdMarcaOld = em.merge(modeloIdMarcaOld);
//            }
//            if (modeloIdMarcaNew != null && !modeloIdMarcaNew.equals(modeloIdMarcaOld)) {
//                modeloIdMarcaNew.getModelosList().add(modelos);
//                modeloIdMarcaNew = em.merge(modeloIdMarcaNew);
//            }
//            if (modeloIdTarOld != null && !modeloIdTarOld.equals(modeloIdTarNew)) {
//                modeloIdTarOld.getModelosList().remove(modelos);
//                modeloIdTarOld = em.merge(modeloIdTarOld);
//            }
//            if (modeloIdTarNew != null && !modeloIdTarNew.equals(modeloIdTarOld)) {
//                modeloIdTarNew.getModelosList().add(modelos);
//                modeloIdTarNew = em.merge(modeloIdTarNew);
//            }
//            for (Autos autosListNewAutos : autosListNew) {
//                if (!autosListOld.contains(autosListNewAutos)) {
//                    Modelos oldIdModeloAutoOfAutosListNewAutos = autosListNewAutos.getIdModeloAuto();
//                    autosListNewAutos.setIdModeloAuto(modelos);
//                    autosListNewAutos = em.merge(autosListNewAutos);
//                    if (oldIdModeloAutoOfAutosListNewAutos != null && !oldIdModeloAutoOfAutosListNewAutos.equals(modelos)) {
//                        oldIdModeloAutoOfAutosListNewAutos.getAutosList().remove(autosListNewAutos);
//                        oldIdModeloAutoOfAutosListNewAutos = em.merge(oldIdModeloAutoOfAutosListNewAutos);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modelos.getIdModelo();
                if (findModelos(id) == null) {
                    throw new NonexistentEntityException("The modelos with id " + id + " no longer exists.");
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
            Modelos modelos;
            try {
                modelos = em.getReference(Modelos.class, id);
                modelos.getIdModelo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modelos with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Autos> autosListOrphanCheck = modelos.getAutosList();
//            for (Autos autosListOrphanCheckAutos : autosListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Modelos (" + modelos + ") cannot be destroyed since the Autos " + autosListOrphanCheckAutos + " in its autosList field has a non-nullable idModeloAuto field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Marcas modeloIdMarca = modelos.getModeloIdMarca();
//            if (modeloIdMarca != null) {
//                modeloIdMarca.getModelosList().remove(modelos);
//                modeloIdMarca = em.merge(modeloIdMarca);
//            }
//            Tarifa modeloIdTar = modelos.getModeloIdTar();
//            if (modeloIdTar != null) {
//                modeloIdTar.getModelosList().remove(modelos);
//                modeloIdTar = em.merge(modeloIdTar);
//            }
            em.remove(modelos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Modelos> findModelosEntities() {
        return findModelosEntities(true, -1, -1);
    }

    public List<Modelos> findModelosEntities(int maxResults, int firstResult) {
        return findModelosEntities(false, maxResults, firstResult);
    }

    private List<Modelos> findModelosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modelos.class));
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

    public Modelos findModelos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modelos.class, id);
        } finally {
            em.close();
        }
    }

    public int getModelosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modelos> rt = cq.from(Modelos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
