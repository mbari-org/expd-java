package org.mbari.expd.jdbc;

import java.util.Optional;

/**
 * The AppConfig class provides configuration utilities required for the application.
 * Specifically, it offers functionality to read JDBC configuration parameters from
 * environment variables and generate a JdbcParameters instance.
 *
 * This class relies on the following environment variables being set:
 * - EXPD_JDBC_URL: The JDBC connection URL.
 * - EXPD_JDBC_USERNAME: The username for database authentication.
 * - EXPD_JDBC_PASSWORD: The password for database authentication.
 * - EXPD_JDBC_DRIVER: Optional. The fully qualified name of the JDBC driver class.
 *
 * If any required environment variable is not set, a RuntimeException is thrown.
 */
public class AppConfig {

    /**
     * Read JDBC parameters from environment variables.
     * The following environment variables are required:
     *  - EXPD_JDBC_URL
     *  - EXPD_JDBC_USERNAME
     *  - EXPD_JDBC_PASSWORD
     *  - EXPD_JDBC_DRIVER
     * @return A new JdbcParameters object.
     */
    public static JdbcParameters fromEnv() {
        var url = Optional.
                ofNullable(System.getenv("EXPD_JDBC_URL"))
                .orElseThrow(() -> new RuntimeException("EXPD_JDBC_URL is not set"));

        var username = Optional
                .ofNullable(System.getenv("EXPD_JDBC_USERNAME"))
                .orElseThrow(() -> new RuntimeException("EXPD_JDBC_USERNAME is not set"));

        var password = Optional
                .ofNullable(System.getenv("EXPD_JDBC_PASSWORD"))
                .orElseThrow(() -> new RuntimeException("EXPD_JDBC_PASSWORD is not set"));

        var driver = Optional
                .ofNullable(System.getenv("EXPD_JDBC_DRIVER"));

        return new JdbcParameters(url, username, password, driver);
    }
}
