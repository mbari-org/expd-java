package org.mbari.expd.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a functional interface for processing a {@link ResultSet} and converting its data
 * into a desired type. This interface is commonly used for extracting and transforming
 * database query results into application-specific forms.
 *
 * @param <T> The type of object that results from processing the {@link ResultSet}.
 */
public interface QueryFunction<T> {
    /**
     * Applies this function to the given {@link ResultSet} to produce a result.
     * @param resultSet the result set to read from; positioned before the first row
     * @return the mapped result
     * @throws SQLException if a database access error occurs while reading from the result set
     */
    T apply(ResultSet resultSet) throws SQLException;
}
