package repository;

import domain.User;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

public class UserDaoJpa extends GenericDaoJpa<User> implements UserDao {

    public UserDaoJpa(){
        super(User.class);
    }
    @Override
    public List<User> getAll() throws EntityNotFoundException {
        try {
            return entityManager.createNamedQuery("Users.findAll", User.class)
                    .getResultList();
        } catch (NoResultException ex){
            throw new EntityNotFoundException();
        }
    }
}

