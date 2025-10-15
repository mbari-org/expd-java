/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd;

/**
 *
 * @author brian
 */
public interface DAOFactory {

    CameraDatumDAO newCameraDatumDAO();
    CtdDatumDAO newCtdDatumDAO();
    DiveDAO newDiveDAO();
    ExpeditionDAO newExpeditionDAO();
    NavigationDatumDAO newNavigationDatumDAO();
    UberDatumDAO newUberDatumDAO();

}
