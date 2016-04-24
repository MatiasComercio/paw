package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class CourseJdbcDao implements CourseDao {

    private static final String TABLE_NAME = "course";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String CREDITS_COLUMN = "credits";

    private static final String DOCKET_COLUMN = "docket";
    private static final String DNI_COLUMN = "dni";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";

    private static final String INSCRIPTION_TABLE_NAME = "inscription";
    private static final String INSCRIPTION_ID_COURSE = "course_id";

    private static final String GRADES_TABLE_NAME = "grade";
    private static final String GRADES_ID_COURSE = "course_id";

    private static final String QUERY_DELETE = "DELETE FROM " + TABLE_NAME
            + " WHERE " + ID_COLUMN + " = ?";

    private static final String QUERY_COUNT_INSCRIPTION = "SELECT COUNT(*) FROM " + INSCRIPTION_TABLE_NAME
            + " WHERE " + INSCRIPTION_ID_COURSE + " = ?";

    private static final String QUERY_COUNT_GRADES = "SELECT COUNT(*) FROM " + GRADES_TABLE_NAME
            + " WHERE " + GRADES_ID_COURSE + " = ?";


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert courseInsert;

    private final RowMapper<Course> courseRowMapper = (resultSet, rowNum) ->
            new Course.Builder(resultSet.getInt(ID_COLUMN))
                        .name(resultSet.getString(NAME_COLUMN))
                        .credits(resultSet.getInt(CREDITS_COLUMN))
                        .build();

    private final RowMapper<Student> studentRowMapper = (resultSet, rowNum) ->
            new Student.Builder(resultSet.getInt(DOCKET_COLUMN), resultSet.getInt(DNI_COLUMN)).firstName(resultSet.getString(FIRST_NAME_COLUMN)).lastName(resultSet.getString(LAST_NAME_COLUMN)).build();

    @Autowired
    public CourseJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.courseInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(ID_COLUMN, NAME_COLUMN, CREDITS_COLUMN);
    }


    @Override
    public Result create(Course course) {
        final Map<String, Object> args = new HashMap<>();

        args.put(ID_COLUMN, course.getId());
        args.put(NAME_COLUMN, course.getName());
        args.put(CREDITS_COLUMN, course.getCredits());
        try{
            courseInsert.execute(args);
        }
        catch (DuplicateKeyException e){
            return Result.COURSE_EXISTS_ID;
        } catch (final DataIntegrityViolationException e) {
            return Result.INVALID_INPUT_PARAMETERS;
        } catch(final DataAccessException e) {
            return Result.ERROR_UNKNOWN;
        }
        return Result.OK;

    }

    @Override
    public Result update(Integer id, Course course) {
        try {
            jdbcTemplate.update("UPDATE course SET id = ?, name = ?, credits = ? WHERE id = ?;", course.getId(), course.getName(), course.getCredits(), id);
        } catch (DuplicateKeyException e){
            return Result.COURSE_EXISTS_ID;
        } catch (final DataIntegrityViolationException e) {
            return Result.INVALID_INPUT_PARAMETERS;
        } catch(final DataAccessException e) {
            return Result.ERROR_UNKNOWN;
        }

        return Result.OK;
    }

    @Override
    public Course getById(final int id) {

        List<Course> course = jdbcTemplate.query("SELECT * FROM course WHERE id = ? LIMIT 1", courseRowMapper, id);

        return course.isEmpty() ? null : course.get(0);
    }

    @Override
    public List<Course> getAllCourses() {
        /* TODO: Replace `course` with TABLE_NAME identifier, but we can't use '?' because JDBC does not support it, so string concatenation seems the best choice */
        List<Course> courses = jdbcTemplate.query("SELECT * FROM course", courseRowMapper);

        return courses;
    }

    @Override
    public Course getCourseStudents(int id) {

        List<Course> courseList = jdbcTemplate.query("SELECT * FROM course WHERE id = ? LIMIT 1", courseRowMapper, id);
        Course course = courseList.isEmpty() ? null : courseList.get(0);

        if(course != null){
            List<Student> students = jdbcTemplate.query("SELECT * FROM inscription,student,users " +
                    " WHERE inscription.docket=student.docket AND " +
                    "student.dni=users.dni AND inscription.course_id = ?", studentRowMapper, id);
            course.setStudents(students);
        }
        return course;
    }

    @Override
    public List<Course> getByFilter(final CourseFilter courseFilter) {
        QueryFilter queryFilter = new QueryFilter();

        if (courseFilter != null) {
            queryFilter.filterByKeyword(courseFilter);
            queryFilter.filterById(courseFilter);
        }

        List<Course> courses = jdbcTemplate.query(queryFilter.getQuery(), courseRowMapper, queryFilter.getFilters().toArray());

        return courses;
    }

    /* +++xreference
        http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html#update-java.lang.String-java.lang.Object...-
     */
    @Override
    public Result deleteCourse(Integer id) {
        Object[] idWrapped = new Object[]{id};
        int courseNumber = jdbcTemplate.queryForObject(QUERY_COUNT_INSCRIPTION, idWrapped, Integer.class);

        if(courseNumber > 0) {
            System.out.println("Inscription exist");
            return Result.COURSE_EXISTS_INSCRIPTION;
        }

        courseNumber = jdbcTemplate.queryForObject(QUERY_COUNT_GRADES, idWrapped, Integer.class);

        if(courseNumber > 0) {
            System.out.println("Grades exist");
            return Result.COURSE_EXISTS_GRADE;
        }
        try {
            int rowsAffected = jdbcTemplate.update(QUERY_DELETE, id);

            return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
        } catch (final DataIntegrityViolationException e) {
            return Result.INVALID_INPUT_PARAMETERS;
        } catch(final DataAccessException e) {
            return Result.ERROR_UNKNOWN;
        }
    }

    private static class QueryFilter {
        private static final String WHERE = " WHERE ";
        private static final String AND = " AND ";
        private static final String ILIKE = " ILIKE ? ";

        private static final String FILTER_KEYWORD = NAME_COLUMN + ILIKE;
        private static final String FILTER_ID = "CAST(" + ID_COLUMN + " AS TEXT) " + ILIKE;

        private final StringBuffer query = new StringBuffer("SELECT * FROM " + TABLE_NAME);
        private boolean filterApplied = false;
        private final List<String> filters;

        private final FilterQueryMapper filterBySubword = (filter, filterName) -> {
            if(filter != null) {
                String stringFilter = "%" + filter.toString() + "%";
                appendFilter(filterName, stringFilter);
            }
        };

        private QueryFilter() {
            filters = new LinkedList<>();
        }

        public void filterByKeyword(final CourseFilter courseFilter) {
            filterBySubword.filter(courseFilter.getKeyword(), FILTER_KEYWORD);
        }

        public void filterById(final CourseFilter courseFilter) {
            filterBySubword.filter(courseFilter.getId(), FILTER_ID);
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
