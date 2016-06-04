package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
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
	public Result changePassword(final int dni, final String prevPassword, final String newPassword, final String repeatNewPassword) {
		if (!newPassword.equals(repeatNewPassword)) {
			return Result.PASSWORDS_DO_NOT_MATCH;
		}
		if(dni < 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.changePassword(dni, prevPassword, newPassword);
	}

	@Transactional
	@Override
	public Result resetPassword(final int dni) {
		if(dni <= 0) {
			return Result.ERROR_DNI_OUT_OF_BOUNDS;
		}
		return userDao.resetPassword(dni);
	}

	/* Test purpose only */
	/* default */ void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
