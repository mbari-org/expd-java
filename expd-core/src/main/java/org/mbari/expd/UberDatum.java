/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

/**
 *
 * @author brian
 */
public interface UberDatum {

    CameraDatum getCameraDatum();
    void setCameraDatum(CameraDatum cameraDatum);

    NavigationDatum getNavigationDatum();
    void setNavigationDatum(NavigationDatum navigationDatum);

    CtdDatum getCtdDatum();
    void setCtdDatum(CtdDatum ctdDatum);

}
