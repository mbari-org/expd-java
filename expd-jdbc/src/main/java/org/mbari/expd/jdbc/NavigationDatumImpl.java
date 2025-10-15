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
 * The NavigationDatumImpl class provides an implementation of the NavigationDatum interface,
 * representing a data structure that encapsulates navigation information for a platform. This
 * information includes geographic coordinates, altitude, depth, pressure, headings, pitch,
 * roll, quality control flags, and timestamps. It is designed to store and manage navigational
 * data collected from a specific platform or ship.
 *
 * Instances of this class are immutable with the exception of the "edited" property, which can
 * be toggled to indicate whether the data has been modified after its initial creation.
 *
 * This class supports comparison based on the date property and provides a string representation
 * for easy description of the encapsulated data.
 *
 * Features:
 * - Implements NavigationDatum to provide concrete implementations of its methods.
 * - Supports comparison between navigation data objects using dates.
 * - Captures and handles key navigation parameters such as latitude, longitude, altitude,
 *   heading, and depth.
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

    /**
     * Constructs a new NavigationDatumImpl instance with detailed navigation data.
     *
     * @param platformName The name of the platform associated with the navigation data.
     * @param altitude The altitude value measured in meters.
     * @param date The timestamp representing when the navigation data was recorded.
     * @param depth The depth value measured in meters.
     * @param pressure The pressure value measured in decibars.
     * @param heading The heading value, typically indicating the direction in degrees.
     * @param latitude The latitude value in decimal degrees.
     * @param longitude The longitude value in decimal degrees.
     * @param pitch The pitch value representing the tilt angle of the platform.
     * @param qcFlag A flag indicating the quality control status of the data.
     * @param roll The roll value representing the rotation angle of the platform.
     * @param shipHeading The ship's heading value, typically in degrees.
     * @param shipLatitude The latitude of the ship's position in decimal degrees.
     * @param shipLongitude The longitude of the ship's position in decimal degrees.
     */
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


    /**
     *
     * @return The name of the platform associated with the navigation data.
     */
    @Override
    public String getPlatformName() {
        return platformName;
    }

    /**
     * @return The altitude value measured in meters.
     */
    @Override
    public Float getAltitude() {
        return altitude;
    }

    /**
     * @return The timestamp representing when the navigation data was recorded.
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * @return The depth value measured in meters.
     */
    @Override
    public Float getDepth() {
        return depth;
    }

    /**
     * @return The pressure value measured in decibars.
     */
    @Override
    public Float getPressure() {
        return pressure;
    }

    /**
     * @return The heading value, typically indicating the direction in degrees.
     */
    @Override
    public Float getHeading() {
        return heading;
    }

    /**
     * @return The latitude value in decimal degrees.
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude value in decimal degrees.
     */
    @Override
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @return The pitch value representing the tilt angle of the platform.
     */
    @Override
    public Float getPitch() {
        return pitch;
    }

    /**
     * @return A flag indicating the quality control status of the data.
     */
    @Override
    public Boolean getQcFlag() {
        return qcFlag;
    }

    /**
     * @return The roll value representing the rotation angle of the platform.
     */
    @Override
    public Float getRoll() {
        return roll;
    }

    /**
     * @return The ship's heading value, typically in degrees.
     */
    @Override
    public Float getShipHeading() {
        return shipHeading;
    }

    /**
     * @return The latitude of the ship's position in decimal degrees.
     */
    @Override
    public Double getShipLatitude() {
        return shipLatitude;
    }

    /**
     * @return The longitude of the ship's position in decimal degrees.
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
