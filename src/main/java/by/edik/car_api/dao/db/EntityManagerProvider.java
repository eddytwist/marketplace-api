package by.edik.car_api.dao.db;

import by.edik.car_api.dao.db.exception.DbManagerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityManagerProvider {

    private static final ThreadLocal<EntityManager> THREAD_LOCAL = new ThreadLocal<>();

    public static EntityManager getEntityManager() {
        try {
            if (THREAD_LOCAL.get() == null) {
                THREAD_LOCAL.set(HibernateUtil.getEntityManager());
            }
            return THREAD_LOCAL.get();
        } catch (Exception e) {
            throw new DbManagerException("Getting connection failed.", e);
        }
    }

    public static void clear() {
        EntityManager entityManager = getEntityManager();

        if (entityManager != null) {
            entityManager.clear();
            entityManager.close();
            THREAD_LOCAL.remove();
        }
    }
}
