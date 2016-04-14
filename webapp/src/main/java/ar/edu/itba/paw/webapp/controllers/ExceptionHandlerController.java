package ar.edu.itba.paw.webapp.controllers;


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

	private static final String PAGE_HEADER =
			"Error en los datos";
	private static final String DESCRIPTION =
			"Por favor, revise los siguientes datos brindados e inténtelo nuevamente.";

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ModelAndView typeMismatchException(final ServletRequest servletRequest, final WebRequest request) {

		final Map<String, String[]> parametersMap = request.getParameterMap();

		final List<String> details = new LinkedList<>();

		final BiConsumer<String, String[]> parametersMapConsumer = (s, strings) ->
				details.add(s + " : " + Arrays.toString(strings));

		if (parametersMap != null) {
			parametersMap.forEach(parametersMapConsumer);
		}

		final ModelAndView mav = new ModelAndView("error");
		mav.addObject("page_header", PAGE_HEADER);
		mav.addObject("description", DESCRIPTION);
		mav.addObject("details", details);

		return mav;
	}
}

