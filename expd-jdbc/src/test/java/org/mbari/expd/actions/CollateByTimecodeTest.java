package org.mbari.expd.actions;

import org.junit.Ignore;
import org.junit.Test;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Brian Schlining
 * @since 2015-06-09T16:38:00
 */
public class CollateByTimecodeTest {

    @Ignore
    @Test
    public void test01() {
        List<UberDatum> uberData = genUberDatum(5);
        List<String> timecodes = List
                .of(new Timecode(45D, FrameRates.NTSC), new Timecode(50D, FrameRates.NTSC),
                        new Timecode(450D, FrameRates.NTSC))
                .stream().map(Timecode::toString).collect(Collectors.toList());
        CollateFunction<String> cf = new CollateByTimecodeFunction();

        Map<String, UberDatum> data = cf.apply(timecodes, uberData, 1000);

        var a = uberData.get(1).getCameraDatum().getTimecode();
        var b = data.get(timecodes.get(0));

        assertEquals(uberData.get(1).getCameraDatum().getTimecode(), data.get(timecodes.get(0)));
        assertEquals(uberData.get(1).getCameraDatum().getTimecode(), data.get(timecodes.get(1)));
        assertNull(data.get(timecodes.get(2)));

    }

    private List<UberDatum> genUberDatum(int size) {
        List<UberDatum> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float j = i;
            Timecode tc = new Timecode(i * 45D, FrameRates.NTSC);
            CameraDatum cd = new CameraDatumImpl(i + "", new Date(), tc.toString(), null, j, j * 2, j * 3);
            UberDatum ud = new UberDatumImpl(cd, null, null);
            data.add(ud);
        }
        return data;
    }

}
