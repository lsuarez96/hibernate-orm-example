/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.NonexistentEntityException;
import ModelLayer.Rol;
import ModelLayer.RolUsuario;
import ModelLayer.Usuarios;
import UtilsLayer.Cipher;
import UtilsLayer.JPAUtil;
import java.io.Serializable;
import java.util.LinkedList;
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
public class RolUsuarioServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(RolUsuario rolUsuario) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rolIdRol = rolUsuario.getRolIdRol();
            if (rolIdRol != null) {
                rolIdRol = em.getReference(rolIdRol.getClass(), rolIdRol.getIdRol());
                rolUsuario.setRolIdRol(rolIdRol);
            }
            Usuarios usuarioIdUser = rolUsuario.getUsuarioIdUser();
            if (usuarioIdUser != null) {
                usuarioIdUser = em.getReference(usuarioIdUser.getClass(), usuarioIdUser.getIdUser());
                rolUsuario.setUsuarioIdUser(usuarioIdUser);
            }
            em.persist(rolUsuario);
            if (rolIdRol != null) {
                rolIdRol.getRolUsuarioList().add(rolUsuario);
                rolIdRol = em.merge(rolIdRol);
            }
            if (usuarioIdUser != null) {
                usuarioIdUser.getRolUsuarioList().add(rolUsuario);
                usuarioIdUser = em.merge(usuarioIdUser);
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

    public void edit(RolUsuario rolUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolUsuario persistentRolUsuario = em.find(RolUsuario.class, rolUsuario.getIdRolUsuario());
//            Rol rolIdRolOld = persistentRolUsuario.getRolIdRol();
//            Rol rolIdRolNew = rolUsuario.getRolIdRol();
//            Usuarios usuarioIdUserOld = persistentRolUsuario.getUsuarioIdUser();
//            Usuarios usuarioIdUserNew = rolUsuario.getUsuarioIdUser();
//            if (rolIdRolNew != null) {
//                rolIdRolNew = em.getReference(rolIdRolNew.getClass(), rolIdRolNew.getIdRol());
//                rolUsuario.setRolIdRol(rolIdRolNew);
//            }
//            if (usuarioIdUserNew != null) {
//                usuarioIdUserNew = em.getReference(usuarioIdUserNew.getClass(), usuarioIdUserNew.getIdUser());
//                rolUsuario.setUsuarioIdUser(usuarioIdUserNew);
//            }
            rolUsuario = em.merge(rolUsuario);
//            if (rolIdRolOld != null && !rolIdRolOld.equals(rolIdRolNew)) {
//                rolIdRolOld.getRolUsuarioList().remove(rolUsuario);
//                rolIdRolOld = em.merge(rolIdRolOld);
//            }
//            if (rolIdRolNew != null && !rolIdRolNew.equals(rolIdRolOld)) {
//                rolIdRolNew.getRolUsuarioList().add(rolUsuario);
//                rolIdRolNew = em.merge(rolIdRolNew);
//            }
//            if (usuarioIdUserOld != null && !usuarioIdUserOld.equals(usuarioIdUserNew)) {
//                usuarioIdUserOld.getRolUsuarioList().remove(rolUsuario);
//                usuarioIdUserOld = em.merge(usuarioIdUserOld);
//            }
//            if (usuarioIdUserNew != null && !usuarioIdUserNew.equals(usuarioIdUserOld)) {
//                usuarioIdUserNew.getRolUsuarioList().add(rolUsuario);
//                usuarioIdUserNew = em.merge(usuarioIdUserNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolUsuario.getIdRolUsuario();
                if (findRolUsuario(id) == null) {
                    throw new NonexistentEntityException("The rolUsuario with id " + id + " no longer exists.");
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
            RolUsuario rolUsuario;
            try {
                rolUsuario = em.getReference(RolUsuario.class, id);
                rolUsuario.getIdRolUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolUsuario with id " + id + " no longer exists.", enfe);
            }
//            Rol rolIdRol = rolUsuario.getRolIdRol();
//            if (rolIdRol != null) {
//                rolIdRol.getRolUsuarioList().remove(rolUsuario);
//                rolIdRol = em.merge(rolIdRol);
//            }
//            Usuarios usuarioIdUser = rolUsuario.getUsuarioIdUser();
//            if (usuarioIdUser != null) {
//                usuarioIdUser.getRolUsuarioList().remove(rolUsuario);
//                usuarioIdUser = em.merge(usuarioIdUser);
//            }
            em.remove(rolUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<RolUsuario> findRolUsuarioEntities() {
        return findRolUsuarioEntities(true, -1, -1);
    }

    public List<RolUsuario> findRolUsuarioEntities(int maxResults, int firstResult) {
        return findRolUsuarioEntities(false, maxResults, firstResult);
    }

    private List<RolUsuario> findRolUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        List<RolUsuario> result;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolUsuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            result = q.getResultList();
        } finally {
            em.close();
        }
        //ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
        return result;
    }

    public RolUsuario findRolUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolUsuario> rt = cq.from(RolUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Rol> login(String userName, String password) {
        List<Rol> assosiatedRolesList = new LinkedList<>();
        boolean validLogin;
        String encryptedPassword = Cipher.SHA256(password);
        Usuarios user = ServicesLocator.getServicesLocatorInstance().getUsuarioServices().findUserByName(userName);
        if (user != null) {
            validLogin = user.getPasswordUsuario().equals(encryptedPassword);
            if (validLogin) {
                List<RolUsuario> rulist = findRolUsuarioEntities();
                rulist.stream().filter((ru) -> (ru.getUsuarioIdUser().equals(user))).forEachOrdered((ru) -> {
                    assosiatedRolesList.add(ru.getRolIdRol());
                });
            } else {
                return null;
            }
        } else {
            return null;
        }
        return assosiatedRolesList;
    }

}
