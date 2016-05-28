package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Authority;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.RoleClass;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@Primary // +++xremove this when AdmindbcDao is deleted
public class AdminHibernateDao implements AdminDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminHibernateDao.class);

	private static final String GET_ALL_ADMINS = "from Admin";
	private static final String GET_BY_ID = "from Admin as a where a.dni = :dni";
	private static final String IS_INSCRIPTION_ENABLED = "from RoleClass as r where r.role = :role";
	private static final String ROLE_COLUMN = "role";

	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DNI_PARAM = "dni";

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Admin> getAllAdmins() {
		final TypedQuery<Admin> query = em.createQuery(GET_ALL_ADMINS, Admin.class);
		return query.getResultList();
	}

	@Override
	public Result create(final Admin admin) {
		/* +++xdebug */
		em.persist(admin);
		return Result.OK;
	}

	@Override
	public Admin getByDni(final int dni) {
		// +++xcheck
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

	@Override
	public Result deleteAdmin(final int dni) {
		// TODO
		return null;
	}

	@Override
	public Result disableAddInscriptions() {
		// TODO
		return null;
	}

	@Override
	public Result disableDeleteInscriptions() {
		// TODO
		return null;
	}

	@Override
	public Result enableAddInscriptions() {
		// TODO
		return null;
	}

	@Override
	public Result enableDeleteInscriptions() {
		// TODO
		return null;
	}

	@Override
	public boolean isInscriptionEnabled() {
		// +++xcheck
		// get students authorities
		final TypedQuery<RoleClass> query = em.createQuery(IS_INSCRIPTION_ENABLED, RoleClass.class);
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
