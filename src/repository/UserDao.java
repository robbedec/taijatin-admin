package repository;

import domain.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface UserDao extends GenericDao<User> {
    public List<User> getAllButNoMembers()
            throws EntityNotFoundException;

    public List<User> getOnlyNoMembers()
            throws EntityNotFoundException;

    public List<User> getAll()
            throws EntityNotFoundException;

    public User getByEmail(String email)
        throws EntityNotFoundException;
}
