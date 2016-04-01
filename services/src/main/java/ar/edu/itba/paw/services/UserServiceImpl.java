package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* +++xchange: kept only for helping purpose. */
@Service
public class UserServiceImpl /*implements UserService*/ {
/*
	@Autowired
	private UserDao userDao;

	@Override
	public User register(final String username, final String password) {
		return userDao.create(username, password);
	}

	@Override
	public User getByUsername(final String username) {
		return userDao.getByUsername(username);
	}

	*//* Test purpose only *//*
	*//* default *//* void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}*/
}
