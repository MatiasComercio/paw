package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class StudentJdbcDao implements StudentDao {

	private static final String STUDENT_TABLE = "student";
	private static final String USER_TABLE = "users";
	private static final String DOCKET_COLUMN = "docket";
	private static final String DNI_COLUMN = "dni";
	private static final String FIRST_NAME_COLUMN = "first_name";
	private static final String LAST_NAME_COLUMN = "last_name";
	private static final String GENRE_COLUMN = "genre";
	private static final String BIRTHDAY_COLUMN = "birthday";
	private static final String EMAIL_COLUMN = "email";
	private static final String STUDENT_QUERY =
					"SELECT * " +
					"FROM " + STUDENT_TABLE + " JOIN " + USER_TABLE +
							" ON " + STUDENT_TABLE + "." + DNI_COLUMN + " = " + USER_TABLE + "." + DNI_COLUMN + " " +
					"WHERE " + DOCKET_COLUMN + " = ? LIMIT 1" +
					";";

	private final RowMapper<Student> studentRowMapper = (resultSet, rowNumber) -> {
		final int docket = resultSet.getInt(DOCKET_COLUMN);
		final int dni = resultSet.getInt(DNI_COLUMN);
		final String firstName = WordUtils.capitalize(resultSet.getString(FIRST_NAME_COLUMN));
		final String lastName = WordUtils.capitalize(resultSet.getString(LAST_NAME_COLUMN));
		final Date birthdayDate = resultSet.getDate(BIRTHDAY_COLUMN);
		final LocalDate birthday;
		if (birthdayDate != null) {
			birthday = birthdayDate.toLocalDate();
		} else {
			birthday = null;
		}
		final String genreString = resultSet.getString(GENRE_COLUMN);
		final User.Genre genre;
		if (genreString != null) {
			genre = User.Genre.valueOf(resultSet.getString(GENRE_COLUMN));
		} else {
			genre = null;
		}
		final String email = resultSet.getString(EMAIL_COLUMN);

		final Student.Builder studentBuilder = new Student.Builder(docket, dni);
		studentBuilder.firstName(firstName).lastName(lastName).genre(genre).birthday(birthday).email(email);


		return studentBuilder.build();
	};

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentJdbcDao (final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Student getByDocket(final int docket) {

		/* This method should return 0 or 1 student. */
		/* Grab student's data */
		List<Student> students = jdbcTemplate.query(STUDENT_QUERY, studentRowMapper, docket);

		return students.isEmpty() ? null : students.get(0);
	}
}
