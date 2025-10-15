/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.actions;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
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
public class CollateByAlternateTimecodeFunction implements CollateFunction<String> {

    private final Logger log = System.getLogger(getClass().getName());

    private Predicate<UberDatum> noNullTimecode = (ud) -> {
        CameraDatum cd = ud.getCameraDatum();
        return cd != null && cd.getAlternativeTimecode() != null;
    };

    private final Comparator<UberDatum> uberDatumComparator = (u1, u2) -> {
        return u1.getCameraDatum().getAlternativeTimecode().compareTo(u2.getCameraDatum().getAlternativeTimecode());
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
                .filter(s -> s != null)
                .sorted()
                .collect(Collectors.toList());

        // Convert Strings to timecodes then to millis
        double[] timecodesAsMillis = timecodeStrings.stream()
                .map(tc -> new Timecode(tc, FrameRates.NTSC))                         // convert to timecode object
                .mapToDouble(t -> t.getFrames() / t.getFrameRate() * 1000D) // convert to millisec
                .toArray();

        // Filter null uberdatum and sort by timecode
        List<UberDatum> uberData = uberDataList.stream()
                .filter(u -> u != null)
                .filter(noNullTimecode)
                .sorted(uberDatumComparator)
                .collect(Collectors.toList());

        double[] uberDatumAsMillis = uberData.stream()
                .map(u -> new Timecode(u.getCameraDatum().getAlternativeTimecode(), FrameRates.NTSC))
                .mapToDouble(t -> t.getFrames() / t.getFrameRate() * 1000D)
                .toArray();

        int[] idx = NearestNeighbor.apply(uberDatumAsMillis, timecodesAsMillis, offsetMillisec);
        for (int i = 0; i < idx.length; i++) {
            UberDatum ud = idx[i] == -1 ? null : uberData.get(idx[i]);
            if (ud == null && log.isLoggable(Logger.Level.DEBUG)) {
                log.log(Logger.Level.DEBUG, "No EXPD Data was found for {}", timecodeStrings.get(i));
            }
            data.put(timecodeStrings.get(i), ud);
        }

        return data;
    }

}
