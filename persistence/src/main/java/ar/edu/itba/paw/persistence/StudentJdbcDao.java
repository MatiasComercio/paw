package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class StudentJdbcDao implements StudentDao {
	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	private static final String STUDENT_TABLE = "student";
	private static final String USER_TABLE = "users";
	private static final String ADDRESS_TABLE = "address";
	private static final String GRADE_TABLE = "grade";
	private static final String COURSE_TABLE = "course";
	private static final String INSCRIPTION_TABLE = "inscription";

	private static final String STUDENT__DOCKET_COLUMN = "docket";
	private static final String STUDENT__DNI_COLUMN = "dni";

	private static final String USER__DNI_COLUMN = "dni";
	private static final String USER__FIRST_NAME_COLUMN = "first_name";
	private static final String USER__LAST_NAME_COLUMN = "last_name";
	private static final String USER__GENRE_COLUMN = "genre";
	private static final String USER__BIRTHDAY_COLUMN = "birthday";
	private static final String USER__EMAIL_COLUMN = "email";

	private static final String ADDRESS__DNI_COLUMN = "dni";
	private static final String ADDRESS__COUNTRY_COLUMN = "country";
	private static final String ADDRESS__CITY_COLUMN = "city";
	private static final String ADDRESS__NEIGHBORHOOD_COLUMN = "neighborhood";
	private static final String ADDRESS__STREET_COLUMN = "street";
	private static final String ADDRESS__NUMBER_COLUMN = "number";
	private static final String ADDRESS__FLOOR_COLUMN = "floor";
	private static final String ADDRESS__DOOR_COLUMN = "door";
	private static final String ADDRESS__TELEPHONE_COLUMN = "telephone";
	private static final String ADDRESS__ZIP_CODE_COLUMN = "zip_code";

	private static final String GRADE__DOCKET_COLUMN = "docket";
	private static final String GRADE__COURSE_ID_COLUMN = "course_id";
	private static final String GRADE__GRADE_COLUMN = "grade";

	private static final String COURSE__ID_COLUMN = "id";
	private static final String COURSE__NAME_COLUMN = "name";
	private static final String COURSE__CREDITS_COLUMN = "credits";

	private static final String INSCRIPTION__COURSE_ID_COLUMN = "course_id";
	private static final String INSCRIPTION__DOCKET_COLUMN = "docket";

	private static final String GET_BY_DOCKET =
					"SELECT * " +
					"FROM " + STUDENT_TABLE + " JOIN " + USER_TABLE +
						" ON " + STUDENT_TABLE + "." + STUDENT__DNI_COLUMN + " = " + USER_TABLE + "." + USER__DNI_COLUMN + " " +
						" LEFT JOIN " + ADDRESS_TABLE + " ON " + STUDENT_TABLE + "." + STUDENT__DNI_COLUMN + " = " + ADDRESS_TABLE + "." + ADDRESS__DNI_COLUMN + " " +
					"WHERE " + STUDENT__DOCKET_COLUMN + " = ? LIMIT 1" +
					";";

	private static final String GET_ALL =
					"SELECT * " +
					"FROM " + STUDENT_TABLE + " JOIN " + USER_TABLE +
							" ON " + STUDENT_TABLE + "." + STUDENT__DNI_COLUMN + " = " + USER_TABLE + "." + USER__DNI_COLUMN;

	private static final String EMAILS_QUERY =
					"SELECT " + USER__EMAIL_COLUMN + " " +
					"FROM " + USER_TABLE + " " +
					";";

	private static final String GET_GRADES =
				"SELECT * " +
				"FROM " + GRADE_TABLE + " JOIN " + COURSE_TABLE + " ON " +
						GRADE_TABLE + "." + GRADE__COURSE_ID_COLUMN + " = " + COURSE_TABLE + "." +  COURSE__ID_COLUMN +
				" WHERE " + GRADE__DOCKET_COLUMN + " = ? " +
				";";


	private static final String GET_COURSES =
				"SELECT * " +
				"FROM " + INSCRIPTION_TABLE + " JOIN " + COURSE_TABLE + " ON " +
					INSCRIPTION_TABLE + "." + INSCRIPTION__COURSE_ID_COLUMN + " = " + COURSE_TABLE + "." + COURSE__ID_COLUMN
				+ " WHERE " + INSCRIPTION_TABLE + "." + INSCRIPTION__DOCKET_COLUMN + " = ?";

	private static final String DELETE_STUDENT_GRADES = "DELETE FROM " + GRADE_TABLE
			+ " WHERE " + GRADE__DOCKET_COLUMN + " = ?";

	private static final String DELETE_STUDENT_INSCRIPTIONS = "DELETE FROM " + INSCRIPTION_TABLE
			+ " WHERE " + INSCRIPTION__DOCKET_COLUMN + " = ?";

	private static final String DELETE_STUDENT = "DELETE FROM " + STUDENT_TABLE
			+ " WHERE " + STUDENT__DOCKET_COLUMN + " = ?";

	private final RowMapper<Student> infoRowMapper = (resultSet, rowNumber) -> {
		final int docket = resultSet.getInt(STUDENT__DOCKET_COLUMN);
		final int dni = resultSet.getInt(STUDENT__DNI_COLUMN);
		final String firstName = WordUtils.capitalizeFully(resultSet.getString(USER__FIRST_NAME_COLUMN));
		final String lastName = WordUtils.capitalizeFully(resultSet.getString(USER__LAST_NAME_COLUMN));
		final Date birthdayDate = resultSet.getDate(USER__BIRTHDAY_COLUMN);
		final LocalDate birthday;

		if (birthdayDate != null) {
			birthday = birthdayDate.toLocalDate();
		} else {
			birthday = null;
		}

		final String genreString = resultSet.getString(USER__GENRE_COLUMN);
		final User.Genre genre;
		if (genreString != null) {
			genre = User.Genre.valueOf(resultSet.getString(USER__GENRE_COLUMN));
		} else {
			genre = null;
		}

		String email = resultSet.getString(USER__EMAIL_COLUMN);
		if (email == null) {
			email = createEmail(docket, firstName, lastName);
		}

		final String country = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__COUNTRY_COLUMN));
		final String city = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__CITY_COLUMN));
		final String neighborhood = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__NEIGHBORHOOD_COLUMN));
		final String street = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__STREET_COLUMN));
		int number = resultSet.getInt(ADDRESS__NUMBER_COLUMN);
		if (resultSet.wasNull()) {
			number = Integer.MIN_VALUE;
		}
		int floor = resultSet.getInt(ADDRESS__FLOOR_COLUMN);
		if (resultSet.wasNull()) {
			floor = Integer.MIN_VALUE;
		}
		String door = resultSet.getString(ADDRESS__DOOR_COLUMN);
		if (door != null) {
			door = door.toUpperCase();
		}
		long telephone = resultSet.getLong(ADDRESS__TELEPHONE_COLUMN);
		if (resultSet.wasNull()) {
			telephone = Long.MIN_VALUE;
		}
		int zipCode = resultSet.getInt(ADDRESS__ZIP_CODE_COLUMN);
		if (resultSet.wasNull()) {
			zipCode = Integer.MIN_VALUE;
		}
		final Address.Builder addressBuilder = new Address.Builder(country, city, neighborhood, street, number);
		addressBuilder.floor(floor).door(door).telephone(telephone).zipCode(zipCode);
		final Address address = addressBuilder.build();

		final Student.Builder studentBuilder = new Student.Builder(docket, dni);
		studentBuilder.firstName(firstName).lastName(lastName).
				genre(genre).birthday(birthday).email(email).address(address);

		return studentBuilder.build();
	};

	private final RowMapper<Course> courseRowMapper = (resultSet, rowNum) ->
			new Course.Builder(resultSet.getInt(COURSE__ID_COLUMN))
					.name(resultSet.getString(COURSE__NAME_COLUMN))
					.credits(resultSet.getInt(COURSE__CREDITS_COLUMN))
					.build();

	private final RowMapper<Student.Builder> studentBasicRowMapper = this::getStudentBasicInfo;
	private final RowMapper<Student> studentRowMapper = (resultSet, rowNumber) ->
			getStudentBasicInfo(resultSet, rowNumber).build();

	private Student.Builder getStudentBasicInfo(final ResultSet resultSet, final int rowNumber) throws SQLException {
		final int docket = resultSet.getInt(STUDENT__DOCKET_COLUMN);
		final int dni = resultSet.getInt(STUDENT__DNI_COLUMN);
		final String firstName = WordUtils.capitalize(resultSet.getString(USER__FIRST_NAME_COLUMN));
		final String lastName = WordUtils.capitalize(resultSet.getString(USER__LAST_NAME_COLUMN));

		String email = resultSet.getString(USER__EMAIL_COLUMN);
		if (email == null) {
			email = createEmail(docket, firstName, lastName);
		}

		final Student.Builder studentBuilder = new Student.Builder(docket, dni);
		studentBuilder.firstName(firstName).lastName(lastName).email(email);

		return studentBuilder;
	}


	private final RowMapper<String> emailRowMapper = (resultSet, rowNumber) -> resultSet.getString(USER__EMAIL_COLUMN);

	private final RowMapper<Grade> gradesRowMapper = (resultSet, rowNumber) -> {
		final int docket = resultSet.getInt(GRADE__DOCKET_COLUMN);
		final int courseId = resultSet.getInt(GRADE__COURSE_ID_COLUMN);
		final String courseName = resultSet.getString(COURSE__NAME_COLUMN);
		final BigDecimal grade = resultSet.getBigDecimal(GRADE__GRADE_COLUMN);

		final Grade.Builder gradeBuilder = new Grade.Builder(docket, courseId, grade);
		gradeBuilder.courseName(courseName);

		return gradeBuilder.build();
	};

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert userInsert;
	private SimpleJdbcInsert studentInsert;
	private SimpleJdbcInsert addressInsert;

	@Autowired
	public StudentJdbcDao (final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
		studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(STUDENT__DOCKET_COLUMN);
		addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);

	}

	@Override
	public Student getByDocket(final int docket) {

		/* This method should return 0 or 1 student. */
		/* Grab student's data */
		final List<Student> students = jdbcTemplate.query(GET_BY_DOCKET, infoRowMapper, docket);

		return students.isEmpty() ? null : students.get(0);
	}

	@Override
	public Student getGrades(final int docket) {
		final List<Student.Builder> studentBuilders = jdbcTemplate.query(GET_BY_DOCKET, studentBasicRowMapper, docket);

		if (studentBuilders.isEmpty()) {
			return null;
		}

		final Student.Builder studentBuilder = studentBuilders.get(0);

		final List<Grade> grades = jdbcTemplate.query(GET_GRADES, gradesRowMapper, docket);

		studentBuilder.addGrades(grades);

		return studentBuilder.build();
	}

	@Override
	public List<Course> getStudentCourses(int docket) {
		Student student = getByDocket(docket);

		if(student == null) {
			return null;
		}

		List<Course> courses = jdbcTemplate.query(GET_COURSES, courseRowMapper, docket);

		return courses;
	}

	@Override
	public List<Student> getByFilter(StudentFilter studentFilter) {
		QueryFilter queryFilter = new QueryFilter();

		queryFilter.filterByDocket(studentFilter);
		queryFilter.filterByFirstName(studentFilter);
		queryFilter.filterByLastName(studentFilter);
		queryFilter.filterByGenre(studentFilter);

		return jdbcTemplate.query(queryFilter.getQuery(), studentRowMapper, queryFilter.getFilters().toArray());
	}

	@Override
	public Result deleteStudent(Integer docket) {
		int gradeRowsAffected = jdbcTemplate.update(DELETE_STUDENT_GRADES, docket);
		int inscriptionRowsAffected = jdbcTemplate.update(DELETE_STUDENT_INSCRIPTIONS, docket);
		/* +++xcheck exceptions */
		int studentRowsAffected = jdbcTemplate.update(DELETE_STUDENT, docket);

		return studentRowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}


	@Override
	public void create(Student student) {
		//TODO: Add further data validation

		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> addressArgs = new HashMap<>();

		/* Store User Data */
		//TODO: DNI > 0 and UNIQUE(email) are guaranteed by the DB. How do we handle in case insertion fails?
		if(student.getDni() > 0) {
			userArgs.put(USER__DNI_COLUMN, student.getDni());
			userArgs.put(USER__FIRST_NAME_COLUMN, student.getFirstName());
			userArgs.put(USER__LAST_NAME_COLUMN, student.getLastName());
			//TODO: Check genre length eq 1 (Again this is verified by the db)
			if(student.getGenre() == "Female")
				userArgs.put(USER__GENRE_COLUMN, "F");
			else
				userArgs.put(USER__GENRE_COLUMN, "M");
			userArgs.put(USER__BIRTHDAY_COLUMN, student.getBirthday());
			//TODO: Get default email
			userArgs.put(USER__EMAIL_COLUMN, student.getEmail());
			userInsert.execute(userArgs);
		}

		/* Store Student Data */
		if(student.getDocket() > 0 && student.getDni()>0) {
			studentArgs.put(STUDENT__DOCKET_COLUMN, student.getDocket());
			studentArgs.put(STUDENT__DNI_COLUMN, student.getDni());
			//PASSWORD??? userArgs.put(ID_COLUMN, student);
			studentInsert.execute(studentArgs);
		}

		/* Store Address Data */
		if(student.getDni() > 0 && student.getAddress() != null) {
			addressArgs.put(ADDRESS__DNI_COLUMN, student.getDni());
			addressArgs.put(ADDRESS__COUNTRY_COLUMN, student.getAddress().getCountry());
			addressArgs.put(ADDRESS__CITY_COLUMN, student.getAddress().getCity());
			addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, student.getAddress().getNeighborhood());
			addressArgs.put(ADDRESS__STREET_COLUMN, student.getAddress().getStreet());
			addressArgs.put(ADDRESS__NUMBER_COLUMN, student.getAddress().getNumber());
			addressArgs.put(ADDRESS__FLOOR_COLUMN, student.getAddress().getFloor());
			addressArgs.put(ADDRESS__DOOR_COLUMN, student.getAddress().getDoor());
			addressArgs.put(ADDRESS__TELEPHONE_COLUMN, student.getAddress().getTelephone());
			addressArgs.put(ADDRESS__ZIP_CODE_COLUMN, student.getAddress().getZipCode());
			addressInsert.execute(addressArgs);
		}
	}

	private String createEmail(final int docket, final String firstName, final String lastName) {
		final String defaultEmail = "student" + docket + EMAIL_DOMAIN;

		if (firstName == null || firstName.equals("")|| lastName == null || lastName.equals("")) {
			return defaultEmail;
		}

		final String initChar = firstName.substring(0, 1).toLowerCase();

		final String[] lastNames = lastName.toLowerCase().split(" ");
		StringBuilder currentEmail;
		for (int i = 0 ; i < 2 && i < lastNames.length ; i++) {
			currentEmail = new StringBuilder(initChar);
			for (int j = 0 ; j <= i; j++) {
				currentEmail.append(lastNames[j]);
			}
			currentEmail.append(EMAIL_DOMAIN);
			if (!exists(currentEmail)) {
				return String.valueOf(currentEmail);
			}
		}

		/* This is in case all email trials failed */
		/* This, for sure, does not exists as includes the docket, which is unique */
		return defaultEmail;
	}

	private boolean exists(final StringBuilder email) {
		List<String> emails = jdbcTemplate.query(EMAILS_QUERY, emailRowMapper);

		return emails.contains(String.valueOf(email));

	}

	private static class QueryFilter {
		private static final String WHERE = " WHERE ";
		private static final String AND = " AND ";
		private static final String ILIKE = " ILIKE ? ";
		private static final String EQUAL = " = ? ";

		private static final String FILTER_DOCKET = "CAST(" + STUDENT__DOCKET_COLUMN + " AS TEXT) ";
		private static final String FILTER_NAME_FIRST = USER__FIRST_NAME_COLUMN;
		private static final String FILTER_NAME_LAST = USER__LAST_NAME_COLUMN;
		private static final String FILTER_GENRE = USER__GENRE_COLUMN;

		private final StringBuffer query = new StringBuffer(GET_ALL);
		private boolean filterApplied = false;
		private final List<String> filters;

		private final FilterQueryMapper filterBySubWord = (filter, filterName) -> {
			if(filter != null && !filter.toString().equals("")) {
				String stringFilter = "%" + filter.toString() + "%";
				appendFilter(filterName, stringFilter);
			}
		};

		private final FilterQueryMapper filterByExactWord = (filter, filterName) -> {
			if(filter != null && !filter.toString().equals("")) {
				String stringFilter = filter.toString();
				appendFilter(filterName, stringFilter);
			}
		};

		private QueryFilter() {
			filters = new LinkedList<>();
		}

		public void filterByDocket(final StudentFilter courseFilter) {
			filterBySubWord.filter(courseFilter.getDocket(), FILTER_DOCKET + ILIKE);
		}

		public void filterByFirstName(final StudentFilter courseFilter) {
			filterBySubWord.filter(courseFilter.getFirstName(), FILTER_NAME_FIRST + ILIKE);
		}

		public void filterByLastName(final StudentFilter courseFilter) {
			filterBySubWord.filter(courseFilter.getLastName(), FILTER_NAME_LAST + ILIKE);
		}

		public void filterByGenre(StudentFilter studentFilter) {
			filterByExactWord.filter(studentFilter.getGenre(), FILTER_GENRE + EQUAL);
		}

		public List<String> getFilters() {
			return filters;
		}

		public String getQuery() {
			return query.toString();
		}

		private void appendFilterConcatenation() {
			if(!filterApplied) {
				filterApplied = true;
				query.append(WHERE);
			} else {
				query.append(AND);
			}
		}

		private void appendFilter(final String filter, final String stringFilter) {
			appendFilterConcatenation();
			query.append(filter);
			filters.add(stringFilter);
		}

		private interface FilterQueryMapper {
			void filter(final Object filter, final String filterName);
		}
	}
}
