package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class StudentJdbcDao implements StudentDao {

	private static final String TABLE_NAME = "student";
	private static final String DOCKET_COLUMN = "docket";
	private static final String DNI_COLUMN = "dni";

	/* LIMIT 1 is not used, as there should be, at most, 1 student with the given docket */
	private static final String STUDENT_QUERY =
					"SELECT *" +
					"FROM " + TABLE_NAME + " " +
					"WHERE " + DOCKET_COLUMN + " = ?" +
					";";

	private final RowMapper<Student> studentRowMapper = (resultSet, rowNumber) -> {
		final int docket = resultSet.getInt(DOCKET_COLUMN);
		final int dni = resultSet.getInt(DNI_COLUMN);
		final Student.Builder studentBuilder = new Student.Builder(docket, dni);

		return studentBuilder.build();
	};

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentJdbcDao (final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
		****** +++xchange: This are user's fields, not student's.
	private static final String FIRST_NAME_COLUMN = "first_name";
	private static final String LAST_NAME_COLUMN = "last_name";
	private static final String GENRE_COLUMN = "genre";
	private static final String BIRTHDAY_COLUMN = "birthday";
	private static final String EMAIL_COLUMN = "email";
*/


	@Override
	public Student getByDocket(final int docket) {
		/* This method should return 0 or 1 student. */
		List<Student> students = jdbcTemplate.query(STUDENT_QUERY, studentRowMapper, docket);
		return students.isEmpty() ? null : students.get(0);
	}
}
