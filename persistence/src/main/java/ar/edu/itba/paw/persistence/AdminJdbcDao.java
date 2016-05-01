package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.models.users.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class AdminJdbcDao implements AdminDao {

    /* TABLE NAMES */
    private static final String ADMIN_TABLE = "admin";
    /* /TABLE NAMES */

    /* COLS NAMES */
    private static final String ADMIN__DNI_COLUMN = "dni";
    /* COLS NAMES */

    /* POSTGRESQL WILDCARDS */
    private static final String EVERYTHING = "*";
    private static final String EQUALS = "=";
    private static final String GIVEN_PARAMETER = "?";
    /* /POSTGRESQL WILDCARDS */

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Admin> getAllAdmins() {
        return null;
    }

    /* Private Static Methods */
    private static String select(final String... cols) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        buildSentence(stringBuilder, cols);
        return stringBuilder.toString();
    }

    private static String from(final String... cols) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FROM ");
        buildSentence(stringBuilder, cols);
        return stringBuilder.toString();
    }

    private static String where(final String... cols) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WHERE ");
        for (String col : cols) {
            stringBuilder.append(col);
        }
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    private static void buildSentence(final StringBuilder stringBuilder, final String... cols) {
        int i = 0;
        final int lCols = cols.length;
        for (String col : cols) {
            i++;
            stringBuilder.append(col);
            if (i < lCols) {
                stringBuilder.append(" ,");
            }
        }
        stringBuilder.append(" ");
    }

    private static String join(final String t1, final String t2, final String c1, final String c2) {
        return t1 + " JOIN " + t2 + " ON " + t1 + "." + c1 + " = " + t2 + "." + c2;
    }

    private static String tableCol(final String table, final String column) {
        return table + "." + column;
    }
}
