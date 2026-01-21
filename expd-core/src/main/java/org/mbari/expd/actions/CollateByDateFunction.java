/*
 * @(#)CoallateByDateFunction.java   2010.01.13 at 11:51:41 PST
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



package org.mbari.expd.actions;

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

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.mbari.expd.NavigationDatum;
import org.mbari.expd.UberDatum;
import org.mbari.expd.math.NearestNeighbor;
import java.lang.System.Logger;

/**
 *
 *
 * @version        Enter version here..., 2010.01.13 at 11:51:41 PST
 * @author         Brian Schlining [brian@mbari.org]
 */
public class CollateByDateFunction implements CollateFunction<Date> {


    private final Logger log = System.getLogger(getClass().getName());

    /**
     *
     * @param dateList
     * @param uberDataList
     * @param offsetMillisec
     * @return
     */
    @Override
    public Map<Date, UberDatum> apply(Collection<Date> dateList, Collection<UberDatum> uberDataList,
                                      long offsetMillisec) {

        Map<Date, UberDatum> data = new LinkedHashMap<>();

        List<Date> dates = dateList.stream()
                .filter(d -> d != null)
                .sorted()
                .collect(Collectors.toList());

        long[] epochMillis = dates.stream()
                .mapToLong(Date::getTime)
                .toArray();

        List<UberDatum> uberData = uberDataList.stream()
                .filter(u -> u != null)
                .filter(u -> u.getNavigationDatum() != null)
                .filter(u -> u.getNavigationDatum().getDate() != null)
                .sorted((u1, u2) -> u1.getNavigationDatum().getDate().compareTo(u2.getNavigationDatum().getDate()))
                .collect(Collectors.toList());

        // If nav data is missing use the camera dates. If cameradates are missing
        // we can't merge
        long[] uberDatumAsMillis;
        if (uberData.isEmpty()) {
            uberData = uberDataList.stream()
                    .filter(u -> u != null)
                    .filter(u -> u.getCameraDatum() != null)
                    .filter(u -> u.getCameraDatum().getDate() != null)
                    .collect(Collectors.toList());
            uberDatumAsMillis = uberData.stream()
                    .mapToLong(u -> u.getCameraDatum().getDate().getTime())
                    .toArray();
        }
        else {
            uberDatumAsMillis = uberData.stream()
                    .mapToLong(u -> u.getNavigationDatum().getDate().getTime())
                    .toArray();
        }


        if (!uberData.isEmpty()) {
            int[] idx = NearestNeighbor.apply(uberDatumAsMillis, epochMillis, offsetMillisec);
            for (int i = 0; i < idx.length; i++) {
                UberDatum ud = idx[i] == -1 ? null : uberData.get(idx[i]);
                if (ud == null && log.isLoggable(Logger.Level.DEBUG)) {
                    log.log(Logger.Level.DEBUG, "No EXPD Data was found for {}", dates.get(i));
                }
                data.put(dates.get(i), ud);
            }
        }
        else {
            log.log(Logger.Level.INFO, "No Navigation or Camera data was found to merge collate with");
        }

        return data;
    }

}
