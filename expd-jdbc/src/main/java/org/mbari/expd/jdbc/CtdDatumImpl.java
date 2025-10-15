/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import java.util.Date;
import org.mbari.expd.CtdDatum;

/**
 * Implementation of the {@link CtdDatum} interface that represents a single CTD (Conductivity, Temperature, Depth)
 * data record. It encapsulates various oceanographic measurements and metadata for a specific time and location.
 *
 * This class provides access to values such as temperature, salinity, oxygen concentrations (with quality flags),
 * light transmission, and pressure, as well as metadata like the platform name and the date the data was recorded.
 * It also includes logic to determine the most reliable oxygen measurement based on associated quality flags.
 */
public class CtdDatumImpl implements CtdDatum {

    private final String platformName;
    private final Date date;
    private final Float temperature;
    private final Float salinity;
    private Float oxygen;
    private final Float oxygen1;
    private final Integer oxygen1Flag;
    private final Float oxygen2;
    private final Integer oxygen2Flag;
    private final Float lightTransmission;
    private final Float pressure;

    public CtdDatumImpl(String platformName, Date date, Float temperature, Float salinity,
                        Float oxygen1, Integer oxygen1Flag, Float oxygen2, Integer oxygen2Flag,
                        Float lightTransmission, Float pressure) {
        this.platformName = platformName;
        this.date = date;
        this.temperature = temperature;
        this.salinity = salinity;
        this.oxygen1 = oxygen1;
        this.oxygen1Flag = oxygen1Flag;
        this.oxygen2 = oxygen2;
        this.oxygen2Flag = oxygen2Flag;
        this.lightTransmission = lightTransmission;
        this.pressure = pressure;
    }

    @Override
    public Float getPressure() {
        return pressure;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Float getLightTransmission() {
        return lightTransmission;
    }

    @Override
    public Float getOxygen() {
        if (oxygen == null) {
            // We use whichever oxygen has better quality control, giving preference to the Seabird (oxygen1)
            oxygen = (oxygen1Flag >= oxygen2Flag) ? oxygen1 : oxygen2;
            if (oxygen1Flag == 0 && oxygen2Flag == 0) { // 0 == BAD!!
                oxygen = null;
            }
        }
        return oxygen;
    }

    @Override
    public Float getOxygen1() {
        return oxygen1;
    }

    @Override
    public Integer getOxygen1Flag() {
        return oxygen1Flag;
    }

    @Override
    public Float getOxygen2() {
        return oxygen2;
    }

    @Override
    public Integer getOxygen2Flag() {
        return oxygen2Flag;
    }

    @Override
    public String getPlatformName() {
        return platformName;
    }

    @Override
    public Float getSalinity() {
        return salinity;
    }

    @Override
    public Float getTemperature() {
        return temperature;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CtdDatumImpl other = (CtdDatumImpl) obj;
        if ((this.platformName == null) ? (other.platformName != null) : !this.platformName.equals(other.platformName)) {
            return false;
        }
        if (this.date != other.date && (this.date == null || !this.date.equals(other.date))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.platformName != null ? this.platformName.hashCode() : 0);
        hash = 97 * hash + (this.date != null ? this.date.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(platformName = " + platformName + ", " +
                "date = " + date + ")";
    }

}
