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
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;


import org.mbari.expd.Dive;
import org.mbari.expd.DiveDAO;
import org.mbari.expd.Expedition;
import org.mbari.expd.math.Envelope;

/**
 * Implementation of the {@link DiveDAO} interface, providing methods to interact
 * with dive-related data in a database. This class is responsible for querying,
 * retrieving, and parsing data related to dives based on various criteria, such
 * as platform, tracking numbers, geographical locations, and expeditions.
 *
 * This implementation extends {@link BaseDAOImpl} to reuse common database operations
 * and functionality, and it uses SQL queries to fetch dive data by utilizing specific
 * filtering and joining logic with related database entities.
 */
public class DiveDAOImpl extends BaseDAOImpl implements DiveDAO {

    private final String SELECT_COLUMNS = "d.diveid AS DiveID, d.rovname AS RovName, " +
            "d.divenumber AS DiveNumber, d.divestartdtg AS DiveStartDtg, " +
            "d.diveenddtg AS DiveEndDtg, d.divechiefscientist AS DiveChiefScientist, " +
            "d.BriefAccomplishments, ds.avgrovlat AS Latitude, ds.avgrovlon AS Longitude";
    private final String FROM_STATEMENT = "FROM DiveSummary ds RIGHT OUTER JOIN " +
            "Dive d ON d.DiveID = ds.diveid";
    private final QueryFunction<List<Dive>> queryFunction = new LoadDiveFunction();

    /**
     * Constructor
     * @param jdbcParameters The JDBC parameters used to connect to the database
     */
    public DiveDAOImpl(JdbcParameters jdbcParameters) {
        super(jdbcParameters);
    }

    /**
     * Returns all the dive numbers that occur for a platform using a given
     * trackingNumber. The trackingNumber parsed as YYYYDDD date format
     *
     * @param platform The platform name
     * @param trackingNumber The tracking number
     * @return A List of dive numbers
     */
    @Override
    public Collection<Dive> findByPlatformAndTrackingNumber(String platform, Integer trackingNumber) {
        Calendar utcCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        String numberString = trackingNumber.toString();
        int year = Integer.parseInt(numberString.substring(0, 4));
        int dayOfYear = Integer.parseInt(numberString.substring(4, 7));

        utcCalendar.set(GregorianCalendar.YEAR, year);
        utcCalendar.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);

        // Get startDate
        utcCalendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
        utcCalendar.set(GregorianCalendar.MINUTE, 0);
        utcCalendar.set(GregorianCalendar.SECOND, 0);
        String startDate = DATE_FORMAT_UTC.format(utcCalendar.getTime());

        // Get endDate
        utcCalendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
        utcCalendar.set(GregorianCalendar.MINUTE, 59);
        utcCalendar.set(GregorianCalendar.SECOND, 59);
        String endDate = DATE_FORMAT_UTC.format(utcCalendar.getTime());

         String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " WHERE " +
                 "DiveStartDtg BETWEEN '" + startDate + "' AND '" + endDate +
                 "' AND d.rovname = '" + toShortRovName(platform) + "' ORDER BY d.divenumber";

         return executeQueryFunction(sql, queryFunction);

    }

    /**
     * Returns a single dive for a given platform and dive number.
     * @param platform The platform name
     * @param diveNumber The dive number
     * @return The dive, or null if none found
     */
    @Override
    public Dive findByPlatformAndDiveNumber(String platform, Integer diveNumber) {
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " WHERE d.rovname = '" +
                toShortRovName(platform) + "' AND d.divenumber = " + diveNumber;
        List<Dive> dives = executeQueryFunction(sql, new LoadDiveFunction());
        return dives.size() > 0 ? dives.get(0) : null;
    }

    /**
     * Returns all dives for a given platform.
     * @param platform The platform name
     * @return A collection of dives
     */
    @Override
    public Collection<Dive> findByPlatform(String platform) {
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " WHERE d.rovname = '" +
               toShortRovName(platform) + "' ORDER BY d.divenumber";
        return executeQueryFunction(sql, queryFunction);
    }


    /**
     * Returns all dives within a given envelope.
     * @param envelope The envelope to search for dives within
     * @return A collection of dives
     */
    @Override
    public Collection<Dive> findAllWithinEnvelope(Envelope<Double> envelope) {
        String sql = "SELECT d.rovname AS RovName, d.divenumber AS DiveNumber " +
                FROM_STATEMENT + " WHERE ds.avgrovlat BETWEEN " +
                envelope.getMinimum().getY() + " AND " + envelope.getMaximum().getY() +
                " AND ds.avgrovlon BETWEEN " + envelope.getMinimum().getX() + " AND " +
                envelope.getMaximum().getX() + " ORDER BY d.divestartdtg";
        QueryFunction<List<Dive>> queryFunction = (ResultSet resultSet) -> {
            List<Dive> dives = new ArrayList<>();
            while (resultSet.next()) {
                String platform = resolveFullRovName(resultSet.getString(1));
                Integer diveNumber = resultSet.getInt(2);
                Dive dive = findByPlatformAndDiveNumber(platform, diveNumber);
                if (dive != null) {
                    dives.add(dive);
                }
            }
            return dives;
        };
        return executeQueryFunction(sql, queryFunction);
    }

    /**
     * Returns all dives in the database
     * @return A collection of dives
     */
    @Override
    public Collection<Dive> findAll() {
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " ORDER BY d.divenumber";
        return executeQueryFunction(sql, queryFunction);
    }

    /**
     * Returns a single dive for a given platform and date.
     * @param platform The platform name
     * @param date The date to search for
     * @return The dive, or null if none found
     */
    @Override
    public Dive findByPlatformAndDate(String platform, Date date) {
        String utc = DATE_FORMAT_UTC.format(date);
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " WHERE d.rovname = '" +
                toShortRovName(platform) + "' AND d.divestartdtg < '" + utc +
                "' AND d.diveenddtg > '" + utc + "'";
        List<Dive> dives = executeQueryFunction(sql, queryFunction);
        return dives.size() > 0 ? dives.get(0) : null;
    }

    /**
     * Returns all dives for a given expedition.
     * @param expedition The expedition
     * @return A collection of dives
     */
    @Override
    public Collection<Dive> findAllByExpedition(Expedition expedition) {
        Collection<String> rovNames = resolveRovByShip(expedition.getShipName());
        Collection<String> shortRovNames = rovNames.stream().map((String input) -> "'" + toShortRovName(input) + "'").collect(Collectors.toList());
        String s = String.join(", ", shortRovNames);
        String start = DATE_FORMAT_UTC.format(expedition.getStartDate());
        String end = DATE_FORMAT_UTC.format(expedition.getEndDate());
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " WHERE d.rovname in (" +
                s + ") AND d.DiveStartDtg BETWEEN '" + start +  "' AND '" + end +
                "' AND d.DiveEndDtg BETWEEN '" + start + "' AND '" + end + "'";
        return executeQueryFunction(sql, queryFunction);
    }


    /**
     * Function that parses the returns of a {@link ResultSet} into {@link Dive}
     * objects.
     */
    private static class LoadDiveFunction implements QueryFunction<List<Dive>> {

        private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

        @Override
        public List<Dive> apply(ResultSet resultSet) throws SQLException {
            List<Dive> dives = new ArrayList<>();
            while (resultSet.next()) {
                Integer diveId = resultSet.getInt(1);
                String rovName = resolveFullRovName(resultSet.getString(2));
                Integer diveNumber = resultSet.getInt(3);
                Date startDate = resultSet.getTimestamp(4, calendar);
                Date endDate = resultSet.getTimestamp(5, calendar);
                String chiefScientist = resultSet.getString(6);
                String briefAccomplishments = resultSet.getString(7);
                Double latitude = resultSet.getDouble(8);
                Double longitude = resultSet.getDouble(9);
                dives.add(new DiveImpl(diveId, rovName, diveNumber, startDate, endDate, chiefScientist,
                        briefAccomplishments, latitude, longitude));
            }
            return dives;
        }

    }

}
