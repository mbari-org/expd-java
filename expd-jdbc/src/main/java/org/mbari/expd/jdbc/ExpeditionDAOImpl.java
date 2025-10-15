/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import org.mbari.expd.Dive;
import org.mbari.expd.Expedition;
import org.mbari.expd.ExpeditionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

/**
 * The ExpeditionDAOImpl class is a concrete implementation of the ExpeditionDAO interface.
 * It provides methods to interact with Expedition data from the database, performing operations
 * such as retrieving all expeditions or finding expeditions based on specific criteria.
 * This implementation uses SQL queries for data retrieval and transformation of ResultSet
 * objects into a list of Expedition objects.
 *
 * The class extends BaseDAOImpl to inherit common database-related functionality and
 * provides additional implementations specific to Expedition entities. It contains
 * SQL query definitions and a nested QueryFunction implementation for processing
 * ResultSet data.
 */
public class ExpeditionDAOImpl extends BaseDAOImpl implements ExpeditionDAO {

    private final String SELECT_COLUMNS = "e.ExpeditionID, e.Accomplishments, e.ExpdChiefScientist," +
            " e.EndDtg, e.EquipmentDesc, e.OperatorComments, e.Participants, e.PlannedTrackDesc," +
            " e.ExpdPrincipalInvestigator, e.Purpose, e.RegionDesc, e.ScheduledEndDtg," +
            " e.ScheduledStartDtg, e.SciObjectivesMet, e.ScientistComments, e.ShipSeqNum, " +
            " e.ShipName, e.StartDtg, e.UpdatedBy";


    private final String FROM_STATEMENT = "FROM Expedition AS e";

    private final QueryFunction<List<Expedition>> queryFunction = new LoadExpdFunction();

    /**
     * Constructor
     * @param jdbcParameters The JDBC parameters used to connect to the database
     */
    public ExpeditionDAOImpl(JdbcParameters jdbcParameters) {
        super(jdbcParameters);
    }

    /**
     * Find all expeditions for a given dive
     * @param dive The dive
     * @return A collection of expeditions
     */
    @Override
    public Collection<Expedition> findByDive(Dive dive) {
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT + " WHERE e.startDtg <= '" +
                DATE_FORMAT_UTC.format(dive.getStartDate()) + "' AND e.endDtg >= '" +
                DATE_FORMAT_UTC.format(dive.getEndDate()) + "'";
        Collection<Expedition> possibleMatches = executeQueryFunction(sql, queryFunction);

        // Filter out expeditions for a different ROV
        Collection<Expedition> exactMatches = new ArrayList<>();
        Collection<String> shipNames = resolveShipByRov(dive.getRovName());
        possibleMatches.stream()
                .filter((e) -> shipNames.contains(e.getShipName()))
                .forEach(exactMatches::add);

        return exactMatches;
    }

    /**
     * Find all expeditions in the database
     * @return A collection of expeditions
     */
    @Override
    public Collection<Expedition> findAll() {
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT;
        return executeQueryFunction(sql, queryFunction);
    }

    private static class LoadExpdFunction implements QueryFunction<List<Expedition>> {

        private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

        @Override
        public List<Expedition> apply(ResultSet resultSet) throws SQLException {
            List<Expedition> expeditions = new ArrayList<>();

            while (resultSet.next()) {
                Integer expdId = resultSet.getInt(1);
                String accomplishments = resultSet.getString(2);
                String chiefScientist = resultSet.getString(3);
                Date endDate = resultSet.getTimestamp(4, calendar);
                String equipDesc = resultSet.getString(5);
                String operatorComments = resultSet.getString(6);
                String participants = resultSet.getString(7);
                String trackDesc = resultSet.getString(8);
                String pi = resultSet.getString(9);
                String purpose = resultSet.getString(10);
                String regionDesc = resultSet.getString(11);
                Date sEndDate = resultSet.getTimestamp(12, calendar);
                Date sStartDate = resultSet.getTimestamp(13, calendar);
                String objectivesMet = resultSet.getString(14);
                String sciComments = resultSet.getString(15);
                Integer shipSeqNum = resultSet.getInt(16);
                String shipName = resultSet.getString(17);
                Date startDate = resultSet.getTimestamp(18, calendar);
                String updatedBy = resultSet.getString(19);
                expeditions.add(new ExpeditionImpl(expdId, accomplishments, chiefScientist, endDate,
                        equipDesc, operatorComments, participants, trackDesc, pi, purpose,
                        regionDesc,sEndDate, sStartDate, objectivesMet, sciComments, shipSeqNum,
                        resolveFullShipName(shipName), startDate, updatedBy));

            }

            return expeditions;
        }
    }
}
