package org.mbari.expd;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Schlining
 * @since 2012-05-29
 */
public interface ShipNavigationDatumDAO {

    public List<ShipNavigationDatum> fetchBetweenDates(String ship, Date start, Date end);
}
