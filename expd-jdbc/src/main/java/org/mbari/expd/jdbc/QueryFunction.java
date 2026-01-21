package org.mbari.expd.jdbc;

/*-
 * #%L
 * org.mbari.expd:expd-jdbc
 * %%
 * Copyright (C) 2008 - 2026 Monterey Bay Aquarium Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
