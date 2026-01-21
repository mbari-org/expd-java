/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

import java.util.*;
import java.util.stream.Collectors;

import org.mbari.expd.CameraDatum;
import org.mbari.expd.UberDatum;
import org.mbari.expd.math.FrameRates;
import org.mbari.expd.math.NearestNeighbor;
import org.mbari.expd.math.Timecode;

import java.lang.System.Logger;

/**
 *
 * @author brian
 */
public class CollateByTimecodeFunction implements CollateFunction<String> {

    private final Logger log = System.getLogger(getClass().getName());

    private java.util.function.Predicate<UberDatum> noNullTimecode = (ud) -> {
        CameraDatum cd = ud.getCameraDatum();
        return cd != null && cd.getTimecode() != null;
    };

    private final Comparator<UberDatum> uberDatumComparator = (u1, u2) -> {
        return u1.getCameraDatum().getTimecode().compareTo(u2.getCameraDatum().getTimecode());
    };

    /**
     *
     * @param timecodeList
     * @param uberDataList
     * @param offsetMillisec
     * @return
     */
    @Override
    public Map<String, UberDatum> apply(Collection<String> timecodeList, Collection<UberDatum> uberDataList,
                                        long offsetMillisec) {

        Map<String, UberDatum> data = new LinkedHashMap<>();

        // Filter out nulls and sort timecodes
        List<String> timecodeStrings = timecodeList.stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        // Convert Strings to timecodes then to millis
        double[] timecodesAsMillis = timecodeStrings.stream()
                .map(tc -> new Timecode(tc, FrameRates.NTSC))                         // convert to timecode object
                .mapToDouble(t -> t.getFrames() / t.getFrameRate() * 1000D) // convert to millisec
                .toArray();

        // Filter null uberdatum and sort by timecode
        List<UberDatum> uberData = uberDataList.stream()
                .filter(Objects::nonNull)
                .filter(noNullTimecode)
                .sorted(uberDatumComparator)
                .collect(Collectors.toList());

        double[] uberDatumAsMillis = uberData.stream()
                .map(u -> new Timecode(u.getCameraDatum().getTimecode()))
                .mapToDouble(t -> t.getFrames() / t.getFrameRate() * 1000D)
                .toArray();

        int[] idx = NearestNeighbor.apply(uberDatumAsMillis, timecodesAsMillis, offsetMillisec);
        for (int i = 0; i < idx.length; i++) {
            UberDatum ud = idx[i] == -1 ? null : uberData.get(idx[i]);
            if (ud == null && log.isLoggable(Logger.Level.DEBUG)) {
                log.log(Logger.Level.DEBUG,"No EXPD Data was found for {}", timecodeStrings.get(i));
            }
            data.put(timecodeStrings.get(i), ud);
        }

        return data;
    }
}
