package ma.fstt.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.controller.TableCreation;

import java.util.List;
import java.util.function.Function;

public class Dao {

    public static <R> R tx(Function<EntityManager, R> work) {
        EntityManager em = TableCreation.emf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            R result = work.apply(em);
            transaction.commit();
            return result;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public static <T> List<T> list(String jpql, Class<T> type, Object... params) {
        return tx(em -> {
            var query = em.createQuery(jpql, type);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
            return query.getResultList();
        });
    }

    public static <T> T one(String jpql, Class<T> type, Object... params) {
        List<T> result = list(jpql, type, params);
        return result.isEmpty() ? null : result.get(0);
    }
}
