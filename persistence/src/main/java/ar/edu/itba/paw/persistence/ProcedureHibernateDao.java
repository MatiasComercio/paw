package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ProcedureDao;
import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.ProcedureState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProcedureHibernateDao implements ProcedureDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcedureHibernateDao.class);

    // Queries //

    private static final String GET_BY_SENDER =
            "from Procedure as p where p.sender_id = :userId";
    private static final String GET_ALL =
            "from Procedure";

    // Params //

    private static final String PARAM_SENDER_ID = "sender_id";

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean createProcedure(final Procedure procedure) {
        em.persist(procedure);
        LOGGER.debug("[create] - {}", procedure);

        return true;
    }

    @Override
    public boolean updateProcedure(final Procedure procedure) {
        /* +++xcheck that this is correct */
        em.merge(procedure);
        LOGGER.debug("[update] - {}", procedure);

        return true;
    }

    @Override
    public List<Procedure> getProceduresByUser(final int senderId) {
        final TypedQuery<Procedure> query = em.createQuery(
                GET_BY_SENDER,
                Procedure.class
        );
        query.setParameter(PARAM_SENDER_ID, senderId);

        return query.getResultList();
    }

    @Override
    public List<Procedure> getAllProcedures() {
        final TypedQuery<Procedure> query = em.createQuery(GET_ALL, Procedure.class);

        return query.getResultList();
    }
}
