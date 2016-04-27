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
import org.omg.CORBA.UNKNOWN;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

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
	private static final String GRADE__MODIFIED_COLUMN = "modified";

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

	private static final String DELETE_INSCRIPTION =
			"DELETE FROM " + INSCRIPTION_TABLE + " WHERE " + INSCRIPTION__COURSE_ID_COLUMN + " = ? AND "
			+ INSCRIPTION__DOCKET_COLUMN + " = ? ";

	private static final String DELETE_STUDENT_GRADES = "DELETE FROM " + GRADE_TABLE
			+ " WHERE " + GRADE__DOCKET_COLUMN + " = ?";

	private static final String DELETE_STUDENT_INSCRIPTIONS = "DELETE FROM " + INSCRIPTION_TABLE
			+ " WHERE " + INSCRIPTION__DOCKET_COLUMN + " = ?";

	private static final String DELETE_STUDENT = "DELETE FROM " + STUDENT_TABLE
			+ " WHERE " + STUDENT__DOCKET_COLUMN + " = ?";

	private static final String COUNT_STUDENTS = "SELECT COUNT(*) FROM " + STUDENT_TABLE
			+ " WHERE " + STUDENT__DOCKET_COLUMN + " = ?";

	private static final String COUNT_COURSES = "SELECT COUNT(*) FROM " + COURSE_TABLE
			+ " WHERE " + COURSE__ID_COLUMN + " = ?";

	private static final String COUNT_INSCRIPTION = "SELECT COUNT(*) FROM " + INSCRIPTION_TABLE
			+ " WHERE " + INSCRIPTION__COURSE_ID_COLUMN + " = ?"
			+ " AND " + INSCRIPTION__DOCKET_COLUMN + " = ?";

	private static final Integer APPROVING_GRADE = 4;
	private static final String GET_APPROVED_COURSES =
					"SELECT * " +
					"FROM " + GRADE_TABLE + " JOIN " + COURSE_TABLE + " ON " +
					GRADE_TABLE + "." + GRADE__COURSE_ID_COLUMN + " = " + COURSE_TABLE + "." +  COURSE__ID_COLUMN +
					" WHERE " + GRADE__DOCKET_COLUMN + " = ? " + " AND " + GRADE__GRADE_COLUMN + " >= " + APPROVING_GRADE +
					";";


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

		final String country = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__COUNTRY_COLUMN));
		final String city = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__CITY_COLUMN));
		final String neighborhood = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__NEIGHBORHOOD_COLUMN));
		final String street = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__STREET_COLUMN));
		Integer number = resultSet.getInt(ADDRESS__NUMBER_COLUMN);
		if (resultSet.wasNull()) {
			number = null;
		}
		Integer floor = resultSet.getInt(ADDRESS__FLOOR_COLUMN);
		if (resultSet.wasNull()) {
			floor = null;
		}
		String door = resultSet.getString(ADDRESS__DOOR_COLUMN);
		if (door != null) {
			door = door.toUpperCase();
		}
		Long telephone = resultSet.getLong(ADDRESS__TELEPHONE_COLUMN);
		if (resultSet.wasNull()) {
			telephone = null;
		}
		Integer zipCode = resultSet.getInt(ADDRESS__ZIP_CODE_COLUMN);
		if (resultSet.wasNull()) {
			zipCode = null;
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
		final Timestamp modified = resultSet.getTimestamp(GRADE__MODIFIED_COLUMN);

		final Grade.Builder gradeBuilder = new Grade.Builder(docket, courseId, grade).modified(modified);
		gradeBuilder.courseName(courseName);

		return gradeBuilder.build();
	};

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert userInsert;
	private SimpleJdbcInsert studentInsert;
	private SimpleJdbcInsert addressInsert;
	private SimpleJdbcInsert inscriptionInsert;
	private SimpleJdbcInsert gradesInsert;

	@Autowired
	public StudentJdbcDao (final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
		studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(STUDENT__DOCKET_COLUMN);
		addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);
		inscriptionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(INSCRIPTION_TABLE);
		gradesInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(GRADE_TABLE)
				.usingColumns(GRADE__DOCKET_COLUMN,
						GRADE__COURSE_ID_COLUMN,
						GRADE__GRADE_COLUMN);
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

		if (studentFilter != null) {
			queryFilter.filterByDocket(studentFilter);
			queryFilter.filterByFirstName(studentFilter);
			queryFilter.filterByLastName(studentFilter);
			queryFilter.filterByGenre(studentFilter);
		}

		return jdbcTemplate.query(queryFilter.getQuery(), studentRowMapper, queryFilter.getFilters().toArray());
	}

	/* +++xchange: TODO: sacar la validacion de dni y docket > 0 (se hace en la capa de servicios */
	@Override
	public Result deleteStudent(Integer docket) {
		try {
			int gradeRowsAffected = jdbcTemplate.update(DELETE_STUDENT_GRADES, docket);
			int inscriptionRowsAffected = jdbcTemplate.update(DELETE_STUDENT_INSCRIPTIONS, docket);

			int studentRowsAffected = jdbcTemplate.update(DELETE_STUDENT, docket);

			return studentRowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
		} catch (DataAccessException dae) {
			return Result.ERROR_UNKNOWN;
		}
	}

	@Override
	public Result addGrade(Grade grade) {
//		Object[] courseId = new Object[]{grade.getCourseId()};
//		Object[] studentDocket = new Object[]{grade.getStudentDocket()};
//		Object[] inscription = new Object[]{grade.getCourseId(), grade.getStudentDocket()};
//
//		int coursesAffected = jdbcTemplate.queryForObject(COUNT_COURSES, courseId, Integer.class);
//		if(coursesAffected == 0) {
//			return Result.COURSE_NOT_EXISTS;
//		}
//
//		int studentsAffected = jdbcTemplate.queryForObject(COUNT_STUDENTS, studentDocket, Integer.class);
//
//		if(studentsAffected == 0) {
//			return Result.STUDENT_NOT_EXISTS;
//		}
//
//		int inscriptionsAffected = jdbcTemplate.queryForObject(COUNT_INSCRIPTION, inscription, Integer.class);
//
//		if(inscriptionsAffected == 0) {
//			return Result.INSCRIPTION_NOT_EXISTS;
//		}

		final Map<String, Object> gradeArgs = new HashMap<>();

		gradeArgs.put(GRADE__DOCKET_COLUMN, grade.getStudentDocket());
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, grade.getCourseId());
		gradeArgs.put(GRADE__GRADE_COLUMN, grade.getGrade());

		try {
			int rowsAffected = gradesInsert.execute(gradeArgs);

			return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
		} catch(DataIntegrityViolationException e) {
			return Result.INVALID_INPUT_PARAMETERS;
		}
	}

	@Override
	public Result editGrade(Grade newGrade, BigDecimal oldGrade){
		try{
			jdbcTemplate.update("UPDATE grade SET grade = ? WHERE docket = ? AND " +
					"course_id = ? AND modified = ? AND grade = ?;",
					newGrade.getGrade(), newGrade.getStudentDocket(), newGrade.getCourseId(), newGrade.getModified(),
					oldGrade);
		}
		catch(Exception e){
			return Result.ERROR_UNKNOWN;
		}
		return Result.OK;
	}



	@Override
	public Result create(Student student) {

		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> addressArgs = new HashMap<>();

		/* Store User Data */
		userArgs.put(USER__DNI_COLUMN, student.getDni());
		userArgs.put(USER__FIRST_NAME_COLUMN, student.getFirstName());
		userArgs.put(USER__LAST_NAME_COLUMN, student.getLastName());

		if(student.getGenre().equals("Female"))
			userArgs.put(USER__GENRE_COLUMN, "F");
		else
			userArgs.put(USER__GENRE_COLUMN, "M");
		userArgs.put(USER__BIRTHDAY_COLUMN, student.getBirthday());
		userArgs.put(USER__EMAIL_COLUMN, createEmail(student.getDni(), student.getFirstName(),
				student.getLastName()));
		try {
			userInsert.execute(userArgs);
		}catch(DuplicateKeyException e){
			return Result.STUDENT_EXISTS_DNI;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		/* Store Student Data */
		studentArgs.put(STUDENT__DNI_COLUMN, student.getDni());
		try {
			studentInsert.execute(studentArgs);
		}catch(DuplicateKeyException e){
			return Result.STUDENT_EXISTS_DOCKET;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		/* Store Address Data */
        if(student.getDni() > 0 && student.getAddress() != null) {
            createAddress(student.getDni(), student);
        }

		return Result.OK;
	}

    @Override
    public Integer getDniByDocket(final Integer docket){
        return jdbcTemplate.query("SELECT dni FROM student WHERE docket = " + docket + ";", rs -> rs.next() ? rs.getInt("dni") : null);
    }

    @Override
    public Result update(final Integer docket, final Integer dni , final Student student) {

        final String genre = student.getGenre().equals("Female")? "F" : "M";

        final String userUpdate = "UPDATE users SET " + USER__DNI_COLUMN + " = ?, " + USER__FIRST_NAME_COLUMN + " = ?, "
                + USER__LAST_NAME_COLUMN + " = ?, " + USER__GENRE_COLUMN + " = ?, " + USER__BIRTHDAY_COLUMN + " = ?, "
                + USER__EMAIL_COLUMN + " = ? WHERE " + USER__DNI_COLUMN + " = ?";

        //final String studentUpdate = "UPDATE student SET " + STUDENT__DOCKET_COLUMN + " = ? WHERE " + STUDENT__DNI_COLUMN + " = ?;";


        //Update user table
        try {
            Date birthday = student.getBirthday() != null ? Date.valueOf(student.getBirthday()) : null;

            jdbcTemplate.update(userUpdate, student.getDni(), student.getFirstName(), student.getLastName(), genre,
                    birthday, createEmail(student.getDni(), student.getFirstName(),
                            student.getLastName()), dni);
        } catch (DuplicateKeyException e) {
            return Result.STUDENT_EXISTS_DNI;
        }

        return Result.OK;
    }

    @Override
    public boolean hasAddress(final Integer dni) {
        return jdbcTemplate.query("SELECT dni FROM address WHERE dni = " + dni + ";", rs -> rs.next() ? true : false);
    }

    @Override
    public void createAddress(final Integer dni, final Student student) {

        Map<String, Object> addressArgs = new HashMap<>();

        Address addr = student.getAddress();


        addressArgs.put(ADDRESS__DNI_COLUMN, dni);
        addressArgs.put(ADDRESS__COUNTRY_COLUMN, WordUtils.capitalize(addr.getCountry().toLowerCase()));
        addressArgs.put(ADDRESS__CITY_COLUMN, WordUtils.capitalize(addr.getCity()).toLowerCase());
        addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, WordUtils.capitalize(addr.getNeighborhood()).toLowerCase());
        addressArgs.put(ADDRESS__STREET_COLUMN, WordUtils.capitalize(addr.getStreet()).toLowerCase());
        addressArgs.put(ADDRESS__NUMBER_COLUMN,addr.getNumber());
        addressArgs.put(ADDRESS__FLOOR_COLUMN, addr.getFloor());
        addressArgs.put(ADDRESS__DOOR_COLUMN, addr.getDoor());
        addressArgs.put(ADDRESS__TELEPHONE_COLUMN, addr.getTelephone());
        addressArgs.put(ADDRESS__ZIP_CODE_COLUMN, addr.getZipCode());
        addressInsert.execute(addressArgs);

    }
    @Override
    public void updateAddress(Integer dni, Student student) {

        final Address addr = student.getAddress();

        final String addressUpdate = "UPDATE address SET " + ADDRESS__COUNTRY_COLUMN + " = ?, " + ADDRESS__CITY_COLUMN + " = ?, "
                + ADDRESS__NEIGHBORHOOD_COLUMN + " = ?, " + ADDRESS__STREET_COLUMN + " = ?, " + ADDRESS__NUMBER_COLUMN + " = ?, "
                + ADDRESS__FLOOR_COLUMN + " = ?, " + ADDRESS__DOOR_COLUMN + " = ?, " + ADDRESS__TELEPHONE_COLUMN + " = ?, "
                + ADDRESS__ZIP_CODE_COLUMN + " = ? WHERE " + ADDRESS__DNI_COLUMN + " = ?;";

        if (addr != null) {
            jdbcTemplate.update(addressUpdate, addr.getCountry(), addr.getCity(), addr.getNeighborhood(),
                    addr.getStreet(), addr.getNumber(), addr.getFloor(), addr.getDoor(), addr.getTelephone(),
                    addr.getZipCode(), student.getDni());
        }
    }

    private String createEmail(final int dni, final String firstName, final String lastName) {
		final String defaultEmail = "student" + dni + EMAIL_DOMAIN;

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
			if (!exists(currentEmail)) { // +++ximprove: should return existent email
				return String.valueOf(currentEmail);
			}
		}

		/* This is in case all email trials failed */
		/* This, for sure, does not exists as includes the docket, which is unique */
		return defaultEmail;
	}

	@Override
	public Result enroll(final int studentDocket, final int courseId) {
		final Map<String, Object> inscriptionArgs = new HashMap<>();
		inscriptionArgs.put(INSCRIPTION__DOCKET_COLUMN, studentDocket);
		inscriptionArgs.put(INSCRIPTION__COURSE_ID_COLUMN, courseId);

		try {
			inscriptionInsert.execute(inscriptionArgs);
		} catch (final DuplicateKeyException e) {
			return Result.ALREADY_ENROLLED;
		} catch (final DataIntegrityViolationException e) {
			return Result.INVALID_INPUT_PARAMETERS;
		} catch(final DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		return Result.OK;
	}

	@Override
	public Result unenroll(final Integer studentDocket, final Integer courseId) {

		int cUpdated = 0;
		try {
			cUpdated = jdbcTemplate.update(DELETE_INSCRIPTION, courseId, studentDocket);
		} catch (final DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		return cUpdated == 1 ? Result.OK : Result.NOT_EXISTENT_ENROLL;
	}

	@Override
	public Collection<Course> getApprovedCourses(final int docket) {
		final RowMapper<Course> approvedCoursesRowMapper = (rs, rowNum) -> {
			final Course.Builder courseBuilder = new Course.Builder(rs.getInt(COURSE__ID_COLUMN));
			courseBuilder.name(rs.getString(COURSE__NAME_COLUMN)).credits(rs.getInt(COURSE__CREDITS_COLUMN));
			return courseBuilder.build();
		};

		return jdbcTemplate.query(GET_APPROVED_COURSES, approvedCoursesRowMapper, docket);
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
