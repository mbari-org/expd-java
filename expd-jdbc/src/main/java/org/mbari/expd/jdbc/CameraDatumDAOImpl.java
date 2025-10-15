/*
 * @(#)CameraDatumDAOImpl.java   2009.12.29 at 09:02:35 PST
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

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.mbari.expd.CameraDatum;
import org.mbari.expd.CameraDatumDAO;
import org.mbari.expd.Dive;
import org.mbari.expd.math.Interp;
import org.mbari.expd.math.NearestNeighbor;
import org.mbari.expd.math.Timecode;
import org.mbari.expd.model.VideoTime;
import org.mbari.expd.model.VideoTimeBean;


/**
 *
 * @author brian
 */
public class CameraDatumDAOImpl extends BaseDAOImpl implements CameraDatumDAO {

    private static final Logger log = System.getLogger(CameraDatumDAOImpl.class.getName());

    /**  */
    public static final String SELECT_COLUMNS = " DateTimeGMT, betaTimecode, hdTimecode, mainFocus, mainZoom, mainIris ";

    /**  */
    public static final String SELECT_COLUMNS_DOC_RICKETTS = " DateTimeGMT, betaTimecode, hdTimecode, focusVolts, zoomVolts, irisVolts ";

    public CameraDatumDAOImpl(JdbcParameters params) {
        super(params);
    }

    /**
     * Fetch all the camera data for a particular dive
     * @param dive
     * @param isHD if true records with a <b>NULL</b> value in the hdTimecode
     *  column will be discarded. If false records with a <b>NULL</b> value in the
     *  betaTimecode column will be discarded
     * @return A {@link List} of {@link CameraDatum} objects containing
     *  cameraData for the dive.
     */
    @Override
    public List<CameraDatum> fetchCameraData(Dive dive, Boolean isHD) {
        String table = resolveRovTablePrefix(dive.getRovName()) + "CamlogData";
        String tc = isHD ? "hdTimecode" : "betaTimecode";
        String columns = table.startsWith("Doc") ? SELECT_COLUMNS_DOC_RICKETTS : SELECT_COLUMNS;
        String sql = "SELECT " + columns + " FROM " + table + " WHERE DateTimeGMT BETWEEN '" +
                     DATE_FORMAT_UTC.format(dive.getStartDate()) + "' AND '" +
                     DATE_FORMAT_UTC.format(dive.getEndDate()) + "' AND " + tc + " IS NOT NULL ORDER BY DateTimeGMT";
        return executeQueryFunction(sql, new LoadDataFunction(dive.getRovName()));
    }

    public List<CameraDatum> findAllNearDate(String platform, Date date, int millisecTolerance) {
        Date startDate = new Date(date.getTime() - millisecTolerance);
        Date endDate = new Date(date.getTime() + millisecTolerance);
        return findAllBetweenDates(platform, startDate, endDate);
    }

     /**
     * Fetch ctdData for a dive. Use nearest neighbor interpolation to return the
     * record nearest to each date in a list within the specified tolerance
     * @param dive
     * @param dates
     * @param toleranceSec
     * @return
     */
    @Override
    public List<CameraDatum> fetchCameraData(Dive dive, List<Date> dates, double toleranceSec,  Boolean isHD) {
        List<CameraDatum> cameraData = fetchCameraData(dive, isHD);

        if (!cameraData.isEmpty()) {
            double[] cameraTimes = cameraData.stream()
                    .mapToDouble(d -> d.getDate().getTime() / 1000D)
                    .toArray();

            double[] times = dates.stream()
                    .mapToDouble(d -> d.getTime() / 1000D)
                    .toArray();


            int[] idx = NearestNeighbor.apply(cameraTimes, times, toleranceSec);

            return NearestNeighbor.collate(cameraData, idx);
        }
        else {
            return cameraData;
        }


    }

    /**
     * Retrieve all CameraDatum's for the platform between the supplied dates.
     * @param platform
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<CameraDatum> findAllBetweenDates(String platform, Date startDate, Date endDate) {
        String table = resolveRovTablePrefix(platform) + "CamlogData";
        String columns = table.startsWith("Doc") ? SELECT_COLUMNS_DOC_RICKETTS : SELECT_COLUMNS;
        String sql = "SELECT " + columns + " FROM " + table + " WHERE DateTimeGMT BETWEEN '" +
                     DATE_FORMAT_UTC.format(startDate) + "' AND '" +
                     DATE_FORMAT_UTC.format(endDate) + "' ORDER BY DateTimeGMT";
        return executeQueryFunction(sql, new LoadDataFunction(platform));
    }

    /**
     * Interpolates a given date to a timecode from data stored in the
     * datastore.
     *
     * @param cameraIdentifier The cameraIdentifier (e.g. Ventana or Tiburon)
     * @param date The date of that we're interested in
     * @param millisecTolerance Specifies the widthInMeters of the time window to pull samples
     *  from the database
     * @param frameRate The Frame rate to use. At MBARI, we use NTSC (29.97 fps)
     * @return The interpolated tape time value. <b>null</b> is returned if no
     *      data was retrieved to interpolate to.
     */
    @Override
    public VideoTime interpolateTimecodeToDate(String cameraIdentifier, Date date, int millisecTolerance, double frameRate) {

        /*
         * Retrive the tapetimes and sort by date
         */
        List<CameraDatum> cameraData = findAllNearDate(cameraIdentifier, date, millisecTolerance);

        VideoTime returnTapeTime = null;
        if (cameraData.size() > 1) {
            Collections.sort(cameraData, (CameraDatum o1, CameraDatum o2) -> o1.getDate().compareTo(o2.getDate()));

            /*
             * Extract the dates and timecodes (as frames) for interpolation
             */
            BigDecimal[] dates = new BigDecimal[cameraData.size()];
            BigDecimal[] frames = new BigDecimal[cameraData.size()];
            for (int i = 0; i < cameraData.size(); i++) {
                CameraDatum cameraDatum = cameraData.get(i);
                dates[i] = new BigDecimal(cameraDatum.getDate().getTime());
                Timecode timecode = new Timecode(cameraDatum.getTimecode(), frameRate);
                frames[i] = new BigDecimal(timecode.getFrames());
            }

            /*
             * Interpolate to the new frame
             */
            BigDecimal[] iFrame = Interp.interpolate(dates, frames, new BigDecimal[]{new BigDecimal(date.getTime())});
            if (iFrame != null && iFrame.length > 0 && iFrame[0] != null) {
                Timecode timecode = new Timecode(iFrame[0].doubleValue(), frameRate);
                returnTapeTime = new VideoTimeBean(date, timecode.toString());
                if (log.isLoggable(Level.DEBUG)) {
                    log.log(Level.DEBUG, "Interpolated timecode at " + date + " is " + timecode +
                        " using frameRate of " + frameRate + "[fps]");
                }
            }
        }
        return returnTapeTime;

    }


    /**
     * Helper function that knows how to generate a {@link List} of {@link CameraDatum}
     * objects from a {@link ResultSet}
     */
    private class LoadDataFunction implements QueryFunction<List<CameraDatum>> {

        private final Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        private final String platformName;

        /**
         * Constructs ...
         *
         * @param platformName
         */
        public LoadDataFunction(String platformName) {
            this.platformName = platformName;
        }

        /**
         *
         * @param resultSet
         * @return
         *
         * @throws SQLException
         */
        @Override
        public List<CameraDatum> apply(ResultSet resultSet) throws SQLException {

            List<CameraDatum> data = new ArrayList<>();
            while (resultSet.next()) {

                Date date = resultSet.getTimestamp(1, calendar);
                String timecode = resultSet.getString(2);
                String alternativeTimecode = resultSet.getString(3);
                Float focus = resultSet.getFloat(4);
                Float zoom = resultSet.getFloat(5);
                Float iris = resultSet.getFloat(6);

                data.add(new CameraDatumImpl(platformName, date, timecode, alternativeTimecode, focus, zoom, iris));

            }

            return data;

        }
    }
}
