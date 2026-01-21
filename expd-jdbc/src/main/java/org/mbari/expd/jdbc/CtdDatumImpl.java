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

    /**
     * Constructs ...
     * @param platformName The name of the platform that recorded this data.
     * @param date The date the data was recorded.
     * @param temperature The temperature of the water in degrees Celsius.
     * @param salinity The salinity of the water in PSU.
     * @param oxygen1 The concentration of oxygen in the water in ml/L.
     * @param oxygen1Flag The quality flag for oxygen1.
     * @param oxygen2 The concentration of oxygen in the water in ml/L.
     * @param oxygen2Flag The quality flag for oxygen2.
     * @param lightTransmission The light transmission of the water in percent.
     * @param pressure The pressure of the water in dbar.
     */
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

    /**
     * Returns the pressure of the water in dbar.
     * @return the pressure
     */
    @Override
    public Float getPressure() {
        return pressure;
    }

    /**
     * Returns the date the data was recorded.
     * @return the date
     */
    @Override
    public Date getDate() {
        return date;
    }

    /**
     * Returns the light transmission of the water in percent.
     * @return the lightTransmission
     */
    @Override
    public Float getLightTransmission() {
        return lightTransmission;
    }

    /**
     * Returns the most reliable oxygen concentration using the o2 measurement with the highest quality control flag.
     * @return the oxygen in ml/L
     */
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

    /**
     * Returns the concentration of oxygen in the water in ml/L.
     * @return the oxygen1
     */
    @Override
    public Float getOxygen1() {
        return oxygen1;
    }

    /**
     * Returns the quality flag for oxygen1.
     * @return the oxygen1Flag
     */
    @Override
    public Integer getOxygen1Flag() {
        return oxygen1Flag;
    }

    /**
     * Returns the concentration of oxygen in the water in ml/L.
     * @return the oxygen2
     */
    @Override
    public Float getOxygen2() {
        return oxygen2;
    }

    /**
     * Returns the quality flag for oxygen2.
     * @return the oxygen2Flag
     */
    @Override
    public Integer getOxygen2Flag() {
        return oxygen2Flag;
    }

    /**
     * Returns the name of the platform that recorded this data.
     * @return the platformName
     */
    @Override
    public String getPlatformName() {
        return platformName;
    }

    /**
     * Returns the salinity of the water in PSU.
     * @return the salinity
     */
    @Override
    public Float getSalinity() {
        return salinity;
    }

    /**
     * Returns the temperature of the water in degrees Celsius.
     * @return the temperature
     */
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
