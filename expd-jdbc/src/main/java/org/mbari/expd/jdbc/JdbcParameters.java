package org.mbari.expd.jdbc;

import java.util.Optional;

public record JdbcParameters(String url, String username, String password, Optional<String> driver) {

}
