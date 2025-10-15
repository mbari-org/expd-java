package org.mbari.expd.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Dec 26, 2009
 * Time: 2:22:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryFunction<T> {
    T apply(ResultSet resultSet) throws SQLException;
}
