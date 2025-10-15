/*
 * @(#)DatumFactoryImpl.java   2012.02.01 at 04:32:48 PST
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

import org.mbari.expd.CameraDatum;
import org.mbari.expd.CtdDatum;
import org.mbari.expd.DatumFactory;
import org.mbari.expd.Dive;
import org.mbari.expd.Expedition;
import org.mbari.expd.NavigationDatum;
import org.mbari.expd.UberDatum;

import java.util.Date;

/**
 * Implementation of the DatumFactory interface. Provides methods
 * to create instances of various datum types including CameraDatum,
 * CtdDatum, NavigationDatum, Dive, and UberDatum.
 */
public class DatumFactoryImpl implements DatumFactory {

    /**
     *
     * @param platformName
     * @param date
     * @param timecode
     * @param alternativeTimecode
     * @param focus
     * @param zoom
     * @param iris
     * @return
     */
    @Override
    public CameraDatum newCameraDatum(String platformName, Date date, String timecode, String alternativeTimecode,
                                      Float focus, Float zoom, Float iris) {
        return new CameraDatumImpl(platformName, date, timecode, alternativeTimecode, focus, zoom, iris);
    }

    /**
     *
     * @param platformName
     * @param date
     * @param temperature
     * @param salinity
     * @param oxygen
     * @param lightTransmission
     * @return
     */
    @Override
    public CtdDatum newCtdDatum(String platformName, Date date, Float temperature, Float salinity, Float oxygen,
                                Float lightTransmission) {
        return new CtdDatumImpl(platformName, date, temperature, salinity, oxygen, 1, oxygen, 1, lightTransmission,
                                null);
    }

    /**
     *
     * @param platformName
     * @param date
     * @param temperature
     * @param salinity
     * @param oxygen1
     * @param oxygen1Flag
     * @param oxygen2
     * @param oxygen2Flag
     * @param lightTransmission
     * @param pressure
     * @return
     */
    public CtdDatum newCtdDatum(String platformName, Date date, Float temperature, Float salinity, Float oxygen1,
                                Integer oxygen1Flag, Float oxygen2, Integer oxygen2Flag, Float lightTransmission,
                                Float pressure) {
        return new CtdDatumImpl(platformName, date, temperature, salinity, oxygen1, oxygen1Flag, oxygen2, oxygen2Flag,
                                lightTransmission, pressure);
    }

    /**
     *
     * @param diveId
     * @param rovName
     * @param diveNumber
     * @param startDate
     * @param endDate
     * @param chiefScientist
     * @param briefAccomplishments
     * @param latitude
     * @param longitude
     * @return
     */
    public Dive newDive(Integer diveId, String rovName, Integer diveNumber, Date startDate, Date endDate,
                        String chiefScientist, String briefAccomplishments, Double latitude, Double longitude) {
        return new DiveImpl(diveId, rovName, diveNumber, startDate, endDate, chiefScientist, briefAccomplishments,
                            latitude, longitude);
    }

    /**
     * @return
     */
    @Override
    public Expedition newExpedition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param platformName
     * @param altitude
     * @param date
     * @param depth
     * @param pressure
     * @param heading
     * @param latitude
     * @param longitude
     * @param pitch
     * @param qcFlag
     * @param roll
     * @param shipHeading
     * @param shipLatitude
     * @param shipLongitude
     * @return
     */
    @Override
    public NavigationDatum newNavigationDatum(String platformName, Float altitude, Date date, Float depth,
            Float pressure, Float heading, Double latitude, Double longitude, Float pitch, Boolean qcFlag, Float roll,
            Float shipHeading, Double shipLatitude, Double shipLongitude) {
        return new NavigationDatumImpl(platformName, altitude, date, depth, pressure, heading, latitude, longitude,
                                       pitch, qcFlag, roll, shipHeading, shipLatitude, shipLongitude);
    }

    /**
     *
     * @param cameraDatum
     * @param navigationDatum
     * @param ctdDatum
     * @return
     */
    @Override
    public UberDatum newUberDatum(CameraDatum cameraDatum, NavigationDatum navigationDatum, CtdDatum ctdDatum) {
        return new UberDatumImpl(cameraDatum, navigationDatum, ctdDatum);
    }
}
