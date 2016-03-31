package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
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

    private final RowMapper<Course> userRowMapper = (resultSet, rowNum) ->
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

        List<Course> course = jdbcTemplate.query("SELECT * FROM course WHERE id = ? LIMIT 1", userRowMapper, id);

        return course.isEmpty() ? null : course.get(0);
    }
}
