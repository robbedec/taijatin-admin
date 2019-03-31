package repository;

import domain.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface UserDao extends GenericDao<User> {
    public List<User> getAll()
            throws EntityNotFoundException;
}
