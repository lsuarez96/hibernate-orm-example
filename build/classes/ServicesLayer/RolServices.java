/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Rol;
import ModelLayer.RolUsuario;
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
public class RolServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getRolUsuarioList() == null) {
            rol.setRolUsuarioList(new ArrayList<RolUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RolUsuario> attachedRolUsuarioList = new ArrayList<RolUsuario>();
            for (RolUsuario rolUsuarioListRolUsuarioToAttach : rol.getRolUsuarioList()) {
                rolUsuarioListRolUsuarioToAttach = em.getReference(rolUsuarioListRolUsuarioToAttach.getClass(), rolUsuarioListRolUsuarioToAttach.getIdRolUsuario());
                attachedRolUsuarioList.add(rolUsuarioListRolUsuarioToAttach);
            }
            rol.setRolUsuarioList(attachedRolUsuarioList);
            em.persist(rol);
            for (RolUsuario rolUsuarioListRolUsuario : rol.getRolUsuarioList()) {
                Rol oldRolIdRolOfRolUsuarioListRolUsuario = rolUsuarioListRolUsuario.getRolIdRol();
                rolUsuarioListRolUsuario.setRolIdRol(rol);
                rolUsuarioListRolUsuario = em.merge(rolUsuarioListRolUsuario);
                if (oldRolIdRolOfRolUsuarioListRolUsuario != null) {
                    oldRolIdRolOfRolUsuarioListRolUsuario.getRolUsuarioList().remove(rolUsuarioListRolUsuario);
                    oldRolIdRolOfRolUsuarioListRolUsuario = em.merge(oldRolIdRolOfRolUsuarioListRolUsuario);
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

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdRol());
//            List<RolUsuario> rolUsuarioListOld = persistentRol.getRolUsuarioList();
//            List<RolUsuario> rolUsuarioListNew = rol.getRolUsuarioList();
//            List<String> illegalOrphanMessages = null;
//            for (RolUsuario rolUsuarioListOldRolUsuario : rolUsuarioListOld) {
//                if (!rolUsuarioListNew.contains(rolUsuarioListOldRolUsuario)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain RolUsuario " + rolUsuarioListOldRolUsuario + " since its rolIdRol field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<RolUsuario> attachedRolUsuarioListNew = new ArrayList<RolUsuario>();
//            for (RolUsuario rolUsuarioListNewRolUsuarioToAttach : rolUsuarioListNew) {
//                rolUsuarioListNewRolUsuarioToAttach = em.getReference(rolUsuarioListNewRolUsuarioToAttach.getClass(), rolUsuarioListNewRolUsuarioToAttach.getIdRolUsuario());
//                attachedRolUsuarioListNew.add(rolUsuarioListNewRolUsuarioToAttach);
//            }
//            rolUsuarioListNew = attachedRolUsuarioListNew;
//            rol.setRolUsuarioList(rolUsuarioListNew);
            rol = em.merge(rol);
//            for (RolUsuario rolUsuarioListNewRolUsuario : rolUsuarioListNew) {
//                if (!rolUsuarioListOld.contains(rolUsuarioListNewRolUsuario)) {
//                    Rol oldRolIdRolOfRolUsuarioListNewRolUsuario = rolUsuarioListNewRolUsuario.getRolIdRol();
//                    rolUsuarioListNewRolUsuario.setRolIdRol(rol);
//                    rolUsuarioListNewRolUsuario = em.merge(rolUsuarioListNewRolUsuario);
//                    if (oldRolIdRolOfRolUsuarioListNewRolUsuario != null && !oldRolIdRolOfRolUsuarioListNewRolUsuario.equals(rol)) {
//                        oldRolIdRolOfRolUsuarioListNewRolUsuario.getRolUsuarioList().remove(rolUsuarioListNewRolUsuario);
//                        oldRolIdRolOfRolUsuarioListNewRolUsuario = em.merge(oldRolIdRolOfRolUsuarioListNewRolUsuario);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getIdRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RolUsuario> rolUsuarioListOrphanCheck = rol.getRolUsuarioList();
            for (RolUsuario rolUsuarioListOrphanCheckRolUsuario : rolUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the RolUsuario " + rolUsuarioListOrphanCheckRolUsuario + " in its rolUsuarioList field has a non-nullable rolIdRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Rol findRoleByName(String rolName) {
        EntityManager em = getEntityManager();
        Rol u = null;
        try {
            Query q = em.createNamedQuery("Rol.findByTipoRol");
            q.setParameter("tipoRol", rolName);
            List l = q.getResultList();
            if (!l.isEmpty()) {
                u = (Rol) l.get(0);
            }
        } finally {
            em.close();
        }
        return u;
    }

}
