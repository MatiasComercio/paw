package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.controllers.DTOEntityMapper;
import org.modelmapper.ModelMapper;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({
		"ar.edu.itba.paw.webapp.controllers",
				"ar.edu.itba.paw.services",
		"ar.edu.itba.paw.persistence"
})
@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

	private static boolean isDevelopmentMode() {
		// use LOGGER debug mode for switching this on/off development/production mode respectively
		return LOGGER.isDebugEnabled();
	}

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
	/* /Database Connection */

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

	/* JPA Beans */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
		final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

		emf.setPackagesToScan("ar.edu.itba.paw.models");
		emf.setDataSource(dataSource);

		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(vendorAdapter);

		// tell Hibernate how he should behave
		final Properties hibernateProperties = new Properties();

		// adds new columns and entities,
		// but it neither drops/deletes unused tables or columns nor add indexes
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

		// set hibernate dialect; tells which database implementation we are using
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");

		if (isDevelopmentMode()) {
			// never show this on production
			hibernateProperties.setProperty("hibernate.show_sql", "true");
			hibernateProperties.setProperty("hibernate.format_sql", "true");
            hibernateProperties.setProperty("hibernate.default_schema", "public");
		}

		emf.setJpaProperties(hibernateProperties);

		return emf;
	}
	/* /JPA Beans */

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public DTOEntityMapper entityMapper(){
		return new DTOEntityMapper(new ModelMapper());
	}

}
