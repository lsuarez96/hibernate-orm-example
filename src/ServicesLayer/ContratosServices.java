/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Choferes;
import ModelLayer.Contratos;
import ModelLayer.FormaPago;
import ModelLayer.Turista;
import ModelLayer.Usuarios;
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
public class ContratosServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Contratos contratos) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Choferes contIdChof = contratos.getContIdChof();
            if (contIdChof != null) {
                contIdChof = em.getReference(contIdChof.getClass(), contIdChof.getIdChofer());
                contratos.setContIdChof(contIdChof);
            }
            FormaPago contIdFormaPago = contratos.getContIdFormaPago();
            if (contIdFormaPago != null) {
                contIdFormaPago = em.getReference(contIdFormaPago.getClass(), contIdFormaPago.getIdPago());
                contratos.setContIdFormaPago(contIdFormaPago);
            }
            Turista contIdTur = contratos.getContIdTur();
            if (contIdTur != null) {
                contIdTur = em.getReference(contIdTur.getClass(), contIdTur.getIdTur());
                contratos.setContIdTur(contIdTur);
            }
            Usuarios idUsuario = contratos.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUser());
                contratos.setIdUsuario(idUsuario);
            }
            em.persist(contratos);
            if (contIdChof != null) {
                contIdChof.getContratosList().add(contratos);
                contIdChof = em.merge(contIdChof);
            }
            if (contIdFormaPago != null) {
                contIdFormaPago.getContratosList().add(contratos);
                contIdFormaPago = em.merge(contIdFormaPago);
            }
            if (contIdTur != null) {
                contIdTur.getContratosList().add(contratos);
                contIdTur = em.merge(contIdTur);
            }
            if (idUsuario != null) {
                idUsuario.getContratosList().add(contratos);
                idUsuario = em.merge(idUsuario);
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

    public void edit(Contratos contratos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contratos persistentContratos = em.find(Contratos.class, contratos.getIdContrato());
            Choferes contIdChofOld = persistentContratos.getContIdChof();
            Choferes contIdChofNew = contratos.getContIdChof();
            FormaPago contIdFormaPagoOld = persistentContratos.getContIdFormaPago();
            FormaPago contIdFormaPagoNew = contratos.getContIdFormaPago();
            Turista contIdTurOld = persistentContratos.getContIdTur();
            Turista contIdTurNew = contratos.getContIdTur();
            Usuarios idUsuarioOld = persistentContratos.getIdUsuario();
            Usuarios idUsuarioNew = contratos.getIdUsuario();
//            if (contIdChofNew != null) {
//                contIdChofNew = em.getReference(contIdChofNew.getClass(), contIdChofNew.getIdChofer());
//                contratos.setContIdChof(contIdChofNew);
//            }
//            if (contIdFormaPagoNew != null) {
//                contIdFormaPagoNew = em.getReference(contIdFormaPagoNew.getClass(), contIdFormaPagoNew.getIdPago());
//                contratos.setContIdFormaPago(contIdFormaPagoNew);
//            }
//            if (contIdTurNew != null) {
//                contIdTurNew = em.getReference(contIdTurNew.getClass(), contIdTurNew.getIdTur());
//                contratos.setContIdTur(contIdTurNew);
//            }
//            if (idUsuarioNew != null) {
//                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUser());
//                contratos.setIdUsuario(idUsuarioNew);
//            }
            contratos = em.merge(contratos);
//            if (contIdChofOld != null && !contIdChofOld.equals(contIdChofNew)) {
//                contIdChofOld.getContratosList().remove(contratos);
//                contIdChofOld = em.merge(contIdChofOld);
//            }
//            if (contIdChofNew != null && !contIdChofNew.equals(contIdChofOld)) {
//                contIdChofNew.getContratosList().add(contratos);
//                contIdChofNew = em.merge(contIdChofNew);
//            }
//            if (contIdFormaPagoOld != null && !contIdFormaPagoOld.equals(contIdFormaPagoNew)) {
//                contIdFormaPagoOld.getContratosList().remove(contratos);
//                contIdFormaPagoOld = em.merge(contIdFormaPagoOld);
//            }
//            if (contIdFormaPagoNew != null && !contIdFormaPagoNew.equals(contIdFormaPagoOld)) {
//                contIdFormaPagoNew.getContratosList().add(contratos);
//                contIdFormaPagoNew = em.merge(contIdFormaPagoNew);
//            }
//            if (contIdTurOld != null && !contIdTurOld.equals(contIdTurNew)) {
//                contIdTurOld.getContratosList().remove(contratos);
//                contIdTurOld = em.merge(contIdTurOld);
//            }
//            if (contIdTurNew != null && !contIdTurNew.equals(contIdTurOld)) {
//                contIdTurNew.getContratosList().add(contratos);
//                contIdTurNew = em.merge(contIdTurNew);
//            }
//            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
//                idUsuarioOld.getContratosList().remove(contratos);
//                idUsuarioOld = em.merge(idUsuarioOld);
//            }
//            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
//                idUsuarioNew.getContratosList().add(contratos);
//                idUsuarioNew = em.merge(idUsuarioNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contratos.getIdContrato();
                if (findContratos(id) == null) {
                    throw new NonexistentEntityException("The contratos with id " + id + " no longer exists.");
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
            Contratos contratos;
            try {
                contratos = em.getReference(Contratos.class, id);
                contratos.getIdContrato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contratos with id " + id + " no longer exists.", enfe);
            }
//            Choferes contIdChof = contratos.getContIdChof();
//            if (contIdChof != null) {
//                contIdChof.getContratosList().remove(contratos);
//                contIdChof = em.merge(contIdChof);
//            }
//            FormaPago contIdFormaPago = contratos.getContIdFormaPago();
//            if (contIdFormaPago != null) {
//                contIdFormaPago.getContratosList().remove(contratos);
//                contIdFormaPago = em.merge(contIdFormaPago);
//            }
//            Turista contIdTur = contratos.getContIdTur();
//            if (contIdTur != null) {
//                contIdTur.getContratosList().remove(contratos);
//                contIdTur = em.merge(contIdTur);
//            }
//            Usuarios idUsuario = contratos.getIdUsuario();
//            if (idUsuario != null) {
//                idUsuario.getContratosList().remove(contratos);
//                idUsuario = em.merge(idUsuario);
//            }
            em.remove(contratos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Contratos> findContratosEntities() {
        return findContratosEntities(true, -1, -1);
    }

    public List<Contratos> findContratosEntities(int maxResults, int firstResult) {
        return findContratosEntities(false, maxResults, firstResult);
    }

    private List<Contratos> findContratosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contratos.class));
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

    public Contratos findContratos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contratos.class, id);
        } finally {
            em.close();
        }
    }

    public int getContratosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contratos> rt = cq.from(Contratos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
