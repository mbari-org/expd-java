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
    T apply(ResultSet resultSet) throws SQLException;
}
