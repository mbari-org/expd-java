/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.Date;
import java.util.List;

/**
 *
 * @author brian
 */
public interface CtdDatumDAO {

    List<CtdDatum> fetchCtdData(Dive dive);

    List<CtdDatum> fetchCtdData(Dive dive, List<Date> dates, double toleranceSec);

}
