package org.mbari.expd.actions;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mbari.expd.CameraDatum;
import org.mbari.expd.UberDatum;
import org.mbari.expd.jdbc.CameraDatumImpl;
import org.mbari.expd.jdbc.UberDatumImpl;
import org.mbari.expd.math.FrameRates;
import org.mbari.expd.math.Timecode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Brian Schlining
 * @since 2015-06-09T15:22:00
 */
public class CollateByAlternativeTimecodeTest {

    @Test
    public void test01() {
        List<UberDatum> uberData = genUberDatum(5);
        List<String> timecodes = List
                .of(new Timecode(45D, FrameRates.NTSC),
                        new Timecode(50D, FrameRates.NTSC),
                        new Timecode(450D, FrameRates.NTSC))
                .stream()
                .map(Timecode::toString)
                .collect(Collectors.toList());
        CollateFunction<String> cf = new CollateByAlternateTimecodeFunction();

        Map<String, UberDatum> data = cf.apply(timecodes, uberData, 1000);

        assertEquals(uberData.get(1), data.get(timecodes.get(0)));
        assertEquals(uberData.get(1), data.get(timecodes.get(1)));
        assertNull(data.get(timecodes.get(2)));


    }

    private List<UberDatum> genUberDatum(int size) {
        List<UberDatum> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float j = i;
            Timecode tc = new Timecode(i * 45D, FrameRates.NTSC);
            CameraDatum cd = new CameraDatumImpl(i + "", new Date(),
                    null, tc.toString(), j , j * 2, j * 3);
            UberDatum ud = new UberDatumImpl(cd, null, null);
            data.add(ud);
        }
        return data;
    }




}
