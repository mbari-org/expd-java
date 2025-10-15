/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

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

    private static final ResourceBundle bundle = ResourceBundle.getBundle("expd-jdbc");
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
     * Constructs ...
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

    public static String resolveRovTablePrefix(String platform) {
        String fullName = resolveFullRovName(platform);
        return switch (fullName) {
            case DOC_RICKETTS -> "DocRicketts";
            case TIBURON, VENTANA -> fullName;
            case MINI_ROV -> "Minirov";
            default -> "";
        };
    }

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
     * @param platform
     * @return
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
