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
     * @param platformName The name of the platform that took the data.
     * @param date The date the sample was taken.
     * @param timecode The timecode of the video player
     * @param alternativeTimecode The alternative timecode of the video player
     * @param focus The focus level of the camera
     * @param zoom The zoom level of the camera
     * @param iris The iris level of the camera
     * @return A new instance of CameraDatum
     */
    @Override
    public CameraDatum newCameraDatum(String platformName, Date date, String timecode, String alternativeTimecode,
                                      Float focus, Float zoom, Float iris) {
        return new CameraDatumImpl(platformName, date, timecode, alternativeTimecode, focus, zoom, iris);
    }

    /**
     *
     * @param platformName The name of the platform that took the data.
     * @param date The date the sample was taken.
     * @param temperature The temperature of the water in degrees Celsius.
     * @param salinity The salinity of the water in PSU.
     * @param oxygen The concentration of oxygen in the water in ml/L.
     * @param lightTransmission The light transmission of the water in percent.
     * @return A new instance of CtdDatum
     */
    @Override
    public CtdDatum newCtdDatum(String platformName, Date date, Float temperature, Float salinity, Float oxygen,
                                Float lightTransmission) {
        return new CtdDatumImpl(platformName, date, temperature, salinity, oxygen, 1, oxygen, 1, lightTransmission,
                                null);
    }

    /**
     *
     * @param platformName The name of the platform that took the data.
     * @param date The date the sample was taken.
     * @param temperature The temperature of the water in degrees Celsius.
     * @param salinity The salinity of the water in PSU.
     * @param oxygen1 The concentration of oxygen in the water in ml/L.
     * @param oxygen1Flag The quality flag for oxygen1.
     * @param oxygen2 The concentration of oxygen in the water in ml/L.
     * @param oxygen2Flag The quality flag for oxygen2.
     * @param lightTransmission The light transmission of the water in percent.
     * @param pressure The pressure of the water in dbar.
     * @return A new instance of CtdDatum
     */
    public CtdDatum newCtdDatum(String platformName, Date date, Float temperature, Float salinity, Float oxygen1,
                                Integer oxygen1Flag, Float oxygen2, Integer oxygen2Flag, Float lightTransmission,
                                Float pressure) {
        return new CtdDatumImpl(platformName, date, temperature, salinity, oxygen1, oxygen1Flag, oxygen2, oxygen2Flag,
                                lightTransmission, pressure);
    }

    /**
     *
     * @param diveId The unique identifier for the dive.
     * @param rovName The name of the Rover that took the dive.
     * @param diveNumber The dive number.
     * @param startDate The date the dive began.
     * @param endDate The date the dive ended.
     * @param chiefScientist The name of the chief scientist.
     * @param briefAccomplishments A brief description of the accomplishments of the dive.
     * @param latitude The latitude of the dive site.
     * @param longitude The longitude of the dive site.
     * @return
     */
    public Dive newDive(Integer diveId, String rovName, Integer diveNumber, Date startDate, Date endDate,
                        String chiefScientist, String briefAccomplishments, Double latitude, Double longitude) {
        return new DiveImpl(diveId, rovName, diveNumber, startDate, endDate, chiefScientist, briefAccomplishments,
                            latitude, longitude);
    }

    /**
     * @return The new instance of Expedition
     */
    @Override
    public Expedition newExpedition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param platformName The name of the platform that took the data.
     * @param date The date the sample was taken.
     * @param depth The depth of the water in meters.
     * @param pressure The pressure of the water in dbar.
     * @param heading The heading of the ROV in degrees.
     * @param latitude The latitude of the ROV.
     * @param longitude The longitude of the ROV.
     * @param pitch The pitch of the ROV in degrees.
     * @param qcFlag The quality flag for the data.
     * @param roll The roll of the ROV in degrees.
     * @param shipHeading The heading of the ship in degrees.
     * @param shipLatitude The latitude of the ship.
     * @param shipLongitude The longitude of the ship.
     * @return A new instance of NavigationDatum
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
     * @param cameraDatum The camera datum
     * @param navigationDatum The navigation datum
     * @param ctdDatum The ctd datum
     * @return A new instance of UberDatum
     */
    @Override
    public UberDatum newUberDatum(CameraDatum cameraDatum, NavigationDatum navigationDatum, CtdDatum ctdDatum) {
        return new UberDatumImpl(cameraDatum, navigationDatum, ctdDatum);
    }
}
