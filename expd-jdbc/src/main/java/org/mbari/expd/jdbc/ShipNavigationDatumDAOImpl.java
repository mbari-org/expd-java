package org.mbari.expd.jdbc;

import org.mbari.expd.ShipNavigationDatum;
import org.mbari.expd.ShipNavigationDatumDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Implementation of the {@link ShipNavigationDatumDAO} interface for managing
 * ship navigation data retrieval from a database.
 *
 * This class provides methods to query navigation data for ships within a
 * specified date range. It relies on database interaction functionalities
 * provided by its parent class {@link BaseDAOImpl}.
 */
public class ShipNavigationDatumDAOImpl extends BaseDAOImpl implements ShipNavigationDatumDAO {

    public static final String SELECT_COLUMNS = " DatetimeGMT, Latitude, Longitude, Heading";

    public ShipNavigationDatumDAOImpl(JdbcParameters params) {
        super(params);
    }

    @Override
    public List<ShipNavigationDatum> fetchBetweenDates(String ship, Date start, Date end) {
        String table = resolveShipTablePrefix(ship) + "RawNavData";
        String sql = "SELECT " + SELECT_COLUMNS + " FROM " + table +
                " WHERE DatetimeGMT BETWEEN '" + DATE_FORMAT_UTC.format(start) + "' AND '" +
                DATE_FORMAT_UTC.format(end) + "'";
        return executeQueryFunction(sql, new LoadDateFunction(ship));
    }


    private class LoadDateFunction implements QueryFunction<List<ShipNavigationDatum>> {

        private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        private final String ship;

        private LoadDateFunction(String ship) {
            this.ship = ship;
        }

        @Override
        public List<ShipNavigationDatum> apply(ResultSet resultSet) throws SQLException {
            List<ShipNavigationDatum> data = new ArrayList<>();
            while(resultSet.next()) {
                Date date = resultSet.getTimestamp(1, calendar);
                Double latitude = resultSet.getDouble(2);
                Double longitude = resultSet.getDouble(3);
                Double heading = resultSet.getDouble(4);

                data.add(new ShipNavigationDatumImpl(date, heading, latitude, longitude, ship));
            }

            return data;
        }
    }

}

