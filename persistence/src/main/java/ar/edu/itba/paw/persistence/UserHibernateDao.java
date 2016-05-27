package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Primary // remove this when UserJdbcDao is deleted
public class UserHibernateDao implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <V extends User, T extends User.Builder<V, T>> V getByDni(final int dni, final User.Builder<V, T> builder) {
		// TODO
		return null;
	}

	@Override
	public List<Role> getRole(final int dni) {
		final TypedQuery<User> query = entityManager.createQuery("select User from User as u where u.dni = :dni", User.class);
		query.getSingleResult();
		return null;
	}

	@Override
	public Result create(final User user, final Role role) {
		// TODO
		return null;
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
}
