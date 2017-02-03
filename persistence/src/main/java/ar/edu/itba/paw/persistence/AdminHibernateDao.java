package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.ProcedureDao;
import ar.edu.itba.paw.models.Authority;
import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.RoleClass;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Repository
public class AdminHibernateDao implements AdminDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminHibernateDao.class);

	private static final String GET_BY_ID = "from Admin as a where a.dni = :dni";
	private static final String GET_ROLE = "from RoleClass as r where r.role = :role";
	private static final String ROLE_COLUMN = "role";

	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DNI_PARAM = "dni";

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ProcedureDao procedureDao;

	@Override
	public List<Admin> getAllAdmins() {
		/*
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<String> query = builder.createQuery(String.class);
		final Root<User> user = query.from(User.class);
		final Predicate equalsPredicate = builder.equal(user.get("email"), email);

		final List<String> emails = em.createQuery(query.select(user.get("email")).where(equalsPredicate))
				.setMaxResults(1).getResultList();

		return !emails.isEmpty();
		 */

		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<Admin> queryForAdmins = builder.createQuery(Admin.class);
		final Root<Admin> fromAdmins = queryForAdmins.from(Admin.class);
		final CriteriaQuery<Admin> orderBy = queryForAdmins.orderBy(
				builder.asc(fromAdmins.get("lastName")),
				builder.asc(fromAdmins.get("firstName")),
				builder.asc(fromAdmins.get("dni"))
		);

		final TypedQuery<Admin> query = em.createQuery(orderBy);

		return query.getResultList();
	}

	@Override
	public boolean create(final Admin admin) {
		em.persist(admin);
		LOGGER.debug("[create] - {}", admin);

		return true;
	}

	@Override
	public void update(final Admin admin) {
		em.merge(admin);
		LOGGER.debug("[update] - {}", admin);
	}

	@Override
	public List<Procedure> getAllProcedures() {
		return procedureDao.getAllProcedures();
	}

	@Override
	public boolean answerProcedure(final Procedure procedure) {
		return procedureDao.updateProcedure(procedure);
	}

	@Override
	public boolean delete(final int dni) {
		// +++xtodo: have to implement front end
		final Admin admin = getByDni(dni);
		em.remove(admin);
		LOGGER.debug("[delete] - {}", admin);

		return true;
	}

	@Override
	public Admin getByDni(final int dni) {
		final TypedQuery<Admin> query = em.createQuery(GET_BY_ID, Admin.class);
		query.setParameter(DNI_PARAM, dni);
		query.setMaxResults(ONE);
		final List<Admin> admins = query.getResultList();
		return admins.isEmpty() ? null : admins.get(FIRST);
	}

	@Override
	public List<Admin> getByFilter(final AdminFilter adminFilter) {
		final UserHibernateDao.QueryFilter<Admin> queryFilter = new UserHibernateDao.QueryFilter<>(em, Admin.class);

		if (adminFilter != null) {
			queryFilter.applyFilters(adminFilter);
		}

		return em.createQuery(queryFilter.getQuery()).getResultList();
	}

	/* +++xchange: all this logic should be on the service when authorities are migrated to runtime */
	private boolean disableAddInscriptions() {
		final TypedQuery<RoleClass> query = em.createQuery(GET_ROLE, RoleClass.class);
		query.setParameter(ROLE_COLUMN, Role.STUDENT);
		query.setMaxResults(ONE);
		final List<RoleClass> roles = query.getResultList();
		final RoleClass role =  roles.get(FIRST);
		final Collection<Authority> authorities = role.getMutableAuthorities();

		authorities.remove(new Authority("ADD_INSCRIPTION"));

		em.persist(role);

		return true;
	}

	private boolean disableDeleteInscriptions() {
		final TypedQuery<RoleClass> query = em.createQuery(GET_ROLE, RoleClass.class);
		query.setParameter(ROLE_COLUMN, Role.STUDENT);
		query.setMaxResults(ONE);
		final List<RoleClass> roles = query.getResultList();
		final RoleClass role =  roles.get(FIRST);
		final Collection<Authority> authorities = role.getMutableAuthorities();

		authorities.remove(new Authority("DELETE_INSCRIPTION"));

		em.persist(role);

		return true;
	}

	@Override
	public boolean disableInscriptions() {
		if(disableAddInscriptions()) {
			if(!disableDeleteInscriptions()) {
				enableAddInscriptions();
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean enableInscriptions() {
		if(enableAddInscriptions()) {
			if(!enableDeleteInscriptions()) {
				disableAddInscriptions();
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean enableAddInscriptions() {
		final TypedQuery<RoleClass> query = em.createQuery(GET_ROLE, RoleClass.class);
		query.setParameter(ROLE_COLUMN, Role.STUDENT);
		query.setMaxResults(ONE);
		final List<RoleClass> roles = query.getResultList();
		final RoleClass role =  roles.get(FIRST);
		final Collection<Authority> authorities = role.getMutableAuthorities();

		authorities.add(new Authority("ADD_INSCRIPTION"));

		em.persist(role);

		return true;
	}

	private boolean enableDeleteInscriptions() {
		final TypedQuery<RoleClass> query = em.createQuery(GET_ROLE, RoleClass.class);
		query.setParameter(ROLE_COLUMN, Role.STUDENT);
		query.setMaxResults(ONE);
		final List<RoleClass> roles = query.getResultList();
		final RoleClass role =  roles.get(FIRST);
		final Collection<Authority> authorities = role.getMutableAuthorities();

		authorities.add(new Authority("DELETE_INSCRIPTION"));

		em.persist(role);

		return true;
	}

	@Override
	public boolean isInscriptionEnabled() {
		// get students authorities
		final TypedQuery<RoleClass> query = em.createQuery(GET_ROLE, RoleClass.class);
		query.setParameter(ROLE_COLUMN, Role.STUDENT);
		query.setMaxResults(ONE);
		final List<RoleClass> roles = query.getResultList();
		if (roles.isEmpty()) {
			return false;
		}
		final Collection<Authority> authorities = roles.get(FIRST).getAuthorities();

		// checks whether required authorities are among student authorities
		final Authority addInscriptionAuthority = new Authority("ADD_INSCRIPTION");
		final Authority deleteInscriptionAuthority = new Authority("DELETE_INSCRIPTION");

		return authorities.contains(addInscriptionAuthority) && authorities.contains(deleteInscriptionAuthority);
	}
}
