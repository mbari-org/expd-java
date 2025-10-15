package org.mbari.expd.jdbc;

import org.mbari.expd.AnnotationSummary;
import org.mbari.expd.AnnotationSummaryDAO;
import org.mbari.expd.Dive;

import java.util.*;

public class AnnotationSummaryDAOImpl extends BaseDAOImpl implements AnnotationSummaryDAO {

    private static final String SELECT_COLUMNS = "a.DiveNumber, a.RovName, a.Camera, a.Notes, a.Mission, " +
            "a.Application, a.Annotators, a.DateAnnotated, a.AnnotationFileName, " +
            "a.RTFileEdited AS RealTimeFileEdited, a.Style, a.HoursAnnotated, a.NumSDTapes, " +
            "a.NumHDTapes, a.NumFileSegments, a.HoursOfVideo";
    private static final String FROM_STATEMENT = "FROM TapeSummary a";
    private static final Calendar CALENDAR = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    private static final QueryFunction<List<AnnotationSummary>> queryFunction = resultSet -> {

        List<AnnotationSummary> xs = new ArrayList<>();
        while (resultSet.next()) {
            Integer diveNumber = resultSet.getInt(1);
            String rovName = resolveFullRovName(resultSet.getString(2));
            String camera = resultSet.getString(3);
            String notes = resultSet.getString(4);
            String mission = resultSet.getString(5);
            String application = resultSet.getString(6);
            String annotators = resultSet.getString(7);
            Date dateAnnotated = resultSet.getDate(8, CALENDAR);
            String annotationFileName = resultSet.getString(9);
            String isRTFileEdited = resultSet.getString(10); // TOOD convert to Boolean
            Boolean realTimeEdited = !(isRTFileEdited == null || isRTFileEdited.toLowerCase().startsWith("f"));
            String style = resultSet.getString(11);
            Double hoursAnnotated = resultSet.getDouble(12);
            Integer nSDTape = resultSet.getInt(13);
            Integer nHDTape = resultSet.getInt(14);
            Integer nFileSeg = resultSet.getInt(15);
            Double hoursOfVideo = resultSet.getDouble(16);
            xs.add(new AnnotationSummaryImpl(diveNumber, rovName, camera, notes, mission, application,
                    annotators, dateAnnotated, annotationFileName, realTimeEdited, style,
                    hoursAnnotated, nSDTape, nHDTape, nFileSeg, hoursOfVideo));
        }
        return xs;
    };

    public AnnotationSummaryDAOImpl(JdbcParameters jdbcParameters) {
        super(jdbcParameters);
    }

    @Override
    public AnnotationSummary findByDive(Dive dive) {
        return findByDive(dive.getRovName(), dive.getDiveNumber());
    }

    @Override
    public AnnotationSummary findByDive(String platform, Integer diveNumber) {
        String sql = "SELECT " + SELECT_COLUMNS + " " + FROM_STATEMENT +
                " WHERE a.RovName = '" + toShortRovName(platform) +
                "'   AND a.DiveNumber = " + diveNumber;
        List<AnnotationSummary> xs = executeQueryFunction(sql, queryFunction);
        return xs.size() > 0 ? xs.get(0) : null;
    }
}
