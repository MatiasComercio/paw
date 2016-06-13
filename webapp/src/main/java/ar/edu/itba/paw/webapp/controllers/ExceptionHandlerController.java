package ar.edu.itba.paw.webapp.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@ControllerAdvice /* Global Handler */
public class ExceptionHandlerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ModelAndView typeMismatchException(final MethodArgumentTypeMismatchException e) {
		LOGGER.warn("Exception: ", (Object[]) e.getStackTrace());
		return new ModelAndView("404");
	}

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

