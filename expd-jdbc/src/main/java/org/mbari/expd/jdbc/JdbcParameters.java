package org.mbari.expd.jdbc;

import java.util.Optional;

/**
 * Contains the JDBC parameters for a connection
 * @param url The JDBC connection URL
 * @param username The username for database authentication
 * @param password The password for database authentication
 * @param driver Optional. The fully qualified name of the JDBC driver class.
 */
public record JdbcParameters(String url, String username, String password, Optional<String> driver) {

}
