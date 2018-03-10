/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Modelos;
import ModelLayer.Tarifa;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yordanka
 */
public class TarifaServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Tarifa tarifa) throws Exception {
        if (tarifa.getModelosList() == null) {
            tarifa.setModelosList(new ArrayList<Modelos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Modelos> attachedModelosList = new ArrayList<Modelos>();
            for (Modelos modelosListModelosToAttach : tarifa.getModelosList()) {
                modelosListModelosToAttach = em.getReference(modelosListModelosToAttach.getClass(), modelosListModelosToAttach.getIdModelo());
                attachedModelosList.add(modelosListModelosToAttach);
            }
            tarifa.setModelosList(attachedModelosList);
            em.persist(tarifa);
//            StoredProcedureQuery sp = em.createStoredProcedureQuery("insertar_tarifa(tarn,tare)");
//            sp.setParameter("tarn", String.valueOf(tarifa.getTarifaNormal()));
//            sp.setParameter("tare", String.valueOf(tarifa.getTarifaEspecial()));

            for (Modelos modelosListModelos : tarifa.getModelosList()) {
                Tarifa oldModeloIdTarOfModelosListModelos = modelosListModelos.getModeloIdTar();
                modelosListModelos.setModeloIdTar(tarifa);
                modelosListModelos = em.merge(modelosListModelos);
                if (oldModeloIdTarOfModelosListModelos != null) {
                    oldModeloIdTarOfModelosListModelos.getModelosList().remove(modelosListModelos);
                    oldModeloIdTarOfModelosListModelos = em.merge(oldModeloIdTarOfModelosListModelos);
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

    public void edit(Tarifa tarifa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarifa persistentTarifa = em.find(Tarifa.class, tarifa.getIdTarifa());
            /* List<Modelos> modelosListOld = persistentTarifa.getModelosList();
            List<Modelos> modelosListNew = tarifa.getModelosList();
            List<String> illegalOrphanMessages = null;
            for (Modelos modelosListOldModelos : modelosListOld) {
                if (!modelosListNew.contains(modelosListOldModelos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Modelos " + modelosListOldModelos + " since its modeloIdTar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Modelos> attachedModelosListNew = new ArrayList<Modelos>();
            for (Modelos modelosListNewModelosToAttach : modelosListNew) {
                modelosListNewModelosToAttach = em.getReference(modelosListNewModelosToAttach.getClass(), modelosListNewModelosToAttach.getIdModelo());
                attachedModelosListNew.add(modelosListNewModelosToAttach);
            }
            modelosListNew = attachedModelosListNew;
            tarifa.setModelosList(modelosListNew);*/
            tarifa = em.merge(tarifa);
            /* for (Modelos modelosListNewModelos : modelosListNew) {
                if (!modelosListOld.contains(modelosListNewModelos)) {
                    Tarifa oldModeloIdTarOfModelosListNewModelos = modelosListNewModelos.getModeloIdTar();
                    modelosListNewModelos.setModeloIdTar(tarifa);
                    modelosListNewModelos = em.merge(modelosListNewModelos);
                    if (oldModeloIdTarOfModelosListNewModelos != null && !oldModeloIdTarOfModelosListNewModelos.equals(tarifa)) {
                        oldModeloIdTarOfModelosListNewModelos.getModelosList().remove(modelosListNewModelos);
                        oldModeloIdTarOfModelosListNewModelos = em.merge(oldModeloIdTarOfModelosListNewModelos);
                    }
                }
            }*/
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarifa.getIdTarifa();
                if (findTarifa(id) == null) {
                    throw new NonexistentEntityException("The tarifa with id " + id + " no longer exists.");
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
            Tarifa tarifa;
            try {
                tarifa = em.getReference(Tarifa.class, id);
                tarifa.getIdTarifa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarifa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Modelos> modelosListOrphanCheck = tarifa.getModelosList();
            for (Modelos modelosListOrphanCheckModelos : modelosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarifa (" + tarifa + ") cannot be destroyed since the Modelos " + modelosListOrphanCheckModelos + " in its modelosList field has a non-nullable modeloIdTar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tarifa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Tarifa> findTarifaEntities() {
        return findTarifaEntities(true, -1, -1);
    }

    public List<Tarifa> findTarifaEntities(int maxResults, int firstResult) {
        return findTarifaEntities(false, maxResults, firstResult);
    }

    private List<Tarifa> findTarifaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarifa.class));
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

    public Tarifa findTarifa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarifa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarifaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarifa> rt = cq.from(Tarifa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void createUsingStoredProcedure(Tarifa t) {
        EntityManager em = getEntityManager();
        try {
//            Query q = em.createNativeQuery("Select insertar_tarifa(?,?)");
//            q.setParameter(1, t.getTarifaNormal());
//            q.setParameter(2, (String) t.getTarifaEspecial());
//            q.getResultList();
            // em.getTransaction().begin();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("insertar_tarifa");
            spq.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            spq.setParameter(1, String.valueOf(t.getTarifaNormal()));
            spq.setParameter(2, String.valueOf(t.getTarifaEspecial()));
            spq.getResultList();
            //spq.executeUpdate();
            //em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        Tarifa t = new Tarifa();
        t.setTarifaNormal(300);
        t.setTarifaEspecial(500);
        TarifaServices jp = new TarifaServices();
        try {
            jp.create(t);
        } catch (Exception ex) {

            // Logger.getLogger(TarifaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
