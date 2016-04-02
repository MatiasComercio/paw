package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mati on 30/03/16.
 */
@Repository
public class CourseDaoJdbc implements CourseDao {

    private static final String TABLE_NAME = "course";
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String CREDITS_COLUMN = "credits";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Course> courseRowMapper = (resultSet, rowNum) ->
            new Course(resultSet.getInt(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN),
                    resultSet.getInt(CREDITS_COLUMN));


    @Autowired
    public CourseDaoJdbc(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);
    }


    @Override
    public Course create(int id, String coursename, int credits) {
        final Map<String, Object> args = new HashMap<>();
        args.put(ID_COLUMN, id);
        args.put(NAME_COLUMN, coursename);
        args.put(CREDITS_COLUMN, credits);
        final Number key = jdbcInsert.executeAndReturnKey(args);

        return new Course(key.intValue(), coursename, credits);
    }

    @Override
    public Course getById(int id) {

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
    public List<Course> getByFilter(CourseFilter courseFilter) {
        List<Course> courses = jdbcTemplate.query("SELECT * FROM course where name ILIKE ? AND CAST(id as CHAR) ILIKE ?", courseRowMapper,
                "%" + courseFilter.getKeyword() + "%",
                courseFilter.getId());

        return courses;
    }

    private class QueryFilter {
        private static final String WHERE = " WHERE ";
        private static final String AND = " AND ";
        private static final String ILIKE = " ILIKE ? ";

        private static final String FILTER_KEYWORD = NAME_COLUMN + ILIKE;
        private static final String FILTER_ID = ID_COLUMN + ILIKE;

        private final StringBuffer query = new StringBuffer("SELECT * FROM " + TABLE_NAME);
        private boolean filterApplied = false;
        private final List<String> filters;

        private QueryFilter() {
            filters = new LinkedList<>();
        }

        public void filterByKeyword(CourseFilter courseFilter) {
            String filter = courseFilter.getKeyword();

            if(filter != null) {
                String stringFilter = "%" + filter + "%";
                appendFilter(FILTER_KEYWORD, stringFilter);
            }
        }

        public void filterById(CourseFilter courseFilter) {
            Integer filter = courseFilter.getId();

            if(filter != null) {
                String stringFilter = filter.toString() + "%";
                appendFilter(FILTER_ID, stringFilter);
            }
        }

        private void appendFilterConcatenation() {
            if(!filterApplied) {
                filterApplied = true;
                query.append(WHERE);
            } else {
                query.append(AND);
            }
        }

        private void appendFilter(String filter, String stringFilter) {
            appendFilterConcatenation();
            query.append(filter);
            filters.add(stringFilter);
        }
    }
}
