package ar.edu.itba.paw.webapp.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice /* Global Handler */
public class ExceptionHandlerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(DataAccessException.class)
	public ModelAndView dataAccessException(final DataAccessException e) {
		LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
		return new ModelAndView("503");
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView exception(final Exception e) {
		LOGGER.warn("Exception: {}", (Object[]) e.getStackTrace());
		return new ModelAndView("500");
	}
}

