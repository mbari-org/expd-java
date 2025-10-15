/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.Date;

/**
 *
 * @author brian
 */
public interface NavigationDatum {

    String getPlatformName();
    Date getDate();
    Double getLatitude();
    Double getLongitude();
    Float getPressure();
    Float getDepth();
    Float getAltitude();
    Float getHeading();
    Float getPitch();
    Float getRoll();
    Double getShipLatitude();
    Double getShipLongitude();
    Float getShipHeading();
    Boolean getQcFlag();
    Boolean isEdited();

}
