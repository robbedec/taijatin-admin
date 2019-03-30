package repository;

import domain.Address;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

public class AddressDaoJpa extends GenericDaoJpa<Address> implements AddressDao {

    public AddressDaoJpa(){
        super(Address.class);
    }
    @Override
    public List<Address> getAll() throws EntityNotFoundException {
        try {
            return entityManager.createNamedQuery("Address.findAll", Address.class)
                    .getResultList();
        } catch (NoResultException ex){
            throw new EntityNotFoundException();
        }
    }
}
