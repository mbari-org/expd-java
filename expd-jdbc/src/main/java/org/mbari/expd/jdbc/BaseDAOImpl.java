/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;


/**
 * Base class DAO. Bascially sets up the JDBC connection and provides static
 * helper methods
 * 
 * @author brian
 */
public class BaseDAOImpl extends Queryable {

    public static final DateFormat DATE_FORMAT_UTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
        {
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    };
    public static final String DAVID_PACKARD = "David Packard";
    public static final String DOC_RICKETTS = "Doc Ricketts";
    public static final String MINI_ROV = "Mini ROV";
    public static final String POINT_LOBOS = "Point Lobos";
    public static final String RACHEL_CARSON = "Rachel Carson";
    public static final String TIBURON = "Tiburon";
    public static final String VENTANA = "Ventana";
    public static final String WESTERN_FLYER = "Western Flyer";
    public static final String UNDEFINED = "undefined";

    public static final Integer OFFSET_SECONDS = 7;

    /**
     * Constructs a new BaseDAOImpl, initializing it with the given JDBC parameters.
     * The constructor sets up the database connection URL, username, password, and optionally
     * loads the specified JDBC driver class if provided.
     *
     * @param params The JDBC parameters for creating and managing the database connection.
     *               This includes the connection URL, username, password, and an optional
     *               fully qualified name of the JDBC driver class to be loaded.
     *               If the driver class is specified, the constructor attempts to load it.
     *               If the driver class cannot be loaded, a {@link RuntimeException} is thrown.
     * @throws RuntimeException If the specified driver class cannot be found or loaded.
     */
    public BaseDAOImpl(JdbcParameters params) {
        super(params.url(), params.username(), params.password(), params.driver().orElse(null));
        params.driver().ifPresent(d -> {
            try {
                Class.forName(d);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Failed to initialize driver class:" + d, ex);
            }
        });
    }

    /**
     * Returns the table prefix for the given ship.
     */
    public static String resolveShipTablePrefix(String ship) {
        String fullname = resolveFullShipName(ship);
        return switch (fullname) {
            case DAVID_PACKARD -> "DavidPackard";
            case WESTERN_FLYER -> "WesternFlyer";
            case POINT_LOBOS -> "PointLobos";
            case RACHEL_CARSON ->"RachelCarson";
            default -> "";
        };
    }

    /**
     * Returns the full name of the ship
     * @param ship The short name of the ship
     * @return The full name of the ship
     */
    public static String resolveFullShipName(String ship) {
        String fullname = ship;
        if (ship != null) {
            return switch (ship.toUpperCase().substring(0, 1)) {
                case "D" -> DAVID_PACKARD;
                case "W" -> WESTERN_FLYER;
                case "P" -> POINT_LOBOS;
                case "R" -> RACHEL_CARSON;
                default -> UNDEFINED;
            };
        }
        return UNDEFINED;
    }

    /**
     * Returns the table prefix for the given platform.
     * @param platform The short name of the platform
     * @return The table prefix for the given platform
     */
    public static String resolveRovTablePrefix(String platform) {
        String fullName = resolveFullRovName(platform);
        return switch (fullName) {
            case DOC_RICKETTS -> "DocRicketts";
            case TIBURON, VENTANA -> fullName;
            case MINI_ROV -> "Minirov";
            default -> "";
        };
    }

    /**
     * Returns the full name of the platform
     * @param platform The short name of the platform
     * @return The full name of the platform
     */
    public static String resolveFullRovName(String platform) {
        String fullName = platform;
        if (platform != null) {
            if (platform.toUpperCase().startsWith("VENT") || platform.equalsIgnoreCase("vnta")) {
                fullName = VENTANA;
            } else if (platform.toUpperCase().startsWith("DOC")) {
                fullName = DOC_RICKETTS;
            } else if (platform.toUpperCase().startsWith("TIB")) {
                fullName = TIBURON;
            } else if (platform.toUpperCase().startsWith("MINI")) {
                fullName = MINI_ROV;
            }
        }
        return fullName;
    }

    /**
     * Returns the short name commonly used in the expd database
     * 
     * @param platform The full name of the platform
     * @return The short name commonly used in the expd database
     */
    public static String toShortRovName(final String platform) {
        String shortName = platform;
        String fullName = resolveFullRovName(platform);

        if (fullName.equals(VENTANA)) {
            shortName = "vnta";
        } else if (fullName.equals(TIBURON)) {
            shortName = "tibr";
        } else if (fullName.equals(DOC_RICKETTS)) {
            shortName = "docr";
        } else if (fullName.equals(MINI_ROV)) {
            shortName = "mini";
        }
        return shortName;
    }

    /**
     * Resolves a collection of ROV (Remotely Operated Vehicle) names associated with the specified ship.
     * The mapping between ships and their corresponding ROVs is predefined and specific to known ship names.
     * If the ship name is unrecognized, the method returns an empty collection.
     *
     * @param shipName The short name of the ship for which the associated ROV names are to be resolved.
     *                 This parameter is case-insensitive but must match the known ship naming convention.
     * @return A collection of ROV names associated with the specified ship. If the ship name is not recognized,
     *         an empty collection is returned.
     */
    public static Collection<String> resolveRovByShip(final String shipName) {
        Collection<String> rovNames = new ArrayList<>();
        String fullShipName = resolveFullShipName(shipName);
        if (fullShipName.equals(POINT_LOBOS)) {
            rovNames.add(VENTANA);
        } else if (fullShipName.equals(WESTERN_FLYER)) {
            rovNames.add(TIBURON);
            rovNames.add(DOC_RICKETTS);
            rovNames.add(MINI_ROV);
        } else if (fullShipName.equals(RACHEL_CARSON)) {
            rovNames.add(VENTANA);
            rovNames.add(MINI_ROV);
        }
        return rovNames;
    }

    /**
     * Resolves a collection of ship names associated with the specified ROV (Remotely Operated Vehicle).
     * The mapping is predefined for known ROVs, and for each ROV, its corresponding ships are identified.
     * If the ROV name is not recognized, the method returns an empty collection.
     *
     * @param rovName The short name of the ROV for which the associated ship names are to be resolved.
     *                This parameter is case-insensitive and must match known ROV naming conventions.
     * @return A collection of ship names associated with the specified ROV. If the ROV name is not recognized,
     *         an empty collection is returned.
     */
    public static Collection<String> resolveShipByRov(final String rovName) {
        Collection<String> shipNames = new ArrayList<>();
        String fullRovName = resolveFullRovName(rovName);
        if (fullRovName.equals(VENTANA)) {
            shipNames.add(POINT_LOBOS);
            shipNames.add(RACHEL_CARSON);
        } else if (fullRovName.equals(TIBURON)) {
            shipNames.add(WESTERN_FLYER);
        } else if (fullRovName.equals(DOC_RICKETTS)) {
            shipNames.add(WESTERN_FLYER);
        } else if (fullRovName.equals(MINI_ROV)) {
            shipNames.add(WESTERN_FLYER);
            shipNames.add(RACHEL_CARSON);
        }
        return shipNames;
    }

    /**
     * Returns a list of all known ROV (Remotely Operated Vehicle) names.
     * The list includes the names of all ROVs that are recognized and used within the
     * context of the application or database.
     * @return A list of all known ROV (Remotely Operated Vehicle) names.
     */
    public static List<String> getAllRovNames() {
        return new ArrayList<String>() {
            {
                add(VENTANA);
                add(TIBURON);
                add(DOC_RICKETTS);
                add(MINI_ROV);
            }
        };
    }

}
