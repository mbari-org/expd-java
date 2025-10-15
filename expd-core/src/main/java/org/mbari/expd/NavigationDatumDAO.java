/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author brian
 */
public interface NavigationDatumDAO {

    List<NavigationDatum> fetchRawNavigationData(Dive dive);

    boolean hasEditedNavigationData(Dive dive);


    /**
     * Fetch the best available navigation and cameradata for a dive. Navigation
     * is matched to the Camera Log times usign the closes time within +/-7 seconds
     *
     * @return A list of ROVDatums.
     */
    List<NavigationDatum> fetchEditedNavigationData(Dive dive);


    /**
     * Fetch nearest navigation data for each date provided that is within +/-
     * seconds defined by the parameter 'toleranceSec'. If no datum is found within
     * the tolerance a null is returned in for that date in the list.
     * @param dive
     * @param dates
     * @param toleranceSec
     * @return
     */
    List<NavigationDatum> fetchNavigationData(Dive dive, List<Date> dates, double toleranceSec);

    List<NavigationDatum> fetchBestNavigationData(Dive dive);




}
