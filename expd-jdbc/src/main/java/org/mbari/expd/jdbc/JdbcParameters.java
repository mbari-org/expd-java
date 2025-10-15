package org.mbari.expd.jdbc;

import java.util.Optional;

/**
 * Contains the JDBC parameters for a connection
 * @param url
 * @param username
 * @param password
 * @param driver
 */
public record JdbcParameters(String url, String username, String password, Optional<String> driver) {

}
