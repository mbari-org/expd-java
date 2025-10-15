/*
 * @(#)NavigationDatumImpl.java   2009.12.29 at 09:34:49 PST
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

import java.util.Date;
import org.mbari.expd.NavigationDatum;

/**
 *
 * @author brian
 */
public class NavigationDatumImpl implements NavigationDatum, Comparable<NavigationDatum> {

    private final String platformName;
    private final Float altitude;
    private final Date date;
    private final Float depth;
    private final Float pressure;
    private final Float heading;
    private final Double latitude;
    private final Double longitude;
    private final Float pitch;
    private final Boolean qcFlag;
    private final Float roll;
    private final Float shipHeading;
    private final Double shipLatitude;
    private final Double shipLongitude;
    private Boolean edited = false;

    public NavigationDatumImpl(String platformName, Float altitude, Date date, Float depth, Float pressure, Float heading, Double latitude, Double longitude, Float pitch, Boolean qcFlag, Float roll, Float shipHeading, Double shipLatitude, Double shipLongitude) {
        this.platformName = platformName;
        this.altitude = altitude;
        this.date = date;
        this.depth = depth;
        this.pressure = pressure;
        this.heading = heading;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pitch = pitch;
        this.qcFlag = qcFlag;
        this.roll = roll;
        this.shipHeading = shipHeading;
        this.shipLatitude = shipLatitude;
        this.shipLongitude = shipLongitude;
    }


    @Override
    public String getPlatformName() {
        return platformName;
    }

    /**
     * @return
     */
    @Override
    public Float getAltitude() {
        return altitude;
    }

    /**
     * @return
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * @return
     */
    @Override
    public Float getDepth() {
        return depth;
    }

    /**
     * @return
     */
    @Override
    public Float getPressure() {
        return pressure;
    }

    /**
     * @return
     */
    @Override
    public Float getHeading() {
        return heading;
    }

    /**
     * @return
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return
     */
    @Override
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @return
     */
    @Override
    public Float getPitch() {
        return pitch;
    }

    /**
     * @return
     */
    @Override
    public Boolean getQcFlag() {
        return qcFlag;
    }

    /**
     * @return
     */
    @Override
    public Float getRoll() {
        return roll;
    }

    /**
     * @return
     */
    @Override
    public Float getShipHeading() {
        return shipHeading;
    }

    /**
     * @return
     */
    @Override
    public Double getShipLatitude() {
        return shipLatitude;
    }

    /**
     * @return
     */
    @Override
    public Double getShipLongitude() {
        return shipLongitude;
    }

    @Override
    public int compareTo(NavigationDatum o) {
        return date.compareTo(o.getDate());
    }

    @Override
    public Boolean isEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(platformName = " + platformName + ", date = " +
                date + ", latitude = " + latitude + ", longitude = " + longitude +
                ", depth = " + depth + ")";
    }


}
