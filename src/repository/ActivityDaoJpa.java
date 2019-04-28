package repository;

import domain.Activity;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;


public class ActivityDaoJpa extends GenericDaoJpa<Activity> implements ActivityDao {

    public ActivityDaoJpa(){ super(Activity.class); }
    @Override
    public List<Activity> getAll() throws EntityNotFoundException {
        try {
            List<Activity> resultList = entityManager.createNamedQuery("Activities.findAll", Activity.class)
                    .getResultList();
            return resultList;
        } catch (NoResultException ex){
            throw new EntityNotFoundException();
        }
    }
}
