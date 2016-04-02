package ar.edu.itba.paw.persistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@ComponentScan({
		"ar.edu.itba.paw.persistence"
})
@Configuration
public class TestConfig {

	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(JDBCDriver.class);
		dataSource.setUrl("jdbc:hsqldb:mem:paw");
		dataSource.setUsername("hq");
		dataSource.setPassword("");

		return dataSource;
	}
}
