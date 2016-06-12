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
    boolean hasAddress(int dni);

    /**
     * Creates a given address mapped to a user's dni
     * @param dni the user's dni
     * @param address the address' data
     * @return OK if the operation was successful;
     *          DNI_NOT_EXISTS if there is no address matched to the DNI;
     *          ERROR_UNKNOWN else;
     */
    Result createAddress(int dni, Address address);

    /**
     * Update a given address mapped to a user's dni
     * @param dni the user's dni
     * @param address the address' data
     * @return OK if the operation was successful;
     *      ERROR_UNKNOWN else;
     */
    Result updateAddress(int dni, Address address);
}
