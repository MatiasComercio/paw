package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
@Primary // +++xremove this when UserJdbcDao is deleted
public class UserHibernateDao implements UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserHibernateDao.class);

	private static final String GET_ROLE_QUERY = "from User as u where u.dni = :dni";

	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DNI_PARAM = "dni";

	@PersistenceContext
	private EntityManager em;

	@Override
	public <V extends User, T extends User.Builder<V, T>> V getByDni(final int dni, final User.Builder<V, T> builder) {
		// TODO
		return null;
	}

	@Override // +++xchange: return only one role
	public List<Role> getRole(final int dni) {
		// write "from..." not "select...". The second form will be causing an exception
		final TypedQuery<User> query = em.createQuery(GET_ROLE_QUERY, User.class);
		query.setParameter(DNI_PARAM, dni);
		query.setMaxResults(ONE);
		final List<User> users = query.getResultList();
		if (users.isEmpty()) {
			return Collections.emptyList();
		}
		final User user = users.get(FIRST);
		LOGGER.debug("[getRole] - User got by dni: {}", user);
		final List<Role> roles = new LinkedList<>();
		roles.add(user.getRole());
		return roles;
	}

	@Override
	public Result create(final User user, final Role role) {
		em.persist(user);
		// TODO
		return Result.OK;
	}

	@Override
	public Result delete(final int dni) {
		// TODO
		return null;
	}

	@Override
	public Result changePassword(final int dni, final String prevPassword, final String newPassword) {
		// TODO
		return null;
	}

	@Override
	public Result update(final int dni, final User user) {
		// TODO
		return null;
	}

	@Override
	public Result resetPassword(final int dni) {
		// TODO
		return null;
	}

	// QueryFilter
	/* package-private */static class QueryFilter<T extends User> {

		private final CriteriaBuilder builder;
		private final CriteriaQuery<T> query;
		private final EntityType<T> type;
		private final Root<T> root;

		private final List<Predicate> predicates;

		private final EntityManager em;

		/* package-private */ QueryFilter(final EntityManager em, final Class<T> modelTClass) {
			this.em = em;
			builder = em.getCriteriaBuilder();
			query = builder.createQuery(modelTClass);
			// 'type' does not use modelTClass because declaredSingularAttribute are load with
			// the explicitly declared fields of the entity, i.e., it does not inherit the ones
			// from User.class, and we need some of those fields to filter
			type = em.getMetamodel().entity(modelTClass);
			root = query.from(modelTClass);
			predicates = new ArrayList<>();
		}

		/* package-private */ void applyFilters(final UserFilter userFilter) {
			filterById(userFilter);
			filterByFirstName(userFilter);
			filterByLastName(userFilter);
		}

		/* package-private */ CriteriaQuery<T> getQuery() {
			return query.where(builder.and(predicates.toArray(new Predicate[] {})));
		}

		private void filterById(final UserFilter userFilter) {
			final Object id = userFilter.getId();
			final String attribute = userFilter.getAttributeId();

			final Predicate p;

			if (id != null){
				p = builder.like(
						root.get(
								type.getSingularAttribute(attribute, id.getClass())
						).as(String.class),
						"%" + escapeFilter(id) + "%"
				);
				predicates.add(p);
			}
		}

		private void filterByFirstName(final UserFilter userFilter) {
			final Object keyword = userFilter.getFirstName();
			addPredicate("firstName", keyword);
		}

		private void filterByLastName(final UserFilter userFilter) {
			final Object keyword = userFilter.getLastName();
			addPredicate("lastName", keyword);
		}

		private <Y> void  addPredicate(final String attribute, final Object keyword) {
			final Predicate p;
			if (keyword != null) {
				p = builder.like(
						builder.lower(
								root.get(
										type.getSingularAttribute(attribute, keyword.getClass())
								).as(String.class)
						),
						"%" + escapeFilter(keyword).toLowerCase() + "%"
				);
				predicates.add(p);
			}
		}

		private String escapeFilter(final Object filter) {
			return filter.toString().replace("%", "\\%").replace("_", "\\_");
		}
	}
}
