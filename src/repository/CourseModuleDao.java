package repository;

import domain.CourseModule;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface CourseModuleDao extends GenericDao<CourseModule> {
    public List<CourseModule> getAll()
        throws EntityNotFoundException;
}
