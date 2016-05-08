package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CourseJdbcDao implements CourseDao {

    private static final String TABLE_NAME = "course";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String CREDITS_COLUMN = "credits";
    private static final String SEMESTER_COLUMN = "semester";

    private static final String DOCKET_COLUMN = "docket";
    private static final String DNI_COLUMN = "dni";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";

    private static final String INSCRIPTION_TABLE_NAME = "inscription";
    private static final String INSCRIPTION_ID_COURSE = "course_id";

    private static final String GRADES_TABLE_NAME = "grade";
    private static final String GRADES_ID_COURSE = "course_id";

    private static final String CORRELATIVE_TABLE_NAME = "correlative";
    private static final String CORRELATIVE_COURSE_ID = "course_id";
    private static final String CORRELATIVE_CORRELATIVE_ID = "correlative_id";

    private static final String QUERY_DELETE = "DELETE FROM " + TABLE_NAME
            + " WHERE " + ID_COLUMN + " = ?";

    private static final String QUERY_COUNT_INSCRIPTION = "SELECT COUNT(*) FROM " + INSCRIPTION_TABLE_NAME
            + " WHERE " + INSCRIPTION_ID_COURSE + " = ?";

    private static final String QUERY_COUNT_GRADES = "SELECT COUNT(*) FROM " + GRADES_TABLE_NAME
            + " WHERE " + GRADES_ID_COURSE + " = ?";


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert courseInsert;
    private final SimpleJdbcInsert correlativeInsert;

    private final RowMapper<Course> courseRowMapper = (resultSet, rowNum) ->
            new Course.Builder(resultSet.getInt(ID_COLUMN))
                        .name(resultSet.getString(NAME_COLUMN))
                        .credits(resultSet.getInt(CREDITS_COLUMN))
                        .semester(resultSet.getInt(SEMESTER_COLUMN))
                        .build();

    private final RowMapper<Student> studentRowMapper = (resultSet, rowNum) ->
            new Student.Builder(resultSet.getInt(DOCKET_COLUMN), resultSet.getInt(DNI_COLUMN)).firstName(resultSet.getString(FIRST_NAME_COLUMN)).lastName(resultSet.getString(LAST_NAME_COLUMN)).build();

    private final RowMapper<Integer> correlativeRowMapper = (resultSet, rowNum) ->
            resultSet.getInt(CORRELATIVE_CORRELATIVE_ID);

    private final RowMapper<Integer> upperCorrelativeRowMapper = (resultSet, rowNum) ->
            resultSet.getInt(CORRELATIVE_COURSE_ID);

    private final RowMapper<Course> correlativeCoursesRowMapper = (resultSet, rowNum) ->
            new Course.Builder(resultSet.getInt(ID_COLUMN))
                    .name(resultSet.getString(NAME_COLUMN))
                    .credits(resultSet.getInt(CREDITS_COLUMN)).build();



    @Autowired
    public CourseJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.courseInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingColumns(ID_COLUMN, NAME_COLUMN, CREDITS_COLUMN, SEMESTER_COLUMN);
        this.correlativeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(CORRELATIVE_TABLE_NAME)
                .usingColumns(CORRELATIVE_COURSE_ID, CORRELATIVE_CORRELATIVE_ID);
    }


    @Override
    public Result create(Course course) {
        final Map<String, Object> args = new HashMap<>();

        args.put(ID_COLUMN, course.getId());
        args.put(NAME_COLUMN, course.getName());
        args.put(CREDITS_COLUMN, course.getCredits());
        args.put(SEMESTER_COLUMN, course.getSemester());

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
            jdbcTemplate.update("UPDATE course SET id = ?, name = ?, credits = ?, semester = ? WHERE id = ?;", course.getId(), course.getName(), course.getCredits(), course.getSemester(), id);
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
    public List<Integer> getCorrelatives(Integer courseId){
        List<Integer> correlatives = jdbcTemplate.query("SELECT * FROM " + CORRELATIVE_TABLE_NAME + " WHERE " +
                CORRELATIVE_COURSE_ID + " = ?", correlativeRowMapper, courseId);
        return correlatives;
    }

    @Override
    public List<Course> getCorrelativeCourses(Integer courseId) {
        String queryCorrelativeId = "SELECT " + CORRELATIVE_CORRELATIVE_ID + " FROM " +
                CORRELATIVE_TABLE_NAME + " JOIN " + TABLE_NAME + " ON "
                + CORRELATIVE_TABLE_NAME + "." + CORRELATIVE_COURSE_ID + " = " + TABLE_NAME + "." + ID_COLUMN + " WHERE "
                + CORRELATIVE_COURSE_ID + " = ?";
        String queryCorrelativeCourses = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " IN " + "("
                + queryCorrelativeId + ")";



        List<Course> correlativeCourses = jdbcTemplate.query(queryCorrelativeCourses, correlativeCoursesRowMapper, courseId);
        return correlativeCourses;
    }

    @Override
    public List<Integer> getUpperCorrelatives(Integer courseId){
        List<Integer> correlatives = jdbcTemplate.query("SELECT * FROM " + CORRELATIVE_TABLE_NAME + " WHERE " +
                CORRELATIVE_CORRELATIVE_ID + " = ?", upperCorrelativeRowMapper, courseId);
        return correlatives;
    }

    @Override
    public Result deleteCorrelative(Integer courseId, Integer correlativeId) {
        try {
            int rowsAffected = jdbcTemplate.update("DELETE FROM " + CORRELATIVE_TABLE_NAME + " WHERE " + CORRELATIVE_COURSE_ID +
                    " = ? AND " + CORRELATIVE_CORRELATIVE_ID + " = ?" , courseId, correlativeId);

            return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
        } catch (final DataIntegrityViolationException e) {
            return Result.INVALID_INPUT_PARAMETERS;
        } catch(final DataAccessException e) {
            return Result.ERROR_UNKNOWN;
        }
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

    @Override
    public boolean checkCorrelativityLoop(Integer id, Integer correlativeId) {
        //TODO: Change table columns name
        String query = "WITH RECURSIVE corr (cid, corrid) AS " +
                        "(SELECT course_id, correlative_id FROM correlative " +
                        "UNION ALL " +
                        "SELECT corr.cid, correlative.correlative_id " +
                        "FROM corr, correlative " +
                        "WHERE corr.corrid = correlative.course_id) " +
                        "SELECT cid, corrid FROM corr;";

        List<Correlativity> list = jdbcTemplate.query(query, (rs, rowNum) -> {
            return new Correlativity(rs.getInt("cid"), rs.getInt("corrid"));
        });

        return list.contains(new Correlativity(correlativeId, id));
    }

    @Override
    public Integer getTotalPlanCredits() {
        String query = "SELECT SUM(" + CREDITS_COLUMN + ") as " + CREDITS_COLUMN + " FROM " + TABLE_NAME + ";";
        RowMapper<Integer> rm = (rs, rowNumber) -> rs.getInt(CREDITS_COLUMN);
        return jdbcTemplate.query(query, rm).get(0);
    }

    @Override
    public Result addCorrelativity(Integer id, Integer correlativeId) {
        final Map<String, Object> args = new HashMap<>();
        args.put(CORRELATIVE_COURSE_ID, id);
        args.put(CORRELATIVE_CORRELATIVE_ID, correlativeId);

        try {
            correlativeInsert.execute(args);
        } catch (DuplicateKeyException e){
            return Result.CORRELATIVE_CORRELATIVITY_EXISTS;
        }

        return Result.OK;
    }

    @Override
    public boolean courseExists(Integer id) {
        String query = "SELECT * FROM course WHERE id = ?";
        List<Integer> list = jdbcTemplate.query(query, (rs, rowNum) -> {
            return rs.getInt(ID_COLUMN);
        }, id);

        return !list.isEmpty();
    }

    /* +++xreference
                    http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html#update-java.lang.String-java.lang.Object...-
                 */
    @Override
    public Result deleteCourse(Integer id) {
        try {
            int rowsAffected = jdbcTemplate.update(QUERY_DELETE, id);
            return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
        } catch (final DataIntegrityViolationException e) {
            return Result.INVALID_INPUT_PARAMETERS;
        } catch(final DataAccessException e) {
            return Result.ERROR_UNKNOWN;
        }
    }

    @Override
    public boolean inscriptionExists(Integer courseId){
        Object[] idWrapped = new Object[]{courseId};
        int courseNumber = jdbcTemplate.queryForObject(QUERY_COUNT_INSCRIPTION, idWrapped, Integer.class);
        if(courseNumber > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean gradeExists(Integer courseId){
        Object[] idWrapped = new Object[]{courseId};
        int courseNumber = jdbcTemplate.queryForObject(QUERY_COUNT_GRADES, idWrapped, Integer.class);
        if(courseNumber > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getTotalSemesters() {
        String query = "SELECT MAX(" + SEMESTER_COLUMN + ") as " + SEMESTER_COLUMN + " FROM " + TABLE_NAME + ";";
        RowMapper<Integer> rm = (rs, rowNum) -> rs.getInt(SEMESTER_COLUMN);
        List<Integer> list = jdbcTemplate.query(query, rm);
        return list.isEmpty() ? 0 : list.get(0);
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
                String escapedFilter = escapeFilter(filter);
                String stringFilter = "%" + escapedFilter + "%";
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

        private String escapeFilter(final Object filter) {
            return filter.toString().replace("%", "\\%").replace("_", "\\_");
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

    private class Correlativity {
        private int course_id;
        private int correlative_id;

        public Correlativity(int course_id, int correlative_id) {
            this.course_id = course_id;
            this.correlative_id = correlative_id;
        }

        //TODO: Improve
        @Override
        public boolean equals(Object obj) {
            return course_id == ((Correlativity)obj).course_id && correlative_id == ((Correlativity)obj).correlative_id;
        }
    }
}
