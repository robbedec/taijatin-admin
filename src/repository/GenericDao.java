package repository;

import java.util.List;

/**
 * @param <T>
 */
public interface GenericDao<T> {
    public List<T> getAll();
    public T get(Long id);
    public T update(T object);
    public void delete(T object);
    public void insert(T object);
    public boolean exists(Long id);
}
