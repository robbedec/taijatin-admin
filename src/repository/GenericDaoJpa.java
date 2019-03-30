package repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class GenericDaoJpa<T> implements GenericDao<T> {

    private static final String PU_NAME = "javaG10";
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PU_NAME);

    protected static final EntityManager entityManager = entityManagerFactory.createEntityManager();

    private final Class<T> type;

    public GenericDaoJpa(Class<T> type){
        this.type = type;
    }

    public static void closePersistency(){
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void startTransaction(){
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction(){
        entityManager.getTransaction().commit();
    }

    public static void rollBackTransaction(){
        entityManager.getTransaction().rollback();
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("select entity from " + type.getName() + " entity", type).getResultList();
    }

    @Override
    public T get(Long id) {
        return entityManager.find(type, id);
    }

    @Override
    public T update(T object) {
        return entityManager.merge(object);
    }

    @Override
    public void delete(T object) {
        entityManager.remove(entityManager.merge(object));
    }

    @Override
    public void insert(T object) {
        entityManager.persist(object);
    }

    @Override
    public boolean exists(Long id) {
        T entity = entityManager.find(type, id);
        return entity != null;
    }
}
