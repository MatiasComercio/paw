package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Procedure;

import java.util.List;

public interface ProcedureDao {
    /**
     * Create a new procedure
     * @param procedure the procedure to persist
     * @return true if the procedure was created successfully; else false
     */
    boolean createProcedure(Procedure procedure);

    /**
     * Respond to a specific procedure already created
     * @param procedure the original procedure with the changes
     * @return true if the procedure was modified successfully; else false
     */
    public boolean updateProcedure(Procedure procedure);

    /**
     * Get all the procedures created by a user
     * @param userId of the desired procedures
     * @return a list of all the procedures, not null
     */
    List<Procedure> getProceduresByUser(int userId);

    /**
     * Get all the procedures created by all the users
     * @return the list of all the procedures, not null
     */
    List<Procedure> getAllProcedures();
}
