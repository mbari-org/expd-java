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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.mbari.expd.CtdDatum;
import org.mbari.expd.CtdDatumDAO;
import org.mbari.expd.Dive;
import org.mbari.expd.math.NearestNeighbor;


/**
 * This class is an implementation of the CtdDatumDAO interface for accessing and
 * fetching CTD (Conductivity, Temperature, Depth) data records from a database.
 * It extends the BaseDAOImpl class to provide common database operations and
 * utilities.
 */
public class CtdDatumDAOImpl extends BaseDAOImpl implements CtdDatumDAO {
    
    public static final String SELECT_COLUMNS = " DatetimeGMT, t, s, o2, o2Flag, o2alt, o2altFlag, light, p ";

    /**
     * Constructs ...
     * @param params The JDBC parameters
     */
    public CtdDatumDAOImpl(JdbcParameters params) {
        super(params);
    }

    /**
     * Fetch all the CTD data records for a Dive
     * @param dive
     * @return
     */
    @Override
    public List<CtdDatum> fetchCtdData(Dive dive) {
        String prefix = resolveRovTablePrefix(dive.getRovName());
        String table = prefix + "RovctdData";

        // Chunk the SQL into parts that we reuse if needed
        String preSql = "SELECT " + SELECT_COLUMNS + " FROM ";
        String postSql = " WHERE DatetimeGMT BETWEEN '" + DATE_FORMAT_UTC.format(dive.getStartDate()) +
                "' AND '" + DATE_FORMAT_UTC.format(dive.getEndDate()) + "' ORDER BY DatetimeGMT";

        String sql = preSql + table + postSql;
        List<CtdDatum> data = executeQueryFunction(sql, new LoadDataFunction(dive.getRovName()));

        /*
         * Occacionally, the CTD data is only available in the RovctdBinData table.
         * If we fail to fetch it from the raw table , check the binned table
         */
        if (data.isEmpty()) {
            table = prefix + "RovctdBinData";
            sql = preSql + table + postSql;
            data = executeQueryFunction(sql, new LoadDataFunction(dive.getRovName()));
        }
        
        return data;
    }

//    public Integer countForDive(Dive dive) {
//        String prefix = resolveRovTablePrefix(dive.getRovName());
//        String table = prefix + "RovctdData";
//        String preSql = "SELECT count(DatetimeGMT) FROM";
//        String postSql = " WHERE DatetimeGMT BETWEEN '" + DATE_FORMAT_UTC.format(dive.getStartDate()) +
//                        "' AND '" + DATE_FORMAT_UTC.format(dive.getEndDate()) + "'";
//        String sql = preSql + table + postSql;
//        executeQueryFunction(sql, )
//    }

    /**
     * Fetch ctdData for a dive. Use nearest neighbor interpolation to return the
     * record nearest to each date in a list within the specified tolerance
     * @param dive
     * @param dates
     * @param toleranceSec
     * @return
     */
    @Override
    public List<CtdDatum> fetchCtdData(Dive dive, List<Date> dates, double toleranceSec) {
        List<CtdDatum> ctdData = fetchCtdData(dive);

        if (!ctdData.isEmpty()) {
            double[] ctdTimes = ctdData.stream()
                    .mapToDouble(d -> d.getDate().getTime() / 1000D)
                    .toArray();

            double[] times = dates.stream()
                    .mapToDouble(d -> d.getTime() / 1000D)
                    .toArray();


            int[] idx = NearestNeighbor.apply(ctdTimes, times, toleranceSec);

            return NearestNeighbor.collate(ctdData, idx);
        }
        else {
            return ctdData;
        }


    }


    /**
     * Helper function that knows how to generate a {@link List} of {@link org.mbari.expd.CameraDatum}
     * objects from a {@link ResultSet}
     */
    private class LoadDataFunction implements QueryFunction<List<CtdDatum>> {

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
         * Applies the function to the given ResultSet, extracting CTD data records
         * and returning them as a List of CtdDatum objects.
         * @param resultSet The ResultSet to process
         * @return A List of CtdDatum objects
         *
         * @throws SQLException if a database access error occurs while reading from the result set
         */
        @Override
        public List<CtdDatum> apply(ResultSet resultSet) throws SQLException {

            List<CtdDatum> data = new ArrayList<>();
            while (resultSet.next()) {

                /*
                     1            2  3  4   5       6      7           8     9
                   " DatetimeGMT, t, s, o2, o2Flag, o2alt, o2altFlag, light, p "
                 */

                // Oxygen requires special care. See https://oceana.mbari.org/jira/browse/VARS-507
                Integer o2Flag = resultSet.getInt(5);
                Float o2 = resultSet.getFloat(4);
                Integer o2AltFlag = resultSet.getInt(7);
                Float o2Alt = resultSet.getFloat(6);
                Date date = resultSet.getTimestamp(1, calendar);
                Float temperature = resultSet.getFloat(2);
                Float salinity = resultSet.getFloat(3);
                Float light = resultSet.getFloat(8);
                Float pressure = resultSet.getFloat(9);

                data.add(new CtdDatumImpl(platformName, date, temperature, salinity, o2, o2Flag,
                        o2Alt, o2AltFlag, light, pressure));

            }

            return data;

        }
    }

}
