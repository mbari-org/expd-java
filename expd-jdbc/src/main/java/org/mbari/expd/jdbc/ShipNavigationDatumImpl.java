/*
 * @(#)ShipNavigationDatumImpl.java   2012.05.29 at 02:06:52 PDT
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

import org.mbari.expd.ShipNavigationDatum;

import java.util.Date;

/**
 * The ShipNavigationDatumImpl class implements the ShipNavigationDatum interface
 * and represents navigation data specific to a ship. This includes information such as the
 * ship's name, its geographical coordinates, heading, and the specific date of the navigation data.
 *
 * Instances of this class can be used to encapsulate and retrieve critical navigation-related
 * properties for a given ship.
 *
 * This class supports equality and hashCode computation based on the properties of the
 * ship's name and the date.
 */
public class ShipNavigationDatumImpl implements ShipNavigationDatum {

    private String ship;
    private Date date;
    private Double heading;
    private Double latitude;
    private Double longitude;

    /**
     * Constructs ...
     */
    public ShipNavigationDatumImpl() {}

    /**
     * Constructs ...
     *
     * @param date The date of the navigation data.
     * @param heading The heading of the ship.
     * @param latitude The latitude of the ship.
     * @param longitude The longitude of the ship.
     * @param ship The name of the ship.
     */
    public ShipNavigationDatumImpl(Date date, Double heading, Double latitude, Double longitude,
                                   String ship) {
        this.date = date;
        this.heading = heading;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ship = ship;
    }

    /**
     * @return The date of the navigation data.
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * @return The heading of the ship.
     */
    @Override
    public Double getHeading() {
        return heading;
    }

    /**
     * @return The latitude of the ship.
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return The longitude of the ship.
     */
    @Override
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @return The name of the ship.
     */
    @Override
    public String getShip() {
        return ship;
    }

    @Override
    public int hashCode() {
        return (ship + " " + date).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }
}
