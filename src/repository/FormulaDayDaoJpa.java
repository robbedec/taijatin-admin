package repository;

import domain.FormulaDay;
import domain.User;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import static repository.GenericDaoJpa.entityManager;

public class FormulaDayDaoJpa implements FormulaDayDao
{
    @Override
    public FormulaDay getByDay(int day) throws EntityNotFoundException {
        try {
            return entityManager.createNamedQuery("FormulaDay.findByDay", FormulaDay.class)
                    .setParameter("day", day)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
}
