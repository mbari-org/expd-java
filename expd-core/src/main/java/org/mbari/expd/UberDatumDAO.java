/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

import java.util.List;

/**
 *
 * @author brian
 */
public interface UberDatumDAO {

    List<UberDatum> fetchData(Dive dive, boolean isHD, double toleranceSec);

    /**
     * Fetch data (but without cameradata) only nav and ctd, camera is null. All CTD for dive is fetched,
     * but with if no navigation within +/- tolerance is found the navigation will be null.
     */
    public List<UberDatum> fetchCtdData(Dive dive, double toleranceSec);

}
