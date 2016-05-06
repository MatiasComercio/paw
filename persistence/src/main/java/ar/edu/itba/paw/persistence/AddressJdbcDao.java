package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AddressDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.shared.Result;
import org.springframework.stereotype.Repository;

@Repository
public class AddressJdbcDao implements AddressDao {

    @Override
    public boolean hasAddress(Integer dni) {
        return false;
    }

    @Override
    public Result createAddress(Integer dni, Address address) {
        return null;
    }

    @Override
    public Result updateAddress(Integer dni, Address address) {
        return null;
    }
}
