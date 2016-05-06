package ar.edu.itba.paw.services;

public interface AddressService {

    /**
     * Checks if the given user has an address
     * @param dni the user's dni
     * @return true if the user has an address;
     *          false in other case
     */
    boolean hasAddress(Integer dni);
}
