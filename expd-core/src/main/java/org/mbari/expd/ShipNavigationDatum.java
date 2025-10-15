package org.mbari.expd;

import java.util.Date;

/**
 * @author Brian Schlining
 * @since 2012-05-29
 */
public interface ShipNavigationDatum {

    String getShip();

    Double getLatitude();

    Double getLongitude();

    Double getHeading();

    Date getDate();

}
