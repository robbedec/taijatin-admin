package repository;

import domain.FormulaDay;

import javax.persistence.EntityNotFoundException;

public interface FormulaDayDao {
    public FormulaDay getByDay(int day)
            throws EntityNotFoundException;
}
