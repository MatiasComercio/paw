package ar.edu.itba.paw.webapp.config;

import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@ComponentScan({
		"ar.edu.itba.paw.webapp.controllers",
		"ar.edu.itba.paw.webapp.forms.validators",
		"ar.edu.itba.paw.services",
		"ar.edu.itba.paw.persistence"
})
@EnableWebMvc
@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

	// equivalents for <mvc:resources/> tags
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31556926);
	}

	// equivalent for <mvc:default-servlet-handler/> tag
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/* Set path to .jsp files */
	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	/* /Set path to .jsp files */

	/* Database Connection */
	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(Driver.class);

		if (isDevelopmentMode()) {
			dataSource.setUrl("jdbc:postgresql://localhost:5432/paw");
			dataSource.setUsername("paw");
			dataSource.setPassword("paw01");
		} else {
			dataSource.setUrl("jdbc:postgresql://10.16.1.110:5432/grupo1");
			dataSource.setUsername("grupo1");
			dataSource.setPassword("OoLuej2w");
		}

		return dataSource;
	}
	/* /Database Connection */

	/* JDBC Beans */ // +++xremove
//	@Value("classpath:schema.sql")
//	private Resource schemaSql;
	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
//		dataSourceInitializer.setDatabasePopulator(databasePopulator());
		return dataSourceInitializer;
	}

//	private DatabasePopulator databasePopulator() {
//		final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//		databasePopulator.addScript(schemaSql);
//		return databasePopulator;
//	}
	/* /JDBC Beans */

	/* JPA Beans */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

		emf.setPackagesToScan("ar.edu.itba.paw.models");
		emf.setDataSource(dataSource());

		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(vendorAdapter);

		// tell Hibernate how he should behave
		final Properties hibernateProperties = new Properties();

		// adds new columns and entities,
		// but it neither drops/deletes unused tables or columns nor add indexes
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

		// set hibernate dialect; tells which database implementation we are using
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

		if (isDevelopmentMode()) {
			// never show this on production
			hibernateProperties.setProperty("hibernate.show.sql", "true");
			hibernateProperties.setProperty("format_sql", "true");
		}

		emf.setJpaProperties(hibernateProperties);

		return emf;
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	/* /JPA Beans */

	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		messageSource.setCacheSeconds(5); // +++xchange
		return messageSource;
	}

	private static boolean isDevelopmentMode() {
		// +++xcheck both on development & production mode
		// use LOGGER debug mode for switching this on/off development/production mode respectively
		return LOGGER.isDebugEnabled();
	}
}
