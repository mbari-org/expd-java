package org.mbari.expd.jdbc;

import java.util.Optional;

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
