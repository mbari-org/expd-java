package org.mbari.expd.jdbc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.lang.System.Logger;

/**
 * Generic class for working with an SQL connection.
 */
public class Queryable {

    protected final Logger log = System.getLogger(getClass().getName());

    /**
     * Standard format for all Dates. No timezone is displayed.
     * THe date will be formatted for the UTC timezone
     */
    protected final DateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {{
            setTimeZone(TimeZone.getTimeZone("UTC"));
    }};
    private final String jdbcPassword;
    private final String jdbcUrl;
    private final String jdbcUsername;

    /**
     * Constructs ...
     * @param jdbcUrl The JDBC URL to the EXPD database.
     * @param jdbcUsername The username used to connect to the database.
     * @param jdbcPassword The password used to connect to the database.
     * @param driverClass Optional. The fully qualified name of the JDBC driver class.
     */
    public Queryable(String jdbcUrl, String jdbcUsername, String jdbcPassword, String driverClass) {
        this.jdbcUrl = jdbcUrl;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;

        if ( driverClass != null) {
            try {
                Class.forName(driverClass);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Failed to initialize driver class:" + driverClass, ex);
            }
        }
    }

    public QueryResults executeQuery(String sql) {

        // Just wrap the QueryResults returned by the sql query with a QueryResults object
        QueryFunction<QueryResults> queryFunction = new QueryFunction<QueryResults>() {

            public QueryResults apply(ResultSet resultsSet) throws SQLException {
                return new QueryResults(resultsSet);
            }
        };

        return executeQueryFunction(sql, queryFunction);

    }

    public <T> T executeQueryFunction(String query, QueryFunction<T> queryFunction) {
        log.log(Logger.Level.DEBUG, "Executing SQL query: \n\t" + query);

        T object = null;
        Connection connection = null;
        try {
            connection = getConnection();
            final Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            object = queryFunction.apply(rs);
            rs.close();
            stmt.close();
            connection.close();
        }
        catch (Exception e) {
            if (connection != null) {
                log.log(Logger.Level.ERROR, "Failed to execute the following SQL on " + jdbcUrl + ":\n" + query, e);

                try {
                    connection.close();
                }
                catch (SQLException ex) {
                    log.log(Logger.Level.ERROR,"Failed to close database connection", ex);
                }
            }

            throw new RuntimeException("Failed to execute the following SQL on " + jdbcUrl + ": " + query, e);
        }

        return object;
    }

    public int executeUpdate(String updateSql) {
        log.log(Logger.Level.DEBUG,"Executing SQL update: \n\t" + updateSql);

        int n = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            final Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
            n = stmt.executeUpdate(updateSql);
            stmt.close();
            connection.close();
        }
        catch (Exception e) {
            if (connection != null) {
                log.log(Logger.Level.ERROR, "Failed to execute the following SQL on " + jdbcUrl + ":\n" + updateSql, e);

                try {
                    connection.close();
                }
                catch (SQLException ex) {
                    log.log(Logger.Level.ERROR,"Failed to close database connection", ex);
                }
            }

            throw new RuntimeException("Failed to execute the following SQL on " + jdbcUrl + ": " + updateSql, e);
        }
        return n;
    }


    /**
     * Opens a connection to the EXPD database.
     * @return A {@link Connection} to the EXPD database. The connection should
     *      be closed when you're done with it.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        log.log(Logger.Level.DEBUG, "Opening JDBC connection:" + jdbcUsername + " @ " + jdbcUrl);
        Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
        return connection;
    }

    /**
     * Closes the connection used in the current thread.
     * @throws SQLException
     * @deprecated Connections are closed after every transaction.
     */
    public void close() {
        // DO NOTHING.
    }
}

