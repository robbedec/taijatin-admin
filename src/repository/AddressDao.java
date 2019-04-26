package repository;

import domain.Address;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface AddressDao extends GenericDao<Address> {
    public List<Address> getAll()
        throws EntityNotFoundException;

    public Address get(int id)
        throws EntityNotFoundException;
}
