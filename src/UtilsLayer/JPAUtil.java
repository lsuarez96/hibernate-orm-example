/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.cache.ehcache.internal.util.HibernateEhcacheUtils;

/**
 *
 * @author Yordanka
 */
public class JPAUtil {

    private static EntityManagerFactory entityManagerFactory = null;

    private static InheritableThreadLocal<EntityManager> entityManagerThreadLocal = new InheritableThreadLocal<EntityManager>();

    static {
        try {
            entityManagerFactory = Persistence
                    .createEntityManagerFactory("RentaCar_ORMPU");  
//            Map<String,Object> map=new HashMap<String, Object>();
//            System.out.println(entityManagerFactory.getProperties());
//           map.put("hibernate.connection.username","postgres");
//           map.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/rentaCar");
//           map.put("hibernate.conection.password", "0000");
//           EntityManager em=entityManagerFactory.createEntityManager(map);
//           Map<String,Object> map=em.getProperties();
//            System.out.println(em.getProperties());
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed. "
                    + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Get the configured entityManagerFactory
     *
     * @return entityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {

        return entityManagerFactory;
    }

    /**
     * Get entity manager from thread
     *
     * @return entity manager
     */
    public static EntityManager getEntityManager() {
        if (entityManagerThreadLocal.get() == null || entityManagerThreadLocal.get().isOpen() == false) {
            entityManagerThreadLocal.set(entityManagerFactory
                    .createEntityManager());
        }
        return entityManagerThreadLocal.get();
    }
}
