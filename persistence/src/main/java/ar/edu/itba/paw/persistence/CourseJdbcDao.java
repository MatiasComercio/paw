package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CourseJdbcDao implements CourseDao {

    private static final String TABLE_NAME = "course";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String CREDITS_COLUMN = "credits";

    private static final String DOCKET_COLUMN = "docket";
    private static final String DNI_COLUMN = "dni";
    private static final String FIRST_NAME_COLUMN = "firstName";
    private static final String LAST_NAME_COLUMN = "lastName";


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Course> courseRowMapper = (resultSet, rowNum) ->
            new Course(resultSet.getInt(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN),
                    resultSet.getInt(CREDITS_COLUMN));

    private final RowMapper<Student> studentRowMapper = (resultSet, rowNum) ->
            new Student.Builder(resultSet.getInt(DOCKET_COLUMN), resultSet.getInt(DNI_COLUMN)).firstName(FIRST_NAME_COLUMN).lastName(LAST_NAME_COLUMN).build();

    @Autowired
    public CourseJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);
    }


    @Override
    public Course create(final int id, final String coursename, final int credits) {
        final Map<String, Object> args = new HashMap<>();
        args.put(ID_COLUMN, id);
        args.put(NAME_COLUMN, coursename);
        args.put(CREDITS_COLUMN, credits);
        final Number key = jdbcInsert.executeAndReturnKey(args);

        return new Course(key.intValue(), coursename, credits);
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

        queryFilter.filterByKeyword(courseFilter);
        queryFilter.filterById(courseFilter);

        List<Course> courses = jdbcTemplate.query(queryFilter.getQuery(), courseRowMapper, queryFilter.getFilters().toArray());

        return courses;
    }

    private static class QueryFilter {
        private static final String WHERE = " WHERE ";
        private static final String AND = " AND ";
        private static final String ILIKE = " ILIKE ? ";

        private static final String FILTER_KEYWORD = NAME_COLUMN + ILIKE;
        private static final String FILTER_ID = "CAST(" + ID_COLUMN + " AS CHAR) " + ILIKE;

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
