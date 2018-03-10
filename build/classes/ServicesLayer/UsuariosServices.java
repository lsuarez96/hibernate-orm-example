/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServicesLayer;

import Exceptions.IllegalOrphanException;
import Exceptions.NonexistentEntityException;
import ModelLayer.Contratos;
import ModelLayer.RolUsuario;
import ModelLayer.Trazas;
import ModelLayer.Usuarios;
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
public class UsuariosServices implements Serializable {

    public EntityManager getEntityManager() {
        return JPAUtil.getEntityManager();
    }

    public void create(Usuarios usuarios) throws Exception {
        if (usuarios.getRolUsuarioList() == null) {
            usuarios.setRolUsuarioList(new ArrayList<RolUsuario>());
        }
        if (usuarios.getTrazasList() == null) {
            usuarios.setTrazasList(new ArrayList<Trazas>());
        }
        if (usuarios.getContratosList() == null) {
            usuarios.setContratosList(new ArrayList<Contratos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RolUsuario> attachedRolUsuarioList = new ArrayList<RolUsuario>();
            for (RolUsuario rolUsuarioListRolUsuarioToAttach : usuarios.getRolUsuarioList()) {
                rolUsuarioListRolUsuarioToAttach = em.getReference(rolUsuarioListRolUsuarioToAttach.getClass(), rolUsuarioListRolUsuarioToAttach.getIdRolUsuario());
                attachedRolUsuarioList.add(rolUsuarioListRolUsuarioToAttach);
            }
            usuarios.setRolUsuarioList(attachedRolUsuarioList);
            List<Trazas> attachedTrazasList = new ArrayList<Trazas>();
            for (Trazas trazasListTrazasToAttach : usuarios.getTrazasList()) {
                trazasListTrazasToAttach = em.getReference(trazasListTrazasToAttach.getClass(), trazasListTrazasToAttach.getIdTraza());
                attachedTrazasList.add(trazasListTrazasToAttach);
            }
            usuarios.setTrazasList(attachedTrazasList);
            List<Contratos> attachedContratosList = new ArrayList<Contratos>();
            for (Contratos contratosListContratosToAttach : usuarios.getContratosList()) {
                contratosListContratosToAttach = em.getReference(contratosListContratosToAttach.getClass(), contratosListContratosToAttach.getIdContrato());
                attachedContratosList.add(contratosListContratosToAttach);
            }
            usuarios.setContratosList(attachedContratosList);
            em.persist(usuarios);
            for (RolUsuario rolUsuarioListRolUsuario : usuarios.getRolUsuarioList()) {
                Usuarios oldUsuarioIdUserOfRolUsuarioListRolUsuario = rolUsuarioListRolUsuario.getUsuarioIdUser();
                rolUsuarioListRolUsuario.setUsuarioIdUser(usuarios);
                rolUsuarioListRolUsuario = em.merge(rolUsuarioListRolUsuario);
                if (oldUsuarioIdUserOfRolUsuarioListRolUsuario != null) {
                    oldUsuarioIdUserOfRolUsuarioListRolUsuario.getRolUsuarioList().remove(rolUsuarioListRolUsuario);
                    oldUsuarioIdUserOfRolUsuarioListRolUsuario = em.merge(oldUsuarioIdUserOfRolUsuarioListRolUsuario);
                }
            }
            for (Trazas trazasListTrazas : usuarios.getTrazasList()) {
                Usuarios oldIdUsuarioOfTrazasListTrazas = trazasListTrazas.getIdUsuario();
                trazasListTrazas.setIdUsuario(usuarios);
                trazasListTrazas = em.merge(trazasListTrazas);
                if (oldIdUsuarioOfTrazasListTrazas != null) {
                    oldIdUsuarioOfTrazasListTrazas.getTrazasList().remove(trazasListTrazas);
                    oldIdUsuarioOfTrazasListTrazas = em.merge(oldIdUsuarioOfTrazasListTrazas);
                }
            }
            for (Contratos contratosListContratos : usuarios.getContratosList()) {
                Usuarios oldIdUsuarioOfContratosListContratos = contratosListContratos.getIdUsuario();
                contratosListContratos.setIdUsuario(usuarios);
                contratosListContratos = em.merge(contratosListContratos);
                if (oldIdUsuarioOfContratosListContratos != null) {
                    oldIdUsuarioOfContratosListContratos.getContratosList().remove(contratosListContratos);
                    oldIdUsuarioOfContratosListContratos = em.merge(oldIdUsuarioOfContratosListContratos);
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

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUser());
//            List<RolUsuario> rolUsuarioListOld = persistentUsuarios.getRolUsuarioList();
//            List<RolUsuario> rolUsuarioListNew = usuarios.getRolUsuarioList();
//            List<Trazas> trazasListOld = persistentUsuarios.getTrazasList();
//            List<Trazas> trazasListNew = usuarios.getTrazasList();
//            List<Contratos> contratosListOld = persistentUsuarios.getContratosList();
//            List<Contratos> contratosListNew = usuarios.getContratosList();
//            List<String> illegalOrphanMessages = null;
//            for (RolUsuario rolUsuarioListOldRolUsuario : rolUsuarioListOld) {
//                if (!rolUsuarioListNew.contains(rolUsuarioListOldRolUsuario)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain RolUsuario " + rolUsuarioListOldRolUsuario + " since its usuarioIdUser field is not nullable.");
//                }
//            }
//            for (Trazas trazasListOldTrazas : trazasListOld) {
//                if (!trazasListNew.contains(trazasListOldTrazas)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Trazas " + trazasListOldTrazas + " since its idUsuario field is not nullable.");
//                }
//            }
//            for (Contratos contratosListOldContratos : contratosListOld) {
//                if (!contratosListNew.contains(contratosListOldContratos)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Contratos " + contratosListOldContratos + " since its idUsuario field is not nullable.");
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
//            usuarios.setRolUsuarioList(rolUsuarioListNew);
//            List<Trazas> attachedTrazasListNew = new ArrayList<Trazas>();
//            for (Trazas trazasListNewTrazasToAttach : trazasListNew) {
//                trazasListNewTrazasToAttach = em.getReference(trazasListNewTrazasToAttach.getClass(), trazasListNewTrazasToAttach.getIdTraza());
//                attachedTrazasListNew.add(trazasListNewTrazasToAttach);
//            }
//            trazasListNew = attachedTrazasListNew;
//            usuarios.setTrazasList(trazasListNew);
//            List<Contratos> attachedContratosListNew = new ArrayList<Contratos>();
//            for (Contratos contratosListNewContratosToAttach : contratosListNew) {
//                contratosListNewContratosToAttach = em.getReference(contratosListNewContratosToAttach.getClass(), contratosListNewContratosToAttach.getIdContrato());
//                attachedContratosListNew.add(contratosListNewContratosToAttach);
//            }
//            contratosListNew = attachedContratosListNew;
//            usuarios.setContratosList(contratosListNew);
            usuarios = em.merge(usuarios);
//            for (RolUsuario rolUsuarioListNewRolUsuario : rolUsuarioListNew) {
//                if (!rolUsuarioListOld.contains(rolUsuarioListNewRolUsuario)) {
//                    Usuarios oldUsuarioIdUserOfRolUsuarioListNewRolUsuario = rolUsuarioListNewRolUsuario.getUsuarioIdUser();
//                    rolUsuarioListNewRolUsuario.setUsuarioIdUser(usuarios);
//                    rolUsuarioListNewRolUsuario = em.merge(rolUsuarioListNewRolUsuario);
//                    if (oldUsuarioIdUserOfRolUsuarioListNewRolUsuario != null && !oldUsuarioIdUserOfRolUsuarioListNewRolUsuario.equals(usuarios)) {
//                        oldUsuarioIdUserOfRolUsuarioListNewRolUsuario.getRolUsuarioList().remove(rolUsuarioListNewRolUsuario);
//                        oldUsuarioIdUserOfRolUsuarioListNewRolUsuario = em.merge(oldUsuarioIdUserOfRolUsuarioListNewRolUsuario);
//                    }
//                }
//            }
//            for (Trazas trazasListNewTrazas : trazasListNew) {
//                if (!trazasListOld.contains(trazasListNewTrazas)) {
//                    Usuarios oldIdUsuarioOfTrazasListNewTrazas = trazasListNewTrazas.getIdUsuario();
//                    trazasListNewTrazas.setIdUsuario(usuarios);
//                    trazasListNewTrazas = em.merge(trazasListNewTrazas);
//                    if (oldIdUsuarioOfTrazasListNewTrazas != null && !oldIdUsuarioOfTrazasListNewTrazas.equals(usuarios)) {
//                        oldIdUsuarioOfTrazasListNewTrazas.getTrazasList().remove(trazasListNewTrazas);
//                        oldIdUsuarioOfTrazasListNewTrazas = em.merge(oldIdUsuarioOfTrazasListNewTrazas);
//                    }
//                }
//            }
//            for (Contratos contratosListNewContratos : contratosListNew) {
//                if (!contratosListOld.contains(contratosListNewContratos)) {
//                    Usuarios oldIdUsuarioOfContratosListNewContratos = contratosListNewContratos.getIdUsuario();
//                    contratosListNewContratos.setIdUsuario(usuarios);
//                    contratosListNewContratos = em.merge(contratosListNewContratos);
//                    if (oldIdUsuarioOfContratosListNewContratos != null && !oldIdUsuarioOfContratosListNewContratos.equals(usuarios)) {
//                        oldIdUsuarioOfContratosListNewContratos.getContratosList().remove(contratosListNewContratos);
//                        oldIdUsuarioOfContratosListNewContratos = em.merge(oldIdUsuarioOfContratosListNewContratos);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUser();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<RolUsuario> rolUsuarioListOrphanCheck = usuarios.getRolUsuarioList();
//            for (RolUsuario rolUsuarioListOrphanCheckRolUsuario : rolUsuarioListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the RolUsuario " + rolUsuarioListOrphanCheckRolUsuario + " in its rolUsuarioList field has a non-nullable usuarioIdUser field.");
//            }
//            List<Trazas> trazasListOrphanCheck = usuarios.getTrazasList();
//            for (Trazas trazasListOrphanCheckTrazas : trazasListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Trazas " + trazasListOrphanCheckTrazas + " in its trazasList field has a non-nullable idUsuario field.");
//            }
//            List<Contratos> contratosListOrphanCheck = usuarios.getContratosList();
//            for (Contratos contratosListOrphanCheckContratos : contratosListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Contratos " + contratosListOrphanCheckContratos + " in its contratosList field has a non-nullable idUsuario field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        ServicesLocator.getServicesLocatorInstance().getTrazaServices().persistTraces();
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        List<Usuarios> result;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuarios findUserByName(String userName) {
        EntityManager em = getEntityManager();
        Usuarios u = null;
        try {
            Query q = em.createNamedQuery("Usuarios.findByUsuario");
            q.setParameter("usuario", userName);
            List l = q.getResultList();
            if (!l.isEmpty()) {
                u = (Usuarios) l.get(0);
            }
        } finally {
            em.close();
        }
        return u;
    }
}
