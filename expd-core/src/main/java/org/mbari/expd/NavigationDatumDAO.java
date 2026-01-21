/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

/*-
 * #%L
 * org.mbari.expd:expd-core
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
