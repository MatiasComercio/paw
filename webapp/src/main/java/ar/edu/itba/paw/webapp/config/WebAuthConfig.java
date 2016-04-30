package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan(
		"ar.edu.itba.paw.webapp.auth"
)
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
	private static final String KEY = "B/1+wzD9jv8UwBZke9EZHfAclHDYre9msPR54UfhXmhx8OBQXrQb0URl9mBwGPBK\n" +
			"ZExc4gxayeTxEPT3DIwMCZfCdMBVh4yjhzJ46AzummsrpyHeo2TgRtWRW+KCTtwo\n" +
			"19kyrxbMpc8dEY9YO2ghNT0ZOcwtvEu13kzngevUsPV9pWsKdRXGgt5HV3rpuvJ7\n" +
			"M0rL3BeV4L2HxkGcs6lfFgsHPxeQvtXLInwdTlrba0TjhN7AdC9vu3000CPmhRGL\n" +
			"4opte33ukexjtkzRXoMdYsDv+pbbufi+vlcl439dwMl9toEhFpKt74ZmPfd989m2\n" +
			"cvSzTN3iOdjfYlf2jMecDV/s05Cs1k4WgCuXYeBY7KVm0hDH3qSzUwPy2I8lH+Pv\n" +
			"ECeypIH0fXNiJurARZ+TndcHicM0ENyEdtM8z8x77oneYioidGC6InotO3FiPiiP\n" +
			"g5gkCMmZKgKdwrHqyT7+/TEUd6dggmHyUgLcSdIb6kznwof/vnbGaoIpyGJqGBc/\n" +
			"rOk4WY61BOQAwnqxg/81vXTv+xFRSjhHWEoSNToshpZwXKq1OvM4s6xIENBXdz6p\n" +
			"V3t4aUYxW7+wBxKxOkl4lq/2MFcTG0szMyiq4E9qrz4kSL6lKEzM/9arXX6+2uOy\n" +
			"ccSr02tkCPLSl05R+/unRZJbOTAgFrvLQvl0dFawj7HQaS0sPf53NenXzU9gmVgo\n" +
			"ZlINm2XKpmf3xOTCS81TS7ReKj4NMGAw0y0WhXasn18ziTEEK9im99SECsZC/hUU\n" +
			"JXBgahepbHqkv8HzJkhV0ExhVn88OOv+Ma2X95ZARmkpbPVBhERRfWVSns12NQzX\n" +
			"FAmS+LCbTTaE2xsz0PDSGZwKIFa5lh2QxKaNlSaaYLbOwVtYxf0CdNQHpvBYJSjj\n" +
			"v5BNMyleYKCZAuLTqTRDuEPn08sPpii2bOafiKPWJ/dgqRjB2sJKpEj0E9IcO5Mk\n" +
			"/SbjyQB7KD0ZwbCQyRC7ffaxjiHkOJV8GwC1x5wB0H5uSfPRIvOmm9FDiZeexbUc\n" +
			"EiOYfXiqetKJe9dTcLKFKRYZDHIj3fZThasbA7nC2qthNHy/0T/UFksmMO+VnOUe\n" +
			"Nm8Zq0DFZqQx6Q/FMUFWZbZvJDT8YtBATX6XkxHGpkWapnkidowDdsWeWT44HCfk\n" +
			"uxCXTkhNRmoQAgOr5Eev2Zda7+ydFK6jxweC5hvR+diobD6ItHllAdBaoOj9hVVs\n" +
			"tJ53S9JXi/az7+UlzxUtPgk4kO1jrulQ5XiN2tn6R2pv1ncnIHdqhaQHjSBkUZik\n" +
			"vUf8FRscFkvo2EbtKPPLQiY6A1yuvrpobCgWj6TOKeujALF6jzdhepsk7sL2Qg1g\n" +
			"6Nah/Tu9I0VOloJlciQenA==\n";

//	@Autowired
//	private PawAuthenticationProvider authProvider;

	@Autowired
	private StudentDetailsService userDetailsService;

	@Autowired
	private SgaAuthenticationSuccessHandler authSuccessHandler;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
				.userDetailsService(userDetailsService)
				.sessionManagement()
				.invalidSessionUrl("/login")

				.and().authorizeRequests()
				.antMatchers("/login/**").anonymous()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").authenticated()

				.and().formLogin()
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/", false)
				.successHandler(authSuccessHandler)
				.failureUrl("/login?error")
				.loginPage("/login")
				.loginProcessingUrl("/login")

				.and().rememberMe()
				.rememberMeParameter("rememberme")
				.userDetailsService(userDetailsService)
				.key(KEY)
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))

				.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")

				.and().exceptionHandling()
				.accessDeniedPage("/403")

				.and().csrf() /* +++xcheck: how to correctly enable this */
				.disable();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/css/*", "/js/**", "/img/**", "/favicon.ico", "/403");
	}
}
