package repository;

import domain.Activity;
import domain.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ActivityDao extends GenericDao<Activity> {
    public List<Activity> getAll()
            throws EntityNotFoundException;

}
