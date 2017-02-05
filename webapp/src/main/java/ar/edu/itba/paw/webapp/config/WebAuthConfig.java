package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan(
		"ar.edu.itba.paw.webapp.auth"
)
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	private static final String API_PREFIX_VERSION = "/api/v1";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;


	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable(); //Configure CSRF Token

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


		http
					.authorizeRequests()

						// StudentController permissions
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/students/*/grades/*").hasAuthority("EDIT_GRADE")
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/students/*/grades").hasAuthority("ADD_GRADE")
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/students").hasAuthority("ADD_STUDENT")
						.antMatchers(HttpMethod.DELETE, API_PREFIX_VERSION + "/students").hasAuthority("DELETE_STUDENT")

						// CourseController permissions
						.antMatchers(HttpMethod.POST, API_PREFIX_VERSION + "courses/finalInscriptions/*/grades").hasAuthority("ADMIN")
						.antMatchers(HttpMethod.DELETE, API_PREFIX_VERSION + "/courses/*/correlatives/*").hasAuthority("DELETE_CORRELATIVE")
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/courses/*/correlatives").hasAuthority("ADD_CORRELATIVE")
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/courses/*").hasAuthority("EDIT_COURSE")
						.antMatchers(HttpMethod.DELETE, API_PREFIX_VERSION + "/courses/*").hasAuthority("DELETE_COURSE")
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/courses").hasAuthority("ADD_COURSE")

						// UserController permissions
						.antMatchers(HttpMethod.POST,   API_PREFIX_VERSION + "/users/*/password/reset").hasAuthority("RESET_PASSWORD")

						// AdminController
						.antMatchers(API_PREFIX_VERSION + "/admins/**").hasAuthority("ADMIN")


						.antMatchers(HttpMethod.POST,API_PREFIX_VERSION + "/login").permitAll()
						.antMatchers(API_PREFIX_VERSION + "/**").authenticated(); // For every resource except /login the user must be authenticated

		http
					.addFilterBefore(new StatelessLoginFilter("/api/v1/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
					.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
					.exceptionHandling().authenticationEntryPoint(new PlainTextBasicAuthenticationEntryPoint()) // resolves 401 Unauthenticated
					.and()
					.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()); // resolves 403 unauthorized to 404 Not found


	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**", "/WEB-INF/jsp/base/**", "/favicon.ico", "/errors/401");
	}
}
