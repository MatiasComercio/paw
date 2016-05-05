package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class HTTPErrorsController {
	@RequestMapping(value="/errors/401")
	public ModelAndView handle401() {
		return new ModelAndView("401");
	}

	@RequestMapping(value="/errors/403")
	public ModelAndView handle403() {
		return new ModelAndView("403");
	}

	@RequestMapping(value="/errors/404")
	public ModelAndView handle404() {
		return new ModelAndView("404");
	}

	/* Package private: Access from within controllers package only */
	static void setAlertMessages(ModelAndView mav, RedirectAttributes ra){
		if(ra != null) {
			Map<String, ?> raMap = ra.getFlashAttributes();
			if (raMap != null && raMap.get("alert") != null && mav != null) {
				mav.addObject("alert", raMap.get("alert"));
				mav.addObject("message", raMap.get("message"));
			}
		}
	}
}
