package repository;

import domain.CourseModule;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

public class CourseModuleDaoJpa extends  GenericDaoJpa<CourseModule> implements CourseModuleDao {
    public CourseModuleDaoJpa() {
        super(CourseModule.class);
    }

    @Override
    public List<CourseModule> getAll() throws EntityNotFoundException {
        try {
            return entityManager.createNamedQuery("CourseModules.findAll", CourseModule.class)
                    .getResultList();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
