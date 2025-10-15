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
public interface CtdDatum {

    Date getDate();
    String getPlatformName();
    Float getTemperature();
    Float getSalinity();
    Float getOxygen();
    Float getOxygen1();
    Integer getOxygen1Flag();
    Float getOxygen2();
    Integer getOxygen2Flag();
    Float getLightTransmission();
    Float getPressure();

}
