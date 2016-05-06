package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AddressDao;
import ar.edu.itba.paw.interfaces.AddressService;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public boolean hasAddress(Integer dni) {
        return addressDao.hasAddress(dni);
    }

    @Override
    public Result createAddress(Integer dni, Address address) {
        return addressDao.createAddress(dni, address);
    }

    @Override
    public Result updateAddress(Integer dni, Address address) {
        return addressDao.updateAddress(dni, address);
    }
}
