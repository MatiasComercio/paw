package ar.edu.itba.paw.interfaces;


import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.shared.Result;

public interface AddressDao {

    /**
     * Checks if the given user has an address
     * @param dni the user's dni
     * @return true if the user has an address;
     *          false in other case
     */
    boolean hasAddress(Integer dni);

    /**
     * Creates a given address mapped to a user's dni
     * @param dni the user's dni
     * @param address the address' data
     * @return OK if the operation was successful;
     *      +++xdocument
     */
    Result createAddress(Integer dni, Address address);

    /**
     * Update a given address mapped to a user's dni
     * @param dni the user's dni
     * @param address the address' data
     * @return OK if the operation was successful;
     *      +++xdocument
     */
    Result updateAddress(Integer dni, Address address);
}
