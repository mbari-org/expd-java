/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.Date;

/**
 *
 * @author brian
 */
public interface DatumFactory {

    CameraDatum newCameraDatum(String platformName, Date date, String timecode,
            String alternativeTimecode, Float focus, Float zoom, Float iris);
    CtdDatum newCtdDatum(String platformName, Date date, Float temperature, Float salinity, Float oxygen,
                         Float lightTransmission);
    Dive newDive(Integer diveId, String rovName, Integer diveNumber, Date startDate, Date endDate,
                 String chiefScientist, String briefAccomplishments, Double latitude, Double longitude);
    Expedition newExpedition();
    NavigationDatum newNavigationDatum(String platformName, Float altitude, Date date, Float depth, Float pressure,
                                       Float heading, Double latitude, Double longitude, Float pitch, Boolean qcFlag, Float roll, Float shipHeading, Double shipLatitude, Double shipLongitude);
    UberDatum newUberDatum(CameraDatum cameraDatum, NavigationDatum navigationDatum, CtdDatum ctdDatum);

}
