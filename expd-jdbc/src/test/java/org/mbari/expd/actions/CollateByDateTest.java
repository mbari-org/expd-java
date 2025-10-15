package org.mbari.expd.actions;


import org.junit.Test;
import org.mbari.expd.NavigationDatum;
import org.mbari.expd.UberDatum;
import org.mbari.expd.jdbc.NavigationDatumImpl;
import org.mbari.expd.jdbc.UberDatumImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Brian Schlining
 * @since 2015-06-09T17:00:00
 */
public class CollateByDateTest {

    @Test
    public void test01() {
        List<UberDatum> uberData = genUberDatum(5);
        List<Date> dates = List
                .of(new Date(10000000L), new Date(16000000L), new Date(1000000000L));

        CollateFunction<Date> cf = new CollateByDateFunction();

        Map<Date, UberDatum> data = cf.apply(dates, uberData, 10000000L);

        assertEquals(uberData.get(1), data.get(dates.get(0)));
        assertEquals(uberData.get(2), data.get(dates.get(1)));
        assertNull(data.get(dates.get(2)));

    }

    private List<UberDatum> genUberDatum(int size) {
        List<UberDatum> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float j = i;
            NavigationDatum nd = new NavigationDatumImpl(i + "", j, new Date(i * 10000000L),
                    j * 10F, j * 10F, 0F, 0D, 0D, 0F, true, 0F, 0F, 0D, 0D);
            UberDatum ud = new UberDatumImpl(null, nd, null);
            data.add(ud);
        }
        return data;
    }
}
