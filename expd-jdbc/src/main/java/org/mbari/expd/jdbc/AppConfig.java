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
