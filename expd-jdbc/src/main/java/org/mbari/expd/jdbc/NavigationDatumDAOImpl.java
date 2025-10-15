/*
 * @(#)NavigationDatumDAOImpl.java   2009.12.29 at 09:50:21 PST
 *
 * Copyright 2009 MBARI
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.mbari.expd.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.mbari.expd.Dive;
import org.mbari.expd.NavigationDatum;
import org.mbari.expd.NavigationDatumDAO;
import org.mbari.expd.math.NearestNeighbor;

/**
 * The NavigationDatumDAOImpl class provides an implementation of the NavigationDatumDAO
 * interface for accessing and managing navigation data, including raw and edited navigation
 * data associated with specific dives. This class interacts with the database to fetch, process,
 * and return navigation data for Remote Operated Vehicle (ROV) dives. It also includes logic
 * for associating navigation data with camera log times and handling nearest neighbor matching.
 */
public class NavigationDatumDAOImpl extends BaseDAOImpl implements NavigationDatumDAO {

    /**  */
    public static final String SELECT_COLUMNS = " DatetimeGMT, Latitude, Longitude, " +
            "Pressure, Depth, Altitude, Heading, Pitch, Roll, ShipLatitude, " +
            "ShipLongitude, ShipHeading, QCFlag ";

    public NavigationDatumDAOImpl(JdbcParameters params) {
        super(params);
    }


    /**
     * Fetch the best available navigation and cameradata for a dive. Navigation
     * is matched to the Camera Log times usign the closes time within +/-7 seconds
     *
     * @param dive The dive to fetch data for
     * @return A list of ROVDatums.
     */
    @Override
    public List<NavigationDatum> fetchRawNavigationData(Dive dive) {
        String prefix = resolveRovTablePrefix(dive.getRovName());
        String tableRaw = prefix + "RawNavData";

        String sql = "SELECT " + SELECT_COLUMNS + " FROM " + tableRaw +
                " WHERE DatetimeGMT BETWEEN '" + DATE_FORMAT_UTC.format(dive.getStartDate()) +
                "' AND '" + DATE_FORMAT_UTC.format(dive.getEndDate()) + "' ORDER BY DatetimeGMT";


        List<NavigationDatum> data = executeQueryFunction(sql, new LoadDataFunction(dive.getRovName()));
        
        // Set edited flag to false
        data.stream().forEach((navigationDatum) -> {
            ((NavigationDatumImpl) navigationDatum).setEdited(Boolean.FALSE);
        });

        return data;

    }

    /**
     * Check to see if there is edited navigation data for the dive.
     * @param dive The dive to check
     * @return true if there is edited navigation data for the dive.
     */
    public boolean hasEditedNavigationData(Dive dive) {
        String prefix = resolveRovTablePrefix(dive.getRovName());
        String tableEdited = prefix + "CleanNavData";
        String sql = "SELECT COUNT(*) FROM " + tableEdited +
                " WHERE DatetimeGMT BETWEEN '" + DATE_FORMAT_UTC.format(dive.getStartDate()) +
                "' AND '" + DATE_FORMAT_UTC.format(dive.getEndDate()) + "'";
        return executeQueryFunction(sql, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            else {
                return false;
            }
        });
    }



    /**
     * Fetch the best available navigation and cameradata for a dive. Navigation
     * is matched to the Camera Log times usign the closes time within +/-7 seconds
     *
     * @param dive The dive to fetch data for
     * @return A list of ROVDatums.
     */
    public List<NavigationDatum> fetchEditedNavigationData(Dive dive) {
        String prefix = resolveRovTablePrefix(dive.getRovName());
        String tableEdited = prefix + "CleanNavData";

        String sql = "SELECT " + SELECT_COLUMNS + " FROM " + tableEdited +
                " WHERE DatetimeGMT BETWEEN '" + DATE_FORMAT_UTC.format(dive.getStartDate()) +
                "' AND '" + DATE_FORMAT_UTC.format(dive.getEndDate()) + "' ORDER BY DatetimeGMT";

        List<NavigationDatum> data = executeQueryFunction(sql, new LoadDataFunction(dive.getRovName()));

        // Set edited flag to true
        data.stream().forEach((navigationDatum) -> {
            ((NavigationDatumImpl) navigationDatum).setEdited(Boolean.TRUE);
        });

        return data;

    }


    /**
     * Fetch nearest navigation data for each date provided that is within +/-
     * seconds defined by the parameter 'toleranceSec'. If no datum is found within
     * the tolerance a null is returned in for that date in the list.
     * @param dive The dive to fetch data for
     * @param dates The list of dates to fetch data for
     * @param toleranceSec The tolerance in seconds
     * @return A list of NavigationDatum objects.
     */
    @Override
    public List<NavigationDatum> fetchNavigationData(Dive dive, List<Date> dates, double toleranceSec) {

        double[] times = dates.stream()
                .mapToDouble(d -> d.getTime() / 1000D)
                .toArray();

        List<NavigationDatum> editedNavData = fetchEditedNavigationData(dive);
        boolean hasMissing = editedNavData.isEmpty();

        List<NavigationDatum> data = new ArrayList<>();

        // --- Fetch edited nav
        if (!editedNavData.isEmpty()) {
            double[] editedNavTimes = editedNavData.stream()
                    .mapToDouble(d -> d.getDate().getTime() / 1000D)
                    .toArray();

            int[] idx = NearestNeighbor.apply(editedNavTimes, times, toleranceSec);

            // idx is the same size as times/dates. this will create a data list
            // of the same size, with null values where idx is 0
            data = NearestNeighbor.collate(editedNavData, idx);

            for (int i = 0; i < idx.length; i++) {
                hasMissing = idx[i] == -1;
                if (hasMissing) {
                    break;
                }
            }
        }

        if (hasMissing) {
            // --- If no edited nav is found, need to fill a list with nulls
            data = new ArrayList<>(Collections.nCopies(dates.size(), null));

            List<NavigationDatum> rawNavData = fetchRawNavigationData(dive);

            if (rawNavData != null && !rawNavData.isEmpty()) {
                double[] rawNavTimes = rawNavData.stream()
                        .mapToDouble(d -> d.getDate().getTime() / 1000D)
                        .toArray();

                int[] idx2 = NearestNeighbor.apply(rawNavTimes, times, toleranceSec);
                List<NavigationDatum> rawData = NearestNeighbor.collate(rawNavData, idx2);

                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) == null) {
                        data.set(i, rawData.get(i));
                    }
                }
            }
        }

        return data;

    }

    /**
     * Fetch the best available navigation and cameradata for a dive.
     * @param dive The dive to fetch data for
     * @return A list of ROVDatums.
     */
    @Override
    public List<NavigationDatum> fetchBestNavigationData(Dive dive) {
        List<NavigationDatum> data = fetchEditedNavigationData(dive);
        if (data.isEmpty()) {
            data = fetchRawNavigationData(dive);
        }
        return data;
    }


    private static class LoadDataFunction implements QueryFunction<List<NavigationDatum>> {

        private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        private final String platformName;

        /**
         * Constructs ...
         *
         * @param platformName The platform name (e.g. Ventana or Tiburon)
         */
        public LoadDataFunction(String platformName) {
            this.platformName = platformName;
        }

        /**
         *
         * @param resultSet The resultset to process
         * @return A List of NavigationDatum objects
         *
         * @throws SQLException if a database access error occurs while reading from the result set
         */
        @Override
        public List<NavigationDatum> apply(ResultSet resultSet) throws SQLException {

            List<NavigationDatum> data = new ArrayList<>();
            while (resultSet.next()) {
                Date date = resultSet.getTimestamp(1, calendar);
                Double latitude = resultSet.getDouble(2);
                Double longitude = resultSet.getDouble(3);
                Float pressure = resultSet.getFloat(4);
                Float depth = resultSet.getFloat(5);
                Float altitude = resultSet.getFloat(6);
                Float heading = resultSet.getFloat(7);
                Float pitch = resultSet.getFloat(8);
                Float roll = resultSet.getFloat(9);
                Double shipLatitude = resultSet.getDouble(10);
                Double shipLongitude = resultSet.getDouble(11);
                Float shipHeading = resultSet.getFloat(12);
                Boolean qcFlag = resultSet.getBoolean(13);

                data.add(new NavigationDatumImpl(platformName, altitude, date, depth, pressure, heading, latitude,
                                                 longitude, pitch, qcFlag, roll, shipHeading, shipLatitude,
                                                 shipLongitude));

            }

            return data;
        }
    }
}
