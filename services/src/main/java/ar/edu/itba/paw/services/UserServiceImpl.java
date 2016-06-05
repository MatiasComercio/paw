package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public List<Role> getRole(final int dni) {
		return userDao.getRole(dni);
	}

	@Override
	public String createEmail(final User user) {
		final StringBuilder defaultEmailBuilder = new StringBuilder();
		final char rolePrefix = user.getRole().originalString().toLowerCase().charAt(0);

		defaultEmailBuilder.append(rolePrefix);
		defaultEmailBuilder.append(user.getDni());
		defaultEmailBuilder.append(EMAIL_DOMAIN);

		return defaultEmailBuilder.toString();
	}

	@Transactional
	@Override
	public boolean changePassword(final int dni, final String prevPassword, final String newPassword, final String repeatNewPassword) {
//		if (!newPassword.equals(repeatNewPassword)) {
//			return Result.PASSWORDS_DO_NOT_MATCH;
//		}
		return userDao.changePassword(dni, prevPassword, newPassword);
	}

	@Transactional
	@Override
	public boolean resetPassword(final int dni) {
		return userDao.resetPassword(dni);
	}

	/* Test purpose only */
	/* default */ void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
